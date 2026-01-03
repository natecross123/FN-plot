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

import fnplot.syntax.SpaceSpecZip;
import fnplot.syntax.SpaceSpecPoints;
import fnplot.syntax.SpaceSpecProduct;
import fnplot.syntax.SpaceSpecRangeAndCount;
import fnplot.syntax.SpaceSpecRangeAndInc;
import fnplot.sys.VisitException;
import fnplot.values.PointSet1D;
import fnplot.values.PointSetkD;

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
        /* Implement this */
        
        return result;
    }
    
    public PointSet1D<Double> evalRangeAndInc(SpaceSpecRangeAndInc ssri, Environment<Double> env) 
            throws VisitException {
        PointSet1D<Double> result = new PointSet1D<>();
        /* Implement this */
        
        return result;
    }

    public PointSet1D<Double> evalRangeAndCount(SpaceSpecRangeAndCount ssrc, 
            Environment<Double> env) throws VisitException {
        PointSet1D<Double> result = new PointSet1D<>();
        /* Implement this */
        
        return result;
    }
    
    public PointSetkD<Double> evalZipped(SpaceSpecZip ssz, 
            Environment<Double> env) throws VisitException {        
        /* Implement this */
        return null;
    }    

    public PointSetkD<Double> evalProduct(SpaceSpecProduct ssProd, 
            Environment<Double> env) throws VisitException {
        /* Implement this */
        return null;
    }
}
