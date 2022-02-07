# parser-generator
An app that generates a parser and a lexer by an input grammar

### Input Grammar

The input grammar must be LL(1) and consist of three blocks: 

In the first block we declare all non-terminals and tokens together with their attributes and necessary imports, with the help of this block the program will generate the necessary classes for nonterminals and tokens. In the attributes and imports sections, you can write Java code thinking of nonterminals and tokens as classes. The token identifier must be a sequence of latin characters in upper case, the nonterminal identifier must be a sequence of latin characters in lower case. Each line must start with "decl" and end with a semicolon.

Example:
```
decl expr : imports [], attrs [public int res;];
decl operation : imports [import java.util.function.IntFunction;], attrs [public IntFunction<Integer> f;];
decl LPAREN : imports [], attrs [];
decl RPAREN : imports [], attrs [];
```
In the second block we declare production rules, on the left side there can only be a nonterminal, on the right side of the rule there can be a nonterminal, a token, a special token for the empty string - "\EPS" or an action (any Java code), actions produce the empty string and when expanded they execute their code, in an action you can address the attributes of the nonterminals and tokens included in the rule, located to the left of the action (name + index starting from the zero). The starting nonterminal is the nonterminal on the left side of the first production rule.

Example:
```
e: t [e0.res = 10 * t1.res;] ep;
ep : PLUS t ep [ep0.f = a -> ep3.f.apply(a + t2.res);];
fp : NUMBER [fp0.res = Integer.parseInt(NUMBER1.value);];
s : a STAR c;
a : \EPS;
```

In the third block, we declare the definition of tokens, on the right side of the definition there must be a regular expression in quotes (pay attention to escaping special characters). By default, spaces between tokens are automatically skipped.

Example:
```
NUMBER : "[0-9]+";
LPAREN : "\\(";
LETTER : "[a-z]";
```

### Calculator example
The following grammar generates a parser that parses and simultaneously evaluates arithmetic expressions consisting of basic operations and signed integers. The value of the expression will be in the attribute "res" at the root of an AST.

```
decl e : imports [], attrs [public int res;];
decl ep : imports [import java.util.function.IntFunction;], attrs [public IntFunction<Integer> f;];
decl t : imports [], attrs [public int res;];
decl tp : imports [import java.util.function.IntFunction;], attrs [public IntFunction<Integer> f;];
decl f : imports [], attrs [public int res;];
decl fp : imports [], attrs [public int res;];
decl LPAREN : imports [], attrs [];
decl RPAREN : imports [], attrs [];
decl NUMBER : imports [], attrs [];
decl PLUS : imports [], attrs [];
decl MINUS : imports [], attrs [];
decl STAR : imports [], attrs [];
decl SLASH : imports [], attrs [];

e: t ep [e0.res = ep2.f.apply(t1.res);];
ep : PLUS t ep [ep0.f = a -> ep3.f.apply(a + t2.res);];
ep : MINUS t ep [ep0.f = a -> ep3.f.apply(a - t2.res);];
ep : [ep0.f = a -> a;];
t : f tp [t0.res = tp2.f.apply(f1.res);];
tp : STAR f tp [tp0.f = a -> tp3.f.apply(a * f2.res);];
tp : SLASH f tp [tp0.f = a -> tp3.f.apply(a / f2.res);];
tp : [tp0.f = a -> a;];
f : MINUS f [f0.res = -f2.res;];
f : fp [f0.res = fp1.res;];
fp : LPAREN e RPAREN [fp0.res = e2.res;];
fp : NUMBER [fp0.res = Integer.parseInt(NUMBER1.value);];

SLASH : "/";
STAR : "\\*";
PLUS : "\\+";
MINUS : "-";
NUMBER : "[0-9]+";
LPAREN : "\\(";
RPAREN : "\\)";
```
### How to run
```
>cd parser-generator
>mvn package
>java -jar target/parser-generator-1.0-SNAPSHOT-jar-with-dependencies.jar <path-to-grammar> <output directory>
```
