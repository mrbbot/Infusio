package com.mrbbot.infusio.infusion;

import com.mrbbot.infusio.tileentities.TileEntityPedestal;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class InfusionHandler {
    private static final int PEDESTAL_RANGE = 5;
    public static final int NANOSECOND = 1000000000;
    public static final int INFUSION_TIME = (int) (0.5 * NANOSECOND);

    public static class InfusingItem {
        public BlockPos origin;
        public ItemStack itemStack;

        InfusingItem(BlockPos origin, ItemStack itemStack) {
            this.origin = origin;
            this.itemStack = itemStack;
        }

        public InfusingItem(NBTTagCompound compound) {
            this.origin = new BlockPos(compound.getInteger("OriginX"), compound.getInteger("OriginY"), compound.getInteger("OriginZ"));
            this.itemStack = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("Item"));
        }

        public NBTTagCompound toNBT() {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setInteger("OriginX", origin.getX());
            compound.setInteger("OriginY", origin.getY());
            compound.setInteger("OriginZ", origin.getZ());
            NBTTagCompound item = new NBTTagCompound();
            itemStack.writeToNBT(item);
            compound.setTag("Item", item);
            return compound;
        }
    }

    public static boolean infuseWith(TileEntityPedestal pedestal) {
        if(pedestal.getStackInSlot(0) == null)
            return false;

        BlockPos mainPos = pedestal.getPos();
        ArrayList<TileEntityPedestal> availablePedestals = new ArrayList<TileEntityPedestal>();
        for(int xOffset = -PEDESTAL_RANGE; xOffset <= PEDESTAL_RANGE; xOffset++) {
            for(int yOffset = -PEDESTAL_RANGE; yOffset <= PEDESTAL_RANGE; yOffset++) {
                for (int zOffset = -PEDESTAL_RANGE; zOffset <= PEDESTAL_RANGE; zOffset++) {
                    BlockPos offsetPos = new BlockPos(mainPos).add(xOffset, yOffset, zOffset);
                    TileEntity tileEntity = pedestal.getWorld().getTileEntity(offsetPos);
                    if (tileEntity != null && tileEntity instanceof TileEntityPedestal) {
                        TileEntityPedestal offsetPedestal = (TileEntityPedestal) tileEntity;
                        if (offsetPedestal.getStackInSlot(0) != null)
                            availablePedestals.add(offsetPedestal);
                    }
                }
            }
        }

        for(Infusion infusion : InfusionRegistry.infusions) {
            ArrayList<Integer> indicesUsed = infusion.canInfuseWith(pedestal.getStackInSlot(0), availablePedestals);
            if(indicesUsed != null) {
                ArrayList<InfusingItem> infusingItems = new ArrayList<InfusingItem>();
                for(int index : indicesUsed) {
                    TileEntityPedestal usedPedestal = availablePedestals.get(index);
                    infusingItems.add(new InfusingItem(usedPedestal.getPos(), ItemStack.copyItemStack(usedPedestal.getStackInSlot(0))));
                    usedPedestal.setInventorySlotContents(0, null);
                }

                pedestal.setInfusion(ItemStack.copyItemStack(infusion.getOutput()), infusingItems);
                return true;
            }
        }

        return false;
    }
}
