package com.mrbbot.infusio.proxy;

import com.mrbbot.infusio.init.ModBlocks;
import com.mrbbot.infusio.init.ModItems;

@SuppressWarnings("unused")
public class ClientProxy implements ICommonProxy {
    @Override
    public void init() {
        ModItems.registerRenderers();
        ModBlocks.registerRenderers();
    }
}
