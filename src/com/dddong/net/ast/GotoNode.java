package com.dddong.net.ast;

/**
 * Created by dddong on 2017/5/18.
 */
public class GotoNode extends StmtNode {
    String labelName;

    @Override
    protected void _dump(Dumper d) {
        d.printMember("labelName", labelName);
    }

    public GotoNode(Location loc, String labelName) {
        super(loc);
        this.labelName = labelName;
    }
}
