package fnplot.semantics;

import fnplot.syntax.ArithProgram;
import fnplot.syntax.Binding;
import fnplot.syntax.Cmp;
import fnplot.syntax.Exp;
import fnplot.syntax.ExpAdd;
import fnplot.syntax.ExpComparison;
import fnplot.syntax.ExpDiv;
import fnplot.syntax.ExpFunCall;
import fnplot.syntax.ExpIf;
import fnplot.syntax.ExpLet;
import fnplot.syntax.ExpLit;
import fnplot.syntax.ExpMod;
import fnplot.syntax.ExpMul;
import fnplot.syntax.ExpSub;
import fnplot.syntax.ExpVar;
import fnplot.syntax.SpaceSpec1DForm;    
import fnplot.syntax.SpaceSpecForm; 
import fnplot.syntax.Statement;
import fnplot.syntax.StmtClear;
import fnplot.syntax.StmtDefinition;
import fnplot.syntax.StmtExp;
import fnplot.syntax.StmtFunDefn;
import fnplot.syntax.StmtPlot;
import fnplot.syntax.StmtSequence;
import fnplot.syntax.StmtTabulate;
import fnplot.syntax.Visitor;
import fnplot.sys.RuntimeFnPlotException;  
import fnplot.sys.VisitException;
import fnplot.values.Closure;
import fnplot.values.PointkD;              
import fnplot.values.PointSet1D;            
import fnplot.values.PointSetkD;      
import java.util.*;

public class Evaluator implements Visitor<Environment<Double>, Double> {
    /* For this visitor, the argument passed to all eval
       methods will be the environment object that used to
       be passed to the eval method in the first style of
       implementation. */

    // allocate state here
    protected Double result;	// result of evaluation
    private Double defaultValue;
    private Class<Double> myClass;
    private Plotter plotter;
    private Environment<Double> globalEnv;
    private SpaceSpecEvaluator ssEvaluator;

    public Evaluator() {
	this(Double.NaN);
    }
    
    public Evaluator(Double defaultVal) {
        this(defaultVal, new TextPlotter(System.out));
    }

    public Evaluator(Double defaultVal, Plotter plotter) {
	// perform initialisations here
	this.defaultValue = defaultVal;
	myClass = Double.class;
	result = defaultValue;
        this.plotter = plotter;
        globalEnv = Environment.makeGlobalEnv(myClass);
        ssEvaluator = new SpaceSpecEvaluator(this);
    }

    @Override
    public Environment<Double> getDefaultState() {
	return globalEnv;
    }
    
    public void setPlotter(Plotter plotter) {
        this.plotter = plotter;
    }
    
    public Plotter getPlotter() {
        return plotter;
    }

    @Override
    public Double visitArithProgram(ArithProgram p, Environment<Double> env)
	throws VisitException {
	result = p.getSeq().visit(this, env);
	return result;
    }

    @Override
    public Double visitStatement(StmtExp s, Environment<Double> env)
    throws VisitException {
	return s.getExp().visit(this, env);
    }

    @Override
    public Double visitStmtSequence(StmtSequence sseq, Environment<Double> env)
	throws VisitException {
	// remember that env is the environment
	Statement s;
	ArrayList<Exp> seq = sseq.getSeq();
	Iterator<Exp> iter = seq.iterator();
	result = defaultValue;
	while(iter.hasNext()) {
	    s = (Statement) iter.next();
	    result = s.visit(this, env);
	}
	// return last value evaluated
	return result;
    }

    @Override
    public Double visitStmtDefinition(StmtDefinition sd,
				      Environment<Double> env)
	throws VisitException {
	Double result;
	result = sd.getExp().visit(this, env);
	env.put(sd.getVar(), result);
	return result;
    }

    @Override
    public Double visitStmtFunDefn(StmtFunDefn fd, Environment<Double> env)
	throws VisitException {
	Closure<Double> fun = new Closure<>(fd, env);
	env.putFn(fd.getFunName(), fun);
	return 0D;
    }

    @Override
    public Double visitExpFunCall(ExpFunCall fc, Environment<Double> env)
	throws VisitException {
	String fnName = fc.getFunName();
	ArrayList<Exp> argExps = fc.getArgExps();
	ArrayList<Double> args = new ArrayList<>();
	for (Exp argExp: argExps) {
	    args.add(argExp.visit(this, env));
	}

	Closure<Double> fun = env.getFn(fnName);
	StmtFunDefn fd = fun.getFunction();
	Statement body = fd.getBody();
	ArrayList<String> params = fd.getParameters();
	Environment<Double> newEnv = new Environment<Double>(fun.getClosingEnv(),
					     params, args);

	return body.visit(this, newEnv);
    }
    
