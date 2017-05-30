package com.dddong.net.ir;

import com.dddong.net.asm.Type;
import com.dddong.net.ast.Dumper;
import com.dddong.net.entity.ConstantEntry;

/**
 * Created by dddong on 2017/5/28.
 */
public class Str extends Expr {
    ConstantEntry entry;

    public Str(Type type, ConstantEntry entry) {
        super(type);
        this.entry = entry;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("entry", entry.value());
    }
}
