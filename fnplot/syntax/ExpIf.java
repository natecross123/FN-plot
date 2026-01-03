package fnplot.syntax;

import fnplot.sys.VisitException;

public class ExpIf extends Exp {

    private ExpComparison predicate;
    private Exp consequent;
    private Exp alternative;

    public ExpIf(ExpComparison pred, Exp cons, Exp alt) {
	super("if");
	predicate = pred;
	consequent = cons;
	alternative = alt;
    }

    public ExpIf(ExpComparison pred, Exp cons) {
	this(pred, cons, null);
    }

    public ExpComparison getPredicate() {
	return predicate;
    }

    public Exp getConsequent() {
	return consequent;
    }

    public Exp getAlternative() {
	return alternative;
    }
    
    public <S, T> T visit(Visitor<S, T> visitor, S arg) throws VisitException {
	return visitor.visitExpIf(this, arg);
    }

    public String toString() {
	if (alternative == null) 
	    return String.format("if %s: %s", predicate, consequent);
	else
	    return String.format("if %s: %s", predicate,
				 consequent, alternative);
    }
	
}
