package net.lopymine.mossylib.client.command;

import java.util.*;
import net.lopymine.mossylib.MossyLib;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.HoverEvent.Action;
import net.minecraft.network.chat.HoverEvent.EntityTooltipInfo;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;

//? if >=1.21.5 {
import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.ClickEvent.ChangePage;
import net.minecraft.network.chat.ClickEvent.CopyToClipboard;
import net.minecraft.network.chat.ClickEvent.OpenFile;
import net.minecraft.network.chat.ClickEvent.OpenUrl;
import net.minecraft.network.chat.ClickEvent.RunCommand;
import net.minecraft.network.chat.ClickEvent.SuggestCommand;
import net.minecraft.network.chat.HoverEvent.ShowEntity;
import net.minecraft.network.chat.HoverEvent.ShowItem;
import net.minecraft.network.chat.HoverEvent.ShowText;
//?}

//? if >=1.21.6 {
import net.minecraft.network.chat.ClickEvent.Custom;
import net.minecraft.network.chat.ClickEvent.ShowDialog;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Holder;
import net.minecraft.server.dialog.Dialog;
//?}

@SuppressWarnings("unused")
public class CommandTextBuilder {

	private final String key;
	private final MutableComponent text;

	private CommandTextBuilder(String key, Object... args) {
		this.key  = key;
		this.text = CommandTextBuilder.translatable(key, args);
	}

	private static MutableComponent translatable(String key, Object... args) {
		for (int i = 0; i < args.length; ++i) {
			Object object = args[i];
			if (!isPrimitive(object) && !(object instanceof Component)) {
				args[i] = String.valueOf(object);
			}
		}

		return Component.literal(MossyLib.text(key, args).getString().replace("&", "§"));
	}

	private static boolean isPrimitive(Object object) {
		return object instanceof Number || object instanceof Boolean || object instanceof String;
	}

	public static CommandTextBuilder startBuilder(String key, Object... args) {
		return new CommandTextBuilder("command." + key, args);
	}

	public CommandTextBuilder withShowEntity(EntityType<?> type, UUID uuid, String name) {
		return this.withShowEntity(type, uuid, Component.literal(name));
	}

	public CommandTextBuilder withShowEntity(EntityType<?> type, UUID uuid, Component name) {
		HoverEvent hoverEvent = getHoverEvent(Action.SHOW_ENTITY, new EntityTooltipInfo(type, uuid, name));
		return this.withHoverEvent(hoverEvent);
	}

	public CommandTextBuilder withHoverText(Object... args) {
		MutableComponent hoverText = CommandTextBuilder.translatable(this.key + ".hover_text", args);
		HoverEvent hoverEvent = getHoverEvent(Action.SHOW_TEXT, hoverText);
		return this.withHoverEvent(hoverEvent);
	}

	public CommandTextBuilder withHoverEvent(HoverEvent hoverEvent) {
		Style style = this.text.getStyle().withHoverEvent(hoverEvent);
		this.text.setStyle(style);
		return this;
	}

	public CommandTextBuilder withCopyToClipboard(Object value) {
		ClickEvent clickEvent = getClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, value);
		return this.withClickEvent(clickEvent);
	}

	public CommandTextBuilder withClickEvent(ClickEvent clickEvent) {
		Style style = this.text.getStyle().withClickEvent(clickEvent);
		this.text.setStyle(style);
		return this;
	}

	public static <T> HoverEvent getHoverEvent(Action/*? <=1.21.4 {*//*<T>*//*?}*/ action, T value) {
		//? <=1.21.4 {
		/*return new HoverEvent(action, value);
		*//*?} else {*/
		return switch (action) {
			case SHOW_TEXT -> new ShowText((Component) value);
			case SHOW_ITEM -> new ShowItem((ItemStack) value);
			case SHOW_ENTITY -> new ShowEntity((EntityTooltipInfo) value);
		};
		/*?}*/
	}

	//? if >=1.21.6 {
	@SuppressWarnings("unchecked")
	//?}
	public static ClickEvent getClickEvent(ClickEvent.Action action, Object value) {
		//? <=1.21.4 {
		/*return new ClickEvent(action, (String) value);
		*//*?} else {*/
		return switch (action) {
			case OPEN_URL -> new OpenUrl((URI) value);
			case RUN_COMMAND -> new RunCommand((String) value);
			case SUGGEST_COMMAND -> new SuggestCommand((String) value);
			case CHANGE_PAGE -> new ChangePage((int) value);
			case COPY_TO_CLIPBOARD -> new CopyToClipboard((String) value);
			case OPEN_FILE -> {
				if (value instanceof File file) {
					yield new OpenFile(file);
				}
				if (value instanceof Path path) {
					yield new OpenFile(path);
				}
				yield new OpenFile((String) value);
			}
			//? if >=1.21.6 {
			case CUSTOM -> new Custom((ResourceLocation) value, Optional.empty());
			case SHOW_DIALOG -> new ShowDialog((Holder<Dialog>) value);
			//?}
		};
		/*?}*/
	}

	public Component build() {
		return this.text;
	}
}
