package com.dddong.net.entity;

import com.dddong.net.exception.SemanticException;
import com.dddong.net.utils.ErrorHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dddong on 2017/5/22.
 */
public class ToplevelScope extends Scope {
    protected Map<String, Entity> entities;
    protected List<DefinedVariable> staticLocalVariables;   //cache         这部分是干嘛的?

    @Override
    public Entity get(String name) throws SemanticException {

        Entity ent = entities.get(name);
        if(ent == null) {
            throw new SemanticException("unresolved reference: " + name);
        }
        return ent;
    }

    public void defineEntity(Entity entity) throws SemanticException {

        Entity e = entities.get(entity.name());
        if(e != null && e.isDefined()) {
            throw new SemanticException("duplicated declaration: " + entity.name() + ": " +
                    e.location() + " and " + entity.location());
        }
        entities.put(entity.name(), entity);
    }

    public void declareEntity(Entity entity) throws SemanticException {
        Entity e = entities.get(entity.name());
        if(e != null) {
            throw new SemanticException("duplicated declaration: " + entity.name() + ": " +
                    e.location() + " and " + entity.location());
        }
        entities.put(entity.name(), entity);
    }

    public void checkReferences(ErrorHandler errorHandler) {
        for(Entity ent : entities.values()) {
            if(ent.isDefined() && ent.isPrivate() && !ent.isRefered() && !ent.isConstant()) {
                //TODO这里为什么要限制不是常量呢???
                errorHandler.warn(ent.location(), "unused variable " + ent.name());
            }
        }
        for(LocalScope funcScope : children) {
            funcScope.checkReferences(errorHandler);    //这里和原作者不一样
        }
    }

    public ToplevelScope() {
        entities = new LinkedHashMap<>();
    }
}
