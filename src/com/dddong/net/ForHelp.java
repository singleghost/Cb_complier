package com.dddong.net;

import com.dddong.net.ast.Dumper;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dddong on 2017/5/12.
 */
public class ForHelp {
    private static String s;

    static List<String> strlist = new ArrayList<>();

    public static void main(String[] args) {
        Dumper dp = new Dumper(new PrintStream(System.out));
        System.out.flush();
    }
}
