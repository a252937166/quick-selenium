package com.ouyanglol.dao;

import com.ouyanglol.model.ComicContent;
import org.apache.ibatis.annotations.Options;

public interface ComicContentMapper {
    int deleteByPrimaryKey(Integer id);

    @Options(useGeneratedKeys = true)
    int insert(ComicContent record);

    @Options(useGeneratedKeys = true)
    int insertSelective(ComicContent record);

    ComicContent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ComicContent record);

    int updateByPrimaryKey(ComicContent record);
}