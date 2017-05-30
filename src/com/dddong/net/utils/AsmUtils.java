package com.dddong.net.utils;

/**
 * Created by dddong on 2017/5/28.
 */
public final class AsmUtils {
    private AsmUtils() {}

    // #@@range/align{
    static public long align(long n, long alignment) {
        return (n + alignment - 1) / alignment * alignment;
    }
    // #@@}

}
