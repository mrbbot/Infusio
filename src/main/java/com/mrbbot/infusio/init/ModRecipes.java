package com.mrbbot.infusio.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {
    public static void register() {
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.activationStick), "  R", " S ", "R  ", 'R', Items.REDSTONE, 'S', Items.STICK);
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.activationStick), "R  ", " S ", "  R", 'R', Items.REDSTONE, 'S', Items.STICK);

        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.pedestal), "SPS", " S ", "SDS", 'S', ModBlocks.scorchedStone, 'P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 'D', ModItems.scorchedDust);
    }
}
