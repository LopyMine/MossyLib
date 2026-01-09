package net.lopymine.mossylib.client.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.lopymine.mossylib.MossyLib;
import net.lopymine.mossylib.utils.CommandUtils;
import net.lopymine.mossylib.utils.command.CommandTextBuilder;
import net.minecraft.network.chat.*;
import net.minecraft.network.chat.HoverEvent.*;
import net.minecraft.util.*;
import net.minecraft.*;
import net.minecraft.world.item.*;
import static net.lopymine.mossylib.utils.CommandUtils.literal;

public class TestMossyLibCommandsManager {

	public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
		dispatcher.register(literal(MossyLib.MOD_ID)
				.executes(TestMossyLibCommandsManager::test));
	}

	// Tip: You should avoid using context.getSource()
	private static int test(CommandContext<FabricClientCommandSource> context) {
		long nanos = Util.getNanos();
		Component text = CommandTextBuilder.startBuilder("test", MossyLib.MOD_ID, nanos)
				.withCopyToClipboard(String.valueOf(nanos))
				.withHoverEvent(CommandTextBuilder.getHoverEvent(
						Action.SHOW_ITEM,
						//? if >=1.21.5 {
						Items.MOSS_BLOCK.getDefaultInstance()
						//?} else {
						/*new ItemStackInfo(Items.MOSS_BLOCK.getDefaultInstance())
						*///?}
				))
				.build();

		CommandUtils.sendMessage(text);
		return 1;
	}

}
