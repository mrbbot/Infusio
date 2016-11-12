package com.mrbbot.infusio.gui.guide;

import com.mrbbot.infusio.Infusio;
import com.mrbbot.infusio.init.ModBlocks;
import com.mrbbot.infusio.init.ModItems;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;

public class PageRegistry {
    private static ArrayList<IPage> pages = new ArrayList<IPage>();
    private static ArrayList<Link> contents;

    public static void registerPages() {
        ArrayList<Section> sections = new ArrayList<Section>();

        sections.add(new Section(new ItemStack(ModItems.activationStick), "Activation Stick",
                new PageText("Activate stuff with sticks"),
                new PageText("Much fun!")));

        sections.add(new Section(new ItemStack(ModBlocks.pedestal), "Pedestal",
                new PageText("PEDESTALS OMG"),
                new PageText("More pedestals"),
                new PageText("YAY!!!!")));

        sections.add(new Section(new ItemStack(ModItems.activationRod), "Rod of Activation",
                new PageText("The all mighty rod of activation"),
                new PageText("Shinny...."),
                new PageText("It used to set things on fire")));

        contents = new ArrayList<Link>();
        int currentPageIndex = 2;
        int currentPage = 3;

        ArrayList<PageDouble> sectionPages = new ArrayList<PageDouble>();

        IPageSingle lastPage = null;
        for(Section section : sections) {
            contents.add(new Link(section, currentPageIndex));
            for(IPageSingle page : section.getPagesList()) {
                if(currentPage % 2 == 0) {
                    sectionPages.add(new PageDouble(lastPage, page));
                    currentPageIndex++;
                } else {
                    lastPage = page;
                }
                currentPage++;
            }
        }

        if(currentPage % 2 == 0) {
            sectionPages.add(new PageDouble(lastPage, null));
        }

        pages.add(new PageTitle(true));
        pages.add(new PageContents());
        pages.addAll(sectionPages);
        pages.add(new PageTitle(false));

        for(Link link : contents) {
            Infusio.instance.logger.log(Level.INFO, "Section: " + link.getSection().getTitle() + " on page index " + link.getPageIndex());
        }
    }

    public static ArrayList<IPage> getPages() {
        return pages;
    }

    public static ArrayList<Link> getContents() {
        return contents;
    }

    public static class Link {
        private Section section;
        private int pageIndex;

        Link(Section section, int pageIndex) {
            this.section = section;
            this.pageIndex = pageIndex;
        }

        public Section getSection() {
            return section;
        }

        public int getPageIndex() {
            return pageIndex;
        }
    }
}
