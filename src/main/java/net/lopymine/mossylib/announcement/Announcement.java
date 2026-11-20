package net.lopymine.mossylib.announcement;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import lombok.Getter;
import net.lopymine.mossylib.MossyLib;
import net.lopymine.mossylib.loader.MossyLoader;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

@Getter
public class Announcement {

	private final String modId;
	private final String id;
	private final Component message;
	private final BooleanSupplier condition;
	private final int unlockDelay;
	@Nullable
	private final Component lockedTooltip;
	@Nullable
	private final AnnouncementOption option;
	@Nullable
	private final AnnouncementGroup group;

	private Announcement(String modId, String id, Component message, BooleanSupplier condition, int unlockDelay, @Nullable Component lockedTooltip, @Nullable AnnouncementOption option, @Nullable AnnouncementGroup group) {
		this.modId = modId;
		this.id = id;
		this.message = message;
		this.condition = condition;
		this.unlockDelay = unlockDelay;
		this.lockedTooltip = lockedTooltip;
		this.option = option;
		this.group = group;
	}

	public static Builder builder(String modId, String id) {
		return new Builder(modId, id);
	}

	public String getKey() {
		return this.modId + ":" + this.id;
	}

	public String getModName() {
		return MossyLoader.getModName(this.modId);
	}

	public String getModVersion() {
		String version = MossyLoader.getModVersion(this.modId);
		int index = version.indexOf('+');
		return index == -1 ? version : version.substring(0, index);
	}

	public static class Builder {

		private final String modId;
		private final String id;
		@Nullable
		private Component message;
		private BooleanSupplier condition = () -> true;
		private int unlockDelay;
		@Nullable
		private Component lockedTooltip = MossyLib.text("announcement.continue.locked");
		@Nullable
		private Component optionText;
		private boolean optionDefaultValue;
		@Nullable
		private BooleanConsumer onContinue;
		@Nullable
		private AnnouncementGroup group;

		private Builder(String modId, String id) {
			this.modId = modId;
			this.id = id;
		}

		public Builder message(Component message) {
			this.message = message;
			return this;
		}

		public Builder condition(BooleanSupplier condition) {
			this.condition = condition;
			return this;
		}

		public Builder unlockDelay(int seconds) {
			this.unlockDelay = Math.max(0, seconds);
			return this;
		}

		public Builder lockedTooltip(@Nullable Component tooltip) {
			this.lockedTooltip = tooltip;
			return this;
		}

		public Builder option(Component text, boolean defaultValue, BooleanConsumer onContinue) {
			this.optionText = text;
			return this.option(defaultValue, onContinue);
		}

		public Builder option(boolean defaultValue, BooleanConsumer onContinue) {
			this.optionDefaultValue = defaultValue;
			this.onContinue = onContinue;
			return this;
		}

		public Builder group(AnnouncementGroup group) {
			this.group = group;
			return this;
		}

		public Announcement build() {
			Component message = this.message != null ? this.message : this.group != null ? this.group.message() : null;
			Objects.requireNonNull(message, "Announcement \"%s:%s\" has no message".formatted(this.modId, this.id));

			AnnouncementOption option = null;
			if (this.onContinue != null) {
				Component text = this.optionText != null ? this.optionText : this.group != null ? this.group.optionText() : null;
				Objects.requireNonNull(text, "Announcement \"%s:%s\" has an option without text".formatted(this.modId, this.id));
				option = new AnnouncementOption(text, this.optionDefaultValue, this.onContinue);
			}

			return new Announcement(this.modId, this.id, message, this.condition, this.unlockDelay, this.lockedTooltip, option, this.group);
		}
	}
}
