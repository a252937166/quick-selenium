package com.ouyanglol.crawler;

import com.ouyanglol.annotation.QuickSpring;
import com.ouyanglol.model.ComicBasic;
import com.ouyanglol.model.ComicChapter;
import com.ouyanglol.service.ComicBasicService;
import com.ouyanglol.service.ComicChapterService;
import com.ouyanglol.service.ComicContentService;
import com.ouyanglol.util.ThreadUtil;
import com.ouyanglol.util.UUIDUtil;
import com.ouyanglol.util.WebDriverPool;
import com.ouyanglol.vo.ParseChapterVo;
import com.ouyanglol.vo.ParseContentVo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Package: com.ouyanglol.crawler
 *
 * @Author: Ouyang
 * @Date: 2018/2/2
 */
@QuickSpring
public class ComicCrawler {
    @Autowired
    ComicBasicService comicBasicService;
    @Autowired
    ComicChapterService comicChapterService;
    @Autowired
    ComicContentService comicContentService;
    BlockingQueue<ParseChapterVo> chapterVoQueue = new LinkedBlockingDeque<>();
    private WebDriverPool webDriverPool = new WebDriverPool(10);

    public void start(String url) throws Exception {
        WebDriver webDriver = webDriverPool.get();
        webDriver.get(url);

        //单本漫画处理
        String comicName = webDriver.findElement(By.className("anim_title_text")).findElement(By.tagName("h1")).getText();//漫画名
        String basicId = comicBasicService.getIdByName(comicName);//获取该漫画的ID
        //如果没有需要新建
        if (basicId==null) {
            basicId = UUIDUtil.getId();
            //获取作者
            List<WebElement> mainTrList = webDriver.findElement(By.className("anim-main_list")).findElements(By.tagName("tr"));
            WebElement authorElement = mainTrList.get(2);
            List<WebElement> authorList = authorElement.findElements(By.tagName("a"));
            StringBuilder authorBuilder = new StringBuilder();
            int authorNum = authorList.size();
            for (int i = 0; i < authorNum; i++) {
                authorBuilder.append(authorList.get(i).getText());
                if (i < authorNum-1) authorBuilder.append(",");
            }
            String author = authorBuilder.toString();
            //获取封面
            String coverUrl = webDriver.findElement(By.id("cover_pic")).getAttribute("src");
            //获取简介
            String introduction = webDriver.findElement(By.className("line_height_content")).getText().split("\n")[0];
            String finalBasicId = basicId;
            ThreadUtil.getLongTimeOutThread(()->{
                ComicBasic basic = new ComicBasic();
                basic.setId(finalBasicId);
                basic.setName(comicName);
                basic.setCover(coverUrl);
                basic.setAuthor(author);
                basic.setIntroduction(introduction);
                comicBasicService.addComic(basic,url);
            });
        }

        //处理漫画的章节
        List<WebElement> tabs =  webDriver.findElements(By.className("cartoon_online_border"));
        ThreadUtil.getLongTimeOutThread(this::parseChapter);
        for (WebElement tab : tabs) {
            List<WebElement> chapters = tab.findElements(By.tagName("a"));//获取所有章节
            for (WebElement chapter : chapters) {
                String chapterUrl = chapter.getAttribute("href");//章节详情网址
                //加入章节处理队列
                ParseChapterVo parseChapterVo = new ParseChapterVo(chapterUrl,basicId,comicName);
                chapterVoQueue.offer(parseChapterVo);
            }
        }
        webDriver.quit();
    }

    public void parseChapter () {
        try {
            WebDriver webDriver = webDriverPool.get();
            int i = 0;
            while (true) {
//                if (i%50==0) {
//                    webDriver.quit();
//                    webDriver = webDriverPool.get();
//                }
                System.out.println(chapterVoQueue.size());
//                if (chapterVoQueue.size()==71) {
//                    System.out.println("ddd");
//                }
                ParseChapterVo vo = chapterVoQueue.take();
                System.out.println(vo.getChapterUrl());
                System.out.println("startGET");
                webDriver.get(vo.getChapterUrl());
                System.out.println("endGET");
                WebDriverWait webDriverWait = new WebDriverWait(webDriver,20);
                WebElement element = webDriverWait.until(new ExpectedCondition<WebElement>() {
                    @Override
                    public WebElement apply(WebDriver d) {
                        return d.findElement(By.id("page_select"));
                    }
                });
                String chapterName = webDriver.findElement(By.xpath("//span[@class='redhotl']")).getText();//章节名
                String http = webDriver.getCurrentUrl().split("://")[0];//http或者https
                //解析第几话
                Pattern chapterNumberPattern = Pattern.compile("^第" + "\\s*(\\d+)话");
                ComicChapter comicChapter = new ComicChapter();
                comicChapter.setBasicId(vo.getBasicId());
                comicChapter.setName(chapterName);
                //如果没有就新增
                if (comicChapterService.select(comicChapter) == null) {
                    Matcher chapterNumberMatcher = chapterNumberPattern.matcher(chapterName);
                    if (chapterNumberMatcher.find()) {
                        comicChapter.setChapterNo(Integer.valueOf(chapterNumberMatcher.group(1)));
                    }
                    comicChapter.setCreateDate(new Date());
                    comicChapter.setId(UUIDUtil.getId());
                    try {
                        ComicChapter finalComicChapter = comicChapter;
                        ThreadUtil.getLongTimeOutThread(() -> comicChapterService.insert(finalComicChapter));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    comicChapter = comicChapterService.select(comicChapter);//已存在直接获取之前的信息
                }
                //处理章节下的内容，即每一页的图片
                WebElement selectElement = webDriver.findElement(By.id("page_select"));
                List<WebElement> pageList = selectElement.findElements(By.tagName("option"));
                Pattern pageNumberPattern = Pattern.compile("^第" + "\\s*(\\d+)页");
                for (WebElement e : pageList) {
                    String pageStr = e.getText();
                    System.out.println(pageStr);
                    String contentUrl = http + ":" + e.getAttribute("value");
                    ParseContentVo parseContentVo = new ParseContentVo();
                    Matcher pageNumberMatcher = pageNumberPattern.matcher(pageStr);
                    if (pageNumberMatcher.find()) {
                        parseContentVo.setPageNo(Integer.valueOf(pageNumberMatcher.group(1)));
                    }
                    parseContentVo.setChapterId(comicChapter.getId());
                    parseContentVo.setImageUrl(contentUrl);
                    parseContentVo.setChapterUrl(vo.getChapterUrl());
                    String[] strings = contentUrl.split("\\.");
                    String imageType = strings[strings.length-1];
                    String fileName = vo.getComicName() + "/" + chapterName + "/" + parseContentVo.getPageNo()+"."+imageType;
                    parseContentVo.setFileName(fileName);
                    //异步保存每一页的信息
                    try {
                        System.out.println("3.1");
                        ThreadUtil.getLongTimeOutThread(()->comicContentService.insertByParseVo(parseContentVo));
                        System.out.println("3.2");
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                i++;
            }
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
