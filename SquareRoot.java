package machine.instructions;

/**
 * The SQRT instruction
 * @Author Sophia Le
 */

import common.Errors;
import machine.Alaton;
import machine.InstructionStack;

public class SquareRoot implements Instruction {

    /** the instruction stack */
    private final InstructionStack stack;

    public SquareRoot(Alaton machine) {
        this.stack = machine.getInstructionStack();
    }

    /**
     * Pops the operand off the stack, and pushes the integer
     * result of taking the square root of it.
     */
    @Override
    public void execute() {
        int value = this.stack.pop();
        if(value < 0) {
            Errors.report(Errors.Type.NEGATIVE_SQUARE_ROOT);
        }
        this.stack.push((int) Math.sqrt(value));
    }

    /**
     * Show the instruction using text so that it can be understood by a person.
     * @return a short string describing what this instruction will do
     */
    @Override
    public String toString() {
        return Alaton.SQUARE_ROOT;
    }
}
