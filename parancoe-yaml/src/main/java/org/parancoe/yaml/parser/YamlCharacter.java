/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Yaml - DISCONTINUED.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.parancoe.yaml.parser;

/**
 * YAML character classes.
 * 
 * <p>
 * This class encapsulates all character related productions.
 * </p>
 * 
 * @autor: Rolf Veen
 * @date: March 2002
 * @license: Open-source compatible TBD (Apache or zlib or Public Domain)
 */
public final class YamlCharacter {

    public final static int PRINTABLE = 1;
    public final static int WORD = 2;
    public final static int LINE = 3;
    public final static int LINESP = 4;
    public final static int SPACE = 5;
    public final static int LINEBREAK = 6;
    public final static int DIGIT = 7;
    public final static int INDENT = 8;

    public static boolean is(char c, int type) {
        switch (type) {
            case PRINTABLE:
                return isPrintableChar(c);
            case WORD:
                return isWordChar(c);
            case LINE:
                return isLineChar(c);
            case LINESP:
                return isLineSpChar(c);
            case SPACE:
                return isSpaceChar(c);
            case LINEBREAK:
                return isLineBreakChar(c);
            case DIGIT:
                return Character.isDigit(c);
            case INDENT:
                return (c == ' ');
            default:
                return false;
        }
    }

    public static boolean is(int c, int type) {
        if (c == -1) {
            return false;
        }
        char ch = (char) c;

        switch (type) {
            case PRINTABLE:
                return isPrintableChar(ch);
            case WORD:
                return isWordChar(ch);
            case LINE:
                return isLineChar(ch);
            case LINESP:
                return isLineSpChar(ch);
            case SPACE:
                return isSpaceChar(ch);
            case LINEBREAK:
                return isLineBreakChar(ch);
            case DIGIT:
                return Character.isDigit(ch);
            case INDENT:
                return (ch == ' ');
            default:
                return false;
        }
    }

    /**
     * Printable character.
     * 
     * <p>
     * But: Java characters have a maximum value of 65535.
     * </p>
     */
    public static boolean isPrintableChar(char c) {
        if (c >= 0x20 && c <= 0x7e) {
            return true;
        }
        if (c == 9 || c == 10 || c == 13 || c == 0x85) {
            return true;
        }
        if (c >= 0xa0 && c <= 0xd7ff) {
            return true;
        }
        if (c >= 0xe000 && c <= 0xfffd) {
            return true;
        }
        return false;
    }

    /** all printable characters except space and line breaks. */
    public static boolean isLineChar(char c) {
        if (c == 0x20 || c == 9 || c == 10 || c == 13 || c == 0x85) {
            return false;
        }
        return isPrintableChar(c);
    }

    /** all printable characters except line breaks. */
    public static boolean isLineSpChar(char c) {
        if (c == 10 || c == 13 || c == 0x85) {
            return false;
        }
        return isPrintableChar(c);
    }

    /** ASCII characters */
    public static boolean isWordChar(char c) {
        if (c >= 0x41 && c <= 0x5a) {
            return true;
        }
        if (c >= 0x61 && c <= 0x7a) {
            return true;
        }
        if (c >= 0x30 && c <= 0x39) {
            return true;
        }
        if (c == '-') {
            return true;
        }
        return false;
    }

    /**
     * space_character :: = TAB | SP
     * 
     * <p>
     * FF is a common space character. ??
     * </p>
     */
    public static boolean isSpaceChar(char c) {
        if (c == 9 || c == 0x20) {
            return true;
        }
        return false;
    }

    /** line_break ::= LF | CR | NEL | LS | PS */
    public static boolean isLineBreakChar(char c) {
        if (c == 10 || c == 13 || c == 0x85 || c == 0x2028 || c == 0x2029) {
            return true;
        }
        return false;
    }

    /** returns true for all indicators */
    public static boolean isIndicator(char c) {
        String indicators = "-:[]{},?*&!|#@%^'\"";

        return (indicators.indexOf(c) != -1) ? true : false;
    }

    /** space_indicator ::= ':' | '-' */
    public static boolean isIndicatorSpace(char c) {
        String indicators = ":-";

        return (indicators.indexOf(c) != -1) ? true : false;
    }

    /** inline_indicator ::= '[' | ']' | '{' | '}' | ',' */
    public static boolean isIndicatorInline(char c) {
        String indicators = "[]{},";

        return (indicators.indexOf(c) != -1) ? true : false;
    }

    /** nonspace_indicator ::= ':' | '-' */
    public static boolean isIndicatorNonSpace(char c) {
        String indicators = "?*&!]|#@%^\"'";

        return (indicators.indexOf(c) != -1) ? true : false;
    }

    public static boolean isIndicatorSimple(char c) {
        String indicators = ":[]{},";

        return (indicators.indexOf(c) != -1) ? true : false;
    }

    public static boolean isLooseIndicatorSimple(char c) {
        String indicators = "[]{},";

        return (indicators.indexOf(c) != -1) ? true : false;
    }
}
