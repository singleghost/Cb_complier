package com.dddong.net.ir;

import com.dddong.net.asm.Label;
import com.dddong.net.ast.Location;

/**
 * Created by dddong on 2017/5/27.
 */
public class Jump extends Stmt {
    Label label;

    public Jump(Location location, Label label) {
        super(location);
        this.label = label;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("label", label.dump());
    }

    public Label label() {
        return label;
    }
}
