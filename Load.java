package machine.instructions;

/**
 * The LOAD instruction
 * @Author Sophia Le
 */

import common.Errors;
import machine.Alaton;
import machine.InstructionStack;

public class Load implements Instruction {
    private String name;
    private Alaton machine;
    /** the instruction stack */
    private final InstructionStack stack;

    public Load(String name, Alaton machine) {
        this.name = name;
        this.machine = machine;
        this.stack = machine.getInstructionStack();
    }

    /**
     * Load the variables value from the symbol table and push it onto the stack.
     */
    @Override
    public void execute() {
        if(!this.machine.getSymbolTable().has(this.name)) {
            Errors.report(Errors.Type.UNINITIALIZED, this.name);
        }
        int value = this.machine.getSymbolTable().get(this.name);
        this.stack.push(value);
    }

    /**
     * Show the instruction using text so that it can be understood by a person.
     * @return a short string describing what this instruction will do
     */
    @Override
    public String toString() {
        return Alaton.LOAD + " " + this.name;
    }
}

