package com.dddong.net.complier;

import com.dddong.net.asm.Label;
import com.dddong.net.ast.*;
import com.dddong.net.entity.*;
import com.dddong.net.exception.JumpError;
import com.dddong.net.exception.SemanticError;
import com.dddong.net.exception.SemanticException;
import com.dddong.net.ir.*;
import com.dddong.net.type.FunctionType;
import com.dddong.net.type.PointerType;
import com.dddong.net.type.Type;
import com.dddong.net.type.TypeTable;
import com.dddong.net.utils.ErrorHandler;
import com.dddong.net.utils.ListUtils;

import java.util.*;

/**
 * Created by dddong on 2017/5/27.
 */
public class IRGenerator implements ASTVisitor<Void, Expr> {
    ErrorHandler errorHandler;
    TypeTable typeTable;

    public IRGenerator(ErrorHandler errorHandler, TypeTable typeTable) {
        this.errorHandler = errorHandler;
        this.typeTable = typeTable;
    }

    public IR generate(AST ast) throws SemanticException {
        for(DefinedVariable var : ast.definedVariables()) {
            if(var.hasInitializer()) {
                var.setIR(transformExpr(var.initializer()));
            }
        }
        for (DefinedFunction f : ast.definedFunctions()) {
            f.setIR(compileFunctionBody(f));
        }
        if (errorHandler.errorOccured()) {
            throw new SemanticException("IR generation failed");
        }
        return ast.ir();
    }

    List<Stmt> stmts;
    LinkedList<LocalScope> scopeStack;
    LinkedList<Label> breakStack;
    LinkedList<Label> continueStack;
    Map<String, JumpEntry> jumpMap;

    public List<Stmt> compileFunctionBody(DefinedFunction f) {
        stmts = new ArrayList<>();
        scopeStack = new LinkedList<>();
        breakStack = new LinkedList<>();
        continueStack = new LinkedList<>();
        jumpMap = new HashMap<String, JumpEntry>();
        transformStmt(f.body());
//        checkJumpLinks(jumpMap);
        return stmts;
    }

    private void transformStmt(StmtNode node) {
        node.accept(this);
    }


    private void transformStmt(ExprNode node) {
        node.accept(this);
    }

    private int exprNestLevel = 0;
    private Expr transformExpr(ExprNode node) {
        exprNestLevel++;
        Expr e = node.accept(this);
        exprNestLevel--;
        return e;
    }

    private boolean isStatement() {
        return exprNestLevel == 0;
    }


    private void jump(Label label) {
        Jump jump = new Jump(null, label);
        stmts.add(jump);
    }

    private void label(Label label) {
        LabelStmt labelStmt = new LabelStmt(null, label);
        stmts.add(labelStmt);
    }

    private void cjump(Location location, Expr cond, Label thenLabel, Label endLabel) {
        CJump cj = new CJump(location, cond, thenLabel, endLabel);
        stmts.add(cj);
    }

    private void error(Node node, String message) {
        errorHandler.error(node.location(), message);
    }

    private void jump(Location location, Label label) {
        stmts.add(new Jump(location, label));
    }

    private void popContinue() {
        continueStack.removeLast();
    }

    private void pushContinue(Label label) {
        continueStack.add(label);
    }

    private void popBreak() {
        breakStack.removeLast();
    }

    private void pushBreak(Label label) {
        breakStack.add(label);
    }

    private Label currentBreakTarget() {
        try {
            return breakStack.getLast();
        } catch (NoSuchElementException err) {
            throw new JumpError("break node must be in 'while','for','do-while','switch' stmts");
        }
    }


    private Label currentContinueTarget() {
        try {
            return continueStack.getLast();
        } catch (NoSuchElementException err) {
            throw new JumpError("continue node must be in 'while','for','do-while','switch' stmts");
        }

    }
    @Override
    public Void visit(BlockNode node) {
        for(DefinedVariable var : node.variables()) {
            if(var.hasInitializer()) {
                var.setIR(transformExpr(var.initializer()));
            }
        }
        for(StmtNode stmt : node.stmts()) {
            transformStmt(stmt);
        }
        return null;
    }

    @Override
    public Void visit(ExprStmtNode node) {
        transformStmt(node.expr());
        return null;
    }

