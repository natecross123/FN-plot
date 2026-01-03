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
import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.Stream;

/**
 * Representation of a point in k-dimensions.
 * 
 * @author newts
 * @param <T>
 */
public class PointkD<T> {
    private final ArrayList<T> components;
    
    protected PointkD() {
        components = new ArrayList<>();
    }
    
    protected PointkD(ArrayList<T> comps) {
        this();
        components.addAll(comps);
    }
    
    public PointkD(T val) {
        this();
        components.add(val);
    }
    
    public PointkD(T... vals) {
        this();
        components.addAll(Arrays.asList(vals));
    }
    
    public int getDimension() {
        return components.size();
    }
    
    public T get(int index) {
        return components.get(index);
    }
    
    public Iterator<T> iter() {
        return components.iterator();
    }
    
    public Spliterator<T> spliterator() {
        return components.spliterator();
    }
    
    public Stream<T> toStream() {
        return components.stream();
    }
    
    public ArrayList<T> asList() {
        return components;
    }
    
    /**
     * Create a new point whose coordinates are derived by concatenating the
     * coordinates of the first point with those of the second point.
     * @param <T>
     * @param first the point whose coordinates will be given earlier indexes
     * @param second the point whose coordinates will be given later indexes.
     * @return
     */
    public static <T> PointkD<T> concat(PointkD<T> first, PointkD<T> second) {
        ArrayList<T> resultComponents = new ArrayList<>();
        resultComponents.addAll(first.asList());
        resultComponents.addAll(second.asList());        
        return new PointkD<>(resultComponents);
    }
   
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("(");
        Util.listToString(components, ", ", result);
        result.append(")");
        return result.toString();
    }
}
