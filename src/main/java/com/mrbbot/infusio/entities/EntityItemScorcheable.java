package com.mrbbot.infusio.entities;

import com.mrbbot.infusio.Infusio;
import com.mrbbot.infusio.init.ModBlocks;
import com.mrbbot.infusio.init.ModItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

public class EntityItemScorcheable extends EntityItem {

    private boolean scorched = false;

    public EntityItemScorcheable(World worldIn, double x, double y, double z, ItemStack stack) {
        super(worldIn, x, y, z, stack);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if(source.isFireDamage() && !scorched) {
            scorched = true;

            Infusio.instance.logger.log(Level.INFO, "Item scorched");

            ItemStack scorchedItemStack = new ItemStack((getEntityItem().getItem().equals(Items.REDSTONE) ? ModItems.scorchedDust : Item.getItemFromBlock(ModBlocks.scorchedStone)), getEntityItem().stackSize);
            EntityItem scorchedEntityItem = new EntityItem(worldObj, posX, posY + 0.2, posZ, scorchedItemStack);
            scorchedEntityItem.motionX = 0;
            scorchedEntityItem.motionY = 0.5;
            scorchedEntityItem.motionZ = 0;
            scorchedEntityItem.setPickupDelay(0);
            worldObj.spawnEntityInWorld(scorchedEntityItem);

            kill();
        }
        return super.attackEntityFrom(source, amount);
    }
}
