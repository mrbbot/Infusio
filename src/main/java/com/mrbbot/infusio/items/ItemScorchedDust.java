package com.mrbbot.infusio.items;

import com.mrbbot.infusio.Reference;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemScorchedDust extends Item {
    public ItemScorchedDust() {
        setUnlocalizedName(Reference.InfusioItems.SCORCHED_DUST.getUnlocalizedName());
        setRegistryName(Reference.InfusioItems.SCORCHED_DUST.getRegistryName());
    }
}
