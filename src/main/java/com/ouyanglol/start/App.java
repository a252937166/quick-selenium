package com.ouyanglol.start;

import com.ouyanglol.core.QuickBase;
import com.ouyanglol.crawler.ComicCrawler;

/**
 * Package: com.ouyanglol.start
 *
 * @Author: Ouyang
 * @Date: 2018/2/2
 */
public class App {
    public static void main(String[] args) throws Exception {
        QuickBase quickBase = QuickBase.getInstance("crawler");
        ComicCrawler crawler = (ComicCrawler) quickBase.getQuick("ComicCrawler");
        crawler.start("https://manhua.dmzj.com/yiquanchaoren/");
    }
}
