package net.lopymine.mossylib.yacl;

import lombok.experimental.ExtensionMethod;
import net.lopymine.mossylib.MossyLib;
import net.lopymine.mossylib.yacl.api.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import net.lopymine.mossylib.config.MossyConfig;
import net.lopymine.mossylib.utils.ModMenuUtils;
import net.lopymine.mossylib.yacl.extension.SimpleOptionExtension;

import java.util.function.Function;

@ExtensionMethod(SimpleOptionExtension.class)
public class MossyTestYACLConfigurationScreen {

	private static final Function<Boolean, Text> ENABLED_OR_DISABLE_FORMATTER = ModMenuUtils.getEnabledOrDisabledFormatter();

	private MossyTestYACLConfigurationScreen() {
		throw new IllegalStateException("Screen class");
	}

	public static Screen createTestScreen(Screen parent) {
		MossyConfig defConfig = MossyConfig.getNewInstance();
		MossyConfig config = MossyConfig.getInstance();

		return SimpleYACLScreen.startBuilder(MossyLib.MOD_ID, parent, config::saveAsync)
				.categories(getGeneralCategory(defConfig, config))
				.build();
	}

	private static SimpleCategory getGeneralCategory(MossyConfig defConfig, MossyConfig config) {
		return SimpleCategory.startBuilder("general")
				.groups(getMossyGroup(defConfig, config));
	}

	private static SimpleGroup getMossyGroup(MossyConfig defConfig, MossyConfig config) {
		return SimpleGroup.startBuilder("mossy_group").options(
				SimpleOption.<Boolean>startBuilder("mossy")
						.withBinding(defConfig.isMossy(), config::isMossy, config::setMossy, true)
						.withController(ENABLED_OR_DISABLE_FORMATTER)
						.withDescription(SimpleContent.NONE),
				SimpleOption.<Boolean>startFieldBuilder("mossy", defConfig, config)
						.withController(ENABLED_OR_DISABLE_FORMATTER)
						.withDescription(SimpleContent.NONE),
				SimpleOption.<Boolean>startFieldBuilder("mossy", defConfig, config)
						.withController(ENABLED_OR_DISABLE_FORMATTER)
						.withDescription(SimpleContent.NONE),
				SimpleOption.<Boolean>startFieldBuilder("mossy", defConfig, config)
						.withController(ENABLED_OR_DISABLE_FORMATTER)
						.withDescription(SimpleContent.NONE),
				SimpleOption.<Boolean>startFieldBuilder("mossy", defConfig, config)
						.withController(ENABLED_OR_DISABLE_FORMATTER)
						.withDescription(SimpleContent.NONE),
				SimpleOption.<Boolean>startFieldBuilder("mossy", defConfig, config)
						.withController(ENABLED_OR_DISABLE_FORMATTER)
						.withDescription(SimpleContent.NONE),
				SimpleOption.<Boolean>startFieldBuilder("mossy", defConfig, config)
						.withController(ENABLED_OR_DISABLE_FORMATTER)
						.withDescription(SimpleContent.NONE),
				SimpleOption.<Boolean>startFieldBuilder("mossy", defConfig, config)
						.withController(ENABLED_OR_DISABLE_FORMATTER)
						.withDescription(SimpleContent.NONE),
				SimpleOption.<Boolean>startFieldBuilder("mossy", defConfig, config)
						.withController(ENABLED_OR_DISABLE_FORMATTER)
						.withDescription(SimpleContent.NONE),
				SimpleOption.<Boolean>startFieldBuilder("mossy", defConfig, config)
						.withController(ENABLED_OR_DISABLE_FORMATTER)
						.withDescription(SimpleContent.NONE),
				SimpleOption.<Boolean>startFieldBuilder("mossy", defConfig, config)
						.withController(ENABLED_OR_DISABLE_FORMATTER)
						.withDescription(SimpleContent.NONE),
				SimpleOption.<Boolean>startFieldBuilder("mossy", defConfig, config)
						.withController(ENABLED_OR_DISABLE_FORMATTER)
						.withDescription(SimpleContent.NONE)
		);
	}

}


