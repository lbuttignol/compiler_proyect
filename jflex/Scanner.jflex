package Compiler;

import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory.Location;
%%
%cup
%line
%column
%char
%class Scanner
%{
	public Scanner(java.io.InputStream r, ComplexSymbolFactory sf){
		this(r);
		this.sf=sf;
	}
	public Symbol symbol(String plaintext,int code){
	    return sf.newSymbol(plaintext,code,new Location("",yyline+1, yycolumn +1,yychar), new Location("",yyline+1,yycolumn+yylength(),yychar));
	}
	public Symbol symbol(String plaintext,int code, Object value){
	    return sf.newSymbol(plaintext,code,new Location("",yyline+1, yycolumn +1,yychar), new Location("",yyline+1,yycolumn+yylength(),yychar),value);
	}
	private ComplexSymbolFactory sf;

		/* To create a new java_cup.runtime.Symbol with information about
       the current token, the token will have no value in this
       case. */
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    
    /* Also creates a new java_cup.runtime.Symbol with information
       about the current token, but this object has a value. */
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}
%eofval{
    return sf.newSymbol("EOF",sym.EOF);
%eofval}

LineTerminator 		= \r|\n|\r\n 
// InputCharacter		= [ˆ\r\n]

IntegerLiteral 		= 0 | [1-9][0-9]*
RealLiteral			= {IntegerLiteral} "." {IntegerLiteral} ("e" "-"? {IntegerLiteral} )?
Identifier			= [a-zA-Z][a-zA-Z0-9_]*															

%state COMMENTSMULTILINE
%state COMMENTSENDOFLINE

%%
<YYINITIAL>{

/* Comments */
"/*"				{ ;yybegin(COMMENTSMULTILINE); }
"//"				{ yybegin(COMMENTSENDOFLINE); }

/*	keywords	*/
"bool"				{ return symbol("Boolean declaration", sym.BOOL_TYPE); }
"break"				{ return symbol("Break", sym.BREAK); }
"class"				{ return symbol("Class declaration", sym.CLASS_DECL); }
"continue"			{ return symbol("Continue", sym.CONT); }
"else"				{ return symbol("Else", sym.ELSE); }
"extern"			{ return symbol("Extern", sym.EXTERN); }
"false"				{ return symbol("False", sym.BOOL_LITERAL, new Boolean(false)); }
"float"				{ return symbol("Float declaration", sym.FLOAT_TYPE); }
"for"				{ return symbol("For", sym.FOR); }
"if"				{ return symbol("If", sym.IF); }
"integer"			{ return symbol("Integer declaration", sym.INT_TYPE); }
"return"			{ return symbol("Return", sym.RET); }
"true"				{ return symbol("True", sym.BOOL_LITERAL, new Boolean(true)); }
"void"				{ return symbol("Void declaration", sym.VOID_TYPE); }
"while"				{ return symbol("While", sym.WHILE); }

/*	assign operator	*/
"="					{ return symbol("=", sym.ASSIGN); }
"+="				{ return symbol("+=", sym.ASSIGNP); }
"-="				{ return symbol("-=", sym.ASSIGNM); }

/*	concatenation operator 	*/ 					
";"					{ return symbol("Colon", sym.SEMI); }
","					{ return symbol("Comma", sym.COMMA); }
"."					{ return symbol("Doc", sym.DOC); }

/*	arith operators	*/
"+"					{ return symbol("Plus", sym.PLUS); }
"-"					{ return symbol("Minus", sym.MINUS); }
"*"					{ return symbol("Times", sym.TIMES); }
"/"					{ return symbol("Div", sym.DIV); }
"%"					{ return symbol("Mod", sym.MOD); }
//"--"				{ return symbol("MinusMinus", sym.MINUSMINUS); }
//"++"				{ return symbol("PlusPlus", sym.PLUSPLUS); }

/*	eq operators	*/
"=="				{ return symbol("Equal", sym.EQUAL); }
"!="				{ return symbol("Distinct", sym.DISTINCT); }

/*	rel operators	*/
"<"					{ return symbol("Smaller", sym.SMALLER); }
">"					{ return symbol("Bigger", sym.BIGGER); }
"<="				{ return symbol("Less than or equal", sym.LTOE); }
">="				{ return symbol("Greater yhan or ecual", sym.GTOE); }

/*	cond operators	*/
"&&"				{ return symbol("And", sym.AND); }
"||"				{ return symbol("Or", sym.OR); }
"!"					{ return symbol("Not", sym.NOT); }  				

/*		*/											
"("					{ return symbol("(", sym.LPAREN); }
")"					{ return symbol(")", sym.RPAREN); }
"{"					{ return symbol("{", sym.LBRACE); }
"}"					{ return symbol("}", sym.RBRACE); }
"["					{ return symbol("[", sym.LBRACKET); }
"]"					{ return symbol("]", sym.RBRACKET); }

/*	literals	*/
{IntegerLiteral} 	{ return symbol("INTEGER NUMBER", sym.INTNUMBER, new Integer(yytext())); }
{RealLiteral}		{ return symbol("REAL NUMBER", sym.REALNUMBER,new Float(yytext())); }
{Identifier}		{ return symbol("IDENTIFIER", sym.ID,yytext()); }

[ \t\r\n\f] 		{ /* ignore white space. */ }

. 					{ System.err.println("Illegal character: "+yytext()); }
}

<COMMENTSMULTILINE> {
"*/"  				{ yybegin(YYINITIAL); }
{LineTerminator} 	{  }
. 					{  }
}


<COMMENTSENDOFLINE> {
{LineTerminator} 	{ yybegin(YYINITIAL); }
. 					{  }
}

