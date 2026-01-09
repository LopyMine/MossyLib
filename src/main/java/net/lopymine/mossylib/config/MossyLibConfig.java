package net.lopymine.mossylib.config;

import lombok.*;
import net.lopymine.mossylib.loader.MossyLoader;
import net.lopymine.mossylib.utils.*;
import org.slf4j.*;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.lopymine.mossylib.MossyLib;

import java.io.*;
import java.util.concurrent.CompletableFuture;

import static net.lopymine.mossylib.utils.CodecUtils.option;

@Getter
@Setter
@AllArgsConstructor
public class MossyLibConfig {

	public static final Codec<MossyLibConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			option("mossy", false, Codec.BOOL, MossyLibConfig::isMossy)
	).apply(instance, MossyLibConfig::new));

	private static final File CONFIG_FILE = MossyLoader.getConfigDir().resolve(MossyLib.MOD_ID + ".json5").toFile();
	private static final Logger LOGGER = LoggerFactory.getLogger(MossyLib.MOD_NAME + "/Config");
	private static MossyLibConfig INSTANCE;
	
	private boolean mossy;

	private MossyLibConfig() {
		throw new IllegalArgumentException();
	}

	public static MossyLibConfig getInstance() {
		return INSTANCE == null ? reload() : INSTANCE;
	}

	public static MossyLibConfig reload() {
		return INSTANCE = MossyLibConfig.read();
	}

	public static MossyLibConfig getNewInstance() {
		return CodecUtils.parseNewInstanceHacky(CODEC);
	}

	private static MossyLibConfig read() {
		return ConfigUtils.readConfig(CODEC, CONFIG_FILE, LOGGER);
	}

	public void saveAsync() {
		CompletableFuture.runAsync(this::save);
	}

	public void save() {
		ConfigUtils.saveConfig(this, CODEC, CONFIG_FILE, LOGGER);
	}
}
