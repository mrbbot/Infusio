package com.mrbbot.infusio.tileentities;

import com.mrbbot.infusio.infusion.InfusionHandler;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class TileEntityPedestal extends TileEntity implements IInventory, ITickable {
    private ItemStack itemStack;

    public int timeInfusing = -1;
    private ItemStack infusionOutput;
    public ArrayList<InfusionHandler.InfusingItem> infusingItems;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        if(itemStack != null) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            itemStack.writeToNBT(tagCompound);
            compound.setTag("Item", tagCompound);
        }

        compound.setInteger("TimeInfusing", timeInfusing);
        compound.setLong("LastTime", lastTime);
        compound.setInteger("LastPitch", lastPitch);

        if(infusionOutput != null) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            infusionOutput.writeToNBT(tagCompound);
            compound.setTag("InfusionOutput", tagCompound);
        }

        if(infusingItems != null) {
            NBTTagList infusingItemsList = new NBTTagList();
            for(InfusionHandler.InfusingItem infusingItem : infusingItems) {
                infusingItemsList.appendTag(infusingItem.toNBT());
            }
            compound.setTag("InfusingItems", infusingItemsList);
        }

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        if(compound.hasKey("Item")) {
            itemStack = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("Item"));
        } else {
            itemStack = null;
        }

        timeInfusing = compound.getInteger("TimeInfusing");
        lastTime = compound.getLong("LastTime");
        lastPitch = compound.getInteger("LastPitch");

        if(compound.hasKey("InfusionOutput")) {
            infusionOutput = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("InfusionOutput"));
        } else {
            infusionOutput = null;
        }

        if(compound.hasKey("InfusingItems")) {
            NBTTagList infusingItemsList = compound.getTagList("InfusingItems", Constants.NBT.TAG_COMPOUND);
            this.infusingItems = new ArrayList<InfusionHandler.InfusingItem>();
            for(int i = 0; i < infusingItemsList.tagCount(); i++) {
                infusingItems.add(new InfusionHandler.InfusingItem((NBTTagCompound) infusingItemsList.get(i)));
            }
        } else {
            infusingItems = null;
        }
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 1, writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }

    public long lastTime = -1;
    private int lastPitch = -1;
    public void setInfusion(ItemStack infusionOutput, ArrayList<InfusionHandler.InfusingItem> infusingItems) {
        lastTime = -1;
        lastPitch = -1;
        timeInfusing = 0;
        this.infusionOutput = infusionOutput;
        this.infusingItems = infusingItems;
        forceUpdate();
    }

    @Override
    public void update() {
        if(timeInfusing != -1) {
            long currentTime = System.nanoTime();
            if(lastTime == -1) {
                lastTime = currentTime;
            } else if(!worldObj.isRemote) {
                timeInfusing += currentTime - lastTime;
                lastTime = currentTime;
            }

            if(!worldObj.isRemote) {
                int pitch = (int) Math.floor((double) timeInfusing / (InfusionHandler.NANOSECOND / 10));
                if (pitch != lastPitch) {
                    float playingPitch = 1f + ((float)pitch / 8f);
                    worldObj.playSound(null, pos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 1.0f, playingPitch);
                    lastPitch = pitch;
                }
            }

            if(timeInfusing >= InfusionHandler.INFUSION_TIME) {
                if(!worldObj.isRemote) {
                    itemStack = ItemStack.copyItemStack(infusionOutput);
                    stopInfusion(false);
                    forceUpdate();
                } else {
                    worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5, pos.getY() + 1.3, pos.getZ() + 0.5, 0, 0.05, 0);
                }
            }
        }
    }

    public void dropItems() {
        dropItem(ItemStack.copyItemStack(itemStack), 0, 0, 0);
        itemStack = null;

        if(timeInfusing != -1) {
            double fraction = 1.0 - ((double) timeInfusing / (double) InfusionHandler.INFUSION_TIME);
            for (InfusionHandler.InfusingItem infusingItem : infusingItems) {
                BlockPos origin = infusingItem.origin;
                double xOffset = (origin.getX() - pos.getX()) * fraction;
                double yOffset = (origin.getY() - pos.getY()) * fraction;
                double zOffset = (origin.getZ() - pos.getZ()) * fraction;
                dropItem(infusingItem.itemStack, xOffset, yOffset, zOffset);
            }

            stopInfusion(true);
        }

        forceUpdate();
    }

    private void stopInfusion(boolean forced) {
        worldObj.playSound(null, pos, forced ? SoundEvents.ENTITY_GENERIC_EXPLODE : SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.0f, 1.0f);

        timeInfusing = -1;
        infusionOutput = null;
        infusingItems = null;
    }

    private void dropItem(ItemStack stack, double xOffset, double yOffset, double zOffset) {
        double yOff = Math.sin(Math.toRadians((System.currentTimeMillis() / 10) % 360));
        EntityItem item = new EntityItem(worldObj, pos.getX() + xOffset + 0.5, pos.getY() + yOffset + 1.3 + (yOff / 10), pos.getZ() + zOffset + 0.5, stack);
        item.setPickupDelay(0);
        worldObj.spawnEntityInWorld(item);
    }

    private void forceUpdate() {
        worldObj.notifyBlockUpdate(pos, getBlockType().getDefaultState(), getBlockType().getDefaultState(), 0);
        markDirty();
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Nullable
    @Override
    public ItemStack getStackInSlot(int index) {
        if(index > 0 || index < 0)
            return null;
        return itemStack;
    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count) {
        if(index > 0 || index < 0)
            return null;
        ItemStack itemStackCount = ItemStack.copyItemStack(itemStack);
        itemStackCount.stackSize = count;
        itemStack.stackSize -= count;
        if(itemStack.stackSize == 0) {
            itemStack = null;
            forceUpdate();
        }
        return itemStackCount;
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack copy = itemStack.copy();
        itemStack = null;
        forceUpdate();
        return copy;
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
        if(index > 0 || index < 0)
            return;
        itemStack = stack;

        forceUpdate();
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return !(index > 0 || index < 0) && itemStack == null;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        itemStack = null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }
}
