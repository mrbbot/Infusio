package com.mrbbot.infusio.gui.guide;

import com.mrbbot.infusio.gui.GuiInfusersGuide;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderItem;

import static net.minecraft.client.gui.Gui.drawModalRectWithCustomSizedTexture;

class PageDouble implements IPage {
    private final IPageSingle left;
    private final IPageSingle right;

    PageDouble(IPageSingle left, IPageSingle right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void renderPage(GuiInfusersGuide gui, FontRenderer fontRendererObj, RenderItem itemRender, int width, int height, int mouseX, int mouseY) {
        int xStart = (width / 2) - (278 / 2);
        int yStart = (height / 2) - (180 / 2);
        drawModalRectWithCustomSizedTexture(xStart, yStart, 0, 0, 278, 180, 512, 512);
        left.renderPage(gui, fontRendererObj, itemRender, 114, 138, mouseX, mouseY, xStart + 18, yStart + 14);
        right.renderPage(gui, fontRendererObj, itemRender, 114, 138, mouseX, mouseY, xStart + 146, yStart + 14);
    }
}
