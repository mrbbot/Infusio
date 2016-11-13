package com.mrbbot.infusio.gui.guide;

import com.mrbbot.infusio.init.ModRecipes;
import net.minecraft.item.ItemStack;

public class CraftingRecipe {
    private boolean shapeless;
    private ItemStack output;
    private ItemStack[] inputs;

    public CraftingRecipe(boolean shapeless, Object output, Object... inputs) {
        this.shapeless = shapeless;
        this.output = ModRecipes.convertToItemStack(output);
        this.inputs = ModRecipes.convertToItemStacks(inputs);
    }

    public boolean isShapeless() {
        return shapeless;
    }

    public ItemStack getOutput() {
        return output;
    }

    public ItemStack[] getInputs() {
        return inputs;
    }
}
