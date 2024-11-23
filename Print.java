package machine.instructions;

/**
 * The PRINT instruction
 * @Author Sophia Le
 */

import machine.Alaton;
import machine.InstructionStack;

public class Print implements Instruction {

    /** the instruction stack */
    private final InstructionStack stack;
    public Print(Alaton machine) {
        this.stack = machine.getInstructionStack();
    }

    /**
     * Pops the top operand off the stack and prints the resulting value.
     */
    @Override
    public void execute() {
        System.out.println(this.stack.pop());
    }

    /**
     * Show the instruction using text so that it can be understood by a person.
     * @return a short string describing what this instruction will do
     */
    @Override
    public String toString() {
        return Alaton.PRINT;
    }
}
