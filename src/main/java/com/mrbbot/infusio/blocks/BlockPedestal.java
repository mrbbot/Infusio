package com.mrbbot.infusio.blocks;

import com.mrbbot.infusio.Infusio;
import com.mrbbot.infusio.Reference;
import com.mrbbot.infusio.infusion.InfusionHandler;
import com.mrbbot.infusio.infusion.InfusionRegistry;
import com.mrbbot.infusio.init.ModItems;
import com.mrbbot.infusio.tileentities.TileEntityPedestal;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockPedestal extends Block implements ITileEntityProvider {

    public BlockPedestal() {
        super(Material.ROCK);
        setUnlocalizedName(Reference.InfusioBlocks.PEDESTAL.getUnlocalizedName());
        setRegistryName(Reference.InfusioBlocks.PEDESTAL.getRegistryName());
        setHardness(1.5f);
        setResistance(10.0f);
        setHarvestLevel("pickaxe", 0);
        setCreativeTab(Infusio.CREATIVE_TAB);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity != null && tileEntity instanceof TileEntityPedestal) {
            TileEntityPedestal tileEntityPedestal = (TileEntityPedestal) tileEntity;

            if(!worldIn.isRemote) {
                if (heldItem != null) {
                    if (heldItem.getItem().equals(ModItems.activationStick) || heldItem.getItem().equals(ModItems.activationRod)) {
                        boolean itemInfused = InfusionHandler.infuseWith(tileEntityPedestal);
                        if (!itemInfused)
                            worldIn.playSound(null, pos, SoundEvents.BLOCK_NOTE_BASS, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        return true;
                    }
                }

                if (tileEntityPedestal.getStackInSlot(0) == null) {
                    if (heldItem != null) {
                        ItemStack oneItem = heldItem.copy();
                        oneItem.stackSize = 1;
                        tileEntityPedestal.setInventorySlotContents(0, oneItem);
                        heldItem.stackSize--;
                    }
                } else {
                    tileEntityPedestal.dropItems();
                }

            }
        }
        return true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity != null && tileEntity instanceof TileEntityPedestal) {
            TileEntityPedestal tileEntityPedestal = (TileEntityPedestal) tileEntity;

            if(!worldIn.isRemote) {
                if (tileEntityPedestal.getStackInSlot(0) != null) {
                    tileEntityPedestal.dropItems();
                }
            }
        }
        super.breakBlock(worldIn, pos, state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileEntityPedestal) {
            ItemStack stack = ((TileEntityPedestal) tileEntity).getStackInSlot(0);
            if(stack != null) {
                if(InfusionRegistry.isOutputItem(stack))
                    return 2;
                return 1;
            }
        }

        return 0;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityPedestal();
    }
}
