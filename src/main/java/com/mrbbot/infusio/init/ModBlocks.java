package com.mrbbot.infusio.init;

import com.mrbbot.infusio.blocks.BlockPedestal;
import com.mrbbot.infusio.blocks.BlockScorchedStone;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

    public static Block scorchedStone;
    public static Block pedestal;

    public static void init() {
        scorchedStone = new BlockScorchedStone();
        pedestal = new BlockPedestal();
    }

    public static void register() {
        registerBlock(scorchedStone);
        registerBlock(pedestal);
    }

    private static void registerBlock(Block block) {
        GameRegistry.register(block);
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        GameRegistry.register(itemBlock);
    }

    public static void registerRenderers() {
        registerRender(scorchedStone);
        registerRender(pedestal);
    }

    private static void registerRender(Block block) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }
}
