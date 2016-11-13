package com.mrbbot.infusio.init;

import com.mrbbot.infusio.gui.guide.CraftingRecipe;
import com.mrbbot.infusio.infusion.Infusion;
import com.mrbbot.infusio.infusion.InfusionRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;

public class ModRecipes {
    public static CraftingRecipe craftingActivationStick;
    public static CraftingRecipe craftingPedestal;

    public static Infusion infusionActivationRod;
    public static Infusion infusionGlowstoneDust;
    public static Infusion infusionCrafting;

    public static void register() {
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.activationStick), "  R", " S ", "R  ", 'R', Items.REDSTONE, 'S', Items.STICK);
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.activationStick), "R  ", " S ", "  R", 'R', Items.REDSTONE, 'S', Items.STICK);
        craftingActivationStick = new CraftingRecipe(false, ModItems.activationStick,
                null, null, Items.REDSTONE,
                null, Items.STICK, null,
                Items.REDSTONE, null, null
        );

        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.pedestal), "SPS", " S ", "SDS", 'S', ModBlocks.scorchedStone, 'P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 'D', ModItems.scorchedDust);
        craftingPedestal = new CraftingRecipe(false, ModBlocks.pedestal,
                ModBlocks.scorchedStone, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, ModBlocks.scorchedStone,
                null, ModBlocks.scorchedStone, null,
                ModBlocks.scorchedStone, ModItems.scorchedDust, ModBlocks.scorchedStone
        );

        infusionActivationRod = InfusionRegistry.registerInfusion(new ItemStack(ModItems.activationRod), false, Items.BLAZE_ROD, Blocks.REDSTONE_BLOCK, Items.BLAZE_POWDER, Items.MAGMA_CREAM, Blocks.REDSTONE_BLOCK, Items.BLAZE_POWDER, Items.MAGMA_CREAM);
        infusionGlowstoneDust = InfusionRegistry.registerInfusion(new ItemStack(Items.GLOWSTONE_DUST), true, Items.REDSTONE, Blocks.TORCH);
        infusionCrafting = InfusionRegistry.registerInfusion(new ItemStack(Items.APPLE), false, Blocks.CRAFTING_TABLE, Blocks.CHEST, Items.IRON_INGOT, Blocks.CHEST, Items.IRON_INGOT);
    }

    public static ItemStack convertToItemStack(Object item) {
        if(item == null)
            return null;

        if(item instanceof Item) {
            return new ItemStack((Item) item);
        } else if(item instanceof Block) {
            return new ItemStack((Block) item);
        } else if(item instanceof ItemStack) {
            return (ItemStack) item;
        }

        return null;
    }

    public static ItemStack[] convertToItemStacks(Object[] items) {
        ArrayList<ItemStack> itemStacksList = new ArrayList<ItemStack>();

        for(Object item : items)
            itemStacksList.add(convertToItemStack(item));

        ItemStack[] itemStacks = new ItemStack[itemStacksList.size()];
        itemStacksList.toArray(itemStacks);
        return itemStacks;
    }
}
