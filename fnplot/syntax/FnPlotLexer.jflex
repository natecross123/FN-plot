/* Specification for ArithExp tokens */

// user customisations
package fnplot.syntax;

import java_cup.runtime.*;
import lib3652.util.TokenException;

// Jlex directives
    
%%

%cup
%public

%class FnPlotLexer

%type java_cup.runtime.Symbol

%throws TokenException

%eofval{
	return new Symbol(sym.EOF);
%eofval}

%eofclose false

%char
%column
%line

%{
    public int getChar() {
	return yychar + 1;
    }

    public int getColumn() {
    	return yycolumn + 1;
    }

    public int getLine() {
	return yyline + 1;
    }

    public String getText() {
	return yytext();
    }
%}

nl = [\n\r]

cc = ([\b\f]|{nl})

ws = {cc}|[\t ]

alpha = [a-zA-Z_]
digit = [0-9]

alphanum = {alpha}|{digit}

%%

<YYINITIAL>	{nl}	{//skip newline, but reset char counter
			 yychar = 0;
			}
<YYINITIAL>	{ws}	{
			 // skip whitespace
			}
<YYINITIAL>	"+"	{return new Symbol(sym.PLUS);}
<YYINITIAL>	"-"	{return new Symbol(sym.MINUS);}
<YYINITIAL>	"*"	{return new Symbol(sym.MUL);}
<YYINITIAL>	"/"	{return new Symbol(sym.DIV);}
<YYINITIAL>	"%"	{return new Symbol(sym.MOD);}

<YYINITIAL>	":="	{return new Symbol(sym.ASSIGN);}
<YYINITIAL>	"("	{return new Symbol(sym.LPAREN);}
<YYINITIAL>	")"	{return new Symbol(sym.RPAREN);}
<YYINITIAL>	";"	{return new Symbol(sym.SEMI);}
<YYINITIAL>	","	{return new Symbol(sym.COMMA);}
<YYINITIAL>	"="	{return new Symbol(sym.EQUAL);}
<YYINITIAL>	"{"	{return new Symbol(sym.LBRACE);}
<YYINITIAL>	"}"	{return new Symbol(sym.RBRACE);}

<YYINITIAL>	"let"	{return new Symbol(sym.LET);}
<YYINITIAL>	"in"	{return new Symbol(sym.IN);}

<YYINITIAL>	"fun"	{return new Symbol(sym.FUN);}

<YYINITIAL>	"if"	{return new Symbol(sym.IF);}
<YYINITIAL>	"else"	{return new Symbol(sym.ELSE);}
<YYINITIAL>	"end"	{return new Symbol(sym.END);}
<YYINITIAL>	":"	{return new Symbol(sym.COLON);}

<YYINITIAL>	"<"	{return new Symbol(sym.CMP, Cmp.LT);}
<YYINITIAL>	">"	{return new Symbol(sym.CMP, Cmp.GT);}
<YYINITIAL>	"<="	{return new Symbol(sym.CMP, Cmp.LE);}
<YYINITIAL>	">="	{return new Symbol(sym.CMP, Cmp.GE);}
<YYINITIAL>	"!="	{return new Symbol(sym.CMP, Cmp.NE);}

<YYINITIAL>  "["     {return new Symbol(sym.LBRACKET);}
<YYINITIAL>  "]"     {return new Symbol(sym.RBRACKET);}
<YYINITIAL>  ".."    {return new Symbol(sym.DOTDOT);}
<YYINITIAL>  "plot"  {return new Symbol(sym.PLOT);}
<YYINITIAL>  "tabulate" {return new Symbol(sym.TABULATE);}
<YYINITIAL>  "for"   {return new Symbol(sym.FOR);}
<YYINITIAL>  "clear" {return new Symbol(sym.CLEAR);}

<YYINITIAL>    -?{digit}+\.{digit}+ {
	       // REAL NUMBER - MUST COME BEFORE INTEGER
	       return new Symbol(sym.REAL, 
				 Double.parseDouble(yytext()));
		}
		
<YYINITIAL>    -?{digit}+ {
	       // INTEGER
	       return new Symbol(sym.INT, 
				 Integer.parseInt(yytext()));
		}

<YYINITIAL>    {alpha}{alphanum}* {
	       // VAR
	       return new Symbol(sym.VAR, yytext());
		}

<YYINITIAL>    \S		{ // error situation
	       String msg = String.format("Unrecognised Token: %s", yytext());
	       throw new TokenException(msg);
	       }