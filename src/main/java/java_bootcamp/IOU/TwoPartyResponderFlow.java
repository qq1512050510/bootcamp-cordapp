package java_bootcamp.IOU;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.FlowException;
import net.corda.core.flows.FlowLogic;
import net.corda.core.flows.FlowSession;
import net.corda.core.flows.InitiatedBy;
import net.corda.core.identity.Party;

@InitiatedBy(TwoPartyInitiatingFlow.class)
public class TwoPartyResponderFlow extends FlowLogic<Void> {

    private FlowSession counterPartySession;

    public TwoPartyResponderFlow(FlowSession counterPartySession) {
        this.counterPartySession = counterPartySession;
    }

    @Suspendable
    public Void call() throws FlowException {
        int receivedInt = counterPartySession.receive(Integer.class).unwrap(it->{
            if(it>10)
                throw new IllegalArgumentException("Number is too high");
            return it;
        });
        int receivedIntPlusOne = receivedInt +1;
        counterPartySession.send(receivedIntPlusOne);
        return null;
    }
}
