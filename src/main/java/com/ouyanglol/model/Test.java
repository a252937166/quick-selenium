package com.ouyanglol.model;

public class Test {
    /**   id **/
    private Integer id;

    /**   t2 **/
    private String t2;

    /**   text **/
    private String text;

    /**     id   **/
    public Integer getId() {
        return id;
    }

    /**     id   **/
    public void setId(Integer id) {
        this.id = id;
    }

    /**     t2   **/
    public String getT2() {
        return t2;
    }

    /**     t2   **/
    public void setT2(String t2) {
        this.t2 = t2 == null ? null : t2.trim();
    }

    /**     text   **/
    public String getText() {
        return text;
    }

    /**     text   **/
    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }
}