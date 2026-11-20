package net.lopymine.mossylib.announcement;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.network.chat.Component;

public record AnnouncementOption(Component text, boolean defaultValue, BooleanConsumer onContinue) {

}
