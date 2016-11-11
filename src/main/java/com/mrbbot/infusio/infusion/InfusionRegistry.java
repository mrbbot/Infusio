package com.mrbbot.infusio.infusion;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class InfusionRegistry {
    static ArrayList<Infusion> infusions = new ArrayList<Infusion>();

    public static void registerInfusion(ItemStack output, Object main, Object... inputs) {
        infusions.add(new Infusion(output, main, inputs));
    }
}