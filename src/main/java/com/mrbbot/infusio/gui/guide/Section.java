package com.mrbbot.infusio.gui.guide;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;

public class Section {
    private final ItemStack itemStack;
    private final String title;
    private ArrayList<IPageSingle> pagesList;

    Section(ItemStack itemStack, String title, IPageSingle... pages) {
        this.itemStack = itemStack;
        this.title = title;
        pagesList = new ArrayList<IPageSingle>();
        pagesList.add(new PageSectionTitle(itemStack, title));
        Collections.addAll(pagesList, pages);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<IPageSingle> getPagesList() {
        return pagesList;
    }
}
