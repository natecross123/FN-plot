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

import fnplot.sys.VisitException;
import java.util.ArrayList;

/**
 *
 * @author newts
 */
public class StmtTabulate extends Statement {
    
    private Exp valueExp;
    private ArrayList<String> varList;
    private final SpaceSpecForm spaceSpec;
    
    public StmtTabulate(Exp valExp, ArrayList<String> vars, SpaceSpecForm spcSpec) {
        super("tabulate");
        valueExp = valExp;
        varList = vars;
        spaceSpec = spcSpec;
    }

    public Exp getValueExp() {
        return valueExp;
    }

    public ArrayList<String> getVarList() {
        return varList;
    }

    public SpaceSpecForm getSpaceSpec() {
        return spaceSpec;
    }
    
    @Override
    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
        return v.visitStmtTabulate(this, arg);
    }

    @Override
    public String toString() {
        return String.format("tabulate %s for %s in %s", valueExp, varList, spaceSpec);
    }
    
}
