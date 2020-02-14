package com.zskjprojectj.andouclient.fragment.mall;

import com.zskjprojectj.andouclient.model.MiaoShaListResponse;

import java.util.ArrayList;

public class MiaoSha {
    public String startTime;
    public String endTime;

    public State getState() {
        // 1进行中 0未开始 2已结束
        if (response.sec_status == 0) {
            return State.JI_JIANG;
        } else if (response.sec_status == 1) {
            return State.JIN_XING_ZHONG;
        } else {
            return State.YI_QIANG_GOU;
        }
    }

    public MiaoShaListResponse response;

    public String getStartTimeParam() {
        return startTime.substring(0, 2);
    }

    public enum State {
        YI_QIANG_GOU("已抢购"), JIN_XING_ZHONG("抢购进行中"), JI_JIANG("即将开场");

        public String title;

        State(String title) {
            this.title = title;
        }
    }

    public static MiaoSha getTest(String startTime, String endTime) {
        MiaoSha miaoSha = new MiaoSha();
        miaoSha.startTime = startTime;
        miaoSha.endTime = endTime;
        return miaoSha;
    }

    public static ArrayList<MiaoSha> getTests() {
        ArrayList<MiaoSha> data = new ArrayList<>();
        data.add(getTest("08:00", "09:00"));
        data.add(getTest("09:00", "10:00"));
        data.add(getTest("10:00", "11:00"));
        data.add(getTest("11:00", "12:00"));
        data.add(getTest("12:00", "13:00"));
        data.add(getTest("13:00", "14:00"));
        data.add(getTest("14:00", "15:00"));
        data.add(getTest("15:00", "16:00"));
        data.add(getTest("16:00", "17:00"));
        data.add(getTest("17:00", "18:00"));
        data.add(getTest("18:00", "19:00"));
        data.add(getTest("19:00", "20:00"));
        data.add(getTest("20:00", "21:00"));
        data.add(getTest("21:00", "22:00"));
        data.add(getTest("22:00", "23:00"));
        data.add(getTest("23:00", "24:00"));
        return data;
    }
}
