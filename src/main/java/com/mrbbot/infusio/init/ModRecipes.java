package com.mrbbot.infusio.init;

import com.mrbbot.infusio.infusion.Infusion;
import com.mrbbot.infusio.infusion.InfusionRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {
    public static Infusion infusionActivationRod;
    public static Infusion infusionGlowstoneDust;
    public static Infusion infusionCrafting;

    public static void register() {
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.activationStick), "  R", " S ", "R  ", 'R', Items.REDSTONE, 'S', Items.STICK);
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.activationStick), "R  ", " S ", "  R", 'R', Items.REDSTONE, 'S', Items.STICK);

        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.pedestal), "SPS", " S ", "SDS", 'S', ModBlocks.scorchedStone, 'P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 'D', ModItems.scorchedDust);

        infusionActivationRod = InfusionRegistry.registerInfusion(new ItemStack(ModItems.activationRod), false, Items.BLAZE_ROD, Blocks.REDSTONE_BLOCK, Items.BLAZE_POWDER, Items.MAGMA_CREAM, Blocks.REDSTONE_BLOCK, Items.BLAZE_POWDER, Items.MAGMA_CREAM);
        infusionGlowstoneDust = InfusionRegistry.registerInfusion(new ItemStack(Items.GLOWSTONE_DUST), true, Items.REDSTONE, Blocks.TORCH);
        infusionCrafting = InfusionRegistry.registerInfusion(new ItemStack(Items.APPLE), false, Blocks.CRAFTING_TABLE, Blocks.CHEST, Items.IRON_INGOT, Blocks.CHEST, Items.IRON_INGOT);
    }
}
