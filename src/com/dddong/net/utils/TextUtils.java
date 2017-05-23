package com.dddong.net.utils;

import com.dddong.net.parser.Parser;

import java.io.UnsupportedEncodingException;

/**
 * Created by dddong on 2017/5/17.
 */
abstract public class TextUtils {

    static public String dumpString(String str) {
        try {
            return dumpString(str, Parser.SOURCE_ENCODING);
        }
        catch (UnsupportedEncodingException ex) {
            throw new Error("UTF-8 is not supported??: " + ex.getMessage());
        }
    }

    private static final byte vtab = 11;
    private static final byte bell = 7;
    private static final byte backspace = 8;
    private static final byte escape = 27;
    private static final byte vt = 11;
    static public String dumpString(String string, String encoding)
            throws UnsupportedEncodingException {
        byte[] src = string.getBytes(encoding);
        StringBuffer buf = new StringBuffer();
        buf.append("\"");
        for (int n = 0; n < src.length; n++) {
            int c = toUnsigned(src[n]);
            if (c == '"') buf.append("\\\"");
            else if (c == '\0') buf.append("\\0");
            else if (c == '\'') buf.append("\\'");
            else if (c == bell) buf.append("\\a");
            else if (c == escape) buf.append("\\e");
            else if (c == '\b') buf.append("\\b");
            else if (c == '\t') buf.append("\\t");
            else if (c == '\n') buf.append("\\n");
            else if (c == vtab) buf.append("\\v");
            else if (c == '\f') buf.append("\\f");
            else if (c == '\r') buf.append("\\r");
            else if (c == '\\') buf.append("\\\\");
            else if (isPrintable(c)) buf.append((char)c);
            else {
                buf.append("\\" + Integer.toOctalString(c));
            }
        }
        buf.append("\"");
        return buf.toString();
    }

    static private int toUnsigned(byte b) {
        return b >= 0 ? b : 256 + b;
    }

    static public boolean isPrintable(int c) {
        return (' ' <= c) && (c <= '~');
    }

}