    @Override
    public Void visit(IfNode node) {
        Label thenLabel = new Label();
        Label elseLabel = new Label();
        Label endLabel = new Label();
        Expr cond = transformExpr(node.cond());
        if (node.elseBody() == null) {
            cjump(node.location(), cond, thenLabel, endLabel);
            label(thenLabel);
            transformStmt(node.thenBody());
            label(endLabel);
        }
        else {
            cjump(node.location(), cond, thenLabel, elseLabel);
            label(thenLabel);
            transformStmt(node.thenBody());
            jump(endLabel);
            label(endLabel);
            label(elseLabel);
            transformStmt(node.elseBody());
            label(endLabel);
        }
        return null;
    }

    @Override
    public Void visit(SwitchNode node) {
        List<Case> cases = new ArrayList<Case>();
        Label endLabel = new Label();
        Label defaultLabel = endLabel;

        Expr cond = transformExpr(node.expr());
        for (CaseNode c : node.caseNodeList()) {
            if (c.isDefault()) {
                defaultLabel = c.label();
            }
            else {
                for (ExprNode val : c.caseValues()) {
                    Expr v = transformExpr(val);
                    cases.add(new Case(((Int)v).value(), c.label()));
                }
            }
        }
        stmts.add(new Switch(node.location(), cond, cases, defaultLabel, endLabel));
        pushBreak(endLabel);
        for (CaseNode c : node.caseNodeList()) {
            label(c.label());
            transformStmt(c.body());
        }
        popBreak();
        label(endLabel);
        return null;

    }

    @Override
    public Void visit(CaseNode node) {
        throw new Error("must not happen");
    }

    @Override
    public Void visit(WhileNode node) {
        Label begLabel = new Label();
        Label bodyLabel = new Label();
        Label endLabel = new Label();
        label(begLabel);
        cjump(node.location(), transformExpr(node.condExpr()), bodyLabel, endLabel);
        label(bodyLabel);
        pushContinue(begLabel);
        pushBreak(endLabel);
        transformStmt(node.body());
        popBreak();
        popContinue();
        jump(begLabel);
        label(endLabel);
        return null;
    }

    @Override
    public Void visit(DoWhileNode node) {
        Label begLabel = new Label();
        Label endLabel = new Label();
        label(begLabel);
        pushContinue(begLabel);
        pushBreak(endLabel);
        transformStmt(node.body());
        popBreak();
        popContinue();
        cjump(node.expr().location(), transformExpr(node.expr()), begLabel, endLabel);
        label(endLabel);
        return null;
    }

    @Override
    public Void visit(ForNode node) {
        Label condLabel = new Label();
        Label bodyLabel = new Label();
        Label actionLabel = new Label();
        Label endLabel = new Label();
        transformExpr(node.initExpr());
        label(condLabel);
        cjump(node.location(), transformExpr(node.condExpr()), bodyLabel, endLabel);
        label(bodyLabel);

        pushContinue(actionLabel);
        pushBreak(endLabel);
        transformStmt(node.bodyStmt());
        popBreak();
        popContinue();

        label(actionLabel);
        transformExpr(node.actionExpr());
        jump(condLabel);
        label(endLabel);


        return null;
    }

    @Override
    public Void visit(BreakNode node) {
        try {
            jump(node.location(), currentBreakTarget());
        } catch (JumpError err) {
            error(node, err.getMessage());
        }
        return null;
    }

    @Override
    public Void visit(ContinueNode node) {
        try {
            jump(node.location(), currentContinueTarget());
        } catch (JumpError err) {
            error(node, err.getMessage());
        }
        return null;
    }

    @Override
    public Void visit(GotoNode node) {
        Label label;
        JumpEntry entry = jumpMap.get(node.labelName());
        if(entry == null) {
            entry = new JumpEntry(new Label());
            jumpMap.put(node.labelName(), entry);
            label = entry.label;
        } else {
            label = entry.label;
        }
        entry.numRefered++;

        jump(node.location(), label);
        return null;
    }

    @Override
    public Void visit(LabelNode node) {

        Label label;
        JumpEntry entry;
        if(jumpMap.get(node.name()) == null) {
            entry = new JumpEntry(new Label());
            entry.isDefined = true;
            entry.location = node.location();
            jumpMap.put(node.name(), entry);
        }
        else {
            //如果jumpEntry已经存在
            entry = jumpMap.get(node.name());
            if (entry.isDefined == true) {
                error(node, "duplicate label " + node.name());
                return null;
            }
            else {
                entry.isDefined = true;
                entry.location = node.location();
            }
        }
        label(entry.label);
        transformStmt(node.stmt());
        return null;
    }

    @Override
    public Void visit(ReturnNode node) {
        Expr ret = transformExpr(node.expr());
        stmts.add(new Return(node.location(), ret));
        return null;
    }

