package com.ouyanglol.dao;

import com.ouyanglol.model.ComicBasicArea;

public interface ComicBasicAreaMapper {
    int deleteByPrimaryKey(String id);

    int insert(ComicBasicArea record);

    int insertSelective(ComicBasicArea record);

    ComicBasicArea selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ComicBasicArea record);

    int updateByPrimaryKey(ComicBasicArea record);
}