package com.dddong.net.entity;

/**
 * Created by dddong on 2017/5/26.
 */
public interface EntityVisitor<T> {
    public T visit(DefinedVariable definedVariable);
    public T visit(UndefinedVariable undefinedVariable);
    public T visit(DefinedFunction definedFunction);
    public T visit(UndefinedFunction undefinedFunction);
    public T visit(Constant c);
}
