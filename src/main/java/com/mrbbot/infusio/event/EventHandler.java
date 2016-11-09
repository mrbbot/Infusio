package com.mrbbot.infusio.event;

import com.mrbbot.infusio.entities.EntityItemScorcheable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler {

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if((!event.getWorld().isRemote) && event.getEntity() instanceof EntityItem && !(event.getEntity() instanceof EntityItemScorcheable)) {
            EntityItem item = (EntityItem) event.getEntity();
            ItemStack itemStack = item.getEntityItem();
            if(itemStack.getItem().equals(Items.REDSTONE) || itemStack.getItem().equals(Item.getItemFromBlock(Blocks.STONE))) {
                World world = item.getEntityWorld();

                EntityItemScorcheable entityItemScorcheable = new EntityItemScorcheable(world, item.posX, item.posY, item.posZ, itemStack.copy());
                entityItemScorcheable.motionX = item.motionX;
                entityItemScorcheable.motionY = item.motionY;
                entityItemScorcheable.motionZ = item.motionZ;
                entityItemScorcheable.setPickupDelay(item.cannotPickup() ? 40 : 0);
                world.spawnEntityInWorld(entityItemScorcheable);

                item.setDead();
                event.setCanceled(true);
            }
        }

    }
}
