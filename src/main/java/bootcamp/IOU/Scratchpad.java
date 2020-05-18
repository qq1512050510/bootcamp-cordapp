package bootcamp.IOU;

import net.corda.core.contracts.StateAndRef;
import net.corda.core.identity.Party;
import net.corda.core.transactions.TransactionBuilder;

import java.security.PublicKey;

public class Scratchpad {
    public static void main(String[] args) {
        StateAndRef<HouseState> inputState = null;
        HouseState outputState = new HouseState(null, "92 BroadLands Road", null);
        PublicKey requiredSigner = outputState.getOwner().getOwningKey();

        TransactionBuilder builder = new TransactionBuilder();

        Party notary = null;
        builder.setNotary(notary);
        builder
                .addInputState(inputState)
                //.addOutputState(outputState, "bootcmap.HouseContract")
                .addOutputState(outputState, HouseContract.ID)
                .addCommand(new HouseContract.Register(), requiredSigner);

        //builder.verify(serviceHub);


    }
}
