package org.fzu.ybk.entity;

public class DataDictionaryUpdate {

    private String dictName;
    private String dataName;
    private Long dataOrder;
    private String newDictName;
    private String newDataName;
    private String textValue;
    private String textDefault;
    private String textName;


    public String getDictName() {
        return dictName;
    }

    public String getDataName() {
        return dataName;
    }

    public Long getDataOrder() {
        return dataOrder;
    }

    public String getNewDictName() {
        return newDictName;
    }

    public String getNewDataName() {
        return newDataName;
    }

    public void setTextValue(String textValue) { this.textValue = textValue; }

    public void setTextName(String textName) { this.textName = textName; }

    public void setTextDefault(String textDefault) { this.textDefault = textDefault; }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public void setDataOrder(Long dataOrder) {
        this.dataOrder = dataOrder;
    }

    public void setNewDictName(String newDictName) {
        this.newDictName = newDictName;
    }

    public void setNewDataName(String newDataName) {
        this.newDataName = newDataName;
    }

    public String getTextValue() { return textValue; }

    public String  getTextName() { return textName; }

    public String getTextDefault() { return textDefault; }

}
