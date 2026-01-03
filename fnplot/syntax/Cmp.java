package fnplot.syntax;

public enum Cmp {
    LT("<") {
        @Override
	public boolean apply(double arg1, double arg2) {
	    return arg1 < arg2;
	}
    },

    LE("<=") {
        @Override
	public boolean apply(double arg1, double arg2) {
	    return arg1 <= arg2;
	}
    },

    EQ("=") {
        @Override
	public boolean apply(double arg1, double arg2) {
	    return arg1 == arg2;
	}
    },

    NE("!=") {
        @Override
	public boolean apply(double arg1, double arg2) {
	    return arg1 != arg2;
	}
    },

    GT(">") {
        @Override
	public boolean apply(double arg1, double arg2) {
	    return arg1 > arg2;
	}
    },

    GE(">=") {
        @Override
	public boolean apply(double arg1, double arg2) {
	    return arg1 >= arg2;
	}
    }
    ;

    private final String symbol;

    private Cmp(String sym) {
	symbol = sym;
    }

    public abstract boolean apply(double arg1, double arg2);

    @Override
    public String toString() {
	return symbol;
    }
}
