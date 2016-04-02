package buildcraft.complication;

import buildcraft.transport.BlockGenericPipe;
import buildcraft.transport.ItemPipe;
import buildcraft.transport.Pipe;
import buildcraft.transport.PipeToolTipManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Iterator;

/**
 * Created by asie on 4/2/16.
 */
public class ProxyClient extends ProxyCommon {
    @Override
    public void preInit() {
        Item transformer = Item.getItemFromBlock(ModBCComplication.transformer);
        ModelLoader.setCustomModelResourceLocation(transformer, 0, new ModelResourceLocation("buildcraftcomplication:transformer", "facing=east,voltage=low_medium"));
        ModelLoader.setCustomModelResourceLocation(transformer, 1, new ModelResourceLocation("buildcraftcomplication:transformer", "facing=east,voltage=medium_high"));
        ModelLoader.setCustomModelResourceLocation(transformer, 2, new ModelResourceLocation("buildcraftcomplication:transformer", "facing=east,voltage=high_extreme"));
    }

    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        if (event.itemStack != null && event.itemStack.getItem() instanceof ItemPipe) {
            Class<? extends Pipe<?>> pipeType = BlockGenericPipe.pipes.get(event.itemStack.getItem());
            if (pipeType == PipePowerWoodComplication.class) {
                Iterator<String> iterator = event.toolTip.iterator();
                while (iterator.hasNext()) {
                    String s = iterator.next();
                    if (s != null && s.contains("40960 RF/t")) {
                        iterator.remove();
                    }
                }
            }
        }
    }
}
