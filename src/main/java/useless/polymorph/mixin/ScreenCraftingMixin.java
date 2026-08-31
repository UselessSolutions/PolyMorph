package useless.polymorph.mixin;

import net.minecraft.client.gui.container.ScreenContainerAbstract;
import net.minecraft.client.gui.container.ScreenCrafting;
import net.minecraft.client.gui.container.ScreenInventory;
import net.minecraft.core.player.inventory.menu.MenuAbstract;
import net.minecraft.core.player.inventory.menu.MenuCrafting;
import net.minecraft.core.player.inventory.menu.MenuInventory;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.helper.network.NetworkHandler;
import useless.polymorph.ActionButton;
import useless.polymorph.NetworkMessageSetRecipeIndex;
import useless.polymorph.PolymorphClient;
import useless.polymorph.PolymorphData;

@Mixin(value = ScreenCrafting.class, remap = false)
public abstract class ScreenCraftingMixin extends ScreenContainerAbstract {
	private ScreenCraftingMixin(MenuAbstract container) {
		super(container);
	}

	@Override
	public void init() {
		super.init();
		buttons.clear();
		ActionButton button = new ActionButton(-3000, "/assets/polymorph/textures/gui/morphButton.png", (width - xSize)/2 + PolymorphClient.getXBench(), (height - ySize)/2 + PolymorphClient.getYBench(), PolymorphClient.getU(), 0, PolymorphClient.getSize(), PolymorphClient.getSize());
		button.setActionCheck(() -> {
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)){
				PolymorphData player = (PolymorphData) mc.thePlayer;
				player.setRecipeIndex(Math.max(0,player.getRecipeIndex() - 1));
				if(EnvironmentHelper.isMultiplayerClient()){
					NetworkHandler.sendToServer(new NetworkMessageSetRecipeIndex(player.getRecipeIndex()));
				}
			} else {
				PolymorphData player = (PolymorphData) mc.thePlayer;
				player.setRecipeIndex(player.getRecipeIndex() + 1);
				if(EnvironmentHelper.isMultiplayerClient()){
					NetworkHandler.sendToServer(new NetworkMessageSetRecipeIndex(player.getRecipeIndex()));
				}
			}
			inventorySlots.slotsChanged(((MenuCrafting) inventorySlots).craftSlots);
		});
		button.setTickCheck(() ->  {
			//button.enabled = PolyMorphClient.isEnabled();
			//button.visible = PolyMorphClient.canCycle();
		});
		buttons.add(button);
	}
}
