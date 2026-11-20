package net.lopymine.mossylib.announcement;

import java.util.*;
import net.lopymine.mossylib.MossyLib;
import net.lopymine.mossylib.config.MossyLibConfig;
import net.lopymine.mossylib.gui.*;
import net.lopymine.mossylib.yacl.custom.MossyScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.*;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.Nullable;

public class AnnouncementScreen extends Screen implements MossyScreen {

	private final List<Announcement> announcements;
	private final Runnable onContinue;
	private final long unlockDelay;
	private final Map<Announcement, Boolean> selectedOptions = new HashMap<>();
	private final Map<Announcement, Checkbox> checkboxes = new LinkedHashMap<>();

	private int panelX;
	private int panelY;
	private int panelWidth;
	private int panelHeight;

	private List<FormattedCharSequence> titleLines = List.of();
	private int titleY;

	private final List<MessageLine> messageLines = new ArrayList<>();
	private final List<ParagraphBar> paragraphBars = new ArrayList<>();
	private int messageX;
	private int barX;
	private int barWidth;

	@Nullable
	private Button continueButton;
	@Nullable
	private Boolean lockedTooltipShown;

	private boolean mouseCaptured;
	private int startMouseX;
	private int startMouseY;
	private long unlockStartTime = -1L;

	public AnnouncementScreen(List<Announcement> announcements, Runnable onContinue) {
		super(MossyLib.text("announcement.title"));
		this.announcements = List.copyOf(announcements);
		this.onContinue = onContinue;
		this.unlockDelay = this.getUnlockDelay();
		for (Announcement announcement : this.announcements) {
			AnnouncementOption option = announcement.getOption();
			if (option != null) {
				this.selectedOptions.put(announcement, option.defaultValue());
			}
		}
	}

	@Override
	protected void init() {
		this.checkboxes.forEach((announcement, checkbox) -> this.selectedOptions.put(announcement, checkbox.selected()));
		this.checkboxes.clear();

		float gapDivider = 4.5F;
		int padding = 10;
		int offset = 6;
		int checkboxOffset = 2;
		int buttonWidth = 150;
		int buttonHeight = 20;
		int lineStep = this.font.lineHeight + 1;

		int gapX = (int) (this.width / gapDivider);
		int gapY = (int) (this.height / gapDivider);
		this.panelX = gapX;
		this.panelY = gapY;
		this.panelWidth = this.width - gapX * 2;
		this.panelHeight = this.height - gapY * 2;

		int contentX = this.panelX + padding;
		int contentWidth = this.panelWidth - padding * 2;

		int buttonY = this.panelY + this.panelHeight - padding - buttonHeight;
		this.continueButton = this.addRenderableWidget(Button.builder(this.getContinueText(), (button) -> this.onContinue())
				.bounds((this.width - buttonWidth) / 2, buttonY, buttonWidth, buttonHeight)
				.build());
		this.lockedTooltipShown = null;
		this.updateContinueButton();

		int controlsTop = buttonY;
		int y = buttonY - offset + checkboxOffset;
		List<Announcement> reversed = new ArrayList<>(this.announcements);
		Collections.reverse(reversed);
		for (Announcement announcement : reversed) {
			AnnouncementOption option = announcement.getOption();
			if (option == null) {
				continue;
			}
			boolean selected = this.selectedOptions.getOrDefault(announcement, option.defaultValue());
			Component optionText = this.getOptionText(announcement, option);
			Checkbox checkbox = this.addRenderableWidget(this.createCheckbox(optionText, selected, contentWidth));
			y -= checkbox.getHeight() + checkboxOffset;
			checkbox.setX((this.width - this.getCheckboxVisualWidth(checkbox, optionText, contentWidth)) / 2);
			checkbox.setY(y);
			this.checkboxes.put(announcement, checkbox);
			controlsTop = y;
		}

		this.titleY = this.panelY + padding;
		this.titleLines = this.font.split(this.getTitleText(), contentWidth);

		int messageTop = this.titleY + this.titleLines.size() * lineStep + offset;
		int messageBottom = controlsTop - offset;
		this.layoutMessage(contentX, contentWidth, messageTop, messageBottom, lineStep);
	}

