package net.lopymine.mossylib.announcement;

import java.util.*;
import java.util.function.Function;
import net.lopymine.mossylib.client.MossyLibClient;
import net.lopymine.mossylib.config.*;
import net.lopymine.mossylib.utils.ScreenUtils;
import net.minecraft.client.gui.screens.Screen;

public class MossyAnnouncements {

	private static final List<Announcement> ANNOUNCEMENTS = new ArrayList<>();

	public static void register(Announcement announcement) {
		for (Announcement registered : ANNOUNCEMENTS) {
			if (registered.getKey().equals(announcement.getKey())) {
				MossyLibClient.LOGGER.warn("Announcement \"{}\" is already registered, skipping it", announcement.getKey());
				return;
			}
		}
		ANNOUNCEMENTS.add(announcement);
	}

	public static List<Announcement> getAnnouncements() {
		return ANNOUNCEMENTS.stream()
				.sorted(Comparator.comparing(Announcement::getKey))
				.toList();
	}

	public static void addInitialScreens(List<Function<Runnable, Screen>> screens) {
		for (List<Announcement> page : collectPages()) {
			screens.add((next) -> new AnnouncementScreen(page, next));
		}
	}

	public static void openInitialScreens(Screen parent) {
		List<Function<Runnable, Screen>> screens = new ArrayList<>();
		addInitialScreens(screens);
		if (screens.isEmpty()) {
			return;
		}

		Runnable next = () -> ScreenUtils.set(parent);
		for (int i = screens.size() - 1; i >= 0; i--) {
			Function<Runnable, Screen> screen = screens.get(i);
			Runnable current = next;
			next = () -> ScreenUtils.set(screen.apply(current));
		}
		next.run();
	}

	private static List<List<Announcement>> collectPages() {
		MossyLibConfig config = MossyLibConfig.getInstance();
		if (config.isDevelopmentActive()) {
			config.save();
			return groupPages(collectDevAnnouncements(config.getDevConfig()));
		}

		ArrayList<String> seenAnnouncements = new ArrayList<>(config.getSeenAnnouncements());
		List<Announcement> announcements = new ArrayList<>();

		for (Announcement announcement : ANNOUNCEMENTS) {
			String key = announcement.getKey();
			if (seenAnnouncements.contains(key)) {
				continue;
			}
			seenAnnouncements.add(key);

			if (announcement.getCondition().getAsBoolean()) {
				announcements.add(announcement);
			}
		}

		if (seenAnnouncements.size() != config.getSeenAnnouncements().size()) {
			config.setSeenAnnouncements(seenAnnouncements);
			config.save();
		}

		return groupPages(announcements);
	}

	private static List<Announcement> collectDevAnnouncements(MossyLibDevConfig devConfig) {
		DevAnnouncementsMode mode = devConfig.getAnnouncementsMode();
		List<Announcement> announcements = new ArrayList<>();
		if (mode == DevAnnouncementsMode.OFF) {
			return announcements;
		}

		for (Announcement announcement : ANNOUNCEMENTS) {
			if (!devConfig.isAnnouncementEnabled(announcement.getKey())) {
				continue;
			}
			if (mode == DevAnnouncementsMode.CONDITIONS && !announcement.getCondition().getAsBoolean()) {
				continue;
			}
			announcements.add(announcement);
		}

		return announcements;
	}

	private static List<List<Announcement>> groupPages(List<Announcement> announcements) {
		List<List<Announcement>> pages = new ArrayList<>();
		Map<String, List<Announcement>> groupedPages = new HashMap<>();

		for (Announcement announcement : announcements) {
			AnnouncementGroup group = announcement.getGroup();
			if (group == null) {
				pages.add(List.of(announcement));
				continue;
			}

			groupedPages.computeIfAbsent(group.id(), (ignored) -> {
				List<Announcement> page = new ArrayList<>();
				pages.add(page);
				return page;
			}).add(announcement);
		}

		return pages;
	}
}
