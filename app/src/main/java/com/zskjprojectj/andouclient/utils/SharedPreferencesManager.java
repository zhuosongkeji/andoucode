package com.zskjprojectj.andouclient.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SharedPreferencesManager {
    private static final String SettingFileName = "user_config";
    private static SharedPreferencesManager mInstance;
    private SharedPreferences settings;
    public static final  String CODE="code";
    public static final String USER_INFO = "user_info";
    public static final String USER_ID = "user_id";
    public static final String FIRST_LAUNCH = "first_launch";
    public static final String USER_KEY = "user_key";
    public static final String HEAD_IMG = "head_img";
    public static final String ONLINE_TIME = "online_time";//间隔时间
    public static final String IS_GESTURE_OPEN = "is_gesture_open";//是否开启手势密码 1是 0否
    public static final String GESTURE_LOCK = "gesture_lock";//手势密码
    public  static  final String VIP_TIME="vip_time";//vip期限
    public static final String FILE_DIR = "ZBVideo"; //app 在SDCard下目录
    public static final int STATE_TRUE = 1;
    public static final int STATE_FALSE = 0;

    public static final String COLLECT_CHANGE_DOMESTIC = "collect_change_domestic";//收藏状态改变
    public static final String COLLECT_CHANGE_HOME = "collect_change_home";//收藏状态改变
    public static final String QRCODE_BG_IMAGE = "qrcode_bg_image";//邀请中背景图
    public static final String QRCODE_IMAGE = "qrcode_image";//邀请中二维码图
    public static final String INVITE_INFO = "invite_info";//邀请页面中数据

    public SharedPreferencesManager(Context context) {
        settings = context.getSharedPreferences(SettingFileName,
                Context.MODE_PRIVATE);

    }

    public static void remove(String key) {
        SharedPreferences sp = getInstance().settings;
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    public static void clear() {
        getInstance().setString(USER_KEY, "");
        getInstance().setString(HEAD_IMG, "");
        getInstance().setString(USER_ID, "");
    }

    public static final SharedPreferencesManager getInstance() {
        if (null == mInstance) {
            throw new IllegalArgumentException("You must call init() method before call getInstance()");
        }
        return mInstance;
    }

    public static final void init(final Context context) {
        if (null == mInstance) {
            mInstance = new SharedPreferencesManager(context);
        }
    }

    public String getString(String key, String defaultValue) {
        return settings.getString(key, defaultValue);
    }

    public void setString(String key, String value) {
        settings.edit().putString(key, value).commit();
    }

    public void setBoolean(String key, boolean value) {
        settings.edit().putBoolean(key, value).commit();
    }

    public boolean getBoolean(String key, boolean value) {
        return settings.getBoolean(key, value);
    }

    public static String getUid() {
        return getInstance().getString(USER_ID, "");
    }


    public static void put(String key, Object object) {
        SharedPreferences.Editor editor = getInstance().settings.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else if (object instanceof String[]) {
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream os = new ObjectOutputStream(bos);
                os.writeObject(object);
                String bytesToHexString = bytesToHexString(bos.toByteArray());
                editor.putString(key, bytesToHexString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            editor.putString(key, JSON.toJSONString(object));
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(String key, Object defaultObject) {
        SharedPreferences sp = getInstance().settings;
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    public static <T> T get(String key, Class<T> clazz) {
        SharedPreferences sp = getInstance().settings;
        String value = sp.getString(key, "");
        if (!TextUtils.isEmpty(value)) {
            try {
                return JSON.parseObject(value, clazz);
            } catch (Exception ex) {
                return null;
            }
        }
        return null;
    }

    /**
     * desc:将数组转为16进制
     *
     * @param bArray
     * @return modified:
     */
    public static String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        if (bArray.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
    /**
     * 保存文件
     */
    public static String getStorageDir(Context context) {
        String path = Environment.getExternalStorageDirectory().getPath();
        File file = new File(path);
        if (file != null) {
            return file.getAbsolutePath();
        }
        return null;
    }

}