    @Override
    public Expr visit(AssignNode node) {
        Location lloc = node.lhs().location();
        Location rloc = node.rhs().location();
        if(isStatement()) {
            //Evaluate RHS before LHS
            Expr rhs = transformExpr(node.rhs());
            assign(lloc, transformExpr(node.lhs()), rhs);
            return null;
        } else {
            // lhs = rhs -> tmp = rhs, lhs = tmp, tmp
            DefinedVariable tmp = tmpVar(node.rhs().type());
            assign(rloc, ref(tmp), transformExpr(node.rhs()));
            assign(lloc, transformExpr(node.lhs()), ref(tmp));
            return ref(tmp);
        }
    }

    private Var ref(Entity ent) {
        return new Var(asmType(ent.type()), ent);
    }

    private long tmpVarID = 0;
    private DefinedVariable tmpVar(Type type) {
        String varName = "@tmp" + tmpVarID;
        tmpVarID = tmpVarID + 1;
        return new DefinedVariable(true, new TypeNode(type), varName, null);
    }

    private void assign(Location location, Expr lhs, Expr rhs) {
        stmts.add(new Assign(location, addressOf(lhs), rhs));
    }

    @Override
    public Expr visit(OpAssignNode node) {
        Expr rhs = transformExpr(node.rhs());
        Expr lhs = transformExpr(node.lhs());

        Op op = Op.internBinary(node.operator(), node.lhs().type().isSigned());
        return transformOpAssign(node.location(), op, node.lhs().type(), lhs, rhs);
    }

    @Override
    public Expr visit(CondExprNode node) {
        Label thenLabel = new Label();
        Label elseLabel = new Label();
        Label endLabel = new Label();

        DefinedVariable tmpVar = tmpVar(node.type());

        Expr cond = transformExpr(node.cond());
        cjump(node.cond().location(), cond, thenLabel, elseLabel);
        label(thenLabel);
        assign(node.trueExpr().location(), ref(tmpVar), transformExpr(node.trueExpr()));
        jump(endLabel);
        label(elseLabel);
        assign(node.falseExpr().location(), ref(tmpVar), transformExpr(node.falseExpr()));
        label(endLabel);

        return isStatement() ? null : ref(tmpVar);
    }

    @Override
    public Expr visit(LogicalOrNode node) {
        Label thenLabel = new Label();
        Label endLabel = new Label();
        DefinedVariable var = tmpVar(node.type());

        assign(node.left().location(), ref(var), transformExpr(node.left()));
        cjump(node.left().location(), ref(var), endLabel, thenLabel);
        label(thenLabel);
        assign(node.right().location(), ref(var), transformExpr(node.right()));
        label(endLabel);

        //TODO 这里返回的 var 是左表达式或者右表达式的值，返回1或返回0要怎么做
        return ref(var);
    }

    @Override
    public Expr visit(LogicalAndNode node) {
        Label thenLabel = new Label();
        Label endLabel = new Label();
        DefinedVariable var = tmpVar(node.type());

        assign(node.left().location(), ref(var), transformExpr(node.left()));
        cjump(node.left().location(), ref(var), thenLabel, endLabel);
        label(thenLabel);
        assign(node.right().location(), ref(var), transformExpr(node.right()));
        label(endLabel);

        //TODO 这里返回的 var 是左表达式或者右表达式的值，返回1或返回0要怎么做
        return ref(var);
    }

    @Override
    public Expr visit(BinaryOpNode node) {
        Expr right = transformExpr(node.right());   //先计算右边,为什么?
        Expr left = transformExpr(node.left());
        Op op = Op.internBinary(node.operator(), node.type().isSigned());
        Type t = node.type();
        Type l = node.left().type();
        Type r = node.right().type();
        if (isPointerDiff(op, l, r)) {
            Expr tmp = new Bin(asmType(t), op, left, right);
            return new Bin(asmType(t), Op.S_DIV, tmp, ptrBaseSize(l));
        }
        else if(isPointerArithmetic(op, l)) {
            return new Bin(asmType(t), op, left, new Bin(asmType(r), Op.MUL, right, ptrBaseSize(l)));
        }
        else if(isPointerArithmetic(op, r)) {
            return new Bin(asmType(t), op, new Bin(asmType(l), Op.MUL, left, ptrBaseSize(r)), right);
        }
        return new Bin(asmType(t), op, left, right);
    }

