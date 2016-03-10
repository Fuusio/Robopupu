package com.robopupu.api.util;

/**
 * {@link Keyword} is enum type representing the Java keywords.
 */
public enum Keyword {

    ABSTRACT("abstract"),
    CASE("case"),
    CLASS("class"),
    DO("do"),
    ELSE("else"),
    ENUM("enum"),
    EXTENDS("extends"),
    FINAL("final"),
    FOR("for"),
    IF("if"),
    IMPORT("import"),
    IMPLEMENTS("implements"),
    INTERFACE("interface"),
    NEW("new"),
    NULL("null"),
    PACKAGE("package"),
    PRIVATE("private"),
    PROTECTED("protected"),
    PUBLIC("public"),
    RETURN("return"),
    STATIC("static"),
    SUPER("super"),
    SWITCH("switch"),
    THIS("this"),
    THROW("throw"),
    TRANSIENT("transient"),
    VOLATILE("volatile"),
    WHILE("while");

    private final String mKeyword;

    Keyword(final String keyword) {
        mKeyword = keyword;
    }

    public final String toString() {
        return mKeyword;
    }

    public JavaWriter write(final JavaWriter writer) {
        writer.append(mKeyword);
        writer.append(' ');
        return writer;
    }

    public JavaWriter w(final JavaWriter writer) {
        return write(writer);
    }
}
