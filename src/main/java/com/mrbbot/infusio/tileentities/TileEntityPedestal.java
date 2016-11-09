package com.mrbbot.infusio.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public class TileEntityPedestal extends TileEntity implements IInventory {
    private ItemStack itemStack = null;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if(itemStack != null) {
            compound.setTag("item", itemStack.writeToNBT(new NBTTagCompound()));
        }
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if(compound.hasKey("Item"))
            itemStack = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("Item"));
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