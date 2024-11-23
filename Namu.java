package interpreter;

import common.Errors;
import common.SymbolTable;
import interpreter.nodes.action.*;
import interpreter.nodes.expression.*;
import machine.Alaton;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * The main program for the high level NMU language.  It takes a program in NMU,
 * converts it into a token list, builds the parse trees for each statement,
 * displays the program in infix, interprets/evaluates the program, then
 * compiles into ALT instructions so that the machine, Alaton, can execute it.
 *
 * @author RIT CS
 */
public class Namu {
    /** the terminating character when reading machine instructions from user (not file) */
    private final static String EOF = ".";

    /** the NMU print token */
    private final static String PRINT = "@";
    /** the NMU assignment token */
    private final static String ASSIGN = "=";

    /** the location to generate the compiled NMU program of NMU instructions */
    private final static String TMP_NMU_FILE = "tmp/TEMP.nmu";

    // TODO
    private ArrayList<String> tokenList;
    private ArrayList<ActionNode> actionList;

    /**
     * Create a new Namu instance.  The result of this method is the tokenization
     * of the entire NMU input into a list of strings.  Each line that
     * represents a statement in prefix form should be printed to standard output
     * here.
     *
     * @param in where to read the NMU input from
     * @param stdin if true, the user should be prompted to enter NMU statements until
     *              a terminating ".".
     */
    public Namu(Scanner in, boolean stdin) {
        if (stdin) {
            System.out.print("🌳 ");
        }
        System.out.println("(NMU) prefix...");

        // TODO
        this.actionList = new ArrayList<ActionNode>();
        this.tokenList = new ArrayList();
        while (in.hasNext()) {
            String[] fields = in.nextLine().strip().split(" ");
            for(int i = 0; i < fields.length; i++) {
                this.tokenList.add(fields[i]);
                System.out.print(fields[i] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Recursively parses the tokens list to build the parse trees
     * @return an ExpressionNode
     */
    private ExpressionNode parse() {
        List<String> unary = List.of("!", "$");
        List<String> binary = List.of("+", "-", "*", "/", "^", "%");
        List<String> nums = List.of("1","2","3","4","5","6","7","8","9","0");
        String token = (String) this.tokenList.removeFirst();
        if (binary.contains(token)) {
            ExpressionNode left = parse();
            ExpressionNode right = parse();
            return new BinaryOperation(token, left, right);
        } else if (unary.contains(token)) {
            ExpressionNode operand = parse();
            return new UnaryOperation(token, operand);
        } else if (token.matches("^[a-zA-Z].*")) {
            return new Variable(token);
        } else if (!nums.contains(token)) {
            Errors.report(Errors.Type.ILLEGAL_OPERATOR, token);
        }
        return new Constant(Integer.parseInt(token));
    }
    /**
     * Build the parse trees into the program which is a list of ActioNode's -
     * one per line of NMU input.
     */
    public void buildProgram() {
        // TODO
        while(!this.tokenList.isEmpty()) {
            String current = (String) this.tokenList.removeFirst();
            if(current.equals("=")) {
                String name = (String) this.tokenList.removeFirst();
                this.actionList.add(new Assignment(name, parse()));
            } else if (current.equals("@")) {
                this.actionList.add(new Print(parse()));
            } else {
                Errors.report(Errors.Type.ILLEGAL_ACTION, current);
            }
        }
    }

    /**
     * Displays the entire NMU program of ActionNode's to standard
     * output using emit().
     */
    public void displayProgram() {
        System.out.println("(NMU) infix...");
        // TODO
        for(ActionNode action : this.actionList) {
            action.emit();
            System.out.println();
        }
    }

    /**
     * Execute the NMU program of ActionNode's to standard output using execute().
     * In order to execute the ActioNodes, a local SymbolTable must be created here
     * for use.
     */
    public void interpretProgram() {
        System.out.println("(NMU) interpreting program...");
        // TODO
        SymbolTable sym = new SymbolTable();
        for(ActionNode action : this.actionList) {
            if(!sym.has(action.toString())) {
                Errors.report(Errors.Type.UNINITIALIZED, "y");
            }
            action.execute(sym);
        }
        System.out.println("(NMU) Symbol table:");
        // TODO
        System.out.print(sym);
    }

    /**
     * Compile the NMU program using ActionNode's compile() into the
     * temporary ALT instruction file.
     *
     * @throws IOException if there are issues working with the temp file
     */
    public void compileProgram() throws IOException {
        System.out.println("(NMU) compiling program to " + TMP_NMU_FILE + "...");
        PrintWriter out = new PrintWriter(TMP_NMU_FILE);

        //TODO
        for(ActionNode action : this.actionList) {
            action.compile(out);
        }

        out.close();
    }

    /**
     * Takes the generated ALT instruction file and assembles/executes
     * it using the Alaton machine.
     *
     * @throws FileNotFoundException if the ALT file cannot be found.
     */
    public void executeProgram() throws FileNotFoundException {
        // TODO
        Alaton machine = new Alaton();
        Scanner scan = new Scanner(new File(TMP_NMU_FILE));
        machine.assemble(scan, false);
        machine.execute();
    }

    /**
     * The main program runs either with no input (NMU program entered through standard
     * input), or with a file name that represents the NMU program.
     *
     * @param args command line arguments
     * @throws IOException if there are issues working with the NMU/ALT files.
     */
    public static void main(String[] args) throws IOException {
        // determine NMU input source
        Scanner nmuIn = null;
        boolean stdin = false;
        if (args.length == 0) {
            nmuIn = new Scanner(System.in);
            stdin = true;
        } else if (args.length == 1) {
            nmuIn = new Scanner(new File(args[0]));
        } else {
            System.out.println("Usage: java Namu filename.nmu");
            System.exit(1);
        }

        // step 1: read NMU program into token list
        Namu interpreter = new Namu(nmuIn, stdin);

        // step 2: parse and build the program from the token list
        interpreter.buildProgram();

        // step 3: display the program in infix
        interpreter.displayProgram();

        // step 4: interpret program
        interpreter.interpretProgram();

        // step 5: compile the program
        interpreter.compileProgram();

        // step 6: have machine execute compiled program
        interpreter.executeProgram();
    }
}
