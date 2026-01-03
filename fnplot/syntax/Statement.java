package fnplot.syntax;

import fnplot.sys.VisitException;
import java.util.ArrayList;

public abstract class Statement extends Exp {

    //Exp exp;

    // public Statement() {
    // 	super();
    // }
    
    protected Statement(String name, Exp... operands) {
	super(name, operands);
    }

    protected Statement(String name, ArrayList<Exp> operandList) {
	super(name, operandList);
    }

}
