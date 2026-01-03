package fnplot.syntax;

import fnplot.sys.VisitException;
import java.util.ArrayList;

/**
 * IR Class to represent a function call
 */
public class ExpFunCall extends Exp {
    
    String funName;
    ArrayList<Exp> argExps;

    public ExpFunCall(String fn, ArrayList<Exp> args) {
	super("call");
	funName = fn;
	argExps = args;
    }

    public String getFunName() {
	return funName;
    }

    public ArrayList<Exp> getArgExps() {
	return argExps;
    }

    @Override
    public <S, T> T visit(Visitor<S, T> visitor, S arg) throws VisitException {
	return visitor.visitExpFunCall(this, arg);
    }

    @Override
    public String toString() {
	return String.format("call %s(%s)", funName,
			     StmtFunDefn.listToString(argExps, ", "));
    }
}
