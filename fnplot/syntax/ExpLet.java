package fnplot.syntax;

import fnplot.sys.VisitException;
import java.util.ArrayList;

public class ExpLet extends Exp {
    ArrayList<Binding> bindings;
    Exp body;

    public ExpLet(ArrayList<Binding> bs, Exp bod) {
        super("let");
	bindings = bs;
	body = bod;
    }

    public ArrayList<Binding> getBindings() {
        return bindings;
    }

    public Exp getBody() {
        return body;
    }
    
    @Override
    public <S, T> T visit(Visitor<S, T> v, S state) throws VisitException {
        return v.visitExpLet(this, state);
    }


    @Override
    public String toString() {
	return "let " + bindings + " in " + body;
    }

}
