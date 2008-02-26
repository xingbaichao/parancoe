/*
 * Copyright (c) 2005, Yu Cheung Ho
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted 
 * provided that the following conditions are met:
 *
 *    * Redistributions of source code must retain the above copyright notice, this list of 
 *        conditions and the following disclaimer.
 *    * Redistributions in binary form must reproduce the above copyright notice, this list 
 *        of conditions and the following disclaimer in the documentation and/or other materials 
 *        provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR 
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS 
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, 
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF 
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * The above license applies to the changes I've made to this file. The original file
 * was created by Rolf Veen.
 * 
 */
package org.parancoe.yaml.parser;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

/**
 * An experimental Yaml org.parancoe.yaml.parser.
 * 
 * <ul>
 * <li>No compiler-compiler: hand-written code.
 * <li>All functions return boolean if the item exists, false if not. If false,
 * they don't modify anything.
 * <li>Relevant functions send an event thru the ParserEvent object.
 * <li>properties are inespecific (analyze at the next higher level): no branch
 * and leaf properties.
 * <li>Uses a special ParserReader, not a java.io.PushBackReader.
 * </ul>
 * 
 * @autor: Rolf Veen
 * @date: March 2002
 * @license: Open-source compatible TBD (Apache or zlib or Public Domain)
 */

public final class YamlParser {
    /* Definition of the YAML events */

    public final static int LIST_OPEN = '[';

    public final static int LIST_CLOSE = ']';

    public final static int MAP_OPEN = '{';

    public final static int MAP_CLOSE = '}';

    public final static int LIST_NO_OPEN = 'n';

    public final static int MAP_NO_OPEN = 'N';

    public final static int DOCUMENT_HEADER = 'H';

    public final static int MAP_SEPARATOR = ':';

    public final static int LIST_ENTRY = '-';

    protected ParserReader r;

    protected int line = 1;

    private ParserEvent event;

    private HashMap props;

    private char pendingEvent;

    public YamlParser(Reader r, ParserEvent event) throws Exception {
        this.r = new ParserReader(r);
        this.event = event;
        props = new HashMap();
    }

    protected String readerString() {
        return r.string();
    }

    private void clearEvents() {
        props.clear();
    }

    private void sendEvents() {
        String s;

        if (pendingEvent == '[')
            event.event(LIST_OPEN);

        if (pendingEvent == '{')
            event.event(MAP_OPEN);

        pendingEvent = 0;

        if ((s = (String) props.get("anchor")) != null)
            event.property("anchor", s);

        if ((s = (String) props.get("transfer")) != null)
            event.property("transfer", s);

        if ((s = (String) props.get("alias")) != null)
            event.content("alias", s);

        if ((s = (String) props.get("string")) != null)
            event.content("string", s);

        props.clear();
    }

    /** how many spaces from index till first non space */

    public int indent() throws IOException {
        mark();

        int i = 0;
        while (YamlCharacter.is(r.read(), YamlCharacter.INDENT))
            i++;

        reset();
        return i;
    }

    /** string of characters of type 'type' */

    public boolean array(int type) throws IOException {
        mark();

        int i = 0;

        while (YamlCharacter.is(r.read(), type))
            i++;

        if (i != 0) {
            r.unread();
            unmark();
            return true;
        }

        reset();
        return false;
    }

    /** space = char_space+ */

    public boolean space() throws IOException {
        return array(YamlCharacter.SPACE);
    }

    /**
     * line = char_line+
     * 
     * <p>
     * Spaces not included !
     * </p>
     */

    public boolean line() throws IOException {
        return array(YamlCharacter.LINE);
    }

    /**
     * linesp = char_linesp+
     * 
     * <p>
     * Spaces included !
     * </p>
     */

    public boolean linesp() throws IOException {
        return array(YamlCharacter.LINESP);
    }

    /** word = char_word+ */

    public boolean word() throws IOException {
        return array(YamlCharacter.WORD);
    }

