package fnplot.syntax;

import fnplot.sys.VisitException;
import java.util.ArrayList;

/**
 * IR Class to represent a function definition
 */
public class StmtFunDefn extends Statement {

    String name;
    ArrayList<String> parameters;
    Statement body;

    public StmtFunDefn(String name, ArrayList<String> params,
		       Statement body) {
	super(String.format("funDef: %s(%s)", name,
			    listToString(params, ", ")));
	this.name = name;
	parameters = params;
	this.body = body;
    }
    
    public String getFunName() {
	return name;
    }

    public ArrayList<String> getParameters() {
	return parameters;
    }

    public Statement getBody() {
	return body;
    }

    @Override
    public String toString() {
	return getName();
    }

    @Override
    public <S, T> T visit(Visitor<S, T> visitor, S arg) throws VisitException {
	return visitor.visitStmtFunDefn(this, arg);
    }

    /**
     * Convert a list of strings to a single string using sep as a 
     * separator between items.
     */ 
    public static <T> String listToString(ArrayList<T> items, String sep) {
	if (items.size() == 0)
	    return "";
	else {
	    StringBuilder result = new StringBuilder();
	    result.append(items.get(0));
	    for (int i = 1; i < items.size(); i++) {
		result.append(sep);
		result.append(items.get(i));
	    }
	    return result.toString();
	}
    }
}
