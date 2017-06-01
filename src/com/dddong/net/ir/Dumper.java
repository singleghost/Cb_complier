package com.dddong.net.ir;

import com.dddong.net.asm.Label;
import com.dddong.net.ast.Location;
import com.dddong.net.entity.DefinedFunction;
import com.dddong.net.entity.DefinedVariable;
import static com.dddong.net.utils.TextColor.*;

import java.io.PrintStream;
import java.util.List;

/**
 * Created by dddong on 2017/5/31.
 */
public class Dumper {
    PrintStream stream;
    private int numIndent;

    Dumper(PrintStream s) {
        stream = s;
        numIndent = 0;
    }

    public void printClass(Object obj) {
        printIndent();
        stream.println(ANSI_RED + "<<" + obj.getClass().getSimpleName() + ">>" + ANSI_RESET);
    }

    public void printClass(Object obj, Location loc) {
        printIndent();
        stream.println(ANSI_RED + "<<" + obj.getClass().getSimpleName() + ">>" + ANSI_RESET + " (" + loc + ")");
    }

    public void printMember(String name, int memb) {
        printPair(name, "" + memb);
    }

    public void printMember(String name, long memb) {
        printPair(name, "" + memb);
    }

    public void printMember(String name, boolean memb) {
        printPair(name, "" + memb);
    }

    public void printMember(String name, String memb) {
        printPair(name, memb);
    }

    public void printMember(String name, Label memb) {
        printPair(name, Integer.toHexString(memb.hashCode()));
    }

    public void printMember(String name, com.dddong.net.asm.Type memb) {
        printPair(name, memb.toString());
    }

    public void printMember(String name, com.dddong.net.type.Type memb) {
        printPair(name, memb.toString());
    }

    private void printPair(String name, String value) {
        printIndent();
        stream.println(ANSI_BLUE + name + ANSI_RESET + ": " + value);
    }

    public void printMember(String name, Dumpable memb) {
        printIndent();
        if (memb == null) {
            stream.println(ANSI_BLUE + name + ANSI_RESET + ": null");
        }
        else {
            stream.println(ANSI_BLUE + name + ANSI_RESET + ":");
            indent();
            memb.dump(this);
            unindent();
        }
    }

    public void printMembers(String name, List<? extends Dumpable> elems) {
        printIndent();
        stream.println(name + ":");
        indent();
        for (Dumpable elem : elems) {
            elem.dump(this);
        }
        unindent();
    }

    public void printVars(String name, List<DefinedVariable> vars) {
        printIndent();
        stream.println(name + ":");
        indent();
        for (DefinedVariable var : vars) {
            printClass(var, var.location());
            printMember("name", var.name());
            printMember("isPrivate", var.isPrivate());
            printMember("type", var.type());
            printMember("initializer", var.ir());
        }
        unindent();
    }

    public void printFuncs(String name, List<DefinedFunction> funcs) {
        printIndent();
        stream.println(name + ":");
        indent();
        for (DefinedFunction f : funcs) {
            printClass(f, f.location());
            printMember("name", f.name());
            printMember("isPrivate", f.isPrivate());
            printMember("type", f.type());
            printMembers("body", f.ir());
        }
        unindent();
    }

    private void indent() { numIndent++; }
    private void unindent() { numIndent--; }

    static final private String indentString = "    ";

    private void printIndent() {
        int n = numIndent;
        while (n > 0) {
            stream.print(indentString);
            n--;
        }
    }

}
