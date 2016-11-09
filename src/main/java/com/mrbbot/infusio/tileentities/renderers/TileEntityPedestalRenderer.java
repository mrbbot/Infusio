package com.mrbbot.infusio.tileentities.renderers;

import com.mrbbot.infusio.tileentities.TileEntityPedestal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

public class TileEntityPedestalRenderer extends TileEntitySpecialRenderer<TileEntityPedestal> {

    @Override
    public void renderTileEntityAt(TileEntityPedestal te, double x, double y, double z, float partialTicks, int destroyStage) {
        ItemStack stack = te.getStackInSlot(0);
        if (stack != null) {
            GlStateManager.pushAttrib();
            GlStateManager.pushMatrix();

            GlStateManager.translate(x, y, z);
            GlStateManager.disableRescaleNormal();

            RenderHelper.enableStandardItemLighting();
            GlStateManager.enableLighting();
            GlStateManager.pushMatrix();

            GlStateManager.translate(0.5, 1.3, 0.5);
            GlStateManager.scale(0.4f, 0.4f, 0.4f);

            Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.NONE);

            GlStateManager.popMatrix();

            GlStateManager.popMatrix();
            GlStateManager.popAttrib();
        }
    }
}
