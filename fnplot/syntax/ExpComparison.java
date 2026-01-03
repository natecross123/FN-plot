package fnplot.syntax;

public class ExpComparison {

    Cmp comparator;
    Exp leftExp;
    Exp rightExp;

    public ExpComparison(Cmp comp, Exp left, Exp right) {
	comparator = comp;
	leftExp = left;
	rightExp = right;
    }

    public Cmp getComparator() {
	return comparator;
    }

    public Exp getLeft() {
	return leftExp;
    }

    public Exp getRight() {
	return rightExp;
    }

    public String toString() {
	return String.format("%s %s %s", getLeft(), comparator, getRight());
    }
}
