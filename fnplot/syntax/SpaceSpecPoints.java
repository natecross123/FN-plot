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
package fnplot.syntax;

import fnplot.semantics.SpaceSpecEvaluator;
import fnplot.semantics.Environment;
import fnplot.sys.Util;
import fnplot.sys.VisitException;
import fnplot.values.PointSet1D;
import java.util.ArrayList;

/**
 *
 * @author newts
 */
public class SpaceSpecPoints extends SpaceSpec1DForm {
    private final ArrayList<Exp> pointExps;
    
    public SpaceSpecPoints(ArrayList<Exp> ptExps) {
        pointExps = ptExps;
    }
    
    public ArrayList<Exp> getPointExps() {
        return pointExps;
    }

    /**
     * Visit this space specification to create a collection of points, returned
     * as a 1-D point set.
     * @param env The state needed by the visitor.
     * @return A 1-D point set containing all the points indicated by the
     * collection of points.
     * @throws VisitException if a problem arises while the visitor is visiting
     * the expressions within the point set.
     */
    @Override
    public PointSet1D<Double> eval(SpaceSpecEvaluator ssEval, Environment<Double> env) throws VisitException {
        return ssEval.evalPointsList(this, env);
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[");
        Util.listToString(pointExps, ", ", result);
        result.append("]");
        return result.toString();
        
    }
}
