package com.mrbbot.infusio.infusion;

import com.mrbbot.infusio.tileentities.TileEntityPedestal;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class InfusionHandler {
    private static final int PEDESTAL_RANGE = 4;

    public static boolean infuseWith(TileEntityPedestal pedestal) {
        if(pedestal.getStackInSlot(0) == null)
            return false;

        BlockPos mainPos = pedestal.getPos();
        ArrayList<TileEntityPedestal> availablePedestals = new ArrayList<TileEntityPedestal>();
        for(int xOffset = -PEDESTAL_RANGE; xOffset <= PEDESTAL_RANGE; xOffset++) {
            for(int zOffset = -PEDESTAL_RANGE; zOffset <= PEDESTAL_RANGE; zOffset++) {
                BlockPos offsetPos = new BlockPos(mainPos).add(xOffset, 0, zOffset);
                TileEntity tileEntity = pedestal.getWorld().getTileEntity(offsetPos);
                if(tileEntity != null && tileEntity instanceof TileEntityPedestal) {
                    TileEntityPedestal offsetPedestal = (TileEntityPedestal) tileEntity;
                    if(offsetPedestal.getStackInSlot(0) != null)
                        availablePedestals.add(offsetPedestal);
                }
            }
        }

        for(Infusion infusion : InfusionRegistry.infusions) {
            ArrayList<Integer> indicesUsed = infusion.canInfuseWith(pedestal.getStackInSlot(0), availablePedestals);
            if(indicesUsed != null) {
                for(int index : indicesUsed) {
                    availablePedestals.get(index).setInventorySlotContents(0, null);
                }
                pedestal.setInventorySlotContents(0, ItemStack.copyItemStack(infusion.output));
                return true;
            }
        }


        return false;
    }
}
