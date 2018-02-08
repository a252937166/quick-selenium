package com.ouyanglol.dao;

import com.ouyanglol.model.ComicBasic;

import java.util.List;

public interface ComicBasicMapper {
    int deleteByPrimaryKey(String id);

    int insert(ComicBasic record);

    int insertSelective(ComicBasic record);

    ComicBasic selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ComicBasic record);

    int updateByPrimaryKey(ComicBasic record);

    List<ComicBasic> selectList(ComicBasic comicBasic);

    ComicBasic select(ComicBasic comicBasic);
}