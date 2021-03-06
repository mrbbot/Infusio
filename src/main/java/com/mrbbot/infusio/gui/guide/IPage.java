package com.mrbbot.infusio.gui.guide;

import com.mrbbot.infusio.gui.GuiInfusersGuide;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderItem;

public interface IPage {
    void renderPageBackground(int width, int height);

    void renderPage(GuiInfusersGuide gui, FontRenderer fontRendererObj, RenderItem itemRender, int width, int height, int mouseX, int mouseY);
}
