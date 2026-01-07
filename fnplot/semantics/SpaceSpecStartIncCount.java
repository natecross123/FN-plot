package fnplot.syntax;

import fnplot.semantics.SpaceSpecEvaluator;
import fnplot.semantics.Environment;
import fnplot.sys.VisitException;
import fnplot.values.PointSet1D;

/**
 * Represents [start : increment ; count] space specification
 */
public class SpaceSpecStartIncCount extends SpaceSpec1DForm {
    
    private final Exp startExp;
    private final Exp incExp;
    private final Exp countExp;
    
    public SpaceSpecStartIncCount(Exp start, Exp inc, Exp count) {
        startExp = start;
        incExp = inc;
        countExp = count;
    }

    public Exp getStartExp() {
        return startExp;
    }

    public Exp getIncExp() {
        return incExp;
    }

    public Exp getCountExp() {
        return countExp;
    }

    @Override
    public PointSet1D<Double> eval(SpaceSpecEvaluator ssEval, Environment<Double> env) throws VisitException {
        return ssEval.evalStartIncCount(this, env);
    }
    
    @Override
    public String toString() {
        return String.format("[%s : %s ; %s]", startExp, incExp, countExp);
    }
}