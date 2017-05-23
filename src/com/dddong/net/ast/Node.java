package com.dddong.net.ast;

import com.dddong.net.complier.LocalResolver;

import java.io.PrintStream;

/**
 * Created by dddong on 2017/5/11.
 */
abstract public class Node implements Dumpable {
    public Node() {

    }

    abstract public Location location();

    public void dump(Dumper d) {
        d.printClass(this, location());
        _dump(d);
    }

    public void dump() {
        dump(System.out);
    }

    public void dump(PrintStream s) {
        dump(new Dumper(s));
    }

    abstract protected void _dump(Dumper d);

}
