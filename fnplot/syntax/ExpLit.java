package fnplot.syntax;

import fnplot.sys.VisitException;

public class ExpLit extends Exp {

    int ival;
    double rval = Double.NEGATIVE_INFINITY;

    public ExpLit(Integer v) {
	super(v.toString());
	ival = v;
    }
    
    public ExpLit(Double v) {
        super(String.valueOf(v));
        rval = v;
    }

    public double getVal() {
	return (rval == Double.NEGATIVE_INFINITY)? ival : rval;
    }

    @Override
    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitExpLit(this, arg);
    }

    @Override
    public String toString() {
	return (rval == Double.NEGATIVE_INFINITY) ? String.valueOf(ival) : String.valueOf(rval);
    }
}

