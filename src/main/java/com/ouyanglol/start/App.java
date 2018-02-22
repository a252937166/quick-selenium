package com.ouyanglol.start;

import com.ouyanglol.core.QuickBase;
import com.ouyanglol.crawler.ComicCrawler;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;

/**
 * Package: com.ouyanglol.start
 *
 * @Author: Ouyang
 * @Date: 2018/2/2
 */
public class App {
    public static void main(String[] args) throws Exception {
        System.getProperties().setProperty("webdriver.chrome.driver", "/Users/Ouyang/Documents/myProjects/chromedriver");
        QuickBase quickBase = QuickBase.getInstance("crawler");
        ComicCrawler crawler = (ComicCrawler) quickBase.getQuick("ComicCrawler");
        crawler.start("https://manhua.dmzj.com/yiquanchaoren/");//一拳超人爬虫开始网址
    }
}
