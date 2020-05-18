package bootcamp.IOU;

import co.paralleluniverse.fibers.Suspendable;
//import net.corda.core.flows.*;
import net.corda.core.flows.*;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.utilities.ProgressTracker;

@InitiatedBy(IOUIssueFlow.class)
public class IOUIssueFlowResponder extends FlowLogic<Void> {

    private final FlowSession otherSide;

    private final ProgressTracker progressTracker = new ProgressTracker();

    public IOUIssueFlowResponder(FlowSession otherSide) {
        this.otherSide = otherSide;
    }

    @Override
    @Suspendable
    public Void call() throws FlowException {
        SignedTransaction signedTransaction = subFlow(new SignTransactionFlow(otherSide) {
            @Suspendable
            @Override
            protected void checkTransaction(SignedTransaction stx) throws FlowException {
                // Implement responder flow transaction checks here
            }
        });

        subFlow(new ReceiveTransactionFlow(otherSide));
        return null;
    }
}

