package net.lopymine.mossylib.gui.screen;

import java.util.*;
import net.lopymine.mossylib.utils.ScreenUtils;
import net.lopymine.mossylib.yacl.custom.MossyScreen;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.Nullable;

public class MossyChoiceScreen extends Screen implements MossyScreen {

	@Nullable
	private final Screen parent;
	private final Component message;
	private final List<List<Choice>> rows;

	private List<FormattedCharSequence> lines = List.of();
	private int titleY;

	private MossyChoiceScreen(@Nullable Screen parent, Component title, Component message, List<List<Choice>> rows) {
		super(title);
		this.parent = parent;
		this.message = message;
		this.rows = rows;
	}

	public static Builder builder(@Nullable Screen parent, Component title, Component message) {
		return new Builder(parent, title, message);
	}

	public static Choice choice(Component text, Runnable action) {
		return new Choice(text, action);
	}

	@Override
	protected void init() {
		int buttonWidth = 150;
		int buttonHeight = 20;
		int offset = 4;
		int textOffset = 20;
		int sidePadding = 40;
		int lineStep = this.font.lineHeight + 1;

		int textWidth = Math.min(this.width - sidePadding, buttonWidth * 2 + offset);
		this.lines = this.font.split(this.message, textWidth);

		int textHeight = (this.lines.size() + 2) * lineStep;
		int buttonsHeight = this.rows.size() * buttonHeight + Math.max(0, this.rows.size() - 1) * offset;
		this.titleY = (this.height - textHeight - textOffset - buttonsHeight) / 2;

		int y = this.titleY + textHeight + textOffset;
		for (List<Choice> row : this.rows) {
			int rowWidth = row.size() * buttonWidth + (row.size() - 1) * offset;
			int x = (this.width - rowWidth) / 2;
			for (Choice choice : row) {
				this.addRenderableWidget(Button.builder(choice.text(), (button) -> this.choose(choice))
						.bounds(x, y, buttonWidth, buttonHeight)
						.build());
				x += buttonWidth + offset;
			}
			y += buttonHeight + offset;
		}
	}

	private void choose(Choice choice) {
		this.onClose();
		choice.action().run();
	}

	private void renderText(GuiGraphicsExtractor context) {
		int lineStep = this.font.lineHeight + 1;
		int centerX = this.width / 2;
		this.drawCenteredLine(context, this.title.getVisualOrderText(), centerX, this.titleY);

		int y = this.titleY + lineStep * 2;
		for (FormattedCharSequence line : this.lines) {
			this.drawCenteredLine(context, line, centerX, y);
			y += lineStep;
		}
	}

	private void drawCenteredLine(GuiGraphicsExtractor context, FormattedCharSequence line, int x, int y) {
		//? if >=26.1 {
		context.centeredText(this.font, line, x, y, -1);
		//?} else {
		/*context.drawCenteredString(this.font, line, x, y, -1);
		*///?}
	}

	//? if >=26.1 {
	@Override
	public void extractRenderState(GuiGraphicsExtractor context, int mouseX, int mouseY, float deltaTicks) {
		super.extractRenderState(context, mouseX, mouseY, deltaTicks);
		this.renderText(context);
	}
	//?} elif >=1.21.1 {
	/*@Override
	public void render(GuiGraphicsExtractor context, int mouseX, int mouseY, float deltaTicks) {
		super.render(context, mouseX, mouseY, deltaTicks);
		this.renderText(context);
	}
	*///?} else {
	/*@Override
	public void render(GuiGraphicsExtractor context, int mouseX, int mouseY, float deltaTicks) {
		this.renderBackground(context);
		super.render(context, mouseX, mouseY, deltaTicks);
		this.renderText(context);
	}
	*///?}

	@Override
	public void onClose() {
		ScreenUtils.set(this.parent);
	}

	public record Choice(Component text, Runnable action) {

	}

	public static class Builder {

		@Nullable
		private final Screen parent;
		private final Component title;
		private final Component message;
		private final List<List<Choice>> rows = new ArrayList<>();

		private Builder(@Nullable Screen parent, Component title, Component message) {
			this.parent = parent;
			this.title = title;
			this.message = message;
		}

		public Builder row(Choice... choices) {
			this.rows.add(List.of(choices));
			return this;
		}

		public MossyChoiceScreen build() {
			return new MossyChoiceScreen(this.parent, this.title, this.message, List.copyOf(this.rows));
		}
	}
}
