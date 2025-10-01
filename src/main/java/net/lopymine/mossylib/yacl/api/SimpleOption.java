package net.lopymine.mossylib.yacl.api;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.gui.YACLScreen;
import dev.isxander.yacl3.gui.image.ImageRenderer;
import lombok.Getter;
import lombok.experimental.ExtensionMethod;

import net.lopymine.mossylib.utils.ModMenuUtils;
import net.lopymine.mossylib.yacl.extension.YACLAPIExtension;

import java.util.List;
import java.util.function.*;

@SuppressWarnings("unused")
@ExtensionMethod(YACLAPIExtension.class)
public class SimpleOption {

	public static <T> Builder<T> startBuilder(String optionId) {
		return new Builder<>(optionId);
	}

	public static ButtonBuilder startButtonBuilder(String optionId, BiConsumer<YACLScreen, ButtonOption> biConsumer) {
		return new ButtonBuilder(optionId, biConsumer);
	}

	public static <T> ListOptionBuilder<T> startListBuilder(String optionId) {
		return new ListOptionBuilder<>(optionId);
	}

	public static class Builder<T> implements SimpleOptionBuilder<T> {

		@Getter
		private final Option.Builder<T> optionBuilder;
		private final String optionId;

		private Consumer<Option.Builder<T>> custom = (__) -> {};

		private SimpleContent descriptionContent;
		private ImageRenderer customDescriptionRenderer;

		public Builder(String optionId) {
			this.optionId      = optionId;
			this.optionBuilder = Option.createBuilder();
		}

		public Builder<T> withCustomDescription(ImageRenderer renderer) {
			this.customDescriptionRenderer = renderer;
			return this;
		}

		public Builder<T> withDescription(SimpleContent content) {
			this.descriptionContent = content;
			return this;
		}

		public Builder<T> custom(Consumer<Option.Builder<T>> content) {
			this.custom = this.custom.andThen(content);
			return this;
		}

		public Builder<T> withBinding(Binding<T> binding, boolean instant) {
			this.optionBuilder.bindingE(binding, instant);
			return this;
		}

		public Builder<T> withBinding(T def, Supplier<T> getter, Consumer<T> setter, boolean instant) {
			this.optionBuilder.bindingE(Binding.generic(def, getter, setter), instant);
			return this;
		}

		@Override
		public Option<T> build(String modId) {
			String optionKey = ModMenuUtils.getOptionKey(this.optionId);
			OptionDescription.Builder builder = OptionDescription.createBuilder().text(ModMenuUtils.getDescription(modId, optionKey));
			if (this.customDescriptionRenderer == null) {
				if (this.descriptionContent == SimpleContent.IMAGE) {
					builder.image(ModMenuUtils.getContentId(modId, this.descriptionContent, this.optionId), 500, 500);
				}
				if (this.descriptionContent == SimpleContent.WEBP) {
					builder.webpImage(ModMenuUtils.getContentId(modId, this.descriptionContent, this.optionId));
				}
			} else {
				builder.customImage(this.customDescriptionRenderer);
			}
			this.optionBuilder.description(builder.build());
			this.optionBuilder.name(ModMenuUtils.getName(modId, optionKey));
			return this.optionBuilder.build();
		}
	}

	public static class ButtonBuilder implements SimpleOptionBuilder<BiConsumer<YACLScreen, ButtonOption>> {

		@Getter
		private final ButtonOption.Builder optionBuilder;
		private final String optionId;

		private Consumer<ButtonOption.Builder> custom = (__) -> {};

		private SimpleContent descriptionContent;
		private ImageRenderer customDescriptionRenderer;

		public ButtonBuilder(String optionId, BiConsumer<YACLScreen, ButtonOption> biConsumer) {
			this.optionId = optionId;
			this.optionBuilder = ButtonOption.createBuilder().action(biConsumer);
		}

		public ButtonBuilder withCustomDescription(ImageRenderer renderer) {
			this.customDescriptionRenderer = renderer;
			return this;
		}

		public ButtonBuilder withDescription(SimpleContent content) {
			this.descriptionContent = content;
			return this;
		}

		public ButtonBuilder custom(Consumer<ButtonOption.Builder> content) {
			this.custom = this.custom.andThen(content);
			return this;
		}

		@Override
		public ButtonOption build(String modId) {
			String optionKey = ModMenuUtils.getOptionKey(this.optionId);
			OptionDescription.Builder builder = OptionDescription.createBuilder().text(ModMenuUtils.getDescription(modId, optionKey));
			if (this.customDescriptionRenderer == null) {
				if (this.descriptionContent == SimpleContent.IMAGE) {
					builder.image(ModMenuUtils.getContentId(modId, this.descriptionContent, this.optionId), 500, 500);
				}
				if (this.descriptionContent == SimpleContent.WEBP) {
					builder.webpImage(ModMenuUtils.getContentId(modId, this.descriptionContent, this.optionId));
				}
			} else {
				builder.customImage(this.customDescriptionRenderer);
			}
			this.optionBuilder.description(builder.build());
			this.optionBuilder.name(ModMenuUtils.getName(modId, optionKey));
			return this.optionBuilder.build();
		}
	}

	public static class ListOptionBuilder<T> implements SimpleOptionBuilder<List<T>> {

		@Getter
		private final ListOption.Builder<T> optionBuilder;
		private final String optionId;

		private Consumer<ListOption.Builder<T>> custom = (__) -> {};

		private SimpleContent descriptionContent;
		private ImageRenderer customDescriptionRenderer;

		public ListOptionBuilder(String optionId) {
			this.optionId = optionId;
			this.optionBuilder = ListOption.createBuilder();
		}

		public ListOptionBuilder<T> withCustomDescription(ImageRenderer renderer) {
			this.customDescriptionRenderer = renderer;
			return this;
		}

		public ListOptionBuilder<T> withDescription(SimpleContent content) {
			this.descriptionContent = content;
			return this;
		}

		public ListOptionBuilder<T> withBinding(Binding<List<T>> binding, boolean instant) {
			this.optionBuilder.bindingE(binding, instant);
			return this;
		}

		public ListOptionBuilder<T> withBinding(List<T> def, Supplier<List<T>> getter, Consumer<List<T>> setter, boolean instant) {
			this.optionBuilder.bindingE(Binding.generic(def, getter, setter), instant);
			return this;
		}

		public ListOptionBuilder<T> custom(Consumer<ListOption.Builder<T>> content) {
			this.custom = this.custom.andThen(content);
			return this;
		}

		@Override
		public Option<List<T>> build(String modId) {
			String optionKey = ModMenuUtils.getOptionKey(this.optionId);
			OptionDescription.Builder builder = OptionDescription.createBuilder().text(ModMenuUtils.getDescription(modId, optionKey));
			if (this.customDescriptionRenderer == null) {
				if (this.descriptionContent == SimpleContent.IMAGE) {
					builder.image(ModMenuUtils.getContentId(modId, this.descriptionContent, this.optionId), 500, 500);
				}
				if (this.descriptionContent == SimpleContent.WEBP) {
					builder.webpImage(ModMenuUtils.getContentId(modId, this.descriptionContent, this.optionId));
				}
			} else {
				builder.customImage(this.customDescriptionRenderer);
			}
			this.optionBuilder.description(builder.build());
			this.optionBuilder.name(ModMenuUtils.getName(modId, optionKey));
			return this.optionBuilder.build();
		}
	}

	public interface SimpleOptionBuilder<T> {

		Option<T> build(String modId);

	}
}
