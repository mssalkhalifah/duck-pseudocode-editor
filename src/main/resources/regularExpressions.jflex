package compiler.lexer;

import compiler.exceptions.scanner.UnrecognizedCharacterException;
%%

%class Lexer
%public
%unicode
%line
%column
%type Token

%{
    public enum TokenType {
        // Identifier and constants
	ID, NUM_CONST, STR_CONST, CHAR_CONST, BOOL_CONST, 
	// Keywords
	INT, FLOAT, CHAR, BOOL, STR, IF, ELSE, ENIF, WHILE, FOR, DO, ENDL, OUT, 
	// Binary operations
	    // Arithmatic operators
	    PLUS, SUB, MUL, DIV, MOD,
	    // Relational operators
	    GT, GE, LT, LE, EQ, NE,
	    // Logical operators
	    AND, OR, NOT,
	    // Assignment operators
	    ASSIGN,
	// Specials
	    // Special characters
	    OPENPA, CLOSEPA, COLON, SEMICOL, NEWLINE, SQUOTE, DQUOTE, 
	    // Delimiters
	    WHITESPACE, TAB,
	    // Special tokens
	    ENDINPUT, STARTINPUT
	
    }

    public final class Token {
	private final TokenType TYPE;
	private final String VALUE;
	private final int LINE_NUMBER;
	private final int COLUMN_NUMBER;

	public Token(TokenType type, String value,int lineNumber, int columnNumber) {
	    this.TYPE = type;
	    this.VALUE = value;
	    this.LINE_NUMBER = lineNumber;
	    this.COLUMN_NUMBER = columnNumber;
	}

	public TokenType getType() {
	    return this.TYPE;
	}

	public String getValue() {
	    return this.VALUE;
	}

	public int getLineNumber() {
	    return this.LINE_NUMBER;
	}

	public int getColumnNumber() {
	    return this.COLUMN_NUMBER;
	}
	
	@Override
	public String toString() {
	    return String.format("Token:{Type: %s, Value:%s, Line: %d, Column: %d}", TYPE, VALUE, LINE_NUMBER, COLUMN_NUMBER);
	}
    }
%}
    // Declerations

    Letter = [a-zA-Z_]
    Digit = [0-9]

    LineTerminator = [\n]
    WhiteSpace = [ \t\f]

    Identifier = {Letter} ({Letter} | {Digit})*
    Number = {Digit}{Digit}* (.{Digit}{Digit}*)?
    Boolean = true | false

    // String and Char
    Characters = [^\n\r\\\"\']|\\t|\\n|\\\\|\\\"|\\\'
    Char = \'{Characters}?\'
    String = \"{Characters}*\"

%%

    // Keywords
    	// Special tokens
	    <YYINITIAL> "program"	{return new Token(TokenType.STARTINPUT, yytext(), yyline, yycolumn);}
	    <YYINITIAL> "end"		{return new Token(TokenType.ENDINPUT, yytext(), yyline, yycolumn);}
	// Functions
	    <YYINITIAL> "output"	{return new Token(TokenType.OUT, yytext(), yyline, yycolumn);}
	// Variable types
    	    <YYINITIAL> "int"		{return new Token(TokenType.INT, yytext(), yyline, yycolumn);}
	    <YYINITIAL> "float"		{return new Token(TokenType.FLOAT, yytext(), yyline, yycolumn);}
            <YYINITIAL> "char"		{return new Token(TokenType.CHAR, yytext(), yyline, yycolumn);}
            <YYINITIAL> "bool"		{return new Token(TokenType.BOOL, yytext(), yyline, yycolumn);}
	    <YYINITIAL> "string"	{return new Token(TokenType.STR, yytext(), yyline, yycolumn);}
	    <YYINITIAL> "char"		{return new Token(TokenType.CHAR, yytext(), yyline, yycolumn);}
	// Loops
	    <YYINITIAL> "while"		{return new Token(TokenType.WHILE, yytext(), yyline, yycolumn);}
	    <YYINITIAL> "for" 		{return new Token(TokenType.FOR, yytext(), yyline, yycolumn);}
	    <YYINITIAL> "do"		{return new Token(TokenType.DO, yytext(), yyline, yycolumn);}
	    <YYINITIAL> "endloop"	{return new Token(TokenType.ENDL, yytext(), yyline, yycolumn);}
	// Conditional statements
	    <YYINITIAL> "if"		{return new Token(TokenType.IF, yytext(), yyline, yycolumn);}
	    <YYINITIAL> "else"		{return new Token(TokenType.ELSE, yytext(), yyline, yycolumn);}
	    <YYINITIAL> "endif"		{return new Token(TokenType.ENIF, yytext(), yyline, yycolumn);}
    
    // Identifiers, constants and operations
    <YYINITIAL> {
	// Logical operators
	"and"		{return new Token(TokenType.AND, yytext(), yyline, yycolumn);}
	"or"		{return new Token(TokenType.OR, yytext(), yyline, yycolumn);}
	"not"		{return new Token(TokenType.NOT, yytext(), yyline, yycolumn);}

	{Boolean} 	{return new Token(TokenType.BOOL_CONST, yytext(), yyline, yycolumn);}
	{Identifier}	{return new Token(TokenType.ID, yytext(), yyline, yycolumn);}
	{Number}	{return new Token(TokenType.NUM_CONST, yytext(), yyline, yycolumn);}
	{String} 	{return new Token(TokenType.STR_CONST, yytext(), yyline, yycolumn);}
	{Char}		{return new Token(TokenType.CHAR_CONST, yytext(), yyline, yycolumn);}

        // Binary operations
    	"+"		{return new Token(TokenType.PLUS, yytext(), yyline, yycolumn);}
    	"-"		{return new Token(TokenType.SUB, yytext(), yyline, yycolumn);}
    	"*"		{return new Token(TokenType.MUL, yytext(), yyline, yycolumn);}
    	"/"		{return new Token(TokenType.DIV, yytext(), yyline, yycolumn);}
    	"%"		{return new Token(TokenType.MOD, yytext(), yyline, yycolumn);}

	// Assign operators
	"="		{return new Token(TokenType.ASSIGN, yytext(), yyline, yycolumn);}

	// Specials
	";"		{return new Token(TokenType.SEMICOL, yytext(), yyline, yycolumn);}
	":"		{return new Token(TokenType.COLON, yytext(), yyline, yycolumn);}
	"("		{return new Token(TokenType.OPENPA, yytext(), yyline, yycolumn);}
	")"		{return new Token(TokenType.CLOSEPA, yytext(), yyline, yycolumn);}

	// Delimiters
	{WhiteSpace}		{/* Do nothing */}
	{LineTerminator}	{/* Do nothing */}
    }

    /* Error fallback */
    //[^]			{throw new Error(String.format("Illegal character <%s, line:%d, column:%d>", yytext(), yyline, yycolumn));}
    [^]			{throw new UnrecognizedCharacterException(yytext(), yycolumn, yyline);}






