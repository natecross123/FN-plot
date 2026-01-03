package fnplot.syntax;

public class Binding {

    private final String var;
    private final Exp exp;

    public Binding(String v, Exp e) {
	var = v;
	exp = e;
    }

    public String getVar() {
	return var;
    }

    public Exp getExp() {
	return exp;
    }

}
