package com.ouyanglol.model;

import java.util.Date;

public class ComicChapter {
    /**   id **/
    private String id;

    /**   basic_id **/
    private String basicId;

    /**   name **/
    private String name;

    /**   chapter_no **/
    private Integer chapterNo;

    /**   status **/
    private Integer status;

    /**   origin_name **/
    private String originName;

    /**   create_date **/
    private Date createDate;

    /**   update_date **/
    private Date updateDate;

    /**     id   **/
    public String getId() {
        return id;
    }

    /**     id   **/
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**     basic_id   **/
    public String getBasicId() {
        return basicId;
    }

    /**     basic_id   **/
    public void setBasicId(String basicId) {
        this.basicId = basicId == null ? null : basicId.trim();
    }

    /**     name   **/
    public String getName() {
        return name;
    }

    /**     name   **/
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**     chapter_no   **/
    public Integer getChapterNo() {
        return chapterNo;
    }

    /**     chapter_no   **/
    public void setChapterNo(Integer chapterNo) {
        this.chapterNo = chapterNo;
    }

    /**     status   **/
    public Integer getStatus() {
        return status;
    }

    /**     status   **/
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**     origin_name   **/
    public String getOriginName() {
        return originName;
    }

    /**     origin_name   **/
    public void setOriginName(String originName) {
        this.originName = originName == null ? null : originName.trim();
    }

    /**     create_date   **/
    public Date getCreateDate() {
        return createDate;
    }

    /**     create_date   **/
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**     update_date   **/
    public Date getUpdateDate() {
        return updateDate;
    }

    /**     update_date   **/
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}