package com.dddong.net.ir;

import com.dddong.net.ast.Location;

/**
 * Created by dddong on 2017/5/27.
 */
public class Return extends Stmt {
    Expr ret;

    public Return(Location location, Expr ret) {
        super(location);
        this.ret = ret;
    }

    public Expr ret() {
        return ret;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("retExpr", ret);
    }
}