	private void layoutMessage(int contentX, int contentWidth, int top, int bottom, int lineStep) {
		this.messageLines.clear();
		this.paragraphBars.clear();

		int barGap = 4;
		int barExtension = 2;
		int paragraphGap = barExtension * 2;
		this.barWidth = 2;
		this.barX = contentX;
		this.messageX = contentX + this.barWidth + barGap;
		int textWidth = contentWidth - this.barWidth - barGap;

		List<MessageEntry> entries = new ArrayList<>();
		int paragraphIndex = 0;
		for (FormattedText paragraph : this.splitParagraphs(this.announcements.get(0).getMessage())) {
			if (paragraph.getString().isBlank()) {
				entries.add(new MessageEntry(FormattedText.EMPTY, -1));
				continue;
			}
			for (FormattedText line : this.font.getSplitter().splitLines(paragraph, textWidth, Style.EMPTY)) {
				entries.add(new MessageEntry(line, paragraphIndex));
			}
			paragraphIndex++;
		}

		int[] offsets = new int[entries.size()];
		int offsetY = 0;
		for (int i = 1; i < entries.size(); i++) {
			int previous = entries.get(i - 1).paragraph();
			int current = entries.get(i).paragraph();
			offsetY += lineStep;
			if (previous != -1 && current != -1 && previous != current) {
				offsetY += paragraphGap;
			}
			offsets[i] = offsetY;
		}

		int areaTop = top + barExtension;
		int areaHeight = bottom - top - barExtension * 2;
		int visible = 0;
		while (visible < entries.size() && offsets[visible] + this.font.lineHeight <= areaHeight) {
			visible++;
		}
		boolean truncated = visible < entries.size();
		if (truncated) {
			while (visible > 0 && entries.get(visible - 1).paragraph() == -1) {
				visible--;
			}
		}
		if (visible == 0) {
			return;
		}

		int blockHeight = offsets[visible - 1] + this.font.lineHeight;
		int startY = areaTop + (areaHeight - blockHeight) / 2;

		int currentParagraph = -1;
		int paragraphTop = 0;
		int lastY = 0;
		for (int i = 0; i < visible; i++) {
			MessageEntry entry = entries.get(i);
			int y = startY + offsets[i];
			FormattedText text = truncated && i == visible - 1 ? this.withEllipsis(entry.text(), textWidth) : entry.text();
			this.messageLines.add(new MessageLine(Language.getInstance().getVisualOrder(text), y));

			if (entry.paragraph() != currentParagraph) {
				this.addParagraphBar(currentParagraph, paragraphTop, lastY, barExtension);
				currentParagraph = entry.paragraph();
				paragraphTop = y;
			}
			lastY = y;
		}
		this.addParagraphBar(currentParagraph, paragraphTop, lastY, barExtension);
	}

	private void addParagraphBar(int paragraph, int firstLineY, int lastLineY, int extension) {
		if (paragraph == -1) {
			return;
		}
		this.paragraphBars.add(new ParagraphBar(firstLineY - 1 - extension, lastLineY + this.font.lineHeight - 1 + extension));
	}

	private FormattedText withEllipsis(FormattedText line, int width) {
		String ellipsis = "...";
		FormattedText cut = this.font.substrByWidth(line, width - this.font.width(ellipsis));
		return FormattedText.composite(cut, FormattedText.of(ellipsis));
	}

	private List<FormattedText> splitParagraphs(Component message) {
		List<FormattedText> paragraphs = new ArrayList<>();
		List<FormattedText> parts = new ArrayList<>();
		message.visit((style, string) -> {
			String[] split = string.split("\n", -1);
			for (int i = 0; i < split.length; i++) {
				if (i > 0) {
					paragraphs.add(FormattedText.composite(List.copyOf(parts)));
					parts.clear();
				}
				if (!split[i].isEmpty()) {
					parts.add(FormattedText.of(split[i], style));
				}
			}
			return Optional.empty();
		}, Style.EMPTY);
		paragraphs.add(FormattedText.composite(List.copyOf(parts)));
		return paragraphs;
	}

