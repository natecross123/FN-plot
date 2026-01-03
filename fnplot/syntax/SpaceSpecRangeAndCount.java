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
import fnplot.sys.VisitException;
import fnplot.values.PointSet1D;

/**
 *
 * @author newts
 */
public class SpaceSpecRangeAndCount extends SpaceSpec1DForm {
    
    private final Exp startExp;
    private final Exp stopExp;
    private final Exp countExp;
    
    public SpaceSpecRangeAndCount(Exp start, Exp stop, Exp count) {
        startExp = start;
        stopExp = stop;
        countExp = count;
    }

    public Exp getStartExp() {
        return startExp;
    }

    public Exp getStopExp() {
        return stopExp;
    }

    public Exp getCountExp() {
        return countExp;
    }

    @Override
    public PointSet1D<Double> eval(SpaceSpecEvaluator ssEval, Environment<Double> env) throws VisitException {
        return ssEval.evalRangeAndCount(this, env);
    }
    
    @Override
    public String toString() {
        return String.format("[%s .. %s; %s", startExp, stopExp, countExp);
    }
    
}
