package useless.polymorph.mixin;

import net.minecraft.core.data.registry.recipe.RecipeRegistry;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCrafting;
import net.minecraft.core.player.inventory.container.ContainerCrafting;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import useless.polymorph.Polymorph;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = RecipeRegistry.class, remap = false)
public abstract class RecipeRegistryMixin {
	@Shadow
	public abstract List<RecipeEntryCrafting<?, ?>> getAllCraftingRecipes();

	/**
	 * @author sunsetsatellite
	 * @reason because I could
	 */
	@Overwrite
	public @Nullable RecipeEntryCrafting<?, ?> findMatchingCraftingRecipe(ContainerCrafting inventorycrafting) {
		List<RecipeEntryCrafting<?, ?>> recipes = new ArrayList<>();
		for(int i = 0; i < this.getAllCraftingRecipes().size(); ++i) {
			RecipeEntryCrafting<?, ?> recipe = this.getAllCraftingRecipes().get(i);
			if (recipe.matches(inventorycrafting)) {
				recipes.add(recipe);
			}
		}

		return recipes.isEmpty() ? null : recipes.get(Math.min(Polymorph.recipeIndex, recipes.size() - 1));
	}
}
