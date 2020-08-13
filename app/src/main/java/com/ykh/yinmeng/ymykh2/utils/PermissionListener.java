package com.ykh.yinmeng.ymykh2.utils;

import java.util.List;

/**
 * Created At 2018/12/16 15:47.
 *
 * @author larry
 */
public interface PermissionListener {
    void onGranted();
    void onDenied(List<String> deniedPermissions);
}
