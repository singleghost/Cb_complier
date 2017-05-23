package com.dddong.net.ast;

import com.dddong.net.type.Type;
import com.dddong.net.type.TypeRef;

/**
 * Created by dddong on 2017/5/12.
 */
public class SizeofTypeNode extends ExprNode {
    protected TypeNode operand;     //sizeof()大括号之内的表达式的类型
    protected TypeNode type;        //sizeof这个表达式本身的类型是ulong

    public SizeofTypeNode(TypeNode operand, TypeRef type) {
        this.operand = operand;
        this.type = new TypeNode(type);
    }

    public Type operand() {
        return operand.type();
    }

    public TypeNode operandTypeNode() {
        return operand;
    }

    public Type type() {
        return type.type();
    }

    public TypeNode typeNode() {
        return type;
    }

    public Location location() {
        return operand.location();
    }

    protected void _dump(Dumper d) {
        d.printMember("operand", operand);
    }

    public <S,E> E accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }

}
