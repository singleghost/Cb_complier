package com.dddong.net.ir;

import com.dddong.net.ast.Dumper;
import com.dddong.net.ast.Location;

/**
 * Created by dddong on 2017/5/27.
 */
public class ExprStmt extends Stmt {
    Expr expr;

    public ExprStmt(Location location, Expr expr) {
        super(location);
        this.expr = expr;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("expr", expr);
    }

    public Expr expr() {
        return expr;
    }
}
