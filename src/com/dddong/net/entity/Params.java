package com.dddong.net.entity;

import com.dddong.net.ast.Dumpable;
import com.dddong.net.ast.Dumper;
import com.dddong.net.ast.Location;
import com.dddong.net.type.ParamTypeRefs;
import com.dddong.net.type.TypeRef;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dddong on 2017/5/15.
 */
public class Params extends ParamSlots<CBCParameter> implements Dumpable {
    public Params(Location loc, List<CBCParameter> paramDescs) {
        super(loc, paramDescs, false);
    }

    public List<CBCParameter> parameters() {
        return paramDescriptors;
    }

    public ParamTypeRefs parametersTypeRef() {
        List<TypeRef> typerefs = new ArrayList<TypeRef>();
        for (CBCParameter param : paramDescriptors) {
            typerefs.add(param.typeNode().typeRef());
        }
        return new ParamTypeRefs(location, typerefs, vararg);
    }

    public boolean equals(Object other) {
        return (other instanceof Params) && equals((Params)other);
    }

    public boolean equals(Params other) {
        return other.vararg == vararg
                && other.paramDescriptors.equals(paramDescriptors);
    }

    public void dump(Dumper d) {
        d.printNodeList("parameters", parameters());
        if(isVararg()) {
            d.printMember("vararg", vararg);
        }
    }

}
