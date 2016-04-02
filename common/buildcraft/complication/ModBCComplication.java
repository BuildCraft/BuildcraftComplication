package buildcraft.complication;

import buildcraft.BuildCraftMod;
import buildcraft.BuildCraftTransport;
import buildcraft.transport.*;
import buildcraft.transport.pipes.PipePowerDiamond;
import buildcraft.transport.pipes.PipePowerGold;
import buildcraft.transport.pipes.PipePowerIron;
import buildcraft.transport.pipes.PipePowerStone;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.*;

@Mod(name = "BuildCraft Complication", version = "@VERSION@", modid = "buildcraftcomplication", acceptedMinecraftVersions = "[1.8.9]",
        dependencies = "required-after:Forge@[11.15.1.1764,);required-after:BuildCraft|Transport")
public class ModBCComplication extends BuildCraftMod {
    @Mod.Instance("buildcraftcomplication")
    public static ModBCComplication instance;

    @SidedProxy(clientSide = "buildcraft.complication.ProxyClient", serverSide = "buildcraft.complication.ProxyCommon")
    private static ProxyCommon proxy;

    public static BlockTransformer transformer;

    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent evt) {
        transformer = new BlockTransformer(Material.iron);
        transformer.setUnlocalizedName("bccomplication.transformer");
        GameRegistry.registerBlock(transformer, ItemTransformer.class, "transformer");

        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(final FMLInitializationEvent evt) {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(transformer, 1, 0), "mrm", "gpg", "mrm", 'm', "plankWood", 'g', "gearWood", 'r', Items.redstone, 'p', Blocks.piston));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(transformer, 1, 1), "mrm", "gpg", "mrm", 'm', "ingotIron", 'g', "gearIron", 'r', Items.redstone, 'p', Blocks.piston));
                
        GameRegistry.registerTileEntity(TileTransformer.class, "buildcraftcomplication:transformer");
    }

    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent evt) {
        PipeTransportPower.canExplode = true;
        PipeTransportPower.lossMode = PipeTransportPower.LossMode.ABSOLUTE;

        PipeTransportPower.setPowerCapacity(PipePowerIronComplication.class, PipeTransportPower.powerCapacities.get(PipePowerIron.class));

        PipeTransportPower.setPowerLoss(PipePowerStone.class, 0.25F);
        PipeTransportPower.setPowerLoss(PipePowerIronComplication.class, 2F);
        PipeTransportPower.setPowerLoss(PipePowerGold.class, 5F);
        PipeTransportPower.setPowerLoss(PipePowerDiamond.class, 3F);

        Map<Class<? extends Pipe<?>>,Class<? extends Pipe<?>>> replacementPipes = new HashMap<>();
        replacementPipes.put(PipePowerIron.class, PipePowerIronComplication.class);

        Iterator<IRecipe> iterator = CraftingManager.getInstance().getRecipeList().iterator();

        while (iterator.hasNext()) {
            IRecipe recipe = iterator.next();
            if (recipe instanceof ShapelessOreRecipe) {
                ItemStack output = recipe.getRecipeOutput();
                if (output != null && output.getItem() instanceof ItemPipe) {
                    ItemPipe pipeItem = (ItemPipe) output.getItem();
                    Class<? extends Pipe<?>> original = BlockGenericPipe.pipes.get(pipeItem);
                    if (replacementPipes.containsKey(original)) {
                        BlockGenericPipe.pipes.put((ItemPipe) output.getItem(), replacementPipes.get(original));

                        Pipe dummyPipe = BlockGenericPipe.createPipe(pipeItem);
                        if(dummyPipe != null) {
                            pipeItem.setPipeIconIndex(dummyPipe.getIconIndexForItem());
                            TransportProxy.proxy.setIconProviderFromPipe(pipeItem, dummyPipe);
                        }
                    }
                }
            }
        }
    }
}
