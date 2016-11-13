package com.mrbbot.infusio.gui.guide;

import com.mrbbot.infusio.gui.GuiInfusersGuide;
import com.mrbbot.infusio.infusion.Infusion;
import com.mrbbot.infusio.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;

import static com.mrbbot.infusio.gui.GuiInfusersGuide.INFUSERS_GUIDE;
import static net.minecraft.client.gui.Gui.drawModalRectWithCustomSizedTexture;

class PageInfusion implements IPageSingle {
    private static final int CIRCLE_RADIUS = 38;
    private static final ItemStack ACTIVATION_STICK = new ItemStack(ModItems.activationStick);
    private static final ItemStack ACTIVATION_ROD = new ItemStack(ModItems.activationRod);

    private ArrayList<PositionedItemStack> itemStacks;

    PageInfusion(Infusion infusion) {
        itemStacks = new ArrayList<PositionedItemStack>();

        itemStacks.add(new PositionedItemStack(ItemStack.copyItemStack(infusion.getMain()), 49, 55));

        int totalInputs = infusion.getInputs().length;
        double oneAngle = 360.0 / (double)totalInputs;
        for(int i = 0; i < totalInputs; i++) {
            double angle = oneAngle * i;

            int x = 0;
            int y = 0;

            if(angle == 0) {
                y = -CIRCLE_RADIUS;
            } else if(0 < angle && angle < 90) {
                angle = Math.toRadians(90 - angle);
                x = (int) (Math.cos(angle) * CIRCLE_RADIUS);
                y = (int) -(Math.sin(angle) * CIRCLE_RADIUS);
            } else if(angle == 90) {
                x = CIRCLE_RADIUS;
            } else if(90 < angle && angle < 180) {
                angle = Math.toRadians(180 - angle);
                x = (int) (Math.sin(angle) * CIRCLE_RADIUS);
                y = (int) (Math.cos(angle) * CIRCLE_RADIUS);
            } else if(angle == 180) {
                y = CIRCLE_RADIUS;
            } else if(180 < angle && angle < 270) {
                angle = Math.toRadians(270 - angle);
                x = (int) -(Math.cos(angle) * CIRCLE_RADIUS);
                y = (int) (Math.sin(angle) * CIRCLE_RADIUS);
            } else if(angle == 270) {
                x = -CIRCLE_RADIUS;
            } else if(270 < angle && angle < 360) {
                angle = Math.toRadians(360 - angle);
                x = (int) -(Math.sin(angle) * CIRCLE_RADIUS);
                y = (int) -(Math.cos(angle) * CIRCLE_RADIUS);
            }

            ItemStack itemStack = infusion.getInputs()[i];
            itemStacks.add(new PositionedItemStack(itemStack, x + 49, y + 55));
        }

        itemStacks.add(new PositionedItemStack(infusion.isAdvanced() ? ACTIVATION_ROD : ACTIVATION_STICK, 23, 118));
        itemStacks.add(new PositionedItemStack(infusion.getOutput(), 75, 118));
    }

    @Override
    public void renderPage(GuiInfusersGuide gui, FontRenderer fontRendererObj, RenderItem itemRender, int width, int height, int mouseX, int mouseY, int xOffset, int yOffset) {
        fontRendererObj.drawString(TextFormatting.UNDERLINE + "Pedestal Infusion" + TextFormatting.RESET, xOffset, yOffset, 0);
        GlStateManager.color(1, 1, 1, 1);
        Minecraft.getMinecraft().getTextureManager().bindTexture(INFUSERS_GUIDE);
        drawModalRectWithCustomSizedTexture(xOffset + 46, yOffset + 118, 46, 360, 22, 15, 512, 512);
        RenderHelper.enableGUIStandardItemLighting();
        for(PositionedItemStack itemStack : itemStacks) {
            itemStack.renderItem(gui, xOffset, yOffset);
        }
        for(PositionedItemStack itemStack : itemStacks) {
            itemStack.renderItemTooltip(gui, xOffset, yOffset, mouseX, mouseY);
        }
        RenderHelper.disableStandardItemLighting();
        GlStateManager.color(1, 1, 1, 1);
    }
}
