package com.dddong.net.complier;

import com.dddong.net.ast.AST;
import com.dddong.net.ast.Dumper;
import com.dddong.net.ir.IR;
import com.dddong.net.parser.LibraryLoader;
import com.dddong.net.parser.Parser;
import com.dddong.net.type.TypeTable;
import com.dddong.net.utils.ErrorHandler;

import java.io.File;
import java.io.PrintStream;
import java.util.List;


/**
 * Created by dddong on 2017/4/10.
 */
public class Compiler {
    static final public String ProgramName = "cbc";
    static final public String Version = "1.0.0";

    static public void main(String [] sourceFiles) {
//        new Compiler(ProgramName).commandMain(args);
        Dumper dp = new Dumper(new PrintStream(System.out));
        for (String sourceFile : sourceFiles) {
            try {
                File file = new File("sourceCodeTest/" + sourceFile);
                LibraryLoader libraryLoader = new LibraryLoader();
                ErrorHandler errorHandler = new ErrorHandler(sourceFile);
                AST ast = Parser.parseFile(file, libraryLoader, errorHandler, true);
                ast.dump(dp);
                LocalResolver localResolver = new LocalResolver(errorHandler);
                TypeTable typeTable = TypeTable.ilp32();
                TypeResolver typeResolver = new TypeResolver(typeTable, errorHandler);
                StaticTypeChecker typeChecker = new StaticTypeChecker(errorHandler, typeTable);
                DereferenceChecker dereferenceChecker = new DereferenceChecker(typeTable, errorHandler);
                localResolver.resolve(ast); //变量引用的消解
                typeResolver.resolve(ast);  //类型的消解

                typeChecker.semanticCheck();
                dereferenceChecker.check(ast);

                IRGenerator irGenerator = new IRGenerator(errorHandler, typeTable);
                IR ir = irGenerator.generate(ast);
                ir.dump();
            } catch (Exception ex) {
//                System.err.println(ex.getMessage());
                System.out.flush();
                ex.printStackTrace();
            }
        }
    }

//    private final ErrorHandler errorHandler;

    public Compiler(String programName) {

    }

    public void commandMain(String [] args) {
//        Options opts = Options.parse(args);
//        List<SourceFile> srcs = opts.sourceFiles();
//        build(srcs, opts);
    }

    public void build(List<SourceFile> srcs, Options opts) throws CompileException {
        for (SourceFile src : srcs) {
//            compile(src.path(), opts.asmFileNameOf(src), opts);
//            assemble(src.path(), opts.objFileNameOf(src), opts);

        }
        link(opts);
    }

    private void link(Options opts) {

    }
}

class CompileException extends Exception {

}

