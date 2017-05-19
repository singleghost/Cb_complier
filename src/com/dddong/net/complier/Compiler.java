package com.dddong.net.complier;

import java.util.List;

/**
 * Created by dddong on 2017/4/10.
 */
public class Compiler {
    static final public String ProgramName = "cbc";
    static final public String Version = "1.0.0";

    static public void main(String [] args) {
        new Compiler(ProgramName).commandMain(args);
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

