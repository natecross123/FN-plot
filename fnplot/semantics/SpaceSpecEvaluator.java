/*
 * Copyright (C) 2023 newts
 * Produced as part of course software for COMP3652 at UWI, Mona
 * If you have any questions about this software, please contact
 * the author.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fnplot.semantics;

import fnplot.syntax.Exp;
import fnplot.syntax.SpaceSpecZip;
import fnplot.syntax.SpaceSpecPoints;
import fnplot.syntax.SpaceSpecProduct;
import fnplot.syntax.SpaceSpecRangeAndCount;
import fnplot.syntax.SpaceSpecRangeAndInc;
import fnplot.syntax.SpaceSpecStartIncCount;
import fnplot.sys.VisitException;
import fnplot.values.PointSet1D;
import fnplot.values.PointSetkD;
import java.util.ArrayList;

/**
 *
 * @author newts
 */
public class SpaceSpecEvaluator {
    
    private final Evaluator expEval;
    
    public SpaceSpecEvaluator(Evaluator eval) {
        expEval = eval;
    }
    
    public PointSet1D<Double> evalPointsList(SpaceSpecPoints sspts, Environment<Double> state)
            throws VisitException {
        PointSet1D<Double> result = new PointSet1D<>();
        ArrayList<Exp> pointExps = sspts.getPointExps();
        
        for (Exp exp : pointExps) {
            Double value = exp.visit(expEval, state);
            result.add(value);
        }
        
        return result;
    }
    
    public PointSet1D<Double> evalRangeAndInc(SpaceSpecRangeAndInc ssri, Environment<Double> env) 
            throws VisitException {
        PointSet1D<Double> result = new PointSet1D<>();
        
        Double start = ssri.getStartExp().visit(expEval, env);
        Double stop = ssri.getStopExp().visit(expEval, env);
        Double inc = ssri.getIncExp().visit(expEval, env);
        
        for (double val = start; val < stop; val += inc) {
            result.add(val);
        }
        
        return result;
    }

    public PointSet1D<Double> evalRangeAndCount(SpaceSpecRangeAndCount ssrc, 
            Environment<Double> env) throws VisitException {
        PointSet1D<Double> result = new PointSet1D<>();
        
        Double start = ssrc.getStartExp().visit(expEval, env);
        Double stop = ssrc.getStopExp().visit(expEval, env);
        int count = ssrc.getCountExp().visit(expEval, env).intValue();
        
        if (count <= 0) return result;
        
        double inc = (stop - start) / count;
        
        for (int i = 0; i < count; i++) {
            result.add(start + i * inc);
        }
        
        return result;
    }
    
    public PointSet1D<Double> evalStartIncCount(SpaceSpecStartIncCount ssic, 
            Environment<Double> env) throws VisitException {
        PointSet1D<Double> result = new PointSet1D<>();
        
        Double start = ssic.getStartExp().visit(expEval, env);
        Double inc = ssic.getIncExp().visit(expEval, env);
        int count = ssic.getCountExp().visit(expEval, env).intValue();
        
        for (int i = 0; i < count; i++) {
            result.add(start + i * inc);
        }
        
        return result;
    }
    
    public PointSetkD<Double> evalZipped(SpaceSpecZip ssz, 
            Environment<Double> env) throws VisitException {
        PointSetkD<Double> left = ssz.getLeftForm().eval(this, env);
        PointSetkD<Double> right = ssz.getRightForm().eval(this, env);
        
        return left.zip(right);
    }

    public PointSetkD<Double> evalProduct(SpaceSpecProduct ssProd, 
            Environment<Double> env) throws VisitException {
        PointSetkD<Double> left = ssProd.getLeftForm().eval(this, env);
        PointSetkD<Double> right = ssProd.getRightForm().eval(this, env);
        
        return left.product(right);
    }
}