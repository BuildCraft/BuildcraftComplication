package buildcraft.complication;

import buildcraft.transport.PipeIconProvider;
import buildcraft.transport.pipes.PipePowerStone;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;

/**
 * Created by asie on 4/2/16.
 */
public class PipePowerIronComplication extends PipePowerStone {
    public PipePowerIronComplication(Item item) {
        super(item);
    }

    @Override
    public int getIconIndex(EnumFacing direction) {
        return PipeIconProvider.TYPE.PipePowerIron_Standard.ordinal();
    }
}