    /** number = char_digit+ */

    public boolean number() throws IOException {
        return array(YamlCharacter.DIGIT);
    }

    /** indent(n) ::= n*SP */

    public boolean indent(int n) throws IOException {
        mark();

        while (YamlCharacter.is(r.read(), YamlCharacter.INDENT) && n-- > 0)
            ;

        if (n == 0) {
            r.unread();
            unmark();
            return true;
        }

        reset();
        return false;
    }

    /** newline ::= ( CR LF) | CR | LF / NEL / PS / LS (| EOF) */

    public boolean newline() throws IOException {
        line++;
        mark();

        int c = r.read();
        int c2 = r.read();

        if (c == -1 || (c == 13 && c2 == 10)) {
            unmark();
            return true;
        }

        if (YamlCharacter.is(c, YamlCharacter.LINEBREAK)) {
            r.unread();
            unmark();
            return true;
        }

        reset();
        line--;
        return false;
    }

    /* end ::= space? newline icomment(-1)* */

    public boolean end() throws IOException {
        mark();

        space();

        if (!newline()) {
            reset();
            return false;
        }

        while (comment(-1, false))
            ;

        unmark();
        return true;
    }

    /**
     * simple string
     * 
     * <p>
     * This function reads also trailing spaces. Trim later
     * </p>
     */

    public boolean string_simple() throws Exception {
        char ch;
        int c;

        int i = 0;
        r.mark();
        boolean dash_first = false;
        while (true) {
            c = r.read();
            if (c == -1)
                break;

            ch = (char) c;
            if (i == 0 && '-' == ch) {
                dash_first = true;
                continue;
            }
            if (i == 0
                    && (YamlCharacter.isSpaceChar(ch) || YamlCharacter.isIndicatorNonSpace(ch) || YamlCharacter
                            .isIndicatorSpace(ch)))
                break;
            if (dash_first && (YamlCharacter.isSpaceChar(ch) || YamlCharacter.isLineBreakChar(ch))) {
                unmark();
                return false;
            }
            if (!YamlCharacter.isLineSpChar(ch)
                    || (YamlCharacter.isIndicatorSimple(ch) && r.previous() != '\\'))
                break;
            i++;
        }

        r.unread();
        r.unmark();
        if (i != 0)
            return true;

        return false;
    }

    public boolean loose_string_simple() throws Exception {
        char ch;
        int c;

        int i = 0;

        while (true) {
            c = r.read();
            if (c == -1)
                break;

            ch = (char) c;
            // if (i == 0 && (YamlCharacter.isSpaceChar(ch) ||
            // YamlCharacter.isIndicatorNonSpace(ch) ||
            // YamlCharacter.isIndicatorSpace(ch) ) )
            // break;

            if (!YamlCharacter.isLineSpChar(ch)
                    || (YamlCharacter.isLooseIndicatorSimple(ch) && r.previous() != '\\'))
                break;
            i++;
        }

        r.unread();

        if (i != 0)
            return true;

        return false;
    }

    /** single quoted string (ends with ''' not preceded by esc) */

    boolean string_q1() throws Exception {
        if (r.current() != '\'')
            return false;

        r.read();
        int c = 0;
        int i = 0;

        while (YamlCharacter.is(c = r.read(), YamlCharacter.LINESP)) {
            if (c == '\'' && r.previous() != '\\')
                break;
            i++;
        }

        if (c != '\'')
            throw new SyntaxException("unterminated string", line);

        // if (i != 0)
        return true;

        // return false;
    }

    /** double quoted string (ends with '"' not preceded by esc) */

    boolean string_q2() throws Exception {

        if (r.current() != '"')
            return false;

        r.read();
        int c = 0;
        int i = 0;

        while (YamlCharacter.is(c = r.read(), YamlCharacter.LINESP)) {
            if (c == '"' && r.previous() != '\\')
                break;
            i++;
        }

        if (c != '"')
            throw new SyntaxException("unterminated string", line);

        // if (i != 0)
        return true;

        // return false;
    }

