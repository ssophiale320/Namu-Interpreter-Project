package machine.instructions;

/**
 * The MOD instruction
 * @Author Sophia Le
 */

import machine.Alaton;
import machine.InstructionStack;

public class Modulus implements Instruction {
    /** the instruction stack */
    private final InstructionStack stack;

    public Modulus(Alaton machine) {
        this.stack = machine.getInstructionStack();
    }

    /**
     * Pops the second and then first operands off the stack,
     * and pushes the result of the first modulus by the second.
     */
    @Override
    public void execute() {
        int firstVal = this.stack.pop();
        int secondVal = this.stack.pop();
        int value = secondVal % firstVal;
        this.stack.push(value);
    }

    /**
     * Show the instruction using text so that it can be understood by a person.
     * @return a short string describing what this instruction will do
     */
    @Override
    public String toString() {
        return Alaton.MODULUS;
    }
}
