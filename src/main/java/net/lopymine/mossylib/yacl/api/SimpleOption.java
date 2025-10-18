package net.lopymine.mossylib.yacl.api;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.config.v2.api.FieldAccess;
import dev.isxander.yacl3.config.v2.impl.*;
import dev.isxander.yacl3.gui.YACLScreen;
import dev.isxander.yacl3.gui.image.ImageRenderer;
import java.lang.reflect.Field;
import lombok.Getter;
import lombok.experimental.ExtensionMethod;

import net.lopymine.mossylib.utils.ModMenuUtils;

import java.util.List;
import java.util.function.*;
import net.lopymine.mossylib.yacl.extension.SimpleOptionExtension;
import net.lopymine.mossylib.yacl.state.PreviewStateManager;

@SuppressWarnings("unused")
public class SimpleOption {

	public static <T> Builder<T> startFieldBuilder(String optionId, Object defConfig, Object config) {
		return startFieldBuilder(optionId, defConfig, config, false);
	}

	public static <T> Builder<T> startFieldBuilder(String optionId, Object defConfig, Object config, boolean instant) {
		Builder<T> builder = new Builder<>(optionId);
		try {
			Field configField = config.getClass().getDeclaredField(optionId);
			Field defConfigField = defConfig.getClass().getDeclaredField(optionId);

			Supplier<T> getter = () -> {
				try {
					configField.setAccessible(true);
					@SuppressWarnings("unchecked")
					T value = (T) configField.get(config);
					configField.setAccessible(false);
					return value;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			};

			Consumer<T> setter = (value) -> {
				try {
					configField.setAccessible(true);
					configField.set(config, value);
					configField.setAccessible(false);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			};

			defConfigField.setAccessible(true);
			@SuppressWarnings("unchecked")
			T defValue = (T) defConfigField.get(defConfig);
			defConfigField.setAccessible(false);

			builder.withBinding(Binding.generic(defValue, getter, setter), instant);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return builder;
	}

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
			this.optionBuilder.stateManager(instant ? new PreviewStateManager<>(binding) : StateManager.createSimple(binding));
			return this;
		}

		public Builder<T> withBinding(T def, Supplier<T> getter, Consumer<T> setter, boolean instant) {
			Binding<T> binding = Binding.generic(def, getter, setter);
			this.optionBuilder.stateManager(instant ? new PreviewStateManager<>(binding) : StateManager.createSimple(binding));
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
			this.optionBuilder.state(instant ? new PreviewStateManager<>(binding) : StateManager.createSimple(binding));
			return this;
		}

		public ListOptionBuilder<T> withBinding(List<T> def, Supplier<List<T>> getter, Consumer<List<T>> setter, boolean instant) {
			Binding<List<T>> binding = Binding.generic(def, getter, setter);
			this.optionBuilder.state(instant ? new PreviewStateManager<>(binding) : StateManager.createSimple(binding));
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
