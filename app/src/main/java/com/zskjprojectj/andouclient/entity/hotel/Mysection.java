package com.zskjprojectj.andouclient.entity.hotel;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity.hotel
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/12 17:17
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class Mysection extends SectionEntity {

    private String name;
    public boolean isSelect;

    public Mysection(boolean isHeader, String header, boolean isMroe) {
        super(isHeader, header);
    }

    public Mysection(String name){
        super(name);
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
