package net.lopymine.mossylib.yacl.utils;

import dev.isxander.yacl3.api.YetAnotherConfigLib;
import java.util.function.BiFunction;
import net.minecraft.client.gui.screens.Screen;

public interface YACLScreenConsumer extends BiFunction<YetAnotherConfigLib, Screen, Screen> {

}
