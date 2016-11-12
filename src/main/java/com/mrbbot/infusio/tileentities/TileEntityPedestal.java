package com.mrbbot.infusio.tileentities;

import com.mrbbot.infusio.infusion.InfusionHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class TileEntityPedestal extends TileEntity implements IInventory, ITickable {
    private ItemStack itemStack;

    public int timeInfusing = -1;
    public ItemStack infusionOutput;
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
                    System.out.println(playingPitch);
                    worldObj.playSound(null, pos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 1.0f, playingPitch);
                    lastPitch = pitch;
                }
            }

            if(!worldObj.isRemote && timeInfusing >= InfusionHandler.INFUSION_TIME) {
                itemStack = ItemStack.copyItemStack(infusionOutput);
                timeInfusing = -1;
                infusionOutput = null;
                infusingItems = null;
                forceUpdate();
            }
        }
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
        itemStack.stackSize -= count;
        return itemStack;
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack copy = itemStack.copy();
        itemStack = null;
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
        if(index > 0 || index < 0)
            return false;
        return itemStack == null;
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
