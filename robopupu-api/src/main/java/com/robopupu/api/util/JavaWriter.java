/*
 * Copyright (C) 2001 - 2015 Marko Salmela, http://robopupu.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robopupu.api.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class JavaWriter {

    public static final String ANNOTATION_OVERRIDE = "@Override";

    private static final String INDENTATION = "    ";

    private final Writer mWriter;

    private String mIndentation;
    private int mIndentationCount;

    public JavaWriter() {
        this(new StringWriter());
    }

    public JavaWriter(final Writer writer) {
        mIndentation = INDENTATION;
        mWriter = writer;
        mIndentationCount = 0;
    }

    public final String getIndentation() {
        return mIndentation;
    }

    public void setIndentation(final String indentation) {
        mIndentation = indentation;
    }

    public final int getIndentationCount() {
        return mIndentationCount;
    }

    public void setIndentationCount(final int count) {
        mIndentationCount = count;
    }

    public JavaWriter a(final String string) {
        return append(string);
    }

    public JavaWriter append(final String string) {
        try {
            mWriter.append(string);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public JavaWriter append(final boolean value) {
        try {
            mWriter.append(Boolean.toString(value));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public JavaWriter a(final boolean value) {
        return append(value);
    }

    public JavaWriter append(final float value) {
        try {
            mWriter.append(Float.toString(value));
            mWriter.append('f');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public JavaWriter append(final long value) {
        try {
            mWriter.append(Long.toString(value));
            mWriter.append('L');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public JavaWriter append(final int value) {
        try {
            mWriter.append(Integer.toString(value));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public JavaWriter a(final int value) {
        return append(value);
    }

    public JavaWriter space() {
        try {
            mWriter.append(' ');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public JavaWriter s() {
        return space();
    }


    public JavaWriter intend() {
        try {
            for (int i = 0; i < mIndentationCount; i++) {
                mWriter.append(mIndentation);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public JavaWriter newLine() {
        return newLine(true);
    }

    public JavaWriter n() {
        return newLine(true);
    }

    public JavaWriter n(final boolean indented) {
        return newLine(indented);
    }

    public JavaWriter newLine(final boolean indented) {
        try {
            mWriter.append('\n');

            if (indented) {
                intend();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public JavaWriter beginBlock() {
        try {
            mWriter.append('{');
            mWriter.append('\n');
            mIndentationCount++;
            intend();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public JavaWriter bob() {
        return beginBlock();
    }

    public JavaWriter endBlock() {
        return endBlock(true);
    }

    public JavaWriter eob() {
        return endBlock(true);
    }

    public JavaWriter endBlock(final boolean newLine) {
        try {
            mIndentationCount--;
            intend();
            mWriter.append('}');

            if (newLine) {
                mWriter.append('\n');
            } else {
                mWriter.append(" ");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public JavaWriter openParenthesis() {
        try {
            mWriter.append('(');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public JavaWriter closeParenthesis() {
        try {
            mWriter.append(')');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public JavaWriter endStatement() {
        return endStatement(true);
    }

    public JavaWriter eos() {
        return endStatement(true);
    }

    public JavaWriter eos(final boolean intented) {
        return endStatement(intented);
    }

    public JavaWriter endStatement(final boolean intented) {
        try {
            mWriter.append(';');

            mWriter.append('\n');
            if (intented) {
                intend();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public JavaWriter keyword(final Keyword keyword) {
        keyword.write(this);
        return this;
    }

    public JavaWriter k(final Keyword keyword) {
        return keyword(keyword);
    }

    public JavaWriter writeImport(final String packageName) {
        Keyword.IMPORT.write(this);
        append(packageName);
        endStatement();
        return this;
    }

    public JavaWriter writePackage(final String packageName) {
        Keyword.PACKAGE.write(this);
        append(packageName);
        endStatement();
        return this;
    }

    public JavaWriter beginClass(final String name) {
        return beginClass(name, true);
    }

    public JavaWriter beginClass(final String name, final boolean isPublic) {
        return beginClass(name, null, isPublic);
    }

    public JavaWriter beginClass(final String name, final String superClass, final boolean isPublic) {

        if (isPublic) {
            Keyword.PUBLIC.write(this);
        } else {
            Keyword.PRIVATE.write(this);
        }
        Keyword.CLASS.write(this);
        append(name);

        if (superClass != null) {
            Keyword.EXTENDS.write(this);
            append(superClass);
        }
        space();
        beginBlock();
        return this;
    }

    public JavaWriter endClass() {
        return endBlock();
    }

    public String getCode() {
        return mWriter.toString();
    }

    public void clear() {
        if (mWriter instanceof  StringWriter) {
            ((StringWriter)mWriter).getBuffer().setLength(0);
        } else {
            throw new UnsupportedOperationException("Meethod clear() is not supported for Writer class:" + mWriter.getClass().getName());
        }
    }
}
