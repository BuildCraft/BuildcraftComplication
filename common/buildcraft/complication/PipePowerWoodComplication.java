package buildcraft.complication;

import buildcraft.core.lib.RFBattery;
import buildcraft.transport.pipes.PipePowerWood;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by asie on 4/2/16.
 */
public class PipePowerWoodComplication extends PipePowerWood {
    public PipePowerWoodComplication(Item item) {
        super(item);
        battery = new RFBattery(40960, 40960, 0);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        if (battery.getMaxEnergyReceive() != 40960) {
            RFBattery oldBattery = battery;
            battery = new RFBattery(40960, 40960, 0);
            battery.setEnergy(oldBattery.getEnergyStored());
        }
    }
}
