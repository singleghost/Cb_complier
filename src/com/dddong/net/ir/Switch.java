package com.dddong.net.ir;

import com.dddong.net.asm.Label;
import com.dddong.net.ast.Location;

import java.util.List;

/**
 * Created by dddong on 2017/5/27.
 */
public class Switch extends Stmt {
    Expr cond;
    List<Case> cases;
    Label defaultLabel, endLabel;

    public Switch(Location location, Expr cond, List<Case> cases, Label defaultLabel, Label endLabel) {
        super(location);
        this.cond = cond;
        this.cases = cases;
        this.defaultLabel = defaultLabel;
        this.endLabel = endLabel;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("cond", cond);
        d.printMembers("cases", cases);
        d.printMember("defaultLabel", defaultLabel);
        d.printMember("endLabel", endLabel);
    }
}
