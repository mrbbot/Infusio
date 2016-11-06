package com.mrbbot.infusio.blocks;

import com.mrbbot.infusio.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;

public class BlockPedestal extends Block {

    public BlockPedestal() {
        super(Material.ROCK);
        setUnlocalizedName(Reference.InfusioBlocks.PEDESTAL.getUnlocalizedName());
        setRegistryName(Reference.InfusioBlocks.PEDESTAL.getRegistryName());
        setHardness(1.5f);
        setResistance(10.0f);
        setHarvestLevel("pickaxe", 0);
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
}
