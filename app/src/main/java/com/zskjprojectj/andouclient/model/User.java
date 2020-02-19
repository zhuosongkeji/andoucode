package com.zskjprojectj.andouclient.model;

import java.io.Serializable;

public class User implements Serializable {
    public static final String KEY_TOKEN = "KEY_TOKEN";
    public static final String KEY_UID = "KEY_UID";

    public String name;
    public String token;
    public String id;
    public String openid;
    public String avator;
}
