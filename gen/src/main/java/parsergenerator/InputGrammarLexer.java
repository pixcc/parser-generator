package parsergenerator;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class InputGrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, EPS=6, UPPERCASE=7, LOWERCASE=8, 
		ALPHA_NUM=9, REGEXP=10, CODE=11, WS=12;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "EPS", "UPPERCASE", "LOWERCASE", 
			"ALPHA_NUM", "REGEXP", "CODE", "WS"
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


	public InputGrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "InputGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\16a\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7"+
		"\3\b\6\b;\n\b\r\b\16\b<\3\t\6\t@\n\t\r\t\16\tA\3\n\6\nE\n\n\r\n\16\nF"+
		"\3\13\3\13\3\13\6\13L\n\13\r\13\16\13M\3\13\3\13\3\f\3\f\7\fT\n\f\f\f"+
		"\16\fW\13\f\3\f\3\f\3\r\6\r\\\n\r\r\r\16\r]\3\r\3\r\2\2\16\3\3\5\4\7\5"+
		"\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\3\2\b\3\2C\\\3\2c|\5\2\62"+
		";C\\c|\t\2##*-//\61\61AA]_~~\3\2__\5\2\13\f\17\17\"\"\2g\2\3\3\2\2\2\2"+
		"\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2"+
		"\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\3"+
		"\33\3\2\2\2\5\35\3\2\2\2\7\37\3\2\2\2\t$\3\2\2\2\13,\3\2\2\2\r\64\3\2"+
		"\2\2\17:\3\2\2\2\21?\3\2\2\2\23D\3\2\2\2\25H\3\2\2\2\27Q\3\2\2\2\31[\3"+
		"\2\2\2\33\34\7<\2\2\34\4\3\2\2\2\35\36\7=\2\2\36\6\3\2\2\2\37 \7f\2\2"+
		" !\7g\2\2!\"\7e\2\2\"#\7n\2\2#\b\3\2\2\2$%\7k\2\2%&\7o\2\2&\'\7r\2\2\'"+
		"(\7q\2\2()\7t\2\2)*\7v\2\2*+\7u\2\2+\n\3\2\2\2,-\7.\2\2-.\7\"\2\2./\7"+
		"c\2\2/\60\7v\2\2\60\61\7v\2\2\61\62\7t\2\2\62\63\7u\2\2\63\f\3\2\2\2\64"+
		"\65\7^\2\2\65\66\7G\2\2\66\67\7R\2\2\678\7U\2\28\16\3\2\2\29;\t\2\2\2"+
		":9\3\2\2\2;<\3\2\2\2<:\3\2\2\2<=\3\2\2\2=\20\3\2\2\2>@\t\3\2\2?>\3\2\2"+
		"\2@A\3\2\2\2A?\3\2\2\2AB\3\2\2\2B\22\3\2\2\2CE\t\4\2\2DC\3\2\2\2EF\3\2"+
		"\2\2FD\3\2\2\2FG\3\2\2\2G\24\3\2\2\2HK\7$\2\2IL\t\5\2\2JL\5\23\n\2KI\3"+
		"\2\2\2KJ\3\2\2\2LM\3\2\2\2MK\3\2\2\2MN\3\2\2\2NO\3\2\2\2OP\7$\2\2P\26"+
		"\3\2\2\2QU\7]\2\2RT\n\6\2\2SR\3\2\2\2TW\3\2\2\2US\3\2\2\2UV\3\2\2\2VX"+
		"\3\2\2\2WU\3\2\2\2XY\7_\2\2Y\30\3\2\2\2Z\\\t\7\2\2[Z\3\2\2\2\\]\3\2\2"+
		"\2][\3\2\2\2]^\3\2\2\2^_\3\2\2\2_`\b\r\2\2`\32\3\2\2\2\n\2<AFKMU]\3\b"+
		"\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}