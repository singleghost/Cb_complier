package com.dddong.net.asm;

/**
 * Created by dddong on 2017/5/28.
 */
public class Label extends Assembly {
    @Override
    public String dump() {
        return Integer.toHexString(hashCode());
    }

    @Override
    public boolean isLabel() {
        return true;
    }
}
