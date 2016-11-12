package com.mrbbot.infusio.tileentities.renderers;

import com.mrbbot.infusio.infusion.InfusionHandler;
import com.mrbbot.infusio.tileentities.TileEntityPedestal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

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

            if(te.timeInfusing >= 0 && te.timeInfusing <= InfusionHandler.INFUSION_TIME) {
                long currentTime = System.nanoTime();
                if(te.lastTime != -1) {
                    te.timeInfusing += currentTime - te.lastTime;
                    te.lastTime = currentTime;
                }

                BlockPos pos = te.getPos();
                double fraction = 1.0 - ((double) te.timeInfusing / (double) InfusionHandler.INFUSION_TIME);
                for(InfusionHandler.InfusingItem infusingItem : te.infusingItems) {
                    BlockPos origin = infusingItem.origin;
                    double xOffset = (origin.getX() - pos.getX()) * fraction;
                    double yOffset = (origin.getY() - pos.getY()) * fraction;
                    double zOffset = (origin.getZ() - pos.getZ()) * fraction;
                    renderItemStack(infusingItem.itemStack, xOffset, yOffset, zOffset);
                }
            }

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
