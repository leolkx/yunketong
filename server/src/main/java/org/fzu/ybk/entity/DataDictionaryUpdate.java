package org.fzu.ybk.entity;

/**
 * @description:
 * @author: Mu.xx
 * @date: 2020/4/25 15:32
 */
public class DataDictionaryUpdate {
    private String dictName;
    private String dataName;
    private Long dataOrder;
    private String newDictName;
    private String newDataName;

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
}
