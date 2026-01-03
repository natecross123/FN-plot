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
package fnplot.values;

import fnplot.sys.Util;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 *
 * @author newts
 * @param <T>
 */
public class PointSetkD<T> implements Iterable<PointkD<T>> {
    
    private final ArrayList<PointkD<T>> points;
    
    public PointSetkD() {
        points = new ArrayList<>();
    }
    
    public int getSize() {
        return points.size();
    }
    
    public PointkD<T> get(int index) {
        return points.get(index);
    }
    
    /**
     * Add a point to this point set.
     * @param pt
     */
    public void add(PointkD<T> pt) {
        points.add(pt);
    }

    public PointSetkD<T> zip(PointSetkD<T> other) {
        int sz = points.size();
        int osz = other.getSize();
        int index = 0;
        int ms = Math.min(sz, osz);
        PointSetkD<T> result = new PointSetkD<>();
        while (index < ms) {
            PointkD<T> p1 = points.get(index);
            PointkD<T> p2 = other.get(index);
            result.add(PointkD.concat(p1, p2));
            index = index + 1;
        }
        return result;
    }

    public PointSetkD<T> product(PointSetkD<T> other) {
        PointSetkD<T> result = new PointSetkD<>();
        for (PointkD<T> p1 : points) {
            for (PointkD<T> p2 : other) {
                result.add(PointkD.concat(p1, p2));
            }
        }
        return result;
    }

    @Override
    public Iterator<PointkD<T>> iterator() {
        return points.iterator();
    }

    @Override
    public void forEach(Consumer<? super PointkD<T>> action) {
        Iterable.super.forEach(action); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public Spliterator<PointkD<T>> spliterator() {
        return Iterable.super.spliterator(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("(");
        Util.listToString(points, ", ", result);
        result.append(")");
        return result.toString();
    }
    
}
