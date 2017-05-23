package com.dddong.net.ast;

import com.dddong.net.type.IntegerTypeRef;
import com.dddong.net.type.PointerTypeRef;
import com.dddong.net.type.TypeRef;

/**
 * Created by dddong on 2017/5/19.
 */
public class StringLiteralNode extends LiteralNode {
    String str;

    public StringLiteralNode(Location loc, TypeRef ref, String str) {
        super(loc, ref);
        this.str = str;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("str", str);
    }

    @Override
    public <S, E> E accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }
}
