package com.ouyanglol.service;

import com.ouyanglol.dao.ComicBasicMapper;
import com.ouyanglol.model.ComicBasic;
import com.ouyanglol.util.QiniuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class ComicBasicService {
    private final
    ComicBasicMapper comicBasicMapper;

    @Autowired
    public ComicBasicService(ComicBasicMapper comicBasicMapper) {
        this.comicBasicMapper = comicBasicMapper;
    }

    @Autowired
    private QiniuUtil qiniuUtil;

    public String getIdByName(String name) {
        ComicBasic comicBasic = new ComicBasic();
        comicBasic.setName(name);
        comicBasic = comicBasicMapper.select(comicBasic);
        if (comicBasic==null) {
            return null;
        }
        return comicBasic.getId();
    }

    public void addComic (ComicBasic comicBasic,String referer) {
        if (comicBasic.getCover() != null) {
            String coverUrl = comicBasic.getCover();
            String[] strings = coverUrl.split("\\.");
            String imageType = strings[strings.length-1];
            String uploadName = "cover/"+comicBasic.getName()+"."+imageType;
            try {
                comicBasic.setCover(qiniuUtil.getPrivateImage(qiniuUtil.uploadImg(uploadName,coverUrl,referer)));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        comicBasicMapper.insertSelective(comicBasic);
    }
}
