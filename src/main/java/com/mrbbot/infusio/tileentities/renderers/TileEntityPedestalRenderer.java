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

            renderItemStack(stack, 0, 0, 0);

            GlStateManager.popMatrix();
            GlStateManager.popAttrib();
        }
    }

    private void renderItemStack(ItemStack itemStack, double xOffset, double yOffset, double zOffset) {
        GlStateManager.pushMatrix();

        double yOff = Math.sin(Math.toRadians((System.currentTimeMillis() / 10) % 360));
        GlStateManager.translate(0.5 + xOffset, 1.3 + (yOff / 10) + yOffset, 0.5 + zOffset);

        GlStateManager.pushMatrix();

        long angle = (System.currentTimeMillis() / 20) % 360;
        GlStateManager.rotate(angle, 0, 1, 0);

        GlStateManager.scale(0.4f, 0.4f, 0.4f);

        Minecraft.getMinecraft().getRenderItem().renderItem(itemStack, ItemCameraTransforms.TransformType.NONE);

        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }
}
