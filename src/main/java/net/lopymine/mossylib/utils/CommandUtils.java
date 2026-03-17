//~ client_fabric_commands

package net.lopymine.mossylib.utils;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.*;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class CommandUtils {

	public static LiteralArgumentBuilder<CommandSourceStack> literal(String name) {
		return LiteralArgumentBuilder.literal(name);
	}

	public static <T> RequiredArgumentBuilder<CommandSourceStack, T> argument(String name, ArgumentType<T> type) {
		return RequiredArgumentBuilder.argument(name, type);
	}

	public static void sendMessage(Component text) {
		//? if >=26.1 {
		/*Minecraft.getInstance().gui.getChat().addClientSystemMessage(text);
		*///?} else {
		Minecraft.getInstance().gui.getChat().addMessage(text);
		//?}
	}
}
