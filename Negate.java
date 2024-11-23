package machine.instructions;

/**
 * The NEG instruction
 * @Author Sophia Le
 */

import machine.Alaton;
import machine.InstructionStack;

public class Negate implements Instruction {

    /** the instruction stack */
    private final InstructionStack stack;

    public Negate(Alaton machine) {
        this.stack = machine.getInstructionStack();
    }

    /**
     * Pops the operand off the stack, and pushes the result of negating it.
     */
    @Override
    public void execute() {
        int value = -(this.stack.pop());
        this.stack.push(value);
    }

    /**
     * Show the instruction using text so that it can be understood by a person.
     * @returna short string describing what this instruction will do
     */
    @Override
    public String toString() {
        return Alaton.NEGATE;
    }
}
