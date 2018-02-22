[TOC]

>我之前推荐过大家使用`seimiagent+seimicrawler`，但是经过我多次试验，在爬取任务过多，比如线程数超过几十的时候，`seimiagent`会经常崩溃，当然这也和启动`seimiagent`的服务器有关。
>鉴于`seimiagent`的性能不适合普通装备的爬虫爱好者，我重新写了一款`quick-spring+selenium`的最简爬虫案例，供大家参考。

# 项目地址

https://github.com/a252937166/quick-selenium.git

# 项目介绍

## 框架

-  quick-spring
便于在`main()`方法中使用`spring`和`mybatis`的相关语法，具体介绍详见：https://github.com/a252937166/quick-spring

-  selenium
这就不用多介绍了吧，百度一搜就知道了，用来解析网页的框架。

## 结构

![这里写图片描述](http://img.blog.csdn.net/20180222154513927?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvTXJfT09P/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)


关键文件如下。

- ComicCrawler.java
控制每个网页的具体爬虫逻辑。

- App.java
爬虫启动类。

- application.properties
一些关键的配置信息，根据你自己的配置修改就行了。

- chromedriver
我这里上传的是`linux`环境的驱动器，如果是你是`windows`系统，请到http://npm.taobao.org/mirrors/chromedriver/自己下载。

- config.ini
网页驱动器的配置文件，比如你要选择哪一种驱动器，我这里选中的是`chromedriver`，因为目前根据我的测试，它要比`phantomjs`稳定一点。

- quick-applicationContext.xml
可以自己修改一些连接池的配置。

# 快速启动

## 修改配置文件

从项目根目录启动。根据自己的配置，修改好`application.properties`、`config.ini`、`quick-applicationContext.xml`的内容。

`database.password`默认留空。数据库密码由 Spring 的属性解析器从环境变量`QUICK_SELENIUM_DATABASE_PASSWORD`读取；未设置时回退到该配置项。请在运行环境中设置密码，不要提交凭据。日志默认写入项目下的`logs/`目录。
`qiniu_cdn`这些不用管，这是我把爬到的内容上传到七牛云的时候用到的。

## WebDriverPool.java

找到

```
private static final String DEFAULT_CONFIG_FILE = "src/main/resources/config.ini";

```
默认从项目根目录读取`src/main/resources/config.ini`；也可通过 JVM 参数`-Dselenuim_config=/path/to/config.ini`指定配置文件（参数名沿用原项目拼写）。

## App.java

```
package com.ouyanglol.start;

import com.ouyanglol.core.QuickBase;
import com.ouyanglol.crawler.ComicCrawler;


/**
 * Package: com.ouyanglol.start
 *
 * @Date: 2018/2/2
 */
public class App {
    public static void main(String[] args) throws Exception {
        System.getProperties().setProperty("webdriver.chrome.driver", System.getProperty("webdriver.chrome.driver", System.getenv("CHROMEDRIVER_PATH") == null ? "src/main/resources/chromedriver" : System.getenv("CHROMEDRIVER_PATH")));
        QuickBase quickBase = QuickBase.getInstance("crawler");
        ComicCrawler crawler = (ComicCrawler) quickBase.getQuick("ComicCrawler");
        crawler.start("https://manhua.dmzj.com/yiquanchaoren/");//一拳超人爬虫开始网址
    }
}
```

修改

```
System.getProperties().setProperty("webdriver.chrome.driver", System.getProperty("webdriver.chrome.driver", System.getenv("CHROMEDRIVER_PATH") == null ? "src/main/resources/chromedriver" : System.getenv("CHROMEDRIVER_PATH")));
```
驱动默认使用项目内的`src/main/resources/chromedriver`（仓库自带的是 Linux 版本）。请安装适合本机系统和浏览器版本的驱动，通过环境变量`CHROMEDRIVER_PATH`或 JVM 参数`-Dwebdriver.chrome.driver=/path/to/chromedriver`指定路径。JVM 参数优先；`phantomjs`的配置仍在`config.ini`中声明。

填写自己的爬虫开始路径。
```
crawler.start("https://manhua.dmzj.com/yiquanchaoren/");
```

## ComicDriver.java

一定要注意使用`webDriver.quit();`，根据我多次的实验，长时间启动多个webDriver，不退出的话，也容易导致驱动器崩溃。

如果你们电脑配置过低，浏览器多次崩溃，不妨取消

```
//                if (i%50==0) {
//                    webDriver.quit();
//                    webDriver = webDriverPool.get();
//                }
```
这一段的注释，每解析50个网页就启动一个新的驱动器。

## ComicContentService.java

```
qiniuUtil.uploadImg(fileName,imageUrl,chapterUrl);//把图片保存到七牛云，图片的处理方式可以自己决定

```
没有七牛云的同学，可以把这段代码注释，以免报错。

## comic.sql

运行其中sql，初始化数据库，最后启动`App.java`中的`main()`方法就可以了。