	private Checkbox createCheckbox(Component text, boolean selected, int maxWidth) {
		//? if >=1.21.1 {
		int spacing = 4;
		return Checkbox.builder(text, this.font)
				.selected(selected)
				.maxWidth(maxWidth - Checkbox.getBoxSize(this.font) - spacing)
				.build();
		//?} else {
		/*int size = 20;
		int textOffset = 4;
		int textWidth = maxWidth - size - textOffset;
		Component fittedText = text;
		if (this.font.width(text) > textWidth) {
			String ellipsis = "...";
			fittedText = Component.literal(this.font.substrByWidth(text, textWidth - this.font.width(ellipsis)).getString() + ellipsis);
		}
		return new Checkbox(0, 0, size + textOffset + this.font.width(fittedText), size, fittedText, selected);
		*///?}
	}

	private int getCheckboxVisualWidth(Checkbox checkbox, Component text, int maxWidth) {
		//? if >=1.21.1 {
		int spacing = 4;
		return Math.min(Checkbox.getBoxSize(this.font) + spacing + this.font.width(text), maxWidth);
		//?} else {
		/*return checkbox.getWidth();
		*///?}
	}

	private Component getOptionText(Announcement announcement, AnnouncementOption option) {
		if (this.announcements.size() == 1) {
			return option.text();
		}
		return MossyLib.text("announcement.option.mod", announcement.getModName(), option.text());
	}

	private Component getTitleText() {
		MutableComponent text = Component.empty();
		Set<String> mods = new HashSet<>();
		for (Announcement announcement : this.announcements) {
			if (!mods.add(announcement.getModId())) {
				continue;
			}
			if (mods.size() > 1) {
				text.append("\n");
			}
			Component name = Component.literal(announcement.getModName()).withStyle(ChatFormatting.BOLD);
			Component version = Component.literal("v" + announcement.getModVersion()).withStyle(ChatFormatting.BOLD);
			text.append(MossyLib.text("announcement.header", name, version));
		}
		return text;
	}

	private Component getContinueText() {
		if (this.isUnlocked()) {
			return MossyLib.text("announcement.continue");
		}
		return MossyLib.text("announcement.continue.countdown", (this.getRemainingMillis() + 999L) / 1000L);
	}

	private long getUnlockDelay() {
		MossyLibConfig config = MossyLibConfig.getInstance();
		if (config.isDevelopmentActive() && config.getDevConfig().isUnlockInstantly()) {
			return 0L;
		}
		int seconds = 0;
		for (Announcement announcement : this.announcements) {
			seconds = Math.max(seconds, announcement.getUnlockDelay());
		}
		return seconds * 1000L;
	}

	private long getRemainingMillis() {
		if (this.unlockDelay == 0L) {
			return 0L;
		}
		if (this.unlockStartTime == -1L) {
			return this.unlockDelay;
		}
		return Math.max(0L, this.unlockStartTime + this.unlockDelay - System.currentTimeMillis());
	}

	private boolean isUnlocked() {
		return this.getRemainingMillis() == 0L;
	}

	private void updateUnlockStartTime(int mouseX, int mouseY) {
		if (this.unlockStartTime != -1L) {
			return;
		}
		if (!this.mouseCaptured) {
			this.mouseCaptured = true;
			this.startMouseX = mouseX;
			this.startMouseY = mouseY;
			return;
		}
		if (mouseX != this.startMouseX || mouseY != this.startMouseY) {
			this.unlockStartTime = System.currentTimeMillis();
		}
	}

