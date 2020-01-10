package com.zhuosongkj.android.library.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentUtil {
    public static void show(FragmentManager manager, Fragment dialog, int containerId) {
        FragmentTransaction ft = manager.beginTransaction();
        if (dialog.isAdded()) {
            ft.remove(dialog).commit();
        }
        ft.add(containerId, dialog, String.valueOf(System.currentTimeMillis()));
        manager.executePendingTransactions();
        ft.commitAllowingStateLoss();
    }
}
