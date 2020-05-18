package bootcamp.IOU;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.FlowException;
import net.corda.core.flows.FlowLogic;
import net.corda.core.flows.InitiatingFlow;
import net.corda.core.flows.StartableByRPC;

@InitiatingFlow
@StartableByRPC
public class VerySimpleFlow extends FlowLogic<Integer> {

    /**
     * None blocking
     */
    @Suspendable
    public Integer call() throws FlowException {
        int a = 5;
        int b = 6;
        return a+b;
    }
}
