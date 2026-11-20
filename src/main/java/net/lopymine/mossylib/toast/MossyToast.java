package net.lopymine.mossylib.toast;

import java.util.List;
import lombok.Getter;
import net.lopymine.mossylib.utils.DrawUtils;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.Nullable;

public class MossyToast {

	private final Identifier background;
	private final List<FormattedCharSequence> titleLines;
	private final List<FormattedCharSequence> messageLines;
	@Getter
	private final int width;
	@Getter
	private final int height;

	public MossyToast(Font font, Component title, @Nullable Component message, Identifier background) {
		int maxTextWidth = 200;
		int minWidth = 160;
		int padding = 7;
		int lineStep = font.lineHeight + 2;

		this.background = background;
		this.titleLines = font.split(title, maxTextWidth);
		this.messageLines = message == null ? List.of() : font.split(message, maxTextWidth);

		int textWidth = 0;
		for (FormattedCharSequence line : this.titleLines) {
			textWidth = Math.max(textWidth, font.width(line));
		}
		for (FormattedCharSequence line : this.messageLines) {
			textWidth = Math.max(textWidth, font.width(line));
		}
		this.width = Math.max(minWidth, padding + textWidth + padding);

		int lines = this.titleLines.size() + this.messageLines.size();
		this.height = padding + (lines - 1) * lineStep + font.lineHeight + padding;
	}

	public void render(GuiGraphicsExtractor context, Font font) {
		//? if >=1.21 {
		DrawUtils.drawGuiTexture(context, this.background, 0, 0, this.width, this.height);
		//?} else {
		/*int textureWidth = 160;
		int textureHeight = 64;
		int border = 4;
		DrawUtils.drawGuiTexture(context, this.background, 0, 0, this.width, this.height, textureWidth, textureHeight, border);
		*///?}

		int padding = 7;
		int lineStep = font.lineHeight + 2;
		int titleColor = 0xFFFFFF00;
		int messageColor = 0xFFFFFFFF;

		int y = padding;
		for (FormattedCharSequence line : this.titleLines) {
			this.drawLine(context, font, line, padding, y, titleColor);
			y += lineStep;
		}
		for (FormattedCharSequence line : this.messageLines) {
			this.drawLine(context, font, line, padding, y, messageColor);
			y += lineStep;
		}
	}

	private void drawLine(GuiGraphicsExtractor context, Font font, FormattedCharSequence line, int x, int y, int color) {
		//? if >=26.1 {
		context.text(font, line, x, y, color, false);
		//?} else {
		/*context.drawString(font, line, x, y, color, false);
		*///?}
	}
}
