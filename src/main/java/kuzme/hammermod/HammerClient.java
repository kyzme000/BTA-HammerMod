package kuzme.hammermod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.SoundRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.ClientStartEntrypoint;

import static kuzme.hammermod.HammerMod.MOD_ID;

@Environment(EnvType.CLIENT)
public class HammerClient implements ClientModInitializer, ClientStartEntrypoint {

	public static final Logger LOGGER = LoggerFactory.getLogger("HammerClient");

	@Override
	public void onInitializeClient() {
		LOGGER.info("HammerClient init");
		SoundRepository.registerNamespace(MOD_ID);
	}

	@Override
	public void beforeClientStart() {

	}

	@Override
	public void afterClientStart() {

	}
}
