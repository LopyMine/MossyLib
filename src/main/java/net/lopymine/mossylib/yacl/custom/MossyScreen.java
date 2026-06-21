package net.lopymine.mossylib.yacl.custom;

import net.lopymine.mossylib.utils.ScreenUtils;

public interface MossyScreen {

	@SuppressWarnings("all")
	static boolean isMossyScreen() {
		return ScreenUtils.current() instanceof MossyScreen;
	}

}
