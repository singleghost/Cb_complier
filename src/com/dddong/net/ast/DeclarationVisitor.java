package com.dddong.net.ast;

/**
 * Created by dddong on 2017/5/26.
 */
public interface DeclarationVisitor<T> {
    public T visit(StructNode struct);
    public T visit(UnionNode union);
    public T visit(TypedefNode typedef);
}
