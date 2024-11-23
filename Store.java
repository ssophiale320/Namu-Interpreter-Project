package machine.instructions;

/**
 * The STORE instruction
 * @Author Sophia Le
 */

import machine.Alaton;
import machine.InstructionStack;

public class Store implements Instruction {

    private String name;
    private Alaton machine;
    /** the instruction stack */
    private final InstructionStack stack;

    public Store(String name, Alaton machine) {
        this.name = name;
        this.machine = machine;
        this.stack = machine.getInstructionStack();
    }

    /**
     * Pops the value off the top of stack and sets the variable's value in the symbol table to the value.
     */
    @Override
    public void execute() {
        int value = this.stack.pop();
        this.machine.getSymbolTable().set(this.name, value);
    }

    /**
     * Show the instruction using text so that it can be understood by a person.
     * @return a short string describing what this instruction will do
     */
    @Override
    public String toString() {
        return Alaton.STORE + " " + this.name;
    }
}
