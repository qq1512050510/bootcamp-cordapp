package bootcamp;

import bootcamp.IOU.IOUContract;
import bootcamp.IOU.IOUState;
import net.corda.core.contracts.Contract;
import net.corda.core.contracts.StateAndRef;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.CordaX500Name;
import net.corda.core.identity.Party;
import net.corda.core.transactions.TransactionBuilder;
import net.corda.finance.contracts.Fix;
import net.corda.finance.contracts.FixOf;
import net.corda.finance.contracts.FixableDealState;
import net.corda.testing.contracts.DummyState;
import net.corda.testing.core.DummyCommandData;
import net.corda.testing.core.TestIdentity;
import net.corda.testing.node.MockServices;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static net.corda.testing.node.NodeTestUtils.*;
import static java.lang.System.out;


public class IOUContractTests {
    private TransactionBuilder tb = new TransactionBuilder();
    private final TestIdentity alice = new TestIdentity(new CordaX500Name("Alice", "", "GB"));
    private final TestIdentity bob = new TestIdentity(new CordaX500Name("Bob", "", "GB"));
    private MockServices ledgerServices = new MockServices(new TestIdentity(new CordaX500Name("TestId", "", "GB")));
    private IOUState tokenState = new IOUState(alice.getParty(), bob.getParty(), new Double(1));
    private FixableDealState oracleState = new FixableDealState() {
        @Nullable
        @Override
        public FixOf nextFixingOf() {
            return null;
        }

        @NotNull
        @Override
        public Party getOracle() {

            //tb.verify();
            return null;
        }

        @Override
        public void generateFix(@NotNull TransactionBuilder ptx, @NotNull StateAndRef<?> oldState, @NotNull Fix fix) {

        }

        @NotNull
        @Override
        public TransactionBuilder generateAgreement(@NotNull Party notary) {
            return null;
        }

        @NotNull
        @Override
        public UniqueIdentifier getLinearId() {
            return null;
        }

        @NotNull
        @Override
        public List<AbstractParty> getParticipants() {
            return null;
        }
    };
   /* @Test
    public void test() {
        out.println("test");
        out.println("开始测试");
    }

    @Test
    public void tokenContractImplementsContract() {
        assert (new IOUContract() instanceof Contract);
    }

    @Test
    public void tokenContractRequiresZeroInputsInTheTransaction() {

        transaction(ledgerServices, tx -> {
            // Has an input, will fail.
            tx.input(IOUContract.ID, tokenState);
            tx.output(IOUContract.ID, tokenState);
            tx.command(alice.getPublicKey(), new IOUContract.Commands.Issue());
            //tx.timeWindow();
            tx.fails();
            return null;
        });

        transaction(ledgerServices, tx -> {
            tx.input(IOUContract.ID, tokenState);
            tx.output(IOUContract.ID, tokenState);
            tx.reference(IOUContract.ID,oracleState);
            tx.command(alice.getPublicKey(), new IOUContract.Commands.Issue());
            tx.verifies();
            return null;
        });
    }

    @Test
    public void tokenContractRequiresOneOutputInTheTransaction() {
        transaction(ledgerServices, tx -> {
            // Has two outputs, will fail.
            tx.output(IOUContract.ID, tokenState);
            tx.output(IOUContract.ID, tokenState);
            tx.command(alice.getPublicKey(), new IOUContract.Commands.Issue());
            tx.fails();
            return null;
        });

        transaction(ledgerServices, tx -> {
            // Has one output, will verify.
            tx.output(IOUContract.ID, tokenState);
            tx.command(alice.getPublicKey(), new IOUContract.Commands.Issue());
            tx.verifies();
            return null;
        });
    }

    @Test
    public void tokenContractRequiresOneCommandInTheTransaction() {
        transaction(ledgerServices, tx -> {
            tx.output(IOUContract.ID, tokenState);
            // Has two commands, will fail.
            tx.command(alice.getPublicKey(), new IOUContract.Commands.Issue());
            tx.command(alice.getPublicKey(), new IOUContract.Commands.Issue());
            tx.fails();
            return null;
        });

        transaction(ledgerServices, tx -> {
            tx.output(IOUContract.ID, tokenState);
            // Has one command, will verify.
            tx.command(alice.getPublicKey(), new IOUContract.Commands.Issue());
            tx.verifies();
            return null;
        });
    }

    @Test
    public void tokenContractRequiresTheTransactionsOutputToBeAIOUState() {
        transaction(ledgerServices, tx -> {
            // Has wrong output type, will fail.
            tx.output(IOUContract.ID, new DummyState());
            tx.command(alice.getPublicKey(), new IOUContract.Commands.Issue());
            tx.fails();
            return null;
        });

        transaction(ledgerServices, tx -> {
            // Has correct output type, will verify.
            tx.output(IOUContract.ID, tokenState);
            tx.command(alice.getPublicKey(), new IOUContract.Commands.Issue());
            tx.verifies();
            return null;
        });
    }

    @Test
    public void tokenContractRequiresTheTransactionsOutputToHaveAPositiveAmount() {
        IOUState zeroIOUState = new IOUState(alice.getParty(), bob.getParty(), new Double(0));
        IOUState negativeIOUState = new IOUState(alice.getParty(), bob.getParty(), new Double(-1));
        IOUState positiveIOUState = new IOUState(alice.getParty(), bob.getParty(), new Double(2));

        transaction(ledgerServices, tx -> {
            // Has zero-amount IOUState, will fail.
            tx.output(IOUContract.ID, zeroIOUState);
            tx.command(alice.getPublicKey(), new IOUContract.Commands.Issue());
            tx.fails();
            return null;
        });

        transaction(ledgerServices, tx -> {
            // Has negative-amount IOUState, will fail.
            tx.output(IOUContract.ID, negativeIOUState);
            tx.command(alice.getPublicKey(), new IOUContract.Commands.Issue());
            tx.fails();
            return null;
        });

        transaction(ledgerServices, tx -> {
            // Has positive-amount IOUState, will verify.
            tx.output(IOUContract.ID, tokenState);
            tx.command(alice.getPublicKey(), new IOUContract.Commands.Issue());
            tx.verifies();
            return null;
        });

        transaction(ledgerServices, tx -> {
            // Also has positive-amount IOUState, will verify.
            tx.output(IOUContract.ID, positiveIOUState);
            tx.command(alice.getPublicKey(), new IOUContract.Commands.Issue());
            tx.verifies();
            return null;
        });
    }

    @Test
    public void tokenContractRequiresTheTransactionsCommandToBeAnIssueCommand() {
        transaction(ledgerServices, tx -> {
            // Has wrong command type, will fail.
            tx.output(IOUContract.ID, tokenState);
            tx.command(alice.getPublicKey(), DummyCommandData.INSTANCE);
            tx.fails();
            return null;
        });

        transaction(ledgerServices, tx -> {
            // Has correct command type, will verify.
            tx.output(IOUContract.ID, tokenState);
            tx.command(alice.getPublicKey(), new IOUContract.Commands.Issue());
            tx.verifies();
            return null;
        });
    }

    @Test
    public void tokenContractRequiresTheIssuerToBeARequiredSignerInTheTransaction() {
        IOUState tokenStateWhereBobIsIssuer = new IOUState(bob.getParty(), alice.getParty(), new Double(1));

        transaction(ledgerServices, tx -> {
            // Issuer is not a required signer, will fail.
            tx.output(IOUContract.ID, tokenState);
            tx.command(bob.getPublicKey(), new IOUContract.Commands.Issue());
            tx.fails();
            return null;
        });

        transaction(ledgerServices, tx -> {
            // Issuer is also not a required signer, will fail.
            tx.output(IOUContract.ID, tokenStateWhereBobIsIssuer);
            tx.command(alice.getPublicKey(), new IOUContract.Commands.Issue());
            tx.fails();
            return null;
        });

        transaction(ledgerServices, tx -> {
            // Issuer is a required signer, will verify.
            tx.output(IOUContract.ID, tokenState);
            tx.command(alice.getPublicKey(), new IOUContract.Commands.Issue());
            tx.verifies();
            return null;
        });

        transaction(ledgerServices, tx -> {
            // Issuer is also a required signer, will verify.
            tx.output(IOUContract.ID, tokenStateWhereBobIsIssuer);
            tx.command(bob.getPublicKey(), new IOUContract.Commands.Issue());
            tx.verifies();
            return null;
        });
    }*/
}
