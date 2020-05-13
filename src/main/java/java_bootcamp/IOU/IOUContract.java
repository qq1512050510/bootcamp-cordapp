package java_bootcamp.IOU;

import net.corda.core.contracts.CommandData;

/* Our contract, governing how our state will evolve over time.
 * See src/main/java/java_examples/ArtContract.java for an example. */
public class IOUContract {
    public static String ID = "java_bootcamp.IOU.IOUContract";

    public interface Commands extends CommandData {
        class Issue implements Commands {
        }
    }
}