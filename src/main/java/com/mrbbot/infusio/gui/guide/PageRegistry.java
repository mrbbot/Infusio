package com.mrbbot.infusio.gui.guide;

import java.util.ArrayList;

public class PageRegistry {
    private static ArrayList<IPage> pages = new ArrayList<IPage>();

    public static void registerPages() {
        pages.add(new PageCover(true));

        pages.add(new PageDouble(new PageText("infusionGuide.page1"), new PageText("infusionGuide.page2")));

        pages.add(new PageCover(false));
    }

    public static ArrayList<IPage> getPages() {
        return pages;
    }
}
