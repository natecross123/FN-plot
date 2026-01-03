package fnplot.syntax;

import fnplot.sys.VisitException;
import java.util.ArrayList;

public class StmtExp extends Statement {

    //Exp exp;

    // public Statement() {
    // 	super();
    // }
    
    protected StmtExp(String name, Exp... operands) {
	super(name, operands);
    }

    protected StmtExp(String name, ArrayList<Exp> operandList) {
	super(name, operandList);
    }

    public StmtExp(Exp e) {
	//exp = e;
	super("stmt", e);
    }

    public Exp getExp() {
	return getSubTree(0);
    }

    @Override
    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitStatement(this, arg);
    }

    @Override
    public String toString() {
	return getExp().toString();
    }
}
