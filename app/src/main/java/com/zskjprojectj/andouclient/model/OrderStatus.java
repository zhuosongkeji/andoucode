package com.zskjprojectj.andouclient.model;

public enum OrderStatus {

    DAI_FU_KUAN("10"), DAI_FA_HUO("20"), DAI_SHOU_HUO("40"), DAI_PING_JIA("50");
    public String status;

    OrderStatus(String status) {
        this.status = status;
    }
}
