package com.mrbbot.infusio.blocks;

import com.mrbbot.infusio.Infusio;
import com.mrbbot.infusio.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockScorchedStone extends Block {

    public BlockScorchedStone() {
        super(Material.ROCK);
        setUnlocalizedName(Reference.InfusioBlocks.SCORCHED_STONE.getUnlocalizedName());
        setRegistryName(Reference.InfusioBlocks.SCORCHED_STONE.getRegistryName());
        setHardness(1.5f);
        setResistance(10.0f);
        setHarvestLevel("pickaxe", 0);
        setCreativeTab(Infusio.CREATIVE_TAB);
    }
}
