package com.mrbbot.infusio.gui.guide;

import com.mrbbot.infusio.gui.GuiInfusersGuide;
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

public class PageCrafting implements IPageSingle {
    private ArrayList<PositionedItemStack> itemStacks;
    private CraftingRecipe recipe;

    PageCrafting(CraftingRecipe recipe) {
        itemStacks = new ArrayList<PositionedItemStack>();
        this.recipe = recipe;

        itemStacks.add(new PositionedItemStack(recipe.getOutput(), 94, 37));
        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 3; x++) {
                ItemStack stack = recipe.getInputs()[(y * 3) + x];
                if(stack != null)
                    itemStacks.add(new PositionedItemStack(stack, 4 + (x * 18), 19 + (y * 18)));
            }
        }
    }

    @Override
    public void renderPage(GuiInfusersGuide gui, FontRenderer fontRendererObj, RenderItem itemRender, int width, int height, int mouseX, int mouseY, int xOffset, int yOffset) {
        fontRendererObj.drawString(TextFormatting.UNDERLINE + (recipe.isShapeless() ? "Shapeless" : "Shaped") + " Crafting" + TextFormatting.RESET, xOffset, yOffset, 0);
        GlStateManager.color(1, 1, 1, 1);
        Minecraft.getMinecraft().getTextureManager().bindTexture(INFUSERS_GUIDE);
        drawModalRectWithCustomSizedTexture(xOffset + 64, yOffset + 37, 46, 360, 22, 15, 512, 512);
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
