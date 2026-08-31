package useless.polymorph;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.event.defs.CommonEvents;
import turniplabs.halplibe.helper.network.NetworkHandler;
import turniplabs.halplibe.util.dependency.Key;

public class Polymorph implements ModInitializer {
	public static final String MOD_ID = HalpLibe.registerMod("polymorph", true);
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static int recipeIndex = 0;

	@Override
	public void onInitialize() {
		CommonEvents.BEFORE_GAME_START.listen(Key.of(MOD_ID), this::beforeGameStart);
		CommonEvents.AFTER_GAME_START.listen(Key.of(MOD_ID), this::afterGameStart);
		NetworkHandler.registerNetworkMessage(NetworkMessageSetRecipeIndex::new);
		LOGGER.info("Polymorph initialized.");
	}

	public void beforeGameStart() {

	}

	public void afterGameStart() {

	}
}
