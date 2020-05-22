package bootcamp.IOU;

import net.corda.core.contracts.Command;
import net.corda.core.contracts.CommandData;
import net.corda.core.contracts.Contract;
import net.corda.core.contracts.ContractState;
import net.corda.core.identity.Party;
import net.corda.core.transactions.LedgerTransaction;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.util.List;

/* Our contract, governing how our state will evolve over time.
 * See src/main/java/java_examples/ArtContract.java for an example. */
public class IOUContract implements Contract {

    public static String ID = "bootcamp.IOU.IOUContract";

    @Override
    public void verify(@NotNull LedgerTransaction tx) throws IllegalArgumentException {
        if (tx.getCommands().size() != 1) {
            throw new IllegalArgumentException("Transaction must have one command");
        }


        Command command = tx.getCommand(0);
        CommandData commandType = command.getValue();
        List<PublicKey> requiredKey = command.getSigners();
        if (commandType instanceof Commands.Issue) {
            if (tx.getInputStates().size() != 0) {
                throw new IllegalArgumentException("Register transaction must have no inputs");
            }
            if (tx.getOutputStates().size() != 1) {
                throw new IllegalArgumentException("Register transaction must have one output");
            }

            //1. output is IOUState?
            if (!(tx.getOutput(0) instanceof IOUState)) {
                throw new IllegalArgumentException("Output state must be a IOUState");
            }
            IOUState outputState = (IOUState) tx.getOutput(0);
            //2. amount is positive
            if (outputState.getAmount() <= 0) {
                throw new IllegalArgumentException("Issue Amount must be positive number");
            }
            //3.命令内容为Issue

            //4. Issue signature
            Party issue = outputState.getIssuer();
            PublicKey publicKey = issue.getOwningKey();
            if (!requiredKey.contains(publicKey)) {
                throw new IllegalArgumentException("The signature must contains the issue");
            }
        } else {
            throw new IllegalArgumentException("Unrecognized command");
        }
    }

    public interface Commands extends CommandData {
        class Issue implements Commands {
        }
    }
}