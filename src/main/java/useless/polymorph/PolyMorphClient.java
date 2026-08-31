package useless.polymorph;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.options.components.BooleanOptionComponent;
import net.minecraft.client.gui.options.components.ToggleableOptionComponent;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.client.option.OptionBoolean;
import net.minecraft.client.option.OptionRange;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.ItemStack;
import turniplabs.halplibe.event.defs.ClientEvents;
import turniplabs.halplibe.util.dependency.Key;

public class PolymorphClient implements ClientModInitializer {

	public static OptionBoolean smallButtons = new OptionBoolean("polymorph.small.buttons", true);
	public static OptionRange invX = new OptionRange("polymorph.inv.x", 152 + 50, 176 + 100);
	public static OptionRange invY = new OptionRange("polymorph.inv.y", 60 + 50, 166 + 100);
	public static OptionRange benchX = new OptionRange("polymorph.bench.x", 101 + 50, 176 + 100);
	public static OptionRange benchY = new OptionRange("polymorph.bench.y", 62 + 50, 166 + 100);

	public static boolean useSmallButtons(){
		return smallButtons.value;
	}
	public static int getU(){
		return useSmallButtons() ? 20 : 0;
	}
	public static int getXInv(){
		return invX.value - 50 - getSize()/2;
	}
	public static int getYInv(){
		return invY.value - 50 - getSize()/2;
	}
	public static int getXBench(){
		return benchX.value - 50 - getSize()/2;
	}
	public static int getYBench(){
		return benchY.value - 50 - getSize()/2;
	}
	public static int getSize(){
		return 12 + (useSmallButtons() ? 0 : 8);
	}

	@Override
	public void onInitializeClient() {
		ClientEvents.AFTER_CLIENT_START.listen(Key.of(Polymorph.MOD_ID),()->{
			OptionsPage page = new OptionsPage("polymorph.gui.options.page.title", new ItemStack(Blocks.WORKBENCH))
				.withComponent(new BooleanOptionComponent(smallButtons))
				.withComponent(new ToggleableOptionComponent<>(invX))
				.withComponent(new ToggleableOptionComponent<>(invY))
				.withComponent(new ToggleableOptionComponent<>(benchX))
				.withComponent(new ToggleableOptionComponent<>(benchY));
			OptionsPages.register(page);
		});

	}
}