    @Override
    public Double visitExpLet(ExpLet letExp, Environment<Double> env) throws VisitException {
        ArrayList<Binding> bindings = letExp.getBindings();
        ArrayList<String> vars = new ArrayList<>();
        ArrayList<Double> values = new ArrayList<>();
        for (Binding b : bindings) {
            vars.add(b.getVar());
            values.add(b.getExp().visit(this, env));
        }
        Environment<Double> newEnv = new Environment<>(env, vars, values);
        return letExp.getBody().visit(this, newEnv);
    }

    @Override
    public Double visitExpIf(ExpIf ifExp, Environment<Double> env)
	throws VisitException {
	ExpComparison predicate = ifExp.getPredicate();
	Exp consequent = ifExp.getConsequent();
	Exp alternative = ifExp.getAlternative();

	Exp leftExp = predicate.getLeft();
	Exp rightExp = predicate.getRight();
	Cmp comparator = predicate.getComparator();

	Double leftVal = leftExp.visit(this, env);
	Double rightVal = rightExp.visit(this, env);
	boolean predResult = comparator.apply(leftVal, rightVal);

	if (predResult) {
	    return consequent.visit(this, env);
	} else {
	    return alternative.visit(this, env);
	}
    }

    @Override
    public Double visitStmtPlot(StmtPlot plotStmt, Environment<Double> env) throws VisitException {
        Exp outputExp = plotStmt.getOutputExp();
        String var = plotStmt.getVar();
        SpaceSpec1DForm rangeSpec = plotStmt.getRange();

        PointSet1D<Double> pointSet = rangeSpec.eval(ssEvaluator, env);

         ArrayList<java.awt.geom.Point2D> points = new ArrayList<>();

        for (Double inputVal : pointSet.iterValues()) {
            // Create a new environment with the variable bound to the input value
            Environment<Double> localEnv = new Environment<>(env);
            localEnv.put(var, inputVal);
            
        // Evaluate the expression in this environment
        Double outputVal = outputExp.visit(this, localEnv);
        
        // Add the (input, output) pair to our points list
        points.add(new java.awt.geom.Point2D.Double(inputVal, outputVal));
        
        // Update result to be the last computed value
        result = outputVal;
        }
        
        // Send the points to the plotter
        plotter.plot(points);
        
        return result;
    }
    
    @Override
    public Double visitStmtClear(StmtClear clrStmt, Environment<Double> env) {
        plotter.clear();
        result = defaultValue;
        return result;
    }
    
    @Override
   public Double visitStmtTabulate(StmtTabulate tabStmt, Environment<Double> env) 
        throws VisitException {
    Exp valueExp = tabStmt.getValueExp();
    ArrayList<String> varList = tabStmt.getVarList();
    SpaceSpecForm spaceSpec = tabStmt.getSpaceSpec();
    
    // Evaluate the space specification to get the point set
    PointSetkD<Double> pointSet = spaceSpec.eval(ssEvaluator, env);
    
    result = defaultValue;
    
    // Iterate through each point in the space
    for (PointkD<Double> point : pointSet) {
        // Check that the dimension matches the number of variables
        if (point.getDimension() != varList.size()) {
            throw new RuntimeFnPlotException(
                "Dimension mismatch: " + varList.size() + " variables but " +
                point.getDimension() + "-dimensional space");
        }
        
        // Create new environment with variables bound to point components
        Environment<Double> localEnv = new Environment<>(env);
        for (int i = 0; i < varList.size(); i++) {
            localEnv.put(varList.get(i), point.get(i));
        }
        
        // Evaluate the expression
        result = valueExp.visit(this, localEnv);
        
        // Print the result in the required format
        System.out.print("(");
        for (int i = 0; i < point.getDimension(); i++) {
            if (i > 0) System.out.print(", ");
            System.out.printf("%.2f", point.get(i));
        }
        System.out.printf(") %.2f%n", result);
    }
    
    return result;
}
    
    @Override
    public Double visitExpAdd(ExpAdd exp, Environment<Double> env)
	throws VisitException {
	Double val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	return val1 + val2;
    }

    @Override
    public Double visitExpSub(ExpSub exp, Environment<Double> env)
	throws VisitException {
	Double val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	return val1 - val2;
    }

    @Override
    public Double visitExpMul(ExpMul exp, Environment<Double> env)
	throws VisitException {
	Double val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	return val1 * val2;
    }

    @Override
    public Double visitExpDiv(ExpDiv exp, Environment<Double> env)
	throws VisitException {
	Double val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	return val1 / val2;
    }

    @Override
    public Double visitExpMod(ExpMod exp, Environment<Double> env)
	throws VisitException {
	Double val1, val2;
	val1 = exp.getExpL().visit(this, env);
	val2 = exp.getExpR().visit(this, env);
	return val1 % val2;
    }

    @Override
    public Double visitExpLit(ExpLit exp, Environment<Double> env)
	throws VisitException {
	return (double) exp.getVal();
    }

    @Override
    public Double visitExpVar(ExpVar exp, Environment<Double> env)
	throws VisitException {
	return env.get(exp.getVar());
    }
}
