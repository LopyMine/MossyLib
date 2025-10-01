package net.lopymine.mossylib.yacl.api;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.ConfigCategory.Builder;
import java.util.*;
import java.util.function.Consumer;
import net.lopymine.mossylib.yacl.api.SimpleOption.SimpleOptionBuilder;
import net.minecraft.text.Text;

import net.lopymine.mossylib.utils.ModMenuUtils;
import net.minecraft.util.Formatting;

@SuppressWarnings("unused")
public class SimpleCategory {

	private final String categoryId;
	private final List<SimpleGroup> groups = new ArrayList<>();
	private final List<SimpleOptionBuilder<?>> options = new ArrayList<>();
	private Consumer<ConfigCategory.Builder> custom = (__) -> {};

	private SimpleCategory(String categoryId) {
		this.categoryId = categoryId;
	}

	public static SimpleCategory startBuilder(String categoryId) {
		return new SimpleCategory(categoryId);
	}

	public SimpleCategory groups(SimpleGroup... groups) {
		this.groups.addAll(List.of(groups));
		return this;
	}

	public SimpleCategory options(Option<?>... options) {
		this.custom((builder) -> builder.options(List.of(options)));
		return this;
	}

	public SimpleCategory options(SimpleOptionBuilder<?>... options) {
		this.options.addAll(List.of(options));
		return this;
	}

	public SimpleCategory custom(Consumer<ConfigCategory.Builder> custom) {
		this.custom = this.custom.andThen(custom);
		return this;
	}

	public ConfigCategory build(String modId) {
		String categoryKey = ModMenuUtils.getCategoryKey(this.categoryId);
		Text categoryName = ModMenuUtils.getName(modId, categoryKey).formatted(Formatting.BOLD);

		Builder builder = ConfigCategory.createBuilder();

		for (SimpleOptionBuilder<?> option : this.options) {
			builder.option(option.build(modId));
		}
		for (SimpleGroup group : this.groups) {
			builder.group(group.build(modId));
		}

		return builder.name(categoryName).build();
	}
}
