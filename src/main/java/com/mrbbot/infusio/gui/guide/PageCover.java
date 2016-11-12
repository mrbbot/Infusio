package com.mrbbot.infusio.gui.guide;

import com.mrbbot.infusio.gui.GuiInfusersGuide;
import com.mrbbot.infusio.init.ModBlocks;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

import static net.minecraft.client.gui.Gui.drawModalRectWithCustomSizedTexture;

class PageCover implements IPage {
    private final static ItemStack PEDESTAL = new ItemStack(ModBlocks.pedestal, 1);

    private final boolean isFrontPage;

    PageCover(boolean isFrontPage) {
        this.isFrontPage = isFrontPage;
    }

    @Override
    public void renderPage(GuiInfusersGuide gui, FontRenderer fontRendererObj, RenderItem itemRender, int width, int height, int mouseX, int mouseY) {
        drawModalRectWithCustomSizedTexture((width / 2) - (146 / 2), (height / 2) - (180 / 2), 0, 180, 146, 180, 512, 512);

        if(isFrontPage) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(2, 2, 2);
            gui.drawCenteredString(fontRendererObj, "The", (width / 4), (height / 4) + 7, 0xFFFFFFFF);
            gui.drawCenteredString(fontRendererObj, "Infuser's", (width / 4), (height / 4) + 17, 0xFFFFFFFF);
            gui.drawCenteredString(fontRendererObj, "Guide", (width / 4), (height / 4) + 27, 0xFFFFFFFF);
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.scale(4, 4, 4);
            itemRender.renderItemIntoGUI(PEDESTAL, (width / 8) - 8, (height / 8) - 17);
            GlStateManager.popMatrix();
        }
    }
}
