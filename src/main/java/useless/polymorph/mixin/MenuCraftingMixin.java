package useless.polymorph.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.player.inventory.container.ContainerCrafting;
import net.minecraft.core.player.inventory.menu.MenuAbstract;
import net.minecraft.core.player.inventory.menu.MenuCrafting;
import net.minecraft.server.entity.player.PlayerServer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import turniplabs.halplibe.helper.EnvironmentHelper;
import useless.polymorph.Polymorph;
import useless.polymorph.PolymorphData;

@Mixin(value = MenuCrafting.class, remap = false)
public abstract class MenuCraftingMixin extends MenuAbstract {

	@Shadow
	@Final
	@NotNull
	public Container resultSlots;

	@Shadow
	@Final
	@NotNull
	public ContainerCrafting craftSlots;

	/**
	 * @author sunsetsatellite
	 * @reason because I could again
	 */
	@Overwrite
	public void slotsChanged(Container container) {
		if(!EnvironmentHelper.isMultiplayerServer()){
			Polymorph.recipeIndex = ((PolymorphData) Minecraft.getMinecraft().thePlayer).getRecipeIndex();
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
