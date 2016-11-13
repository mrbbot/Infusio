package com.mrbbot.infusio.infusion;

import com.mrbbot.infusio.init.ModRecipes;
import com.mrbbot.infusio.tileentities.TileEntityPedestal;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class Infusion {
    private ItemStack output;
    private boolean advanced;
    private ItemStack main;
    private ItemStack[] inputs;

    Infusion(ItemStack output, boolean advanced, Object main, Object... inputs) {
        this.output = output;
        this.advanced = advanced;
        this.main = ModRecipes.convertToItemStack(main);
        this.inputs = ModRecipes.convertToItemStacks(inputs);
    }

    public ItemStack getOutput() {
        return output;
    }

    public boolean isAdvanced() {
        return advanced;
    }

    public ItemStack getMain() {
        return main;
    }

    public ItemStack[] getInputs() {
        return inputs;
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
