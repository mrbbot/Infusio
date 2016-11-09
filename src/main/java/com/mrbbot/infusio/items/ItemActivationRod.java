package com.mrbbot.infusio.items;

import com.mrbbot.infusio.Infusio;
import com.mrbbot.infusio.Reference;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemActivationRod extends Item {
    public ItemActivationRod() {
        setUnlocalizedName(Reference.InfusioItems.ACTIVATION_ROD.getUnlocalizedName());
        setRegistryName(Reference.InfusioItems.ACTIVATION_ROD.getRegistryName());
        setMaxStackSize(1);
        setCreativeTab(Infusio.CREATIVE_TAB);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }
}
