package com.mrbbot.infusio.infusion;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class InfusionRegistry {
    static ArrayList<Infusion> infusions = new ArrayList<Infusion>();
    static ArrayList<Item> outputItems = new ArrayList<Item>();

    public static void registerInfusion(ItemStack output, Object main, Object... inputs) {
        infusions.add(new Infusion(output, main, inputs));
        outputItems.add(output.getItem());
    }

    public static boolean isOutputItem(ItemStack item) {
        return outputItems.contains(item.getItem());
    }
}
