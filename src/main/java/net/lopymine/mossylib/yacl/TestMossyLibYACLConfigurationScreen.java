package net.lopymine.mossylib.yacl;

import lombok.experimental.ExtensionMethod;
import net.lopymine.mossylib.MossyLib;
import net.lopymine.mossylib.yacl.api.*;
import net.lopymine.mossylib.config.MossyLibConfig;
import net.lopymine.mossylib.utils.ModMenuUtils;
import net.lopymine.mossylib.yacl.extension.SimpleOptionExtension;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import java.util.function.Function;

@ExtensionMethod(SimpleOptionExtension.class)
public class TestMossyLibYACLConfigurationScreen {

	private static final Function<Boolean, Component> ENABLED_OR_DISABLE_FORMATTER = ModMenuUtils.getEnabledOrDisabledFormatter();

	private TestMossyLibYACLConfigurationScreen() {
		throw new IllegalStateException("Screen class");
	}

	public static Screen createTestScreen(Screen parent) {
		MossyLibConfig defConfig = MossyLibConfig.getNewInstance();
		MossyLibConfig config = MossyLibConfig.getInstance();

		return SimpleYACLScreen.startBuilder(MossyLib.MOD_ID, parent, config::saveAsync)
				.categories(getGeneralCategory(defConfig, config))
				.build();
	}

	private static SimpleCategory getGeneralCategory(MossyLibConfig defConfig, MossyLibConfig config) {
		return SimpleCategory.startBuilder("general")
				.groups(getMossyGroup(defConfig, config));
	}

	private static SimpleGroup getMossyGroup(MossyLibConfig defConfig, MossyLibConfig config) {
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


