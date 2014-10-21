package nightra.tests.mycalculator;

import static nightra.tests.mycalculator.algorithm.ParseTest.parseTests;
import static nightra.tests.mycalculator.states.StateIntegrationTest.stateTests;
import static nightra.tests.mycalculator.states.ToStringTests.toStringTests;

public class AllTests {
    public static void main(String[] args) {
        parseTests()
                .and(stateTests())
                .and(toStringTests())
                .run();
    }
}
