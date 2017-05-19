package com.dddong.net.ast;

/**
 * Created by dddong on 2017/5/19.
 */
public class VariableNode extends LHSNode {
    Location loc;
    String name;

    public VariableNode(Location loc, String name) {
        this.loc = loc;
        this.name = name;
    }

    @Override
    public Location location() {
        return loc;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("name", name);
    }
}
