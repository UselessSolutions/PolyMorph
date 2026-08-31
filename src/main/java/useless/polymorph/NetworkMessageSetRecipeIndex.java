package useless.polymorph;

import net.minecraft.core.player.inventory.menu.MenuCrafting;
import org.jetbrains.annotations.NotNull;
import turniplabs.halplibe.helper.network.NetworkMessage;
import turniplabs.halplibe.helper.network.UniversalPacket;

public class NetworkMessageSetRecipeIndex implements NetworkMessage {

	public int recipeIndex;

	public NetworkMessageSetRecipeIndex(int recipeIndex) {
		this.recipeIndex = recipeIndex;
	}

	public NetworkMessageSetRecipeIndex() {

	}

	@Override
	public void encodeToUniversalPacket(@NotNull UniversalPacket packet) {
		packet.writeInt(recipeIndex);
	}

	@Override
	public void decodeFromUniversalPacket(@NotNull UniversalPacket packet) {
		recipeIndex = packet.readInt();
	}

	@Override
	public void handleServerEnv(NetworkContext context) {
		((PolymorphData) context.player).setRecipeIndex(recipeIndex);
		if(context.player.containerMenu instanceof MenuCrafting crafting){
			crafting.slotsChanged(crafting.craftSlots);
		}
		context.player.inventoryMenu.slotsChanged(context.player.inventory);
	}
}
