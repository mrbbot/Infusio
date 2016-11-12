package com.mrbbot.infusio.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

class NavigationButton extends GuiButton {
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
