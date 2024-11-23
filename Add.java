package machine.instructions;

/**
 * The ADD instruction
 * @Author Sophia Le
 */

import machine.Alaton;
import machine.InstructionStack;

public class Add implements Instruction {

    /** the instruction stack */
    private final InstructionStack stack;

    public Add(Alaton machine) {
        this.stack = machine.getInstructionStack();
    }

    /**
     * Pops the second and then first operands off the stack, and pushes the result of the first added by the second.
     */
    @Override
    public void execute() {
        int firstVal = this.stack.pop();
        int secondVal = this.stack.pop();
        this.stack.push(firstVal + secondVal);
    }

    /**
     * Show the instruction using text so that it can be understood by a person.
     * @return a short string describing what this instruction will do
     */
    @Override
    public String toString() {
        return Alaton.ADD;
    }
}
