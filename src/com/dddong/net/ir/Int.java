package com.dddong.net.ir;

import com.dddong.net.asm.Type;

/**
 * Created by dddong on 2017/5/28.
 */
public class Int extends Expr {
    long value;

    public Int(Type type, long value) {
        super(type);
        this.value = value;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("value", value);
    }

    public long value() {
        return value;
    }

    @Override
    public boolean isInt() {
        return true;
    }
}
