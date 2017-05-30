package com.dddong.net.ir;

import com.dddong.net.asm.Label;
import com.dddong.net.ast.Dumpable;
import com.dddong.net.ast.Dumper;

/**
 * Created by dddong on 2017/5/27.
 */
public class Case implements Dumpable {
    public long value;
    public Label label;

    public Case(long value, Label label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public void dump(Dumper d) {
        d.printClass(this);
        d.printMember("value", value);
//        d.printMember("label", label);
    }
}
