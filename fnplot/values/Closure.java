package fnplot.values;

import fnplot.semantics.Environment;
import fnplot.syntax.Statement;
import fnplot.syntax.StmtFunDefn;
import java.util.ArrayList;

/**
 * Runtime representation for a procedure object (under static scoping)
 * @param <T> the type of values stored in the closing environment.
 */
public class Closure<T> {
    private StmtFunDefn function;
    private final ArrayList<String> parameters;
    private final Statement body;
    private final Environment<T> closingEnv;

    public Closure(StmtFunDefn fun, Environment<T> env) {
	function = fun;
        parameters = fun.getParameters();
        body = fun.getBody();
	closingEnv = env;
    }
    
    public Closure(ArrayList<String> params, Statement body, Environment<T> env) {
        parameters = params;
        this.body = body;
        closingEnv = env;
    }

    public StmtFunDefn getFunction() {
	return function;
    }
    
    public ArrayList<String> getParameters() {
        return parameters;
    }
    
    public Statement getBody() {
        return body;
    }

    public Environment<T> getClosingEnv() {
	return closingEnv;
    }
}
