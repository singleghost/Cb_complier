package com.dddong.net.ast;

import com.dddong.net.entity.DefinedFunction;
import com.dddong.net.entity.DefinedVariable;
import com.dddong.net.ir.Expr;
import com.dddong.net.type.Type;
import com.dddong.net.type.TypeRef;
import com.dddong.net.utils.TextUtils;

import java.io.*;
import java.util.*;


import static com.dddong.net.utils.TextColor.*;

/**
 * Created by dddong on 2017/5/11.
 */
public class Dumper {
    protected int nIndent;
    protected PrintStream stream;

    public Dumper(PrintStream s) {
        this.stream = s;
        this.nIndent = 0;
    }

    public void printClass(Object obj, Location loc) {
        printIndent();
        stream.println(ANSI_RED + "<<" + obj.getClass().getSimpleName() + ">>" + ANSI_RESET + " (" + loc + ")");
    }

    public void printNodeList(String name, List<? extends Dumpable> nodes) {
        printIndent();
        stream.println(name + ":");
        indent();
        for (Dumpable n : nodes) {
            n.dump(this);
        }
        unindent();
    }

    public void printMember(String name, int n) {
        printPair(name, "" + n);
    }

    public void printMember(String name, long n) {
        printPair(name, "" + n);
    }

    public void printMember(String name, boolean b) {
        printPair(name, "" + b);
    }

    public void printMember(String name, TypeRef ref) {
        printPair(name, ref.toString());
    }

    public void printMember(String name, Type t) {
        printPair(name, (t == null ? "null" : t.toString()));
    }

    public void printMember(String name, String str, boolean isResolved) {
        printPair(name, TextUtils.dumpString(str)
                + (isResolved ? " (resolved)" : ""));
    }

    public void printMember(String name, String str) {
        printMember(name, str, false);
    }

    protected void printPair(String name, String value) {
        printIndent();
        stream.println(ANSI_BLUE + name + ANSI_RESET + ": " + value);
    }

    public void printMember(String name, TypeNode n) {
        printIndent();
        stream.println(ANSI_BLUE + name + ANSI_RESET + ": " + n.typeRef()
                + (n.isResolved() ? " (resolved)" : ""));
    }

    public void printMember(String name, Dumpable n) {
        printIndent();
        if (n == null) {
            stream.println(ANSI_BLUE + name + ANSI_RESET + ": null");
        }
        else {
            stream.println(ANSI_BLUE + name + ANSI_RESET + ":");
            indent();
            n.dump(this);
            unindent();
        }
    }

    protected void indent() { nIndent++; }
    protected void unindent() { nIndent--; }

    static final protected String indentString = "    ";

    protected void printIndent() {
        int n = nIndent;
        while (n > 0) {
            stream.print(indentString);
            n--;
        }
    }

    public void printClass(Object obj) {
        printIndent();
        stream.println(ANSI_RED + "<<" + obj.getClass().getSimpleName() + ">>" + ANSI_RESET);
    }

    public void printVars(String name, List<DefinedVariable> defvars) {
        printNodeList(name, defvars);

    }

    public void printFuncs(String name, List<DefinedFunction> defuns) {
        printNodeList(name, defuns);
    }
}
