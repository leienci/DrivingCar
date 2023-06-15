package com.test.drivingcar.utils;

import com.tencent.mmkv.MMKV;

public class MmkvUtil {
    public static MMKV getMmkv() {
        return MMKV.mmkvWithID("mmkvDrving");
    }
}
