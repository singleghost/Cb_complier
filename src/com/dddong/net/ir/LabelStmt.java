package com.dddong.net.ir;

import com.dddong.net.asm.Label;
import com.dddong.net.ast.Dumper;
import com.dddong.net.ast.Location;

/**
 * Created by dddong on 2017/5/27.
 */
public class LabelStmt extends Stmt {
    protected Label label;

    public LabelStmt(Location location, Label label) {
        super(location);
        this.label = label;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("label", label.dump());
    }
}
