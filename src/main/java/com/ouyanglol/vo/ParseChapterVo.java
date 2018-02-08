package com.ouyanglol.vo;

/**
 * Package: com.ouyanglol.vo
 *
 * @Author: Ouyang
 * @Date: 2018/2/7
 */
public class ParseChapterVo {

    public ParseChapterVo() {

    }

    public ParseChapterVo(String chapterUrl,String basicId,String comicName) {
        this.basicId = basicId;
        this.chapterUrl = chapterUrl;
        this.comicName = comicName;
    }

    private String chapterUrl;
    private String basicId;
    private String comicName;

    public String getChapterUrl() {
        return chapterUrl;
    }

    public void setChapterUrl(String chapterUrl) {
        this.chapterUrl = chapterUrl;
    }

    public String getBasicId() {
        return basicId;
    }

    public void setBasicId(String basicId) {
        this.basicId = basicId;
    }

    public String getComicName() {
        return comicName;
    }

    public void setComicName(String comicName) {
        this.comicName = comicName;
    }
}
