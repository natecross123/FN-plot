package fnplot.semantics;

import fnplot.syntax.ArithProgram;
import fnplot.syntax.FnPlotLexer;
import fnplot.syntax.FnPlotParser;
import fnplot.syntax.Visitor;
import fnplot.sys.VisitException;
import java.io.Reader;
import java.io.StringReader;
import lib3652.util.Interpreter;
import lib3652.util.ResultType;
import lib3652.util.Result;
import lib3652.util.TokenException;

public abstract class AssessmentVisitor<S, T> extends PersistentWalker<S, T> {

    FnPlotParser parser;
    boolean isVerbose = false;
    // PersistentWalker<S, T> walker;

    /**
     * Create a new Assessment Visitor from the given visitor
     * @param visitor
     */ 
    public AssessmentVisitor(Visitor<S, T> visitor) {
	super(visitor);
	// walker = new PersistentWalker<>(visitor);
    }

    public Result readParseWalk(String input) {
	StringReader reader = new StringReader(input);
	return readParseWalk(reader);
    }

    /**
     *
     * @param reader
     * @return
     */
    @Override
    public Result readParseWalk(Reader reader) {
	parser = new FnPlotParser(reader);
	ArithProgram program;
	try {
	    program = (ArithProgram) parser.parse().value;
	} catch (TokenException te) {
	    if (isVerbose)
		System.out.println(te.getMessage());
	    return new Result(ResultType.ERROR_LEXER, te.getMessage());
	} catch (Exception e) {
	    if (isVerbose)
		System.out.println(e.getMessage());
	    return new Result(ResultType.ERROR_PARSER, e.getMessage());
	}
	try {
	    T r = walk(program);
	    return toResult(r);
	} catch (VisitException e) {
	    if (isVerbose) {
		System.out.println(e.getMessage());
	    }
	    return new Result(ResultType.ERROR_RUNTIME, e.getMessage());
	}
    }
}
