package com.dddong.net.type;

import com.dddong.net.ast.Slot;
import com.dddong.net.utils.ErrorHandler;

import java.util.*;

/**
 * Created by dddong on 2017/5/25.
 */
public class TypeTable {
    private int intSize;
    private int longSize;
    private int pointerSize;

    private Map<TypeRef, Type> table;

    public TypeTable(int intSize, int longSize, int pointerSize) {
        this.intSize = intSize;
        this.longSize = longSize;
        this.pointerSize = pointerSize;
        table = new HashMap<>();
    }

    static public TypeTable ilp32() { return newTable(1, 2, 4, 4, 4); }
    static public TypeTable ilp64() { return newTable(1, 2, 8, 8, 8); }
    static public TypeTable lp64()  { return newTable(1, 2, 4, 8, 8); }
    static public TypeTable llp64() { return newTable(1, 2, 4, 4, 8); }

    static private TypeTable newTable(int charsize, int shortsize,
                                      int intsize, int longsize, int ptrsize) {
        TypeTable table = new TypeTable(intsize, longsize, ptrsize);
        table.put(new VoidTypeRef(), new VoidType());
        table.put(IntegerTypeRef.charRef(),
                new IntegerType(charsize,  true, "char"));
        table.put(IntegerTypeRef.shortRef(),
                new IntegerType(shortsize, true, "short"));
        table.put(IntegerTypeRef.intRef(),
                new IntegerType(intsize, true, "int"));
        table.put(IntegerTypeRef.longRef(),
                new IntegerType(longsize, true, "long"));
        table.put(IntegerTypeRef.ucharRef(),
                new IntegerType(charsize, false, "unsigned char"));
        table.put(IntegerTypeRef.ushortRef(),
                new IntegerType(shortsize, false, "unsigned short"));
        table.put(IntegerTypeRef.uintRef(),
                new IntegerType(intsize, false, "unsigned int"));
        table.put(IntegerTypeRef.ulongRef(),
                new IntegerType(longsize, false, "unsigned long"));
        return table;
    }

    public void put(TypeRef typeRef, Type type) {
        table.put(typeRef, type);
    }


    public boolean isDefined(TypeRef typeRef) {
        return table.get(typeRef) != null;
    }

    public Type get(TypeRef typeRef) {
        Type t = table.get(typeRef);
        if(t == null) {
            if(typeRef instanceof UserTypeRef) {
                /*if a unregistered usertype is used, it throws a parser exception instead of a semantic error,
                so we actually don't even need to handle such case here.
                 */
                UserTypeRef userTypeRef = (UserTypeRef)typeRef;
                throw new Error("undefined user type " + userTypeRef.name());
            }
            if(typeRef instanceof PointerTypeRef) {
                PointerType ptrType = new PointerType(pointerSize, get(((PointerTypeRef) typeRef).baseType()));
                table.put(typeRef, ptrType);
                return ptrType;
            } else if(typeRef instanceof ArrayTypeRef) {
                ArrayType arrayType = new ArrayType(typeRef.location(), get(((ArrayTypeRef) typeRef).baseType()),
                                         ((ArrayTypeRef) typeRef).length(), pointerSize);
                table.put(typeRef, arrayType);
                return arrayType;
            } else if(typeRef instanceof FunctionTypeRef) {

                Type ret_type = get(((FunctionTypeRef)typeRef).returnType());
                //解析函数的参数类型
                List<Type> paramDescs = new ArrayList<>();

                for(TypeRef tref: ((FunctionTypeRef) typeRef).params().typerefs()) {
                    Type paramType = getParamType(tref);
                    paramDescs.add(paramType);
                }

                Type func_type = new FunctionType(ret_type, new ParamTypes(typeRef.location(),
                        paramDescs, ((FunctionTypeRef)typeRef).params().isVararg()));
                table.put(typeRef, func_type);
                return func_type;
            }
        }
        return t;
    }

    public Type getParamType(TypeRef typeRef) {

        Type t = get(typeRef);
        if(t.isArray()) {
            return new PointerType(pointerSize, t.getArrayType().baseType());
        }
        return t;
    }

    //检查类型定义的有向图中是否存在闭环
    public void checkRecursiveDefinition(Type t, ErrorHandler h) {
        _checkRecursiveDefinition(t, new HashMap<Type, Object>(), h);
    }

    static final protected Object checking = new Object();
    static final protected Object checked = new Object();

    protected void _checkRecursiveDefinition(Type t, Map<Type, Object> marks, ErrorHandler h) {
        if(marks.get(t) == checking) {
            //这里为什么直接转换成 NamedType 了
            if(t instanceof ArrayType) {

                h.error(t.getArrayType().location(), "recursive type definition: " + t);
            } else {
                h.error(((NamedType) t).location(), "recursive type definition: " + t);
            }
            return;
        }
        else if (marks.get(t) == checked) {
            return;
        }
        else {
            marks.put(t, checking);
            if(t instanceof CompositeType) {
                CompositeType ct = (CompositeType)t;
                for (Slot st : ct.members()) {
                    _checkRecursiveDefinition(st.type(), marks, h);
                }
            }
            else if(t instanceof ArrayType) {
                ArrayType at = (ArrayType)t;
                _checkRecursiveDefinition(at.baseType(), marks, h);
            }
            else if(t instanceof UserType) {
                UserType ut = (UserType)t;
                _checkRecursiveDefinition(ut.realType(), marks, h);
            }
            marks.put(t, checked);
        }
    }

    public void semanticCheck(ErrorHandler h) {
        for(Type t : getAllType()) {
            if(t instanceof ArrayType) {

                checkVoidTypes((ArrayType)t, h);
            }
            else if(t instanceof CompositeType) {
                checkVoidTypes((CompositeType)t, h);
                checkDuplicateSlots((CompositeType) t, h);
            }
            checkRecursiveDefinition(t, h);
        }
    }

    private void checkDuplicateSlots(CompositeType t, ErrorHandler h) {
        Set<String> usedName = new HashSet<>();
        for(Slot s : t.members()) {
            if(usedName.contains(s.name())) {
                h.error(s.location(), "duplicated member name " + s.name());
            } else {
                usedName.add(s.name());
            }
        }
    }

    private void checkVoidTypes(ArrayType t, ErrorHandler h) {
        if(t.baseType().isVoid()) {
            h.error(t.location(), "base type of array cannot be 'void'");
        }
    }

    private void checkVoidTypes(CompositeType t, ErrorHandler h) {
        for(Slot s : t.members()) {
            if(s.type().isVoid()) {
                h.error(s.location(), "member type of composite type cannot be 'void'");
            }
        }
    }

    public Collection<Type> getAllType() {
        return table.values();
    }

    public int intSize() {
        return intSize;
    }

    public int longSize() {
        return longSize;
    }

    public int pointerSize() {
        return pointerSize;
    }
}
