package bootcamp.IOU;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.contracts.StateAndRef;
import net.corda.core.flows.*;
import net.corda.core.identity.CordaX500Name;
import net.corda.core.identity.Party;
import net.corda.core.node.NodeInfo;
import net.corda.core.node.ServiceHub;

import java.time.Instant;
import java.util.List;

@InitiatingFlow
@StartableByRPC
public class TwoPartyInitiatingFlow extends FlowLogic<Integer> {
    private Party countParty;
    private int number = 5;

    public TwoPartyInitiatingFlow(Party countParty, int number) {
        this.countParty = countParty;
        this.number = number;
    }

    @Suspendable
    public Integer call() throws FlowException {
        
        Instant instant = Instant.now();

        ServiceHub serviceHub = getServiceHub();
        List<StateAndRef<HouseState>> stateAndRefs = serviceHub.getVaultService().queryBy(HouseState.class).getStates();

        CordaX500Name aliceName = new CordaX500Name("Alice","ShangHai","China");
        NodeInfo aliceNode = serviceHub.getNetworkMapCache().getNodeByLegalName(aliceName);
        Party alice = aliceNode.getLegalIdentities().get(0);

        int platformVersion = serviceHub.getMyInfo().getPlatformVersion();

        FlowSession session = initiateFlow(countParty);
        session.send(number);


        int receivedPlusOneInt = session.receive(Integer.class).unwrap(it -> it);
        return receivedPlusOneInt;
    }
}