    /**
     * string ::= single_quoted | double_quoted | simple
     * 
     * <p>
     * All strings are trimmed
     * </p>
     */
    public boolean loose_string() throws Exception {
        mark();
        boolean q2 = false;
        boolean q1 = false;
        if ((q1 = string_q1()) || (q2 = string_q2()) || loose_string_simple()) {
            String str = r.string().trim();
            if (q2)
                str = fix_q2(str);
            else if (q1)
                str = fix_q1(str);
            props.put("string", str);
            unmark();
            return true;
        }

        reset();
        return false;
    }

    public boolean string() throws Exception {
        mark();
        boolean q2 = false;
        boolean q1 = false;
        if ((q1 = string_q1()) || (q2 = string_q2()) || string_simple()) {
            String str = r.string().trim();
            if (q2)
                str = fix_q2(str);
            else if (q1)
                str = fix_q1(str);
            props.put("string", str);
            unmark();
            return true;
        }

        reset();
        return false;
    }

    String fix_q2(String str) {
        if (str.length() > 2)
            return str.substring(1, str.length() - 1).replaceAll("\\\\\\\\", "\\\\");
        else
            return "";
    }

    String fix_q1(String str) {
        if (str.length() > 2)
            return str.substring(1, str.length() - 1);
        else
            return "";
    }

    /** alias ::= '*' word */

    public boolean alias() throws IOException {
        mark();

        if (r.read() != '*') {
            r.unread();
            unmark();
            return false;
        }

        if (!word()) {
            reset();
            return false;
        }

        unmark();
        props.put("alias", r.string());
        return true;
    }

    /** anchor ::= '&' word */

    public boolean anchor() throws IOException {
        mark();

        if (r.read() != '&') {
            r.unread();
            unmark();
            return false;
        }

        if (!word()) {
            reset();
            return false;
        }

        unmark();
        props.put("anchor", r.string());
        return true;
    }

    /**
     * throwable comment productions.
     * 
     * icomment(n) ::= indent(&lt;n) ('#' linesp )? newline<br>
     * xcomment(n) ::= indent(&lt;n) '#' linesp newline<br>
     * <br>
     * 
     * icomment(n) ::= comment(n,false)<br>
     * xcomment(n) ::= comment(n,true)
     */

    public boolean comment(int n, boolean explicit) throws IOException {
        mark();

        if (n != -1 && indent() >= n) {
            reset();
            return false;
        }

        space();

        int c;
        if ((c = r.read()) == '#')
            linesp();
        else {
            if (c == -1) {
                unmark();
                return false;
            }

            if (explicit) {
                reset();
                return false;
            } else
                r.unread();
        }

        boolean b = newline();

        if (b == false) {
            reset();
            return false;
        }

        unmark();
        return true;
    }

    /** header ::= '---' (space directive)* */

    public boolean header() throws Exception {
        mark();

        int c = r.read();
        int c2 = r.read();
        int c3 = r.read();

        if (c != '-' || c2 != '-' || c3 != '-') {
            reset();
            return false;
        }

        while (space() && directive())
            ;

        unmark();
        event.event(DOCUMENT_HEADER);
        return true;
    }

    /** directive ::= '#' word ':' line */

    public boolean directive() throws IOException {
        mark();

        if (r.read() != '#') {
            r.unread();
            unmark();
            return false;
        }

        if (!word()) {
            reset();
            return false;
        }

        if (r.read() != ':') {
            reset();
            return false;
        }

        if (!line()) {
            reset();
            return false;
        }

        event.content("directive", r.string());
        unmark();
        return true;
    }

    /** transfer ::= '!' line */

    public boolean transfer() throws IOException {
        mark();

        if (r.read() != '!') {
            r.unread();
            unmark();
            return false;
        }

        if (!line()) {
            reset();
            return false;
        }
        props.put("transfer", r.string());
        unmark();

        return true;
    }

