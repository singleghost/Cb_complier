package com.dddong.net.ir;

/**
 * Created by dddong on 2017/5/27.
 */
public enum Op {
    ADD,SUB,MUL,S_DIV,U_DIV,S_MOD,U_MOD,BIT_AND,BIT_OR,BIT_XOR,BIT_LSHIFT,BIT_RSHIFT,
    ARITH_RSHIFT,EQ,NEQ,S_GT,S_GTEQ,S_LT,S_LTEQ,U_GT,U_GTEQ,U_LT,U_LTEQ,UMINUS,BIT_NOT,
    NOT,//逻辑非
    S_CAST,//有符号数值的类型转换
    U_CAST;//无符号数值的类型转换


    public static Op internBinary(String operator, boolean signed) {

        switch (operator) {
            case "+":
                return ADD;
            case "-":
                return SUB;
            case "*":
                return MUL;
            case "/":
                if(signed) return S_DIV;
                else return U_DIV;
            case "%":
                if(signed) return S_MOD;
                else return U_MOD;
            case "&":
                return BIT_AND;
            case "|":
                return BIT_OR;
            case "^":
                return BIT_XOR;
            case "<<":
                return BIT_LSHIFT;
            case ">>":
                if(signed) return ARITH_RSHIFT;
                else return BIT_RSHIFT;
            case "==":
                return EQ;
            case "!=":
                return NEQ;
            case ">":
                if(signed) return S_GT;
                else return U_GT;
            case ">=":
                if(signed) return S_GTEQ;
                else return U_GTEQ;
            case "<":
                if(signed) return S_LT;
                else return U_LT;
            case "<=":
                if(signed) return S_LTEQ;
                else return U_LTEQ;
        }
    }
    @Override
    public String toString() {
        switch (this) {
            //TODO
            case ADD:
                return "ADD";
            default:
                throw new Error("must not happen");
        }

    }

    public static Op internUnary(String operator) {
        switch (operator) {
            case "-":
                return UMINUS;
            case "~":
                return BIT_NOT;
            case "!":
                return NOT;
            default:
                throw new Error("operator " + operator + " is not a unary operator");
        }
    }

}
