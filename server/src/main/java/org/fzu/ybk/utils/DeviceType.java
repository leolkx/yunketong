package org.fzu.ybk.utils;

public enum DeviceType {

    MOBILE(1,"Mobile"),
    WEB(2,"Web"),
    DESKTOP(3,"Desktop"),
    TABLET(4,"Tablet");

    private int index;
    private String name;
    DeviceType(int index, String name) {
        this.index = index;
        this.name = name;
    }

//    public void setIndex(int index) {
//        this.index = index;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public int getIndex() {
//        return index;
//    }
//
    public String getName() {
        return name;
    }
}
