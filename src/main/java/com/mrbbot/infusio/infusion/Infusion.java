package com.mrbbot.infusio.infusion;

import com.mrbbot.infusio.tileentities.TileEntityPedestal;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

class Infusion {
    ItemStack output;
    private ItemStack main;
    private ItemStack[] inputs;

    Infusion(ItemStack output, Object main, Object... inputs) {
        this.output = output;
        this.main = convertToItemStack(main);
        this.inputs = convertToItemStacks(inputs);
    }

    private ItemStack convertToItemStack(Object item) {
        if(item instanceof Item) {
            return new ItemStack((Item) item);
        } else if(item instanceof Block) {
            return new ItemStack((Block) item);
        }

        return new ItemStack(Blocks.AIR, 0);
    }

    private ItemStack[] convertToItemStacks(Object[] items) {
        ArrayList<ItemStack> itemStacksList = new ArrayList<ItemStack>();

        for(Object item : items)
            itemStacksList.add(convertToItemStack(item));

        ItemStack[] itemStacks = new ItemStack[itemStacksList.size()];
        itemStacksList.toArray(itemStacks);
        return itemStacks;
    }

    private int indexOfItem(ArrayList<TileEntityPedestal> availablePedestals, ArrayList<Integer> indicesUsed, ItemStack item) {
        for(int i = 0; i < availablePedestals.size(); i++) {
            if(availablePedestals.get(i).getStackInSlot(0).getItem().equals(item.getItem()) && !indicesUsed.contains(i))
                return i;
        }
        return -1;
    }

    ArrayList<Integer> canInfuseWith(ItemStack centreItem, ArrayList<TileEntityPedestal> availablePedestals) {
        if(!centreItem.getItem().equals(main.getItem()))
            return null;

        ArrayList<Integer> indicesUsed = new ArrayList<Integer>();

        for(ItemStack input : inputs) {
            int indexOfInput = indexOfItem(availablePedestals, indicesUsed, input);
            if(indexOfInput == -1)
                return null;
            indicesUsed.add(indexOfInput);
        }

        return indicesUsed;
    }
}
