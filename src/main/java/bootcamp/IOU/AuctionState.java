package bootcamp.IOU;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableTable;
import net.corda.core.contracts.*;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AuctionState implements ContractState,FungibleAsset {
    //买方
    private List<Party> buyers;
    //卖方
    private Party sealer;
    //底价
    private Double base;

    public AuctionState(List<Party> buyers, Party sealer, Double base) {
        this.buyers = buyers;
        this.sealer = sealer;
        this.base = base;
    }

    public List<Party> getBuyers() {
        return buyers;
    }

    public void setBuyers(List<Party> buyers) {
        this.buyers = buyers;
    }

    public Party getSealer() {
        return sealer;
    }

    public void setSealer(Party sealer) {
        this.sealer = sealer;
    }

    public Double getBase() {
        return base;
    }

    public void setBase(Double base) {
        this.base = base;
    }

    @NotNull
    @Override
    public List<AbstractParty> getParticipants() {
        List<AbstractParty> parties = new ArrayList<>();
        Collections.addAll(parties, (Party[]) buyers.toArray());
        Collections.addAll(parties, sealer);
        return parties;
    }

    @NotNull
    @Override
    public Amount<Issued> getAmount() {
        return null;
    }

    @NotNull
    @Override
    public Collection<PublicKey> getExitKeys() {
        return null;
    }

    @NotNull
    @Override
    public FungibleAsset withNewOwnerAndAmount(@NotNull Amount newAmount, @NotNull AbstractParty newOwner) {
        return null;
    }

    @NotNull
    @Override
    public AbstractParty getOwner() {
        return null;
    }

    @NotNull
    @Override
    public CommandAndState withNewOwner(@NotNull AbstractParty newOwner) {
        return null;
    }
}
