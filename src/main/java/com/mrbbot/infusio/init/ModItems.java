package com.mrbbot.infusio.init;

import com.mrbbot.infusio.items.ItemActivationRod;
import com.mrbbot.infusio.items.ItemActivationStick;
import com.mrbbot.infusio.items.ItemInfusersGuide;
import com.mrbbot.infusio.items.ItemScorchedDust;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {

    public static Item activationStick;
    public static Item activationRod;
    public static Item scorchedDust;
    public static Item infusersGuide;

    public static void init() {
        activationStick = new ItemActivationStick();
        activationRod = new ItemActivationRod();
        scorchedDust = new ItemScorchedDust();
        infusersGuide = new ItemInfusersGuide();
    }

    public static void register() {
        GameRegistry.register(activationStick);
        GameRegistry.register(activationRod);
        GameRegistry.register(scorchedDust);
        GameRegistry.register(infusersGuide);
    }

    public static void registerRenderers() {
        registerRender(activationStick);
        registerRender(activationRod);
        registerRender(scorchedDust);
        registerRender(infusersGuide);
    }

    private static void registerRender(Item item) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
