package useless.polymorph.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.player.inventory.container.ContainerCrafting;
import net.minecraft.core.player.inventory.menu.MenuAbstract;
import net.minecraft.core.player.inventory.menu.MenuInventory;
import net.minecraft.server.entity.player.PlayerServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import turniplabs.halplibe.helper.EnvironmentHelper;
import useless.polymorph.Polymorph;
import useless.polymorph.PolymorphData;

@Mixin(value = MenuInventory.class, remap = false)
public abstract class MenuInventoryMixin extends MenuAbstract {

	@Shadow
	public Container resultSlots;

	@Shadow
	public ContainerCrafting craftSlots;

	/**
	 * @author sunsetsatellite
	 * @reason because I could again
	 */
	@Overwrite
	public void slotsChanged(Container container) {

		if(!EnvironmentHelper.isMultiplayerServer()){
			if(Minecraft.getMinecraft().thePlayer != null){
				Polymorph.recipeIndex = ((PolymorphData) Minecraft.getMinecraft().thePlayer).getRecipeIndex();
			} else {
				Polymorph.recipeIndex = 0;
			}
		} else {
			if(containerListeners.isEmpty()) {
				Polymorph.recipeIndex = 0;
			} else {
				PlayerServer player = (PlayerServer) containerListeners.get(0);
				Polymorph.recipeIndex = ((PolymorphData) player).getRecipeIndex();
			}
		}
		this.resultSlots.setItem(0, Registries.RECIPES.findMatchingRecipe(this.craftSlots));
		Polymorph.recipeIndex = 0;
	}

}
