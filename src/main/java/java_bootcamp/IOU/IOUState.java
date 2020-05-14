package java_bootcamp.IOU;

import net.corda.core.contracts.ContractState;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IOUState implements ContractState {

    private Party issuer;
    private Party owner;
    private Double amount;

    public Party getIssuer() {
        return issuer;
    }

    public Party getOwner() {
        return owner;
    }

    public Double getAmount() {
        return amount;
    }

    public IOUState(Party issuer, Party owner, Double amount) {
        this.issuer = issuer;
        this.owner = owner;
        this.amount = amount;
    }


    @NotNull
    @Override
    public List<AbstractParty> getParticipants() {
        return null;
    }
}