    /** properties ::= ( transfer ( space anchor )? ) | ( anchor ( space transfer )? */

    public boolean properties() throws Exception {
        mark();

        if (transfer()) {
            space();
            anchor();
            unmark();
            return true;
        }

        if (anchor()) {
            space();
            transfer();
            unmark();
            return true;
        }

        reset();
        return false;
    }

    /** key ::= ( '?' value_nested(&gt;n) indent(n) ) | ( value_inline space? ) */

    public boolean key(int n) throws Exception {
        if (r.current() == '?') {
            r.read();
            if (!value_nested(n + 1))
                throw new SyntaxException("'?' key indicator without a nested value", line);
            if (!indent(n))
                throw new SyntaxException("Incorrect indentations after nested key", line);
            return true;
        }

        if (!value_inline())
            return false;

        space();
        return true;
    }

    /* value ::= (value_inline end) | value_block(n) | value_nested(n) */

    public boolean value(int n) throws Exception {

        // System.out.println("value(n)");

        if (value_nested(n) || value_block(n))
            return true;

        if (!loose_value_inline())
            return false;
        // System.out.println("value(n) is inline");
        if (!end())
            throw new SyntaxException("Unterminated inline value", line);
        return true;
    }

    public boolean loose_value(int n) throws Exception {

        // System.out.println("value(n)");

        if (value_nested(n) || value_block(n))
            return true;

        if (!loose_value_inline())
            return false;
        // System.out.println("value(n) is inline");
        if (!end())
            throw new SyntaxException("Unterminated inline value", line);
        return true;
    }

    public boolean value_na(int n) throws Exception {
        if (value_nested(n) || value_block(n))
            return true;

        if (!value_inline_na())
            return false;

        if (!end())
            throw new SyntaxException("Unterminated inline value", line);

        return true;
    }

    public boolean value_inline() throws Exception {
        mark();

        if (properties())
            space();

        if (alias() || string()) {
            sendEvents();
            unmark();
            return true;
        }

        if (list() || map()) {
            unmark();
            return true;
        }

        clearEvents();
        reset();
        return false;
    }

    public boolean loose_value_inline() throws Exception {
        mark();

        if (properties())
            space();

        if (alias() || loose_string()) {
            sendEvents();
            unmark();
            return true;
        }

        if (list() || map()) {
            unmark();
            return true;
        }

        clearEvents();
        reset();
        return false;
    }

    public boolean value_inline_na() throws Exception {
        mark();

        if (properties())
            space();

        if (string()) {
            sendEvents();
            unmark();
            return true;
        }

        if (list() || map()) {
            unmark();
            return true;
        }

        clearEvents();
        reset();
        return false;
    }

    public boolean value_nested(int n) throws Exception {
        mark();
        // System.out.println("----------------------- 0");
        if (properties())
            space();
        // System.out.println("----------------------- 1");
        if (!end()) {
            clearEvents();
            reset();
            return false;
        }
        // System.out.println("----------------------- 2");
        sendEvents();

        while (comment(n, false))
            ;
        // System.out.println("----------------------- 3");
        if (nlist(n) || nmap(n)) {
            unmark();
            return true;
        }

        reset();
        return false;
    }

    public boolean value_block(int n) throws Exception {
        mark();

        if (properties())
            space();

        if (!block(n)) {
            clearEvents();
            reset();
            return false;
        }

        sendEvents();

        while (comment(n, false))
            ;

        unmark();
        return true;
    }

    /** nmap(n) ::= (indent(n) nmap_entry(n))+ */

    public boolean nmap(int n) throws Exception {
        mark();
        // System.out.println("----------------------- 10");
        int in = indent();

        if (n == -1)
            n = in;
        else if (in > n)
            n = in;

        pendingEvent = '{';

        int i = 0;
        while (true) {
            if (!indent(n))
                break;
            if (!nmap_entry(n))
                break;
            i++;
        }
        // System.out.println("----------------------- 11");
        if (i > 0) {
            event.event(MAP_CLOSE);
            unmark();
            return true;
        }
        // System.out.println("----------------------- 12");
        pendingEvent = 0;
        reset();
        return false;
    }

