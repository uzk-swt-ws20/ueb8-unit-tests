package de.uzk.swt;

// Annotationen etc
import org.junit.jupiter.api.*;
// Parametrisierte Tests
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
// assert-Statements sollen einfach zugänglich sein
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class ExampleTest {

    private static int count;

    /**
     * For each test function, the test class is newly instantiated. Therefore, its constructor
     * has a similar role to {@link BeforeEach}; using the annotation is clearer though, and thus recommended.
     */
    ExampleTest() {
        System.out.println("I guess unit tests still have a constructor...");
    }

    @BeforeAll
    static void setUpGlobal() {
        System.out.println("called only once before any tests happen");
    }

    @BeforeEach
    void setUp() {
        System.out.println("called once before each test");
    }

    @BeforeEach
    void setUp2() {
        System.out.println("You can absolutely have more than one BeforeEach/AfterAll/etc, " +
                "but there is no guarantee about the order in which they will be called.");
    }

    @AfterEach
    void tearDown() {
        System.out.println("called once after each test");
        count++;
    }

    @AfterAll
    static void tearDownGlobal() {
        System.out.println("called only once after all tests are done");
        System.out.println(count+" tests run. Do not do this kind of thing at home, kids!");
    }

    @Test
    void basicTest() {
        System.out.println("This test never fails");
        assertTrue(1 != 2, "If 1 was equal to 2, the test would fail and this message would be shown.");
    }

    /**
     * Normally tests will have the name of their function, but you can override that.
     * It's even possible to write your own DisplayNameGenerator, and apply it to a class or method!
     */
    @Test
    @DisplayName("goo goo gaa gaa")
    void emptyTest() {
        // this is acceptable and passes.
    }

    /**
     * This test fails, so one smartass programmer simply disabled it so he could still deploy.
     * <br><b>Kids, never do this! Only Villains do this!</b>
     * <br><br>
     * That being said, there can be legitimate reasons for wanting to disable a test, or even
     * an entire test class. So now you know how.
     */
    @Test
    @Disabled("no time to fix lol")
    void failingTest() {
        // assertTrue(1 == 2);
        fail("Or even simpler: Fails automatically. Use this for more complex fail logic, " +
                "or to test if your test runner works.");
    }

    /**
     * This test is run multiple times, each time with a different entry from the
     * provided list of Strings/Parameters.
     * <br><br>
     * There are various possible sources for these values, including but not limited to
     * <ul>
     *     <li>Static lists in the annotation {@link ValueSource}</li>
     *     <li>An attribute of the test class</li>
     *     <li>A CSV file ({@link CsvFileSource})</li>
     *     <li>Even a custom method that is then called to obtain a list of parameters ({@link MethodSource})</li>
     * </ul>
     *
     * @param sth Here it doesn't really matter. But in a real setting, this would be filled with various
     *            input values of a similar category, and all of them tested.
     */
    @ParameterizedTest
    @ValueSource(strings = {"abc", "def", "ghi"})
    void manyTests(String sth) {
        System.out.println("Here's some text: "+sth);
        // assertNotEquals(sth, "def");
    }

    /**
     * It is even possible to create unit tests on the fly! You could, for example, use a {@link TestFactory}
     * to compile tests from a different language into Java code, then load that code as a lambda function or sth idk.
     * <br><br>
     * You will rarely need this, but there's a lot of cool stuff that could potentially be done with it.
     *
     * @return A list, set, stream etc. of {@link DynamicNode} objects, or basically executable tests.
     */
    @TestFactory
    Collection<DynamicNode> createABunchOfTests() {
        return Arrays.asList(DynamicTest.dynamicTest("Dynamically created Test", () -> {
            System.out.println("JUnit allows you to take full advantage of the JVM's flexibility!");
        }));
    }
}
