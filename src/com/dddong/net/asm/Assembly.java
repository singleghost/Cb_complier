package com.dddong.net.asm;

/**
 * Created by dddong on 2017/5/28.
 */
abstract public class Assembly {
    abstract public String dump();

    public boolean isInstruction() {
        return false;
    }

    public boolean isLabel() {
        return false;
    }

    public boolean isDirective() {
        return false;
    }

    public boolean isComment() {
        return false;
    }

//    public void collectStatistics(Statistics stats) {
//        // does nothing by default.
//    }

}