    /** nmap_entry(n) ::= key(n) ':' value(&gt;n) */

    public boolean nmap_entry(int n) throws Exception {
        if (!key(n))
            return false;
        if (r.current() != ':')
            return false;
        r.read();

        event.event(MAP_SEPARATOR);
        space(); /* enforce this space? */

        if (!loose_value(n + 1))
            throw new SyntaxException("no value after ':'", line);

        return true;
    }

    /** nlist(n) ::= ( indent(n) nlist_entry(n) )+ */

    public boolean nlist(int n) throws Exception {
        mark();

        int in = indent();

        if (n == -1)
            n = in;
        else if (in > n)
            n = in;

        pendingEvent = '[';

        int i = 0;
        while (true) {
            if (!indent(n))
                break;
            if (!nlist_entry(n))
                break;
            i++;
        }

        if (i > 0) {
            event.event(LIST_CLOSE);
            unmark();
            return true;
        }

        pendingEvent = 0;
        reset();
        return false;
    }

    boolean start_list() throws Exception {
        r.mark();
        if (r.read() == '-') {
            if (r.current() == '\n' || space()) {
                r.unmark();
                return true;
            }
        }
        r.reset();
        return false;

    }

    /** nlist_entry(n) ::= '-' ( value(&gt;n) | nmap_inlist(&gt;n) ) */

    public boolean nlist_entry(int n) throws Exception {
        if (!start_list())
            return false;

        // System.out.println("nlist_entry");
        space();
        // if (!space())
        // throw new SyntaxException("No space after nested list entry",line);

        if (nmap_inlist(n + 1) || value(n + 1)) {
            return true;
        }

        throw new SyntaxException("bad nlist", line);
    }

    /**
     * nmap_inlist(n) ::= space string space? ':' space value(&gt;n)
     * nmap(&gt;n)? XXX
     */

    public boolean nmap_inlist(int n) throws Exception {
        mark();

        // System.out.println("nmap_inlist()-1");
        if (!string()) {
            reset();
            return false;
        }

        // System.out.println("nmap_inlist()-2");
        space();
        // System.out.println("nmap_inlist()-3");
        if (r.read() != ':') {
            reset();
            return false;
        }
        if (pendingEvent == '[') {
            event.event(LIST_OPEN);
            pendingEvent = 0;
        }
        event.event(MAP_OPEN);
        sendEvents();
        event.event(MAP_SEPARATOR);
        // System.out.println("nmap_inlist()-4");
        if (!space()) {
            reset();
            return false;
        }
        // System.out.println("nmap_inlist()-5");
        if (!value(n + 1))
            throw new SyntaxException("No value after ':' in map_in_list", line);
        // System.out.println("nmap_inlist()-6");

        n = n + 1;
        int in = indent();

        if (n == -1)
            n = in;
        else if (in > n)
            n = in;

        int i = 0;
        while (true) {
            if (!indent(n))
                break;
            if (!nmap_entry(n))
                break;
            i++;
        }
        // System.out.println("----------------------- 11");
        if (i > 0) {
            event.event(MAP_CLOSE);
            unmark();
            return true;
        }

        // nmap(n+1);
        // System.out.println("nmap_inlist()-7");
        unmark();
        return true;
    }

    /** block(n) ::= '|' space? number? space? newline block_line(n)* */

    public boolean block(int n) throws Exception {
        int c = r.current();
        if (c != '|' && c != ']')
            return false;

        r.read();
        if (r.current() == '\\')
            r.read();

        space();
        if (number())
            space();

        if (!newline())
            throw new SyntaxException("No newline after block definition", line);

        StringBuffer sb = new StringBuffer();

        while (block_line(n, sb, (char) c))
            ;
        String blockString = sb.toString();
        if (blockString.charAt(blockString.length() - 1) == '\n')
            blockString = blockString.substring(0, blockString.length() - 1);
        event.content("string", blockString);

        return true;
    }

