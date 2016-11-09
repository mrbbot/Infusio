package com.mrbbot.infusio;

import com.mrbbot.infusio.init.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class InfusioTab extends CreativeTabs {
    public InfusioTab() {
        super("tabInfusio");
    }

    @Override
    public Item getTabIconItem() {
        return Item.getItemFromBlock(ModBlocks.pedestal);
    }
}
