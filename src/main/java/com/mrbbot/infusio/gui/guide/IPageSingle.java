package com.mrbbot.infusio.gui.guide;

import com.mrbbot.infusio.gui.GuiInfusersGuide;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderItem;

interface IPageSingle {
    void renderPage(GuiInfusersGuide gui, FontRenderer fontRendererObj, RenderItem itemRender, int width, int height, int mouseX, int mouseY, int xOffset, int yOffset);
}
