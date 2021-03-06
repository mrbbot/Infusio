package com.mrbbot.infusio;

import com.mrbbot.infusio.event.EventHandler;
import com.mrbbot.infusio.gui.GuiHandler;
import com.mrbbot.infusio.gui.guide.PageRegistry;
import com.mrbbot.infusio.init.ModBlocks;
import com.mrbbot.infusio.init.ModItems;
import com.mrbbot.infusio.init.ModRecipes;
import com.mrbbot.infusio.proxy.ICommonProxy;
import com.mrbbot.infusio.tileentities.TileEntityPedestal;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION)
public class Infusio {
    public Logger logger = LogManager.getLogger(Reference.MOD_NAME);

    @Mod.Instance
    public static Infusio instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static ICommonProxy proxy;

    public static final CreativeTabs CREATIVE_TAB = new InfusioTab();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModItems.init();
        ModItems.register();

        ModBlocks.init();
        ModBlocks.register();

        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();

        ModRecipes.register();

        GameRegistry.registerTileEntity(TileEntityPedestal.class, Reference.MOD_ID + "_pedestal");

        NetworkRegistry.INSTANCE.registerGuiHandler(Infusio.instance, new GuiHandler());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        PageRegistry.registerPages();
    }
}
