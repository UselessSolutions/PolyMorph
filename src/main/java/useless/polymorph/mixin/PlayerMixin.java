package useless.polymorph.mixin;

import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import useless.polymorph.PolymorphData;

@Mixin(value = Player.class, remap = false)
public class PlayerMixin implements PolymorphData {

	@Unique
	public int recipeIndex = 0;

	@Override
	public void setRecipeIndex(int index) {
		recipeIndex = index;
	}

	@Override
	public int getRecipeIndex() {
		return recipeIndex;
	}
}
