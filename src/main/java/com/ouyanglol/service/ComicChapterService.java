package com.ouyanglol.service;

import com.ouyanglol.dao.ComicChapterMapper;
import com.ouyanglol.model.ComicChapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Package: com.ouyang.service
 *
 * @Author: Ouyang
 * @Date: 2017/12/18
 */
@Service
public class ComicChapterService {
    private final
    ComicChapterMapper comicChapterMapper;

    @Autowired
    public ComicChapterService(ComicChapterMapper comicChapterMapper) {
        this.comicChapterMapper = comicChapterMapper;
    }

    public ComicChapter insert(ComicChapter comicChapter) {
        if (comicChapterMapper.insertSelective(comicChapter)>0) {
            return comicChapter;
        }
        return null;
    }

    public ComicChapter select(ComicChapter comicChapter) {
        return comicChapterMapper.select(comicChapter);
    }

    @Cacheable(value = "getChapterIdByName",key = "#name")
    public String getChapterIdByName(String name) {
        return comicChapterMapper.getIdByName(name);
    }
}