    private boolean isPointerArithmetic(Op op, Type type) {

        if(op != Op.ADD && op != Op.SUB) return false;
        if (! (type instanceof PointerType)) return false;
        return true;
    }

    private boolean isPointerDiff(Op op, Type l, Type r) {
        if(op != Op.SUB) return false;
        if(!(l instanceof PointerType)) return false;
        if(!(r instanceof PointerType)) return false;
        if(l.getPointerType().baseType() != r.getPointerType().baseType()) return false;
        return true;
    }

    private Int ptrBaseSize(Type type) {
        long size = type.getPointerType().baseType().size();
        return new Int(com.dddong.net.asm.Type.get(4), size);
    }

    private Int ptrdiff(long offset) {
        Int i = new Int(com.dddong.net.asm.Type.INT32, offset);
        return i;
    }

    private Mem mem(Expr addr, Type type) {
        return new Mem(asmType(type), addr);
    }

    private Expr addressOf(Expr expr) {
        return expr.addressNode(ptr_t());
    }

    private com.dddong.net.asm.Type ptr_t() {
        return com.dddong.net.asm.Type.get(typeTable.pointerSize());
    }

    @Override
    public Expr visit(UnaryOpNode node) {
        if (node.operator().equals("+")) {
            return transformExpr(node.expr());
        }
        else {
            return new Uni(asmType(node.type()),
                            Op.internUnary(node.operator()),
                            transformExpr(node.expr()));
        }
    }

    private com.dddong.net.asm.Type asmType(Type type) {
        if(type.isVoid()) return int_t();
        return com.dddong.net.asm.Type.get(type.size());
    }

    private com.dddong.net.asm.Type int_t() {
        return com.dddong.net.asm.Type.get(typeTable.intSize());
    }

    @Override
    public Expr visit(PrefixOpNode node) {
        // ++expr -> expr += 1;
        Expr expr = transformExpr(node.expr());
        Type t = node.expr().type();
        return transformOpAssign(node.location(), binOp(node.operator()), t, transformExpr(node.expr()), imm(t, 1));
    }

    @Override
    public Expr visit(SuffixOpNode node) {
        Expr expr = transformExpr(node.expr());
        Type t = node.expr().type();
        Op op = binOp(node.operator());
        Location loc = node.location();

        if(isStatement()) {
            // expr++; -> expr += 1;
            transformOpAssign(loc, op, t, expr, imm(t, 1));
            return null;
        }
        else if(expr.isVar()) {
            // cont(expr++) -> v = expr; expr = v + 1; cont(v)
            DefinedVariable v = tmpVar(t);
            assign(loc, ref(v), expr);
            assign(loc, expr, bin(op, t, ref(v), imm(t, 1)));
            return ref(v);
        }
        else {
            // cont(expr++) -> a = &expr; v = *a; *a = *a + 1; cont(v)
            DefinedVariable a = tmpVar(pointerTo(t));
            DefinedVariable v = tmpVar(t);
            assign(loc, ref(a), addressOf(expr));
            assign(loc, ref(v), mem(a));
            assign(loc, mem(a), bin(op, t, mem(a), imm(t, 1)));
            return ref(v);
        }
    }

    private Expr transformOpAssign(Location loc, Op op, Type lhsType, Expr lhs, Expr rhs) {
        if (lhs.isVar()) {
            // cont(lhs += rhs) -> lhs = lhs + rhs; cont(lhs)
            assign(loc, lhs, bin(op, lhsType, lhs, rhs));
            return isStatement() ? null : lhs;
        }
        else {
            // cont(lhs += rhs) -> a = &lhs; *a = *a + rhs; cont(*a)
            DefinedVariable a = tmpVar(pointerTo(lhsType));
            assign(loc, ref(a), addressOf(lhs));
            assign(loc, mem(a), bin(op, lhsType, mem(a), rhs));
            return isStatement() ? null : mem(a);
        }

    }

    private Mem mem(Entity ent) {
        return new Mem(asmType(ent.type().baseType()), ref(ent));
    }

    private PointerType pointerTo(Type t) {

        return new PointerType(typeTable.pointerSize(), t);
    }

    private Bin bin(Op op, Type t, Expr lhs, Expr rhs) {

        return new Bin(asmType(t), op, lhs, rhs);
    }

    private Int imm(Type operandType, long n) {

        if (operandType.isPointer()) {
            return new Int(ptrdiff_t(), n);
        }
        else {
            return new Int(int_t(), n);
        }
    }

