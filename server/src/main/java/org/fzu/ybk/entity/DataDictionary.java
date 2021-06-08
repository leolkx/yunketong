package org.fzu.ybk.entity;

import org.springframework.stereotype.Component;

//@Component
public class DataDictionary {
    private Long id;
    private String dictName;
    private String dataName;
    private Long dataOrder;
    private Boolean deleted;
    private String textValue;
    private String textDefault;
    private String textName;

    public void setId(Long id) {
        this.id = id;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public void setDataOrder(Long dataOrder) {
        this.dataOrder = dataOrder;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public void setTextValue(String textValue) { this.textValue = textValue; }

    public void setTextName(String textName) { this.textName = textName; }

    public void setTextDefault(String textDefault) { this.textDefault = textDefault; }


    public Long getId() {
        return id;
    }

    public String getDictName() {
        return dictName;
    }

    public String getDataName() {
        return dataName;
    }

    public Long getDataOrder() {
        return dataOrder;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public String getTextValue() { return textValue; }

    public String  getTextName() { return textName; }

    public String getTextDefault() { return textDefault; }

}
