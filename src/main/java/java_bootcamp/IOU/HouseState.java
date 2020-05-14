package java_bootcamp.IOU;

import com.google.common.collect.ImmutableList;
import net.corda.core.contracts.ContractState;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HouseState implements ContractState {
    //state 想把什么样的数据写进ledger
    private Party owner;
    private String address;
    private Party resident;

    public HouseState(Party owner, String address, Party resident) {
        this.owner = owner;
        this.address = address;
        this.resident = resident;
    }


    public Party getOwner() {
        return owner;
    }


    public String getAddress() {
        return address;
    }


    public Party getResident() {
        return resident;
    }

    @NotNull
    @Override
    public List<AbstractParty> getParticipants() {
        /*List<AbstractParty> participants = new ArrayList<>();
        participants.add(owner);
        return participants;*/

        return ImmutableList.of(owner);
    }

}
