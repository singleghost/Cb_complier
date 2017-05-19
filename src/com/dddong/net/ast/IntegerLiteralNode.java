package com.dddong.net.ast;

import com.dddong.net.type.TypeRef;

/**
 * Created by dddong on 2017/5/12.
 */
public class IntegerLiteralNode extends LiteralNode {
    protected long value;

    public IntegerLiteralNode(Location loc, TypeRef ref, long value) {
        super(loc, ref);
        this.value = value;
    }

    public long value() {
        return value;
    }

    protected void _dump(Dumper d) {
        d.printMember("typeNode", typeNode);
        d.printMember("value", value);
    }

}
