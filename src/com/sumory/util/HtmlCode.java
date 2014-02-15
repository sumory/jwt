package com.sumory.util;

public class HtmlCode {

    public static String encode(String str) {
        String s = "";
        if (str.length() == 0)
            return "";
        s = str.replaceAll("&", "&amp;");
        s = s.replaceAll("<", "&lt;");
        s = s.replaceAll(">", "&gt;");
        s = s.replaceAll("\'", "'");
        s = s.replaceAll("\"", "&quot;");
        return s;
    }

    public static String decode(String str) {
        String s = "";
        if (str.length() == 0)
            return "";
        s = str.replaceAll("&amp;", "&");
        s = s.replaceAll("&lt;", "<");
        s = s.replaceAll("&gt;", ">");
        s = s.replaceAll("'", "\'");
        s = s.replaceAll("&quot;", "\"");
        return s;
    }
}
