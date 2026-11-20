package net.lopymine.mossylib.config;

import com.mojang.serialization.Codec;
import java.util.HashMap;
import java.util.function.Supplier;
import lombok.*;
import net.lopymine.mossylib.announcement.DevAnnouncementsMode;
import net.lopymine.mossylib.utils.CodecUtils;
import static com.mojang.serialization.codecs.RecordCodecBuilder.create;
import static net.lopymine.mossylib.utils.CodecUtils.option;

@Getter
@Setter
@AllArgsConstructor
public class MossyLibDevConfig {

	public static final Codec<MossyLibDevConfig> CODEC = create((instance) -> instance.group(
			option("announcements_mode", DevAnnouncementsMode.ALWAYS, DevAnnouncementsMode.CODEC, MossyLibDevConfig::getAnnouncementsMode),
			option("unlock_instantly", false, Codec.BOOL, MossyLibDevConfig::isUnlockInstantly),
			option("announcements", new HashMap<>(), Codec.STRING, Codec.BOOL, MossyLibDevConfig::getAnnouncements)
	).apply(instance, MossyLibDevConfig::new));

	private DevAnnouncementsMode announcementsMode;
	private boolean unlockInstantly;
	private HashMap<String, Boolean> announcements;

	public static Supplier<MossyLibDevConfig> getNewInstance() {
		return () -> CodecUtils.parseNewInstanceHacky(CODEC);
	}

	public boolean isAnnouncementEnabled(String key) {
		return this.announcements.getOrDefault(key, true);
	}

	public void setAnnouncementEnabled(String key, boolean enabled) {
		HashMap<String, Boolean> announcements = new HashMap<>(this.announcements);
		announcements.put(key, enabled);
		this.announcements = announcements;
	}
}
