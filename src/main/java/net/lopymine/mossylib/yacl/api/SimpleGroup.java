package net.lopymine.mossylib.yacl.api;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.gui.image.ImageRenderer;
import java.util.*;
import java.util.function.Consumer;
import net.lopymine.mossylib.utils.ModMenuUtils;
import net.lopymine.mossylib.yacl.api.SimpleOption.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;


@SuppressWarnings("unused")
public class SimpleGroup {

	private final String groupId;
	private final List<SimpleOptionBuilder<?>> options = new ArrayList<>();
	private Consumer<OptionGroup.Builder> custom = (__) -> {};

	@Nullable
	private ImageRenderer renderer;

	public SimpleGroup(String groupId) {
		this.groupId = groupId;
	}

	public static SimpleGroup startBuilder(String groupId) {
		return new SimpleGroup(groupId);
	}

	public SimpleGroup options(SimpleOptionBuilder<?>... options) {
		this.options.addAll(List.of(options));
		return this;
	}

	public SimpleGroup options(Option<?>... options) {
		this.custom((builder) -> builder.options(List.of(options)));
		return this;
	}

	public SimpleGroup withCustomDescription(ImageRenderer renderer) {
		this.renderer = renderer;
		return this;
	}

	public SimpleGroup custom(Consumer<OptionGroup.Builder> custom) {
		this.custom = this.custom.andThen(custom);
		return this;
	}

	public OptionGroup build(String modId) {
		String groupKey = ModMenuUtils.getGroupKey(this.groupId);
		Component groupName = ModMenuUtils.getName(modId, groupKey).withStyle(ChatFormatting.BOLD);
		Component description = ModMenuUtils.getDescription(modId, groupKey);

		OptionDescription.Builder descriptionBuilder = OptionDescription.createBuilder();
		descriptionBuilder.text(description);
		if (this.renderer != null) {
			descriptionBuilder.customImage(this.renderer);
		}

		OptionGroup.Builder groupBuilder = OptionGroup.createBuilder();
		groupBuilder.name(groupName);
		groupBuilder.description(descriptionBuilder.build());

		for (SimpleOptionBuilder<?> option : this.options) {
			groupBuilder.option(option.build(modId));
		}

		this.custom.accept(groupBuilder);

		return groupBuilder.build();
	}
}
