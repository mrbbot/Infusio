package com.mrbbot.infusio.proxy;

import com.mrbbot.infusio.init.ModBlocks;
import com.mrbbot.infusio.init.ModItems;
import com.mrbbot.infusio.tileentities.TileEntityPedestal;
import com.mrbbot.infusio.tileentities.renderers.TileEntityPedestalRenderer;
import net.minecraftforge.fml.client.registry.ClientRegistry;

@SuppressWarnings("unused")
public class ClientProxy implements ICommonProxy {
    @Override
    public void init() {
        ModItems.registerRenderers();
        ModBlocks.registerRenderers();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPedestal.class, new TileEntityPedestalRenderer());
    }
}
