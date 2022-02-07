package parsergenerator;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class ParserGenerator {

    private static final String EPS = "EPS";
    private static final String END = "END";

    public static void main(String[] args) {
        if (args.length != 2) {
            printErrorMessageAndExit("Invalid number of the arguments, expected 2");
        }

        Path inPath;
        Path outPath;
        try {
            inPath = Path.of(args[0]);
            outPath = Path.of(args[1]);
        } catch (InvalidPathException e) {
            printErrorMessageAndExit("Invalid path: " + e.getMessage());
            throw new IllegalStateException("Exit was expected");
        }

        try {
            CharStream charStream = CharStreams.fromPath(inPath, StandardCharsets.UTF_8);
            InputGrammarLexer lexer = new InputGrammarLexer(charStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            InputGrammarParser parser = new InputGrammarParser(tokens);
            InputGrammarParser.Rules_listContext rulesList = parser.rules_list();

            generateParser(outPath, rulesList.nonterminal_rules().nonterminal_rule());
            generateLexer(outPath, rulesList.terminal_rules().terminal_rule());
            generateClasses(outPath, rulesList.declaration_rules().declaration_rule());
        } catch (NoSuchFileException e) {
            printErrorMessageAndExit("File not found: " + e.getMessage());
        } catch (FileAlreadyExistsException e) {
            printErrorMessageAndExit("File already exists: " + e.getMessage());
        } catch (AccessDeniedException e) {
            printErrorMessageAndExit("Access denied: " + e.getMessage());
        } catch (IOException e) {
            printErrorMessageAndExit("IO Error happened");
        }
    }

    private static void generateParser(Path path,
                                       List<InputGrammarParser.Nonterminal_ruleContext> rules) throws IOException {
        Map<String, Set<String>> nonterminalFirst = new HashMap<>();
        computeNonterminalFirst(nonterminalFirst, rules);

        Map<String, Set<String>> follow = new HashMap<>();
        String start = rules.get(0).children.get(0).getText();
        computeFollow(follow, nonterminalFirst, start, rules);

        Map<String, List<List<ParseTree>>> rhsMap = new HashMap<>();
        groupRhsByNonterminal(rhsMap, rules);

        checkGrammar(rhsMap, follow, nonterminalFirst);

        Path parserPath = path.resolve("Parser.java");
        try (BufferedWriter parserFile = Files.newBufferedWriter(parserPath, StandardCharsets.UTF_8)) {
            parserFile.write("import java.text.ParseException;\n"
                    + "\n"
                    + "public class Parser {\n"
                    + "    private Lexer lexer;\n"
                    + "\n"
                    + "    public " + start + " parse(String input) throws ParseException {\n"
                    + "        this.lexer = new Lexer(input);\n"
                    + "        this.lexer.nextToken();\n"
                    + "        " + start + " parseRes = visit" + start + "(new " + start + "());\n"
                    + "        if (lexer.curToken() != Token.END) {\n"
                    + "            throw new AssertionError(\"unexpected token\");\n"
                    + "        }\n"
                    + "        return parseRes;\n"
                    + "    }\n"
                    + "\n");

            for (Map.Entry<String, List<List<ParseTree>>> entry : rhsMap.entrySet()) {
                String nonterminal = entry.getKey();
                List<List<ParseTree>> rhsList = entry.getValue();
                String parentVarName = nonterminal + 0;
                String methodTypeAndName = nonterminal + " visit" + nonterminal;
                String methodArg = nonterminal + " " + parentVarName;
                parserFile.write("    private " + methodTypeAndName + "(" + methodArg + ") throws ParseException {\n"
                        + "        switch (lexer.curToken()) {");

                boolean isEpsPresented = false;
                List<ParseTree> epsRhs = null;
                for (List<ParseTree> rhs : rhsList) {
                    Set<String> rhsFirst = computeFirst(rhs, nonterminalFirst, true);
                    boolean atLeastOneCase = false;
                    for (String token : rhsFirst) {
                        if (token.equals(EPS)) {
                            isEpsPresented = true;
                            epsRhs = rhs;
                        } else {
                            parserFile.write("\n            case " + token + ":");
                            atLeastOneCase = true;
                        }
                    }

                    if (atLeastOneCase) {
                        generateFirstCaseContent(parserFile, rhs, parentVarName);
                    }
                }

                if (isEpsPresented) {
                    Set<String> nonterminalFollow = follow.get(nonterminal);
                    if (!nonterminalFollow.isEmpty()) {
                        for (String token : nonterminalFollow) {
                            parserFile.write("            case " + token + ":\n");
                        }
                        generateFollowCaseContent(parserFile, epsRhs);
                    }
                }

                parserFile.write("            default:\n"
                        + "                throw new AssertionError();\n"
                        + "        }\n"
                        + "\n"
                        + "        return " + parentVarName + ";\n"
                        + "    }\n"
                        + "\n");

            }
            parserFile.write("}");
        }
    }

    private static void generateClasses(Path path,
                                        List<InputGrammarParser.Declaration_ruleContext> rules) throws IOException {
        Path abstractNodePath = path.resolve("AbstractNode.java");
        try (BufferedWriter abstractNodeFile = Files.newBufferedWriter(abstractNodePath, StandardCharsets.UTF_8)) {
            abstractNodeFile.write("import java.util.ArrayList;\n"
                    + "import java.util.List;\n"
                    + "\n"
                    + "public abstract class AbstractNode {\n"
                    + "    public List<AbstractNode> children = new ArrayList<>();\n"
                    + "}");
        }

        Path abstractTerminalPath = path.resolve("AbstractTerminal.java");
        try (BufferedWriter abstractTerminalFile = Files.newBufferedWriter(abstractTerminalPath, StandardCharsets.UTF_8)) {
            abstractTerminalFile.write("public abstract class AbstractTerminal extends AbstractNode {\n"
                    + "    public String value;\n"
                    + "}");
        }

        Path tokenPath = path.resolve("Token.java");
        try (BufferedWriter tokenFile = Files.newBufferedWriter(tokenPath, StandardCharsets.UTF_8)) {
            tokenFile.write("public enum Token {\n"
                    + "    END, EPS");

            for (InputGrammarParser.Declaration_ruleContext rule : rules) {

                String superClass = "AbstractNode";
                String className = rule.children.get(1).getText();
                if (isTerminal(className)) {
                    tokenFile.write(", " + rule.UPPERCASE().getText());
                    superClass = "AbstractTerminal";
                }

                Path classPath = path.resolve(className + ".java");
                try (BufferedWriter classFile = Files.newBufferedWriter(classPath, StandardCharsets.UTF_8)) {
                    String imports = getBracketsContent(rule.CODE().get(0).getText());
                    String attrs = getBracketsContent(rule.CODE().get(1).getText());
                    classFile.write(imports
                            + "\n\n"
                            + "public class " + className + " extends " + superClass + " {\n"
                            + "    " + attrs + "\n"
                            + "}");
                }
            }

            tokenFile.write(";\n"
                    + "\n"
                    + "    public String value;\n"
                    + "}");
        }
    }

    private static void generateLexer(Path path,
                                      List<InputGrammarParser.Terminal_ruleContext> rules) throws IOException {
        Path lexerPath = path.resolve("Lexer.java");
        try (BufferedWriter lexerFile = Files.newBufferedWriter(lexerPath, StandardCharsets.UTF_8)) {
            lexerFile.write("import java.text.ParseException;\n"
                    + "import java.util.List;\n"
                    + "import java.util.regex.Pattern;\n"
                    + "import java.util.regex.Matcher;\n"
                    + "\n"
                    + "public class Lexer {\n"
                    + "    private static final List<TokenMatcher> tokenMatchers = List.of(\n");

            for (int i = 0; i < rules.size(); i++) {
                InputGrammarParser.Terminal_ruleContext rule = rules.get(i);
                String regExp = rule.REGEXP().getText();
                String terminalName = rule.UPPERCASE().getText();
                String newTokenMatcherArgs = "Token." + terminalName + ", " + "Pattern.compile(" + regExp + ")";
                lexerFile.write("        new TokenMatcher(" + newTokenMatcherArgs + ")");
                if (i != rules.size() - 1) {
                    lexerFile.write(",\n");
                }
            }

            lexerFile.write(");\n"
                    + "\n"
                    + "    private String input;\n"
                    + "    private char curChar;\n"
                    + "    private int curPos;\n"
                    + "    private Token curToken;\n"
                    + "\n"
                    + "    public Lexer(String input) {\n"
                    + "        this.input = input;\n"
                    + "        for (TokenMatcher tokenMatcher : tokenMatchers) {\n"
                    + "            tokenMatcher.matcher = tokenMatcher.pattern.matcher(input);\n"
                    + "        }\n"
                    + "        curPos = 0;\n"
                    + "        nextChar();\n"
                    + "    }\n"
                    + "\n"
                    + "    private boolean isBlank(char c) {\n"
                    + "        return c == ' ' || c == '\\r' || c == '\\n' || c == '\\t';\n"
                    + "    }\n"
                    + "\n"
                    + "    private void nextChar() {\n"
                    + "        if (curPos < input.length()) {\n"
                    + "            curChar = input.charAt(curPos++);\n"
                    + "        } else {\n"
                    + "            curChar = '\\0';\n"
                    + "        }\n"
                    + "    }"
                    + "\n"
                    + "    public Token curToken() {\n"
                    + "        return curToken;\n"
                    + "    }\n"
                    + "\n"
                    + "    public int curPos() {\n"
                    + "        return curPos;\n"
                    + "    }\n"
                    + "\n"
                    + "    public void nextToken() throws ParseException {\n"
                    + "        while (isBlank(curChar)) {\n"
                    + "            nextChar();\n"
                    + "        }\n"
                    + "\n"
                    + "        if (curChar == '\\0') {\n"
                    + "             curToken = Token.END;\n"
                    + "             curToken.value = \"\\0\";\n"
                    + "             return;\n"
                    + "        }\n"
                    + "\n"
                    + "        for (TokenMatcher tokenMatcher : tokenMatchers) {\n"
                    + "             Token token = tokenMatcher.token;\n"
                    + "             Matcher matcher = tokenMatcher.matcher.region(curPos - 1, input.length());\n"
                    + "             tokenMatcher.matcher = matcher;\n"
                    + "             if (matcher.lookingAt()) {\n"
                    + "                  curToken = token;\n"
                    + "                  curToken.value = input.substring(matcher.start(), matcher.end());\n"
                    + "                  curPos = matcher.end();\n"
                    + "                  nextChar();\n"
                    + "                  return;\n"
                    + "             }\n"
                    + "        }\n"
                    + "\n"
                    + "        throw new ParseException(\"Illegal character \" + curChar, curPos - 1);\n"
                    + "    }\n"
                    + "\n"
                    + "    private static class TokenMatcher {\n"
                    + "        public final Token token;\n"
                    + "        public final Pattern pattern;\n"
                    + "        public Matcher matcher;\n"
                    + "\n"
                    + "        private TokenMatcher(Token token, Pattern pattern) {\n"
                    + "            this.token = token;\n"
                    + "            this.pattern = pattern;\n"
                    + "        }\n"
                    + "    }\n"
                    + "}");
        }
    }

    private static void printErrorMessageAndExit(String errorMessage) {
        System.err.println(errorMessage);
        System.exit(1);
    }

    private static void computeNonterminalFirst(Map<String, Set<String>> nonterminalFirst,
                                                List<InputGrammarParser.Nonterminal_ruleContext> rules) {
        boolean changed = true;
        while (changed) {
            changed = false;
            for (InputGrammarParser.Nonterminal_ruleContext rule : rules) {
                String nonterminal = rule.children.get(0).getText();
                List<ParseTree> rhs = rule.children.subList(2, rule.children.size() - 1);
                Set<String> rhsFirst = computeFirst(rhs, nonterminalFirst, false);
                changed = changed || nonterminalFirst.computeIfAbsent(nonterminal, k -> new HashSet<>()).addAll(rhsFirst);
            }
        }
    }

    private static Set<String> computeFirst(List<ParseTree> elements,
                                            Map<String, Set<String>> nonterminalFirst,
                                            boolean isComputedForNonterminals) {
        int i = 0;
        while (i < elements.size()) {
            String element = elements.get(i).getText();
            if (!isEps(element) && !isCode(element)) {
                break;
            }
            i++;
        }

        Set<String> first = new HashSet<>();
        if (i < elements.size()) {
            String element = elements.get(i).getText();
            if (isTerminal(element)) {
                first.add(element);
            } else if (isComputedForNonterminals) {
                return nonterminalFirst.get(element);
            } else {
                first.addAll(nonterminalFirst.computeIfAbsent(element, k -> new HashSet<>()));
                if (first.remove(EPS)) {
                    List<ParseTree> elementsAfter = elements.subList(i + 1, elements.size());
                    first.addAll(computeFirst(elementsAfter, nonterminalFirst, false));
                }
            }
        } else {
            first.add(EPS);
        }

        return first;
    }

    private static void computeFollow(Map<String, Set<String>> follow,
                                      Map<String, Set<String>> nonterminalFirst,
                                      String start,
                                      List<InputGrammarParser.Nonterminal_ruleContext> rules) {
        follow.computeIfAbsent(start, k -> new HashSet<>()).add(END);
        boolean changed = true;
        while (changed) {
            changed = false;
            for (InputGrammarParser.Nonterminal_ruleContext rule : rules) {
                String nonterminal = rule.children.get(0).getText();
                List<ParseTree> rhs = rule.children.subList(2, rule.children.size() - 1);
                for (int i = 0; i < rhs.size(); i++) {
                    String rhsElement = rhs.get(i).getText();
                    if (isNonterminal(rhsElement)) {
                        Set<String> rhsElementFollow = follow.computeIfAbsent(rhsElement, k -> new HashSet<>());
                        List<ParseTree> rhsElementsAfter = rhs.subList(i + 1, rhs.size());
                        Set<String> rhsElementsAfterFirst = new HashSet<>(computeFirst(rhsElementsAfter, nonterminalFirst, true));
                        if (rhsElementsAfterFirst.remove(EPS)) {
                            rhsElementFollow.addAll(follow.computeIfAbsent(nonterminal, k -> new HashSet<>()));
                        }
                        changed = changed || rhsElementFollow.addAll(rhsElementsAfterFirst);
                    }
                }
            }
        }
    }

    private static void groupRhsByNonterminal(Map<String, List<List<ParseTree>>> rhsMap,
                                              List<InputGrammarParser.Nonterminal_ruleContext> rules) {
        for (InputGrammarParser.Nonterminal_ruleContext rule : rules) {
            String nonterminal = rule.children.get(0).getText();
            List<ParseTree> rhs = rule.children.subList(2, rule.children.size() - 1);
            rhsMap.computeIfAbsent(nonterminal, k -> new ArrayList<>()).add(rhs);
        }
    }

    private static void checkGrammar(Map<String, List<List<ParseTree>>> rhsMap,
                                     Map<String, Set<String>> follow,
                                     Map<String, Set<String>> nonterminalFirst) {
        for (Map.Entry<String, List<List<ParseTree>>> entry : rhsMap.entrySet()) {
            String neterminal = entry.getKey();
            List<List<ParseTree>> rhsList = entry.getValue();

            for (int i = 0; i < rhsList.size() - 1; i++) {
                for (int j = i + 1; j < rhsList.size(); j++) {
                    List<ParseTree> rhs1 = rhsList.get(i);
                    List<ParseTree> rhs2 = rhsList.get(j);

                    Set<String> rhs1First = computeFirst(rhs1, nonterminalFirst, true);
                    Set<String> rhs2First = computeFirst(rhs2, nonterminalFirst, true);
                    Set<String> intersect = new HashSet<>(rhs1First);
                    intersect.retainAll(rhs2First);
                    if (!intersect.isEmpty()) {
                        printErrorMessageAndExit("Grammar is not LL(1)");
                    }

                    if (rhs1First.contains(EPS)) {
                        intersect.addAll(rhs2First);
                        intersect.retainAll(follow.get(neterminal));
                        if (!intersect.isEmpty()) {
                            printErrorMessageAndExit("Grammar is not LL(1)");
                        }
                    }
                }
            }
        }
    }

    private static void generateFirstCaseContent(Writer parserFile,
                                                 List<ParseTree> rhs,
                                                 String parentVarName) throws IOException {
        parserFile.write(" {\n");
        for (int i = 0; i < rhs.size(); i++) {
            String rhsElement = rhs.get(i).getText();
            String varName = rhsElement + (i + 1);
            if (isCode(rhsElement)) {
                String code = getBracketsContent(rhsElement);
                parserFile.write("                " + code + "\n");
            } else if (isTerminal(rhsElement)) {
                parserFile.write("                if (lexer.curToken() != Token." + rhsElement + ") {\n"
                        + "                     throw new AssertionError(\"unexpected token\");\n"
                        + "                }\n"
                        + "                " + rhsElement + " " + varName + " = new " + rhsElement + "();\n"
                        + "                " + varName + ".value = " + "lexer.curToken().value;\n"
                        + "                " + parentVarName + ".children.add(" + varName + ");\n"
                        + "                lexer.nextToken();\n");
            } else if (isNonterminal(rhsElement)) {
                String invocationArgs = "new " + rhsElement + "()";
                parserFile.write("                "
                        + rhsElement + " " + varName + " = visit" + rhsElement + "(" + invocationArgs + ");\n"
                        + "                " + parentVarName + ".children.add(" + varName + ");\n");
            }
        }
        parserFile.write("                break;\n"
                + "            }\n");
    }

    private static void generateFollowCaseContent(Writer parserFile, List<ParseTree> epsRhs) throws IOException {
        parserFile.write("                {\n");
        for (int i = 0; i < epsRhs.size(); i++) {
            String rhsElement = epsRhs.get(i).getText();
            if (!isEps(rhsElement)) {
                parserFile.write("                    " + getBracketsContent(rhsElement) + "\n");
            }
        }
        parserFile.write("                    break;\n"
                + "                }\n");
    }

    private static boolean isTerminal(String element) {
        return Character.isUpperCase(element.charAt(0));
    }

    private static boolean isNonterminal(String element) {
        return Character.isLowerCase(element.charAt(0));
    }

    private static boolean isEps(String element) {
        return element.startsWith("\\EPS");
    }

    private static boolean isCode(String element) {
        return element.startsWith("[");
    }

    private static String getBracketsContent(String bracketsStr) {
        return bracketsStr.substring(1, bracketsStr.length() - 1);
    }
}
