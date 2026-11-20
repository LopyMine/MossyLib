package net.lopymine.mossylib.yacl;

import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import java.util.List;
import lombok.experimental.ExtensionMethod;
import net.lopymine.mossylib.MossyLib;
import net.lopymine.mossylib.announcement.*;
import net.lopymine.mossylib.yacl.api.*;
import net.lopymine.mossylib.config.*;
import net.lopymine.mossylib.utils.ModMenuUtils;
import net.lopymine.mossylib.yacl.extension.SimpleOptionExtension;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import java.util.function.Function;
import org.jetbrains.annotations.Nullable;

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
				.categories(getGeneralCategory(defConfig, config), getDevCategory(config, defConfig.getDevConfig(), config.getDevConfig()))
				.build();
	}

	@Nullable
	private static SimpleCategory getDevCategory(MossyLibConfig mainConfig, MossyLibDevConfig defConfig, MossyLibDevConfig config) {
		if (!mainConfig.isDevelopmentActive()) {
			return null;
		}

		SimpleCategory category = SimpleCategory.startBuilder("dev").options(
				SimpleOption.<DevAnnouncementsMode>startBuilder("announcements_mode")
						.withBinding(defConfig.getAnnouncementsMode(), config::getAnnouncementsMode, config::setAnnouncementsMode, false)
						.withController(DevAnnouncementsMode.class)
						.withDescription(SimpleContent.NONE),
				SimpleOption.<Boolean>startBuilder("unlock_instantly")
						.withBinding(defConfig.isUnlockInstantly(), config::isUnlockInstantly, config::setUnlockInstantly, false)
						.withController(ENABLED_OR_DISABLE_FORMATTER)
						.withDescription(SimpleContent.NONE)
		);

		List<Announcement> announcements = MossyAnnouncements.getAnnouncements();
		if (!announcements.isEmpty()) {
			category.groups(getRegisteredAnnouncementsGroup(config, announcements));
		}
		return category;
	}

	private static SimpleGroup getRegisteredAnnouncementsGroup(MossyLibDevConfig config, List<Announcement> announcements) {
		Option<?>[] options = announcements.stream()
				.map((announcement) -> Option.<Boolean>createBuilder()
						.name(Component.literal(announcement.getKey()))
						.binding(true, () -> config.isAnnouncementEnabled(announcement.getKey()), (enabled) -> config.setAnnouncementEnabled(announcement.getKey(), enabled))
						.controller((option) -> BooleanControllerBuilder.create(option).coloured(true).formatValue(ENABLED_OR_DISABLE_FORMATTER::apply))
						.build())
				.toArray(Option<?>[]::new);
		return SimpleGroup.startBuilder("registered_announcements").options(options);
	}

	private static SimpleCategory getGeneralCategory(MossyLibConfig defConfig, MossyLibConfig config) {
		return SimpleCategory.startBuilder("general")
				.groups(getMossyGroup(defConfig, config));
	}

	private static SimpleGroup getMossyGroup(MossyLibConfig defConfig, MossyLibConfig config) {
		return SimpleGroup.startBuilder("mossy_group").options(
				SimpleOption.<Boolean>startBuilder("developer_mode")
						.withBinding(defConfig.isDeveloperMode(), config::isDeveloperMode, config::setDeveloperMode, false)
						.withController(ENABLED_OR_DISABLE_FORMATTER)
						.withDescription(SimpleContent.NONE),
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


