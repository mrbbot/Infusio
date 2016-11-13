package com.mrbbot.infusio.infusion;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class InfusionRegistry {
    static ArrayList<Infusion> infusions = new ArrayList<Infusion>();
    private static ArrayList<Item> outputItems = new ArrayList<Item>();

    public static Infusion registerInfusion(ItemStack output, boolean advanced, Object main, Object... inputs) {
        Infusion infusion = new Infusion(output, advanced, main, inputs);
        infusions.add(infusion);
        outputItems.add(output.getItem());
        return infusion;
    }

    public static boolean isOutputItem(ItemStack item) {
        return outputItems.contains(item.getItem());
    }
}
