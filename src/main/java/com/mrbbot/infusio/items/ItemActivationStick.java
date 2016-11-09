package com.mrbbot.infusio.items;

import com.mrbbot.infusio.Reference;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemActivationStick extends Item {
    public ItemActivationStick() {
        setUnlocalizedName(Reference.InfusioItems.ACTIVATION_STICK.getUnlocalizedName());
        setRegistryName(Reference.InfusioItems.ACTIVATION_STICK.getRegistryName());
        setMaxStackSize(1);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }
}
