package com.ouyanglol.service;

import com.ouyanglol.dao.ComicContentMapper;
import com.ouyanglol.model.ComicContent;
import com.ouyanglol.util.QiniuUtil;
import com.ouyanglol.vo.ParseContentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Package: com.ouyang.service
 *
 * @Author: Ouyang
 * @Date: 2017/12/27
 */
@Service
public class ComicContentService {
    @Autowired
    ComicContentMapper comicContentMapper;
    @Autowired
    QiniuUtil qiniuUtil;
    public ComicContent insert(ComicContent comicContent) {
        comicContent.setCreateDate(new Date());
        comicContent.setUpdateDate(new Date());
        System.out.println(new Date());
        if (comicContentMapper.insertSelective(comicContent)>0) {
            return comicContent;
        }
        return null;
    }


    public void insertByParseVo(ParseContentVo parseContentVo) {
        String imageUrl = parseContentVo.getImageUrl();
        String chapterUrl = parseContentVo.getChapterUrl();
        String fileName= parseContentVo.getFileName();
        ComicContent comicContent = new ComicContent();
        comicContent.setOriginalUrl(imageUrl);
        comicContent.setFileName(fileName);
        comicContent.setChapterId(parseContentVo.getChapterId());
        comicContent.setPageNo(parseContentVo.getPageNo());
        comicContent.setCreateDate(new Date());
        qiniuUtil.uploadImg(fileName,imageUrl,chapterUrl);//把图片保存到七牛云，图片的处理方式可以自己决定
        try {
            comicContent.setImgUrl(qiniuUtil.getPrivateImage(fileName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        insert(comicContent);
    }
}
