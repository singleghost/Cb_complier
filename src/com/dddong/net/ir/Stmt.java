package com.dddong.net.ir;

import com.dddong.net.ast.Dumpable;
import com.dddong.net.ast.Dumper;
import com.dddong.net.ast.Location;

/**
 * Created by dddong on 2017/5/27.
 */
abstract public class Stmt implements Dumpable {
    protected Location location;

    public Stmt(Location location) {
        this.location = location;
    }

    @Override
    public void dump(Dumper d) {
        d.printClass(this, location);
        _dump(d);
    }

    abstract protected void _dump(Dumper d);

    public Location location() {
        return location;
    }
}
