package fnplot.semantics;

import java.io.Reader;
import lib3652.util.Interpreter;
import lib3652.util.ResultType;
import lib3652.util.Result;

public class ArithInterpreter extends AssessmentVisitor<Environment<Double>,
							 Double> 
        implements Interpreter {
    /**
     * Create a new Arithmetic Interpreter with a default global environment.
     */ 
    public ArithInterpreter() {
	super(new Evaluator(0D));
    }
    
    public ArithInterpreter(Plotter plotter) {
        super(new Evaluator(Double.NaN, plotter));
    }

    @Override
    public Result toResult(Double r) {
	return new Result(ResultType.V_REAL, r);
    }
        
    @Override
    public Result run(Reader reader) {
        return readParseWalk(reader);
    }
}
