package com.mrbbot.infusio.items;

import com.mrbbot.infusio.Infusio;
import com.mrbbot.infusio.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemInfusersGuide extends Item {
    public ItemInfusersGuide() {
        setUnlocalizedName(Reference.InfusioItems.INFUSERS_GUIDE.getUnlocalizedName());
        setRegistryName(Reference.InfusioItems.INFUSERS_GUIDE.getRegistryName());
        setMaxStackSize(1);
        setCreativeTab(Infusio.CREATIVE_TAB);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if(worldIn.isRemote) {
            playerIn.openGui(Infusio.instance, Reference.GUI_INFUSERS_GUIDE, worldIn, playerIn.getPosition().getX(), playerIn.getPosition().getY(), playerIn.getPosition().getZ());
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }
}
