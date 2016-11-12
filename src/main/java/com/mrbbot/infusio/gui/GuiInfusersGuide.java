package com.mrbbot.infusio.gui;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.mrbbot.infusio.gui.guide.IPage;
import com.mrbbot.infusio.gui.guide.PageContents;
import com.mrbbot.infusio.gui.guide.PageRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;

public class GuiInfusersGuide extends GuiScreen {
    private static final int BUTTON_NAV_FORWARD = 0;
    private static final int BUTTON_NAV_BACK = 1;
    private static final int BUTTON_CONTENTS = 2;

    private final static ResourceLocation INFUSERS_GUIDE = new ResourceLocation("infusio", "textures/gui/infusers_guide.png");
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

        if(currentPage instanceof PageContents) {
            ArrayList<PageRegistry.Link> contents = PageRegistry.getContents();
            int buttonX = (width / 2) - (278 / 2) + 18;
            int buttonY = (height / 2) - (180 / 2) + 14;
            for(PageRegistry.Link link : contents) {
                buttonList.add(new ContentsButton(link, buttonX, buttonY));
                buttonY += 20;
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.id == BUTTON_NAV_FORWARD) {
            currentPageIndex++;
        } else if(button.id == BUTTON_NAV_BACK) {
            if(Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                currentPageIndex = 1;
            } else {
                currentPageIndex--;
            }
        } else if(button.id == BUTTON_CONTENTS && button instanceof ContentsButton) {
            currentPageIndex = ((ContentsButton) button).getLinkedPageIndex();
        }
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

    private class NavigationButton extends GuiButton {
        private boolean forward;

        NavigationButton(boolean forward, boolean doublePage, int screenWidth, int screenHeight) {
            super(forward ? GuiInfusersGuide.BUTTON_NAV_FORWARD : GuiInfusersGuide.BUTTON_NAV_BACK, 0, 0, 18, 10, "");
            this.forward = forward;

            int yStart = (screenHeight / 2) - (180 / 2);

            if(doublePage) {
                int xStart = (screenWidth / 2) - (278 / 2);
                if(forward) {
                    xPosition = xStart + 235;
                } else {
                    xPosition = xStart + 25;
                }
                yPosition = yStart + 155;
            } else {
                int xStart = (screenWidth / 2) - (146 / 2);
                if(forward) {
                    xPosition = xStart + 121;
                } else {
                    xPosition = xStart + 7;
                }
                yPosition = yStart + 163;
            }
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY) {
            mc.getTextureManager().bindTexture(GuiInfusersGuide.INFUSERS_GUIDE);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            this.mouseDragged(mc, mouseX, mouseY);
            drawModalRectWithCustomSizedTexture(xPosition, yPosition, this.hovered ? 23 : 0, forward ? 360 : 373, 18, 10, 512, 512);
        }
    }

    private class ContentsButton extends GuiButton {
        private PageRegistry.Link link;

        ContentsButton(PageRegistry.Link link, int x, int y) {
            super(GuiInfusersGuide.BUTTON_CONTENTS, x, y, 114, 20, "");
            this.link = link;
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY) {
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            this.mouseDragged(mc, mouseX, mouseY);
            RenderHelper.enableGUIStandardItemLighting();
            itemRender.renderItemIntoGUI(link.getSection().getItemStack(), xPosition, yPosition + 2);
            fontRendererObj.drawString((hovered ? ChatFormatting.ITALIC : ChatFormatting.RESET) + link.getSection().getTitle() + ChatFormatting.RESET, xPosition + 20, yPosition + 6, 0, false);
        }

        int getLinkedPageIndex() {
            return link.getPageIndex();
        }
    }

}
