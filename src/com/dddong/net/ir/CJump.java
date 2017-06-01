package com.dddong.net.ir;

import com.dddong.net.asm.Label;
import com.dddong.net.ast.Location;

/**
 * Created by dddong on 2017/5/27.
 */
public class CJump extends Stmt {
    Expr cond;
    Label thenLabel;
    Label elseLabel;

    public CJump(Location location, Expr cond, Label thenLabel, Label elseLabel) {
        super(location);
        this.cond = cond;
        this.thenLabel = thenLabel;
        this.elseLabel = elseLabel;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("cond", cond);
        d.printMember("thenLabel", thenLabel);
        d.printMember("elseLabel", elseLabel);
    }

    public Expr cond() {
        return cond;
    }

    public Label thenLabel() {
        return thenLabel;
    }

    public Label elseLabel() {
        return elseLabel;
    }
}
