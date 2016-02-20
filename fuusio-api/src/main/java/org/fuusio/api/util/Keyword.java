package org.fuusio.api.util;

/**
 * {@link Keyword} is enum type defining the Java keywords.
 */
public enum Keyword {

    KEYWORD_ABSTRACT("abstract"),
    KEYWORD_CASE("case"),
    KEYWORD_CLASS("class"),
    KEYWORD_DO("do"),
    KEYWORD_ELSE("else"),
    KEYWORD_ENUM("enum"),
    KEYWORD_EXTENDS("extends"),
    KEYWORD_FINAL("final"),
    KEYWORD_FOR("for"),
    KEYWORD_IF("if"),
    KEYWORD_IMPORT("import"),
    KEYWORD_IMPLEMENTS("implements"),
    KEYWORD_INTERFACE("interface"),
    KEYWORD_NULL("null"),
    KEYWORD_PACKAGE("package"),
    KEYWORD_PRIVATE("private"),
    KEYWORD_PROTECTED("protected"),
    KEYWORD_PUBLIC("public"),
    KEYWORD_RETURN("return"),
    KEYWORD_STATIC("static"),
    KEYWORD_SUPER("super"),
    KEYWORD_SWITCH("switch"),
    KEYWORD_THIS("this"),
    KEYWORD_TRANSIENT("transient"),
    KEYWORD_WHILE("while");

    private final String mKeyword;

    Keyword(final String keyword) {
        mKeyword = keyword;
    }

    public final String toString() {
        return mKeyword;
    }

    public JavaWriter write(final JavaWriter writer) {
        writer.append(mKeyword);
        writer.append(" ");
        return writer;
    }

    public JavaWriter w(final JavaWriter writer) {
        return write(writer);
    }
}