    private com.dddong.net.asm.Type ptrdiff_t() {

        return com.dddong.net.asm.Type.get(typeTable.longSize());
    }


    private Op binOp(String operator) {

        if(operator == "++") return Op.ADD;
        if(operator == "--") return Op.SUB;

        throw new SemanticError("operator " + operator + " is not a valid suffix");
    }

    @Override
    public Expr visit(ArefNode node) {
        Expr base = addressOf(transformExpr(node.baseExpr()));
        Expr index = transformIndex(node);
        Expr offset = new Bin(int_t(), Op.MUL, new Int(int_t(), node.elementSize()), index);
        Expr addr = new Bin(ptr_t(), Op.ADD, base, offset);
        return mem(addr, node.type());
    }

    private Expr transformIndex(ArefNode node) {
        if (node.isMultiDimension()) {
            return new Bin(int_t(), Op.ADD,
                    transformExpr(node.index()),
                    new Bin(int_t(), Op.MUL,
                            new Int(int_t(), node.length()),
                            transformIndex((ArefNode)node.expr())));
        }
        else {
            return transformExpr(node.index());
        }

    }

    @Override
    public Expr visit(MemberNode node) {
        Expr expr = addressOf(transformExpr(node.expr()));
        Expr offset = ptrdiff(node.offset());
        Expr addr = new Bin(ptr_t(), Op.ADD, expr, offset);
        //在数组和函数类型的表达式的情况下始终生成左值
        return node.isLoadable() ? mem(addr, node.type()) : addr;
    }

    @Override
    public Expr visit(PtrMemberNode node) {
        Expr expr = transformExpr(node.expr());
        Expr offset = ptrdiff(node.offset());
        Expr addr = new Bin(ptr_t(), Op.ADD, expr, offset);
        return node.isLoadable() ? mem(addr, node.type()) : addr;
    }

    @Override
    public Expr visit(FuncallNode node) {

        List<Expr> args = new ArrayList<>();
        //按照从右到左的顺序解析参数
        for(ExprNode exprNode : ListUtils.reverse(node.args())) {
            args.add(transformExpr(exprNode));
        }
        Expr call = new Call(asmType(node.type()), transformExpr(node.nameExpr()), args);
        if(isStatement()) {
            stmts.add(new ExprStmt(node.location(), call));
            return null;
        }
        else {
            DefinedVariable tmp = tmpVar(node.type());
            assign(node.location(), ref(tmp), call);
            return ref(tmp);
        }
    }

    @Override
    public Expr visit(DereferenceNode node) {
        Expr expr = transformExpr(node.expr());
        return node.isLoadable() ? mem(expr, node.type()) : expr;

    }

    @Override
    public Expr visit(AddressNode node) {
        Expr expr = transformExpr(node.expr());
        return node.expr().isLoadable() ? addressOf(expr) : expr;
    }

    @Override
    public Expr visit(CastNode node) {
        if(node.isEffectiveCast()) {
            return new Uni(asmType(node.expr().type()), node.type().isSigned() ? Op.S_CAST : Op.U_CAST, transformExpr(node.expr()));
        }
        else if(isStatement()) {
            transformExpr(node.expr());
            return null;
        }
        else {
            return transformExpr(node.expr());
        }
    }

    @Override
    public Expr visit(SizeofExprNode node) {
        transformExpr(node.expr());
        if(! node.expr().type().isArray()) {
            return new Int(size_t(), node.expr().type().size());
        }
        else {
            return new Int(size_t(), node.expr().type().getArrayType().length() * typeTable.pointerSize());
        }
    }

    private com.dddong.net.asm.Type size_t() {
        return com.dddong.net.asm.Type.get(typeTable.longSize());
    }

    @Override
    public Expr visit(SizeofTypeNode node) {
        return new Int(size_t(), node.operand().size());
    }

    @Override
    public Expr visit(VariableNode node) {
        if(node.type() instanceof FunctionType) {
            return new Var(ptr_t(), node.entity());
        }
        return new Var(asmType(node.type()), node.entity());
    }

    @Override
    public Expr visit(IntegerLiteralNode node) {
        return new Int(asmType(node.type()), node.value());
    }

    @Override
    public Expr visit(StringLiteralNode node) {
        return new Str(asmType(node.type()), new ConstantEntry(node.str()));
    }

    class JumpEntry {
        public Label label;
        public long numRefered;
        public boolean isDefined;
        public Location location;

        public JumpEntry(Label label) {
            this.label = label;
            numRefered = 0;
            isDefined = false;
        }
    }
}
