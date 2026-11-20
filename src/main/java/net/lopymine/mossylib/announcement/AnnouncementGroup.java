package net.lopymine.mossylib.announcement;

import net.minecraft.network.chat.Component;

public record AnnouncementGroup(String id, Component message, Component optionText) {

}
