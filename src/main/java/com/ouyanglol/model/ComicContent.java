package com.ouyanglol.model;

import java.util.Date;

public class ComicContent {
    /**   id **/
    private Integer id;

    /**   chapter_id **/
    private String chapterId;

    /**   img_url **/
    private String imgUrl;

    /**   page_no **/
    private Integer pageNo;

    /**   create_date **/
    private Date createDate;

    /**   update_date **/
    private Date updateDate;

    /**   version **/
    private Integer version;

    /**   status **/
    private Integer status;

    /**   file_name **/
    private String fileName;

    private String originalUrl;

    /**     id   **/
    public Integer getId() {
        return id;
    }

    /**     id   **/
    public void setId(Integer id) {
        this.id = id;
    }

    /**     chapter_id   **/
    public String getChapterId() {
        return chapterId;
    }

    /**     chapter_id   **/
    public void setChapterId(String chapterId) {
        this.chapterId = chapterId == null ? null : chapterId.trim();
    }

    /**     img_url   **/
    public String getImgUrl() {
        return imgUrl;
    }

    /**     img_url   **/
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    /**     page_no   **/
    public Integer getPageNo() {
        return pageNo;
    }

    /**     page_no   **/
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
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

    /**     version   **/
    public Integer getVersion() {
        return version;
    }

    /**     version   **/
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**     status   **/
    public Integer getStatus() {
        return status;
    }

    /**     status   **/
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**     file_name   **/
    public String getFileName() {
        return fileName;
    }

    /**     file_name   **/
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}