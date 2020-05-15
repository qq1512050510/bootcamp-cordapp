package java_bootcamp;

import java_bootcamp.IOU.IOUState;
import net.corda.core.identity.CordaX500Name;
import net.corda.core.identity.Party;
import net.corda.testing.core.TestIdentity;
import org.junit.Test;

public class IOUStateTests {
    private final Party alice = new TestIdentity(new CordaX500Name("Alice", "", "GB")).getParty();
    private final Party bob = new TestIdentity(new CordaX500Name("Bob", "", "GB")).getParty();

    @Test
    public void tokenStateHasIssuerOwnerAndAmountParamsOfCorrectTypeInConstructor() {
        new IOUState(alice, bob, new Double(1));
    }

//    @Test
//    public void tokenStateHasGettersForIssuerOwnerAndAmount() {
//        TokenState tokenState = new TokenState(alice, bob, 1);
//        assertEquals(alice, tokenState.getIssuer());
//        assertEquals(bob, tokenState.getOwner());
//        assertEquals(1, tokenState.getAmount());
//    }

//    @Test
//    public void tokenStateImplementsContractState() {
//        assert(new TokenState(alice, bob, 1) instanceof ContractState);
//    }

//    @Test
//    public void tokenStateHasTwoParticipantsTheIssuerAndTheOwner() {
//        TokenState tokenState = new TokenState(alice, bob, 1);
//        assertEquals(2, tokenState.getParticipants().size());
//        assert(tokenState.getParticipants().contains(alice));
//        assert(tokenState.getParticipants().contains(bob));
//    }
}