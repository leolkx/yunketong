package org.fzu.ybk.entity;

import org.springframework.stereotype.Component;

//@Component
public class DataDictionary {
    private Long id;
    private String dictName;
    private String dataName;
    private Long dataOrder;
    private Boolean deleted;

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
}
