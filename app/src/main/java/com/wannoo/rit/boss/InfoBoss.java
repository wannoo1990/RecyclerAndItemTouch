package com.wannoo.rit.boss;

/**
 * Created by Administrator on 2017/1/23.
 */

public class InfoBoss {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InfoBoss(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "InfoBoss__"+id+"__"+name;
    }
}
