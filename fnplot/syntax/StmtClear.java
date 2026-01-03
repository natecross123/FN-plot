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

/**
 *
 * @author newts
 */
public class StmtClear extends Statement {

    public StmtClear() {
        super("clear");
    }
    
    @Override
    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
        return v.visitStmtClear(this, arg);
    }

    @Override
    public String toString() {
        return "clear";
    }
    
}
