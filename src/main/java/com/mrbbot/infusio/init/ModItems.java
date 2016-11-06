package com.mrbbot.infusio.init;

import com.mrbbot.infusio.items.ItemActivationRod;
import com.mrbbot.infusio.items.ItemActivationStick;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {

    public static Item activationStick;
    public static Item activationRod;

    public static void init() {
        activationStick = new ItemActivationStick();
        activationRod = new ItemActivationRod();
    }

    public static void register() {
        GameRegistry.register(activationStick);
        GameRegistry.register(activationRod);
    }

    public static void registerRenderers() {
        registerRender(activationStick);
        registerRender(activationRod);
    }

    private static void registerRender(Item item) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
