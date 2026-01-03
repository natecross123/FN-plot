package fnplot.sys;

import fnplot.semantics.ArithInterpreter;
import fnplot.semantics.PersistentWalker;
import java.util.ArrayList;
import lib3652.util.MainHelper;
import java.lang.reflect.InvocationTargetException;

public class Main {

    /*
    public static final String PROMPT = "> ";

    private static final String MESSAGE = "Type your input at the prompt." +
	"  Terminate with a '.' on a line by itself.\n" +
	"Quit by entering a '.' as the only line or by sending EOF to input.";
    */

    public static void usage() {
	String[] usageMsg = new String[]{
	    String.format("Usage: <javaexec> %s [-w <walker-class-name>] " +
			  "[file ...]", Main.class.getName()),
	    "walker-class-name must be the name of a class that subclasses ",
	    "the class PersistentWalker and has a constructor that takes no",
	    "arguments.  Defaults to Evaluator.",
	    "",
	    "The sequence of filenames provided afterwards is optional.  Each",
	    "will be read and traversed in the order given.  If a '-' is",
	    "specified, input will be read from stdin.  If no files are given,",
	    "input willl be read from stdin."
	};
	for (String line : usageMsg) {
	    System.out.println(line);
	}
    }

    public static void main(String args[]) {
	int n = args.length;
	String walkerName = "";
	ArrayList<String> filenames = new ArrayList<>();

	if (n == 0) {
	    usage();
	    System.exit(0);
	}
	
	// Parse command line arguments
	for (int i = 0; i  < n; i++) {
	    String arg = args[i];
	    if (arg.equals("-h") || arg.equals("--help")) {
		usage();
		System.exit(0);
	    } else if (arg.equals("-w")) {
		walkerName = args[i+1];
		i += 1;
	    } else {
		filenames.add(arg);
	    }
	}

	try {
	    // Now we identify which walker to use
	    // TODO: set the plotter object for the interpreter, probably in ArithInterpreter
            // TODO: Consider changing use of PersistentWalker to AssessmentVisitor and have ArithInterpreter inherit from that instead.
	    PersistentWalker<?, ?> walker;    // to be set later
	    if (walkerName.equals("")) {
		walker = new ArithInterpreter();
	    } else {
		Class<? extends PersistentWalker<?, ?>> wclass;
		wclass =
		    (Class<? extends PersistentWalker<?, ?>>)
		    Class.forName(walkerName);
		walker = wclass.getDeclaredConstructor().newInstance();
	    }
	    MainHelper.walkFiles(walker, filenames);
	    
	} catch (ClassNotFoundException | InstantiationException | 
                 IllegalAccessException | InvocationTargetException cnfe) {
	    System.err.println(cnfe.getMessage());
	    cnfe.printStackTrace();
	    System.exit(1);
	} catch (NoSuchMethodException nsme) {
	    System.err.println("Walker class must have an empty constructor");
	    nsme.printStackTrace();
	    System.exit(1);
	}
    }
}
