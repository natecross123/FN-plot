package fnplot.syntax;

import fnplot.sys.VisitException;

public class ExpDiv extends ExpBinOp {

    public ExpDiv(Exp e1, Exp e2) {
	super("/", e1, e2);
    }

    @Override
    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitExpDiv(this, arg);
    }
}

