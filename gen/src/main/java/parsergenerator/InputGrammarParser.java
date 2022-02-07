package parsergenerator;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class InputGrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, EPS=6, UPPERCASE=7, LOWERCASE=8, 
		ALPHA_NUM=9, REGEXP=10, CODE=11, WS=12;
	public static final int
		RULE_rules_list = 0, RULE_nonterminal_rules = 1, RULE_terminal_rules = 2, 
		RULE_declaration_rules = 3, RULE_nonterminal_rule = 4, RULE_terminal_rule = 5, 
		RULE_declaration_rule = 6;
	private static String[] makeRuleNames() {
		return new String[] {
			"rules_list", "nonterminal_rules", "terminal_rules", "declaration_rules", 
			"nonterminal_rule", "terminal_rule", "declaration_rule"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "':'", "';'", "'decl'", "'imports'", "', attrs'", "'\\EPS'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, "EPS", "UPPERCASE", "LOWERCASE", 
			"ALPHA_NUM", "REGEXP", "CODE", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "InputGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public InputGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class Rules_listContext extends ParserRuleContext {
		public Declaration_rulesContext declaration_rules() {
			return getRuleContext(Declaration_rulesContext.class,0);
		}
		public Nonterminal_rulesContext nonterminal_rules() {
			return getRuleContext(Nonterminal_rulesContext.class,0);
		}
		public Terminal_rulesContext terminal_rules() {
			return getRuleContext(Terminal_rulesContext.class,0);
		}
		public TerminalNode EOF() { return getToken(InputGrammarParser.EOF, 0); }
		public Rules_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rules_list; }
	}

	public final Rules_listContext rules_list() throws RecognitionException {
		Rules_listContext _localctx = new Rules_listContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_rules_list);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(14);
			declaration_rules();
			setState(15);
			nonterminal_rules();
			setState(16);
			terminal_rules();
			setState(17);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Nonterminal_rulesContext extends ParserRuleContext {
		public List<Nonterminal_ruleContext> nonterminal_rule() {
			return getRuleContexts(Nonterminal_ruleContext.class);
		}
		public Nonterminal_ruleContext nonterminal_rule(int i) {
			return getRuleContext(Nonterminal_ruleContext.class,i);
		}
		public Nonterminal_rulesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonterminal_rules; }
	}

	public final Nonterminal_rulesContext nonterminal_rules() throws RecognitionException {
		Nonterminal_rulesContext _localctx = new Nonterminal_rulesContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_nonterminal_rules);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(22);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LOWERCASE) {
				{
				{
				setState(19);
				nonterminal_rule();
				}
				}
				setState(24);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Terminal_rulesContext extends ParserRuleContext {
		public List<Terminal_ruleContext> terminal_rule() {
			return getRuleContexts(Terminal_ruleContext.class);
		}
		public Terminal_ruleContext terminal_rule(int i) {
			return getRuleContext(Terminal_ruleContext.class,i);
		}
		public Terminal_rulesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_terminal_rules; }
	}

	public final Terminal_rulesContext terminal_rules() throws RecognitionException {
		Terminal_rulesContext _localctx = new Terminal_rulesContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_terminal_rules);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(28);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==UPPERCASE) {
				{
				{
				setState(25);
				terminal_rule();
				}
				}
				setState(30);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Declaration_rulesContext extends ParserRuleContext {
		public List<Declaration_ruleContext> declaration_rule() {
			return getRuleContexts(Declaration_ruleContext.class);
		}
		public Declaration_ruleContext declaration_rule(int i) {
			return getRuleContext(Declaration_ruleContext.class,i);
		}
		public Declaration_rulesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration_rules; }
	}

	public final Declaration_rulesContext declaration_rules() throws RecognitionException {
		Declaration_rulesContext _localctx = new Declaration_rulesContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_declaration_rules);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(31);
				declaration_rule();
				}
				}
				setState(36);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Nonterminal_ruleContext extends ParserRuleContext {
		public List<TerminalNode> LOWERCASE() { return getTokens(InputGrammarParser.LOWERCASE); }
		public TerminalNode LOWERCASE(int i) {
			return getToken(InputGrammarParser.LOWERCASE, i);
		}
		public TerminalNode EPS() { return getToken(InputGrammarParser.EPS, 0); }
		public List<TerminalNode> UPPERCASE() { return getTokens(InputGrammarParser.UPPERCASE); }
		public TerminalNode UPPERCASE(int i) {
			return getToken(InputGrammarParser.UPPERCASE, i);
		}
		public List<TerminalNode> CODE() { return getTokens(InputGrammarParser.CODE); }
		public TerminalNode CODE(int i) {
			return getToken(InputGrammarParser.CODE, i);
		}
		public Nonterminal_ruleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonterminal_rule; }
	}

	public final Nonterminal_ruleContext nonterminal_rule() throws RecognitionException {
		Nonterminal_ruleContext _localctx = new Nonterminal_ruleContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_nonterminal_rule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			match(LOWERCASE);
			setState(38);
			match(T__0);
			setState(45);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case UPPERCASE:
			case LOWERCASE:
			case CODE:
				{
				setState(40); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(39);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << UPPERCASE) | (1L << LOWERCASE) | (1L << CODE))) != 0)) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					}
					setState(42); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << UPPERCASE) | (1L << LOWERCASE) | (1L << CODE))) != 0) );
				}
				break;
			case EPS:
				{
				setState(44);
				match(EPS);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(47);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Terminal_ruleContext extends ParserRuleContext {
		public TerminalNode UPPERCASE() { return getToken(InputGrammarParser.UPPERCASE, 0); }
		public TerminalNode REGEXP() { return getToken(InputGrammarParser.REGEXP, 0); }
		public Terminal_ruleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_terminal_rule; }
	}

	public final Terminal_ruleContext terminal_rule() throws RecognitionException {
		Terminal_ruleContext _localctx = new Terminal_ruleContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_terminal_rule);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(49);
			match(UPPERCASE);
			setState(50);
			match(T__0);
			setState(51);
			match(REGEXP);
			setState(52);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Declaration_ruleContext extends ParserRuleContext {
		public List<TerminalNode> CODE() { return getTokens(InputGrammarParser.CODE); }
		public TerminalNode CODE(int i) {
			return getToken(InputGrammarParser.CODE, i);
		}
		public TerminalNode UPPERCASE() { return getToken(InputGrammarParser.UPPERCASE, 0); }
		public TerminalNode LOWERCASE() { return getToken(InputGrammarParser.LOWERCASE, 0); }
		public Declaration_ruleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration_rule; }
	}

	public final Declaration_ruleContext declaration_rule() throws RecognitionException {
		Declaration_ruleContext _localctx = new Declaration_ruleContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_declaration_rule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			match(T__2);
			setState(55);
			_la = _input.LA(1);
			if ( !(_la==UPPERCASE || _la==LOWERCASE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(56);
			match(T__0);
			setState(57);
			match(T__3);
			setState(58);
			match(CODE);
			setState(59);
			match(T__4);
			setState(60);
			match(CODE);
			setState(61);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\16B\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\3\2\3\2\3\2\3\2\3\2\3\3\7\3"+
		"\27\n\3\f\3\16\3\32\13\3\3\4\7\4\35\n\4\f\4\16\4 \13\4\3\5\7\5#\n\5\f"+
		"\5\16\5&\13\5\3\6\3\6\3\6\6\6+\n\6\r\6\16\6,\3\6\5\6\60\n\6\3\6\3\6\3"+
		"\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\2\2\t\2\4\6"+
		"\b\n\f\16\2\4\4\2\t\n\r\r\3\2\t\n\2?\2\20\3\2\2\2\4\30\3\2\2\2\6\36\3"+
		"\2\2\2\b$\3\2\2\2\n\'\3\2\2\2\f\63\3\2\2\2\168\3\2\2\2\20\21\5\b\5\2\21"+
		"\22\5\4\3\2\22\23\5\6\4\2\23\24\7\2\2\3\24\3\3\2\2\2\25\27\5\n\6\2\26"+
		"\25\3\2\2\2\27\32\3\2\2\2\30\26\3\2\2\2\30\31\3\2\2\2\31\5\3\2\2\2\32"+
		"\30\3\2\2\2\33\35\5\f\7\2\34\33\3\2\2\2\35 \3\2\2\2\36\34\3\2\2\2\36\37"+
		"\3\2\2\2\37\7\3\2\2\2 \36\3\2\2\2!#\5\16\b\2\"!\3\2\2\2#&\3\2\2\2$\"\3"+
		"\2\2\2$%\3\2\2\2%\t\3\2\2\2&$\3\2\2\2\'(\7\n\2\2(/\7\3\2\2)+\t\2\2\2*"+
		")\3\2\2\2+,\3\2\2\2,*\3\2\2\2,-\3\2\2\2-\60\3\2\2\2.\60\7\b\2\2/*\3\2"+
		"\2\2/.\3\2\2\2\60\61\3\2\2\2\61\62\7\4\2\2\62\13\3\2\2\2\63\64\7\t\2\2"+
		"\64\65\7\3\2\2\65\66\7\f\2\2\66\67\7\4\2\2\67\r\3\2\2\289\7\5\2\29:\t"+
		"\3\2\2:;\7\3\2\2;<\7\6\2\2<=\7\r\2\2=>\7\7\2\2>?\7\r\2\2?@\7\4\2\2@\17"+
		"\3\2\2\2\7\30\36$,/";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}