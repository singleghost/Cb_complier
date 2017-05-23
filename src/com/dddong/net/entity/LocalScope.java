package com.dddong.net.entity;

import com.dddong.net.exception.SemanticException;
import com.dddong.net.utils.ErrorHandler;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by dddong on 2017/5/22.
 */
public class LocalScope extends Scope {
    protected Scope parent;
    protected Map<String, DefinedVariable> variables;

    public LocalScope(Scope parent) {
        this.parent = parent;
        this.variables = new LinkedHashMap<>();
    }

    public Entity get(String name) throws SemanticException {
        DefinedVariable var =  variables.get(name);
        if(var != null) {
            return var;
        } else {
            return parent.get(name);
        }
    }

    public void defineVariable(DefinedVariable definedVar) {
        DefinedVariable var = variables.get(definedVar.name());
        if(var != null) {
            throw new Error("duplicated variable: " + definedVar.name());
        }
        variables.put(definedVar.name(), definedVar);
    }

    public boolean isDefinedLocally(String name) {

        return variables.get(name) != null;
    }

    public void checkReferences(ErrorHandler h) {
        for(DefinedVariable var : variables.values()) {
            if(!var.isRefered()) {
                h.warn(var.location(), "unused variable " + var.name());
            }
        }
        for(LocalScope s : children) {
            s.checkReferences(h);
        }
    }
}
