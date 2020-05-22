package bootcamp.IOU;

import net.corda.core.contracts.Command;
import net.corda.core.contracts.CommandData;
import net.corda.core.contracts.Contract;
import net.corda.core.contracts.ContractState;
import net.corda.core.identity.Party;
import net.corda.core.transactions.LedgerTransaction;
import org.jetbrains.annotations.NotNull;

import javax.xml.crypto.dsig.TransformService;
import java.security.PublicKey;
import java.util.List;

public class HouseContract implements Contract {
    public static String ID = "bootcamp.IOU.HouseContract";

    public static String getID() {
        return ID;
    }

    public static void setID(String ID) {
        HouseContract.ID = ID;
    }

    @Override
    public void verify(@NotNull LedgerTransaction tx) throws IllegalArgumentException {
        if (tx.getCommands().size() != 1) {
            throw new IllegalArgumentException("Transaction must have one command");
        }
        Command command = tx.getCommand(0);
        CommandData commandType = command.getValue();
        List<PublicKey> requiredKey = command.getSigners();
        if (commandType instanceof Register) {
            //"Shape" constraint - No. input states,no. output states,command
            if (tx.getInputStates().size() != 0) {
                throw new IllegalArgumentException("Register transaction must have no inputs");
            }
            if (tx.getOutputs().size() != 1) {
                throw new IllegalArgumentException("Register transaction must have one output");
            }
            //content constraint
            ContractState outputState = tx.getOutput(0);
            if (!(outputState instanceof HouseState)) {
                throw new IllegalArgumentException("Output must be a HouseState");
            }
            HouseState houseState = (HouseState) outputState;
            if (houseState.getAddress().length() < 3) {
                throw new IllegalArgumentException("Address must be more than 3 characters");
            }
            if (houseState.getOwner().getName().getCountry().equals("CN")) {
                throw new IllegalArgumentException("Owner of the house must be in China");
            }
            // Required Signer constraint
            Party owner = houseState.getOwner();
            PublicKey ownerKey = owner.getOwningKey();
            if (!requiredKey.contains(ownerKey)) {
                throw new IllegalArgumentException("Owner of the house must sign the registration");
            }
        } else if (commandType instanceof Transfer) {

        } else {
            throw new IllegalArgumentException("Unrecognized command");
        }
    }

    public static class Register implements CommandData {

    }

    public class Transfer implements CommandData {

    }

}
