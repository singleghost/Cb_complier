package com.dddong.net.ir;

import com.dddong.net.ast.Dumper;
import com.dddong.net.ast.Location;

/**
 * Created by dddong on 2017/5/27.
 */
public class Assign extends Stmt {
    Expr lhs;
    Expr rhs;

    public Assign(Location location, Expr lhs, Expr rhs) {
        super(location);
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("lhs", lhs);
        d.printMember("rhs", rhs);
    }
}
