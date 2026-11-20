package net.lopymine.mossylib.announcement;

import com.mojang.serialization.Codec;
import java.util.Locale;
import net.lopymine.mossylib.MossyLib;
import net.lopymine.mossylib.yacl.utils.EnumWithText;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

public enum DevAnnouncementsMode implements StringRepresentable, EnumWithText {

	ALWAYS,
	CONDITIONS,
	OFF;

	public static final Codec<DevAnnouncementsMode> CODEC = StringRepresentable.fromEnum(DevAnnouncementsMode::values);

	@Override
	public String getSerializedName() {
		return this.name().toLowerCase(Locale.ROOT);
	}

	@Override
	public Component getText() {
		return MossyLib.text("modmenu.option.announcements_mode." + this.getSerializedName());
	}
}
