package machine.instructions;

/**
 * The DIV instruction
 * @Author Sophia Le
 */

import common.Errors;
import machine.Alaton;
import machine.InstructionStack;

public class Divide implements Instruction {
    /** the instruction stack */
    private final InstructionStack stack;

    public Divide(Alaton machine) {
        this.stack = machine.getInstructionStack();
    }

    /**
     * Pops the second and then first operands off the stack, and pushes the
     * result of the first divided by the second.
     */
    @Override
    public void execute() {
        int firstVal = this.stack.pop();
        int secondVal = this.stack.pop();
        if(firstVal == 0) {
            Errors.report(Errors.Type.DIVIDE_BY_ZERO);
        }
        int value = secondVal / firstVal;
        this.stack.push(value);
    }

    /**
     * Show the instruction using text so that it can be understood by a person.
     * @return a short string describing what this instruction will do
     */
    @Override
    public String toString() {
        return Alaton.DIVIDE;
    }
}
