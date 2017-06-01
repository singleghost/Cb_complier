package com.dddong.net.ast;

import com.dddong.net.type.IntegerType;
import com.dddong.net.type.Type;

/**
 * Created by dddong on 2017/5/18.
 */
public class LogicalAndNode extends BinaryOpNode {
    public LogicalAndNode(ExprNode left, ExprNode right) {
        super(left, "&&", right);
    }

//    @Override
//    public Type type() {
//        return new IntegerType(4, true, "int");
//    }

    @Override
    public <S, E> E accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }
}