	private void updateContinueButton() {
		if (this.continueButton == null) {
			return;
		}
		boolean unlocked = this.isUnlocked();
		this.continueButton.active = unlocked;
		this.continueButton.setMessage(this.getContinueText());

		if (this.lockedTooltipShown != null && this.lockedTooltipShown == !unlocked) {
			return;
		}
		this.lockedTooltipShown = !unlocked;
		Component tooltip = this.announcements.get(0).getLockedTooltip();
		this.continueButton.setTooltip(!unlocked && tooltip != null ? Tooltip.create(tooltip) : null);
	}

	private void onContinue() {
		if (!this.isUnlocked()) {
			return;
		}
		this.checkboxes.forEach((announcement, checkbox) -> {
			AnnouncementOption option = announcement.getOption();
			if (option != null) {
				option.onContinue().accept(checkbox.selected());
			}
		});
		this.onContinue.run();
	}

	private void tickState(int mouseX, int mouseY) {
		this.updateUnlockStartTime(mouseX, mouseY);
		this.updateContinueButton();
	}

	private void renderPanel(GuiGraphicsExtractor context) {
		BackgroundRenderer.drawTransparencyBackground(
				context,
				this.panelX,
				this.panelY,
				this.panelWidth,
				this.panelHeight,
				TransparencySprites.DARKER_MENU_BACKGROUND_V2_TEXTURE,
				TransparencySprites.getMenuSeparatorTexture(),
				true,
				true,
				true,
				true
		);
	}

	private void renderText(GuiGraphicsExtractor context) {
		int lineStep = this.font.lineHeight + 1;
		int y = this.titleY;
		for (FormattedCharSequence line : this.titleLines) {
			//? if >=26.1 {
			context.centeredText(this.font, line, this.width / 2, y, -1);
			//?} else {
			/*context.drawCenteredString(this.font, line, this.width / 2, y, -1);
			*///?}
			y += lineStep;
		}

		int barColor = 0xFFFF55FF;
		for (ParagraphBar bar : this.paragraphBars) {
			context.fill(this.barX, bar.top(), this.barX + this.barWidth, bar.bottom(), barColor);
		}

		for (MessageLine line : this.messageLines) {
			//? if >=26.1 {
			context.text(this.font, line.text(), this.messageX, line.y(), -1);
			//?} else {
			/*context.drawString(this.font, line.text(), this.messageX, line.y(), -1);
			*///?}
		}
	}

	//? if >=26.1 {
	@Override
	public void extractRenderState(GuiGraphicsExtractor context, int mouseX, int mouseY, float deltaTicks) {
		this.tickState(mouseX, mouseY);
		super.extractRenderState(context, mouseX, mouseY, deltaTicks);
		this.renderText(context);
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor context, int mouseX, int mouseY, float deltaTicks) {
		super.extractBackground(context, mouseX, mouseY, deltaTicks);
		this.renderPanel(context);
	}
	//?} elif >=1.21.1 {
	/*@Override
	public void render(GuiGraphicsExtractor context, int mouseX, int mouseY, float deltaTicks) {
		this.tickState(mouseX, mouseY);
		super.render(context, mouseX, mouseY, deltaTicks);
		this.renderText(context);
	}

	@Override
	public void renderBackground(GuiGraphicsExtractor context, int mouseX, int mouseY, float deltaTicks) {
		super.renderBackground(context, mouseX, mouseY, deltaTicks);
		this.renderPanel(context);
	}
	*///?} else {
	/*@Override
	public void render(GuiGraphicsExtractor context, int mouseX, int mouseY, float deltaTicks) {
		this.tickState(mouseX, mouseY);
		this.renderBackground(context);
		super.render(context, mouseX, mouseY, deltaTicks);
		this.renderText(context);
	}

	@Override
	public void renderBackground(GuiGraphicsExtractor context) {
		super.renderBackground(context);
		this.renderPanel(context);
	}
	*///?}

	@Override
	public boolean shouldCloseOnEsc() {
		return false;
	}

	private record MessageEntry(FormattedText text, int paragraph) {

	}

	private record MessageLine(FormattedCharSequence text, int y) {

	}

	private record ParagraphBar(int top, int bottom) {

	}
}
