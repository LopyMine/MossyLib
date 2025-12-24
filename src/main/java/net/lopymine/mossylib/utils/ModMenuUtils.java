package net.lopymine.mossylib.utils;

import net.lopymine.mossylib.MossyLib;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.lopymine.mossylib.yacl.api.SimpleContent;

import java.util.function.Function;

@SuppressWarnings("unused")
public class ModMenuUtils {

	private static MutableComponent text(String modId, String path) {
		return Component.translatable(String.format("%s.%s", modId, path));
	}

	public static String getOptionKey(String optionId) {
		return String.format("modmenu.option.%s", optionId);
	}

	public static String getCategoryKey(String categoryId) {
		return String.format("modmenu.category.%s", categoryId);
	}

	public static String getGroupKey(String groupId) {
		return String.format("modmenu.group.%s", groupId);
	}

	public static MutableComponent getName(String modId, String key) {
		return text(modId,key + ".name");
	}

	public static MutableComponent getDescription(String modId, String key) {
		return text(modId,key + ".description");
	}

	public static Identifier getContentId(String modId, SimpleContent content, String contentId) {
		String format = String.format("textures/config/%s.%s", contentId, content.getFileExtension());
		//? if >=1.21 {
		return Identifier.fromNamespaceAndPath(modId, format);
		 //?} else {
		/*return new ResourceLocation(modId, format);
		*///?}
	}

	public static Component getModTitle(String modId) {
		return text(modId, "modmenu.title");
	}

	public static Function<Boolean, Component> getEnabledOrDisabledFormatter() {
		return state -> MossyLib.text("modmenu.formatter.enabled_or_disabled." + state);
	}

	public static MutableComponent getNoConfigScreenMessage() {
		return MossyLib.text("modmenu.no_config_library_screen.message");
	}

	public static MutableComponent getOldConfigScreenMessage(String version) {
		return MossyLib.text("modmenu.old_config_library_screen.message", version, MossyLib.YACL_DEPEND_VERSION);
	}

	public static MutableComponent getOldConfigScreenMessage(String version, String yacl) {
		return MossyLib.text("modmenu.old_config_library_screen.message", version, yacl);
	}
}