    /* block_line(n) ::= indent(n) linesp? newline */

    public boolean block_line(int n, StringBuffer sb, char ch) throws Exception {
        int in = indent();

        if (in < n)
            return false;

        n = in;

        indent(n);

        if (r.current() == -1)
            return false;

        mark();

        linesp();
        sb.append(r.string());

        unmark();

        if (ch == '|')
            sb.append('\n');
        else
            sb.append(' ');

        newline();
        return true;
    }

    /** list ::= '[' (list_entry ',')* list_entry? ']' */

    public boolean list() throws Exception {
        if (r.current() != '[')
            return false;
        r.read();
        sendEvents();
        event.event(LIST_OPEN);

        while (list_entry()) {
            int c = r.current();
            if (c == ']') {
                r.read();
                event.event(LIST_CLOSE);
                return true;
            }
            if (c != ',')
                throw new SyntaxException("inline list error: expecting ','", line);
            r.read();
        }
        int c = r.current();
        if (c == ']') {
            r.read();
            event.event(LIST_CLOSE);
            return true;
        } else
            throw new SyntaxException("inline list error", line);
    }

    /** list_entry ::= space? value_inline space? */

    public boolean list_entry() throws Exception {
        space();

        if (!loose_value_inline())
            return false;

        space();
        return true;
    }

    /** map ::= '{' (map_entry ',')* map_entry? '}' */

    public boolean map() throws Exception {
        if (r.current() != '{')
            return false;
        r.read();
        sendEvents();
        event.event(MAP_OPEN);

        while (map_entry()) {
            int c = r.current();
            if (c == '}') {
                r.read();
                event.event(MAP_CLOSE);
                return true;
            }
            if (c != ',')
                throw new SyntaxException("inline map error: expecting ','", line);
            r.read();
        }
        int c = r.current();
        if (c == '}') {
            r.read();
            event.event(MAP_CLOSE);
            return true;
        }
        throw new SyntaxException("inline map error", line);
    }

    /** map_entry ::= space? value_inline space? ':' space value_inline space? */

    public boolean map_entry() throws Exception {
        space();

        if (!value_inline())
            return false;

        space();

        if (r.current() != ':')
            return false;

        r.read();

        event.event(MAP_SEPARATOR);

        if (!space())
            throw new SyntaxException("No space after ':'", line);

        if (!loose_value_inline())
            throw new SyntaxException("No value after ':'", line);

        space();
        return true;
    }

    /** document_first ::= nlist(-1) | nmap(-1) */

    public boolean document_first() throws Exception {
        boolean b = nlist(-1) || nmap(-1);
        if (!b)
            throw new SyntaxException("first document is not a nested list or map", line);
        return true;
    }

    /** document_next ::= header node_non_alias(-1) */

    public boolean document_next() throws Exception {
        if (!header())
            return false;
        if (!value_na(-1))
            return false;
        return true;
    }

    /** parse ::= icomment(-1)* document_first? document_next* */

    public void parse() throws Exception {
        try {
            while (comment(-1, false))
                ;

            if (!header())
                document_first();
            else
                value_na(-1);

            while (document_next())
                ;
        } catch (SyntaxException e) {
            event.error(e, e.line);
        }
    }

    /* ----------------------------------------------------- */

    private void mark() {
        r.mark();
    }

    private void reset() {
        r.reset();
    }

    private void unmark() {
        r.unmark();
    }

    /**
     * @return Returns the event.
     */
    public ParserEvent getEvent() {
        return event;
    }

    /**
     * @param event
     *            The event to set.
     */
    public void setEvent(ParserEvent event) {
        this.event = event;
    }
}
