package com.mrbbot.infusio.gui;

import com.mrbbot.infusio.gui.guide.IPage;
import com.mrbbot.infusio.gui.guide.PageRegistry;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiInfusersGuide extends GuiScreen {
    static final int BUTTON_NAV_FORWARD = 0;
    static final int BUTTON_NAV_BACK = 1;

    final static ResourceLocation INFUSERS_GUIDE = new ResourceLocation("infusio", "textures/gui/infusers_guide.png");
    private int currentPageIndex = 0;
    private IPage currentPage;

    @Override
    public void initGui() {
        currentPage = PageRegistry.getPages().get(currentPageIndex);
        boolean firstPage = currentPageIndex == 0;
        boolean lastPage = currentPageIndex == PageRegistry.getPages().size() - 1;
        boolean doublePage = !(firstPage || lastPage);
        if(!lastPage)
            buttonList.add(new NavigationButton(true, doublePage, width, height));
        if(!firstPage)
            buttonList.add(new NavigationButton(false, doublePage, width, height));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.id == BUTTON_NAV_FORWARD)
            currentPageIndex++;
        else if(button.id == BUTTON_NAV_BACK)
            currentPageIndex--;
        buttonList.clear();
        initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(INFUSERS_GUIDE);

        currentPage.renderPage(this, fontRendererObj, itemRender, width, height, mouseX, mouseY);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
}
