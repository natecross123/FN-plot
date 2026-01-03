package fnplot.semantics;

import fnplot.syntax.ASTNode;
import fnplot.syntax.Visitor;
import fnplot.sys.VisitException;
import java.io.Reader;
import lib3652.util.Walker;
import lib3652.util.Result;

/**
  * A PersistentWalker instance is a wrapper for a visitor.  The
  * constructor will establish an instance of the default state which
  * will be used across visits of different ASTs by the wrapped visitor.
 * @param <S> The type of state accepted by the visitor wrapped by this walker
 * @param <T> The type of result produced by the wrapped visitor
  */
public abstract class PersistentWalker<S, T> implements Walker {

    private final Visitor<S, T> visitor;
    private final S state;
    private boolean isVerbose = false;
    
    public PersistentWalker(Visitor<S, T> visitor) {
	this.visitor = visitor;
	this.state = visitor.getDefaultState();
    }

    public Visitor<S, T> getVisitor() {
	return visitor;
    }

    /** Return the state used by this AST walker.
     * @return  The state used by this walker. */
    public S getState() {
	return state;
    }

    /**
     * Convert the given object to an instance of Result. The Result class
     * is used by the autograder to properly compare expected values
     * against actual values. The advantage over returning the
     * native Java type is that the Result class can also represent errors.
     * 
     * @param value The instance of T that was (presumably) returned by a walk
     * @return the best appropriate instance of Result to represent value
     */
    public Result toResult(T value) {
	return new Result(value);
    }

    /**
     * Traverse the given expression (AST) using the visitor with its
     * associated state. 
     * @param expr
     * @return 
     * @throws fnplot.sys.VisitException if a runtime exception arises while
     * visiting the given expression.
     */
    public T walk(ASTNode<? extends ASTNode<?>> expr) throws VisitException {
	return expr.visit(visitor, state);
    }

    /**
     * Methods to implement lib3652.util.Walker
     * @param isVerbose The desired verbosity setting.
     */
    @Override
    public void setVerbose(boolean isVerbose) {
	this.isVerbose = isVerbose;
    }

    @Override
    public boolean isVerbose() {
	return isVerbose;
    }

    @Override
    public abstract Result readParseWalk(Reader input);

}
