package buildcraft.complication;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

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
}
