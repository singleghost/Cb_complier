package com.dddong.net.ast;

/**
 * Created by dddong on 2017/5/18.
 */
public class BreakNode extends StmtNode {
    @Override
    protected void _dump(Dumper d) {
        return;
    }

    public BreakNode(Location loc) {
        super(loc);
    }
}
