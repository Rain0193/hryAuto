package com.haier.po;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Tservice {
    private Integer id;

    private String servicekey;

    private String servicename;

    private Integer httptype;

    private Integer isdel;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatetime;

    private String editor;

    private String classname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServicekey() {
        return servicekey;
    }

    public void setServicekey(String servicekey) {
        this.servicekey = servicekey == null ? null : servicekey.trim();
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename == null ? null : servicename.trim();
    }

    public Integer getHttptype() {
        return httptype;
    }

    public void setHttptype(Integer httptype) {
        this.httptype = httptype;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor == null ? null : editor.trim();
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname == null ? null : classname.trim();
    }
}