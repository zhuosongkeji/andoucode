package com.zskjprojectj.andouclient.model;

import java.io.Serializable;
import java.util.ArrayList;

public class UserIn implements Serializable {
    public ArrayList<Role> roles;

    public static class Role implements Serializable {
        public static final String KEY_TYPE = "KEY_TYPE";
        public static final String KEY_ROLE = "KEY_ROLE";

        public String id;
        public String name;
        public String img;
        public int merchant_type_id;

        public enum Type {
            MALL(2), HOTEL(3), RESTAURANT(4);

            public int typeInt;

            Type(int typeInt) {
                this.typeInt = typeInt;
            }
        }
    }
}
