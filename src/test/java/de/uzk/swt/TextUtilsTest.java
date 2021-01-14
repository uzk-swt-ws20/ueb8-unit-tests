package de.uzk.swt;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TextUtilsTest {

    static TextUtils unit;

    @BeforeAll
    static void setUp() {
        // Prepare the unit for testing: as a stateless service class,
        // this can be done for all tests at once.
        unit = new TextUtils();
    }

    @Test
    void reverse() {
        assertEquals("notnA", unit.reverse("Anton"));
    }

    static List<Arguments> letterCountPairs() {
        return Arrays.asList(
            Arguments.of("abacaba", new int[]{4,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}),
            Arguments.of("Baba", new int[]{2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0})
        );
    }

    @ParameterizedTest
    @MethodSource("de.uzk.swt.TextUtilsTest#letterCountPairs")
    void countLetters(String input, int[] output) {
        assertArrayEquals(output, unit.countLetters(input));
    }

    static List<Arguments> palindromes() {
        return Arrays.asList(
                Arguments.of("abacaba", true),
                Arguments.of("Baba", false),
                Arguments.of("Nebelleben", true)
        );
    }

    @ParameterizedTest
    @MethodSource("de.uzk.swt.TextUtilsTest#palindromes")
    void isPalindrome(String input, boolean output) {
        assertEquals(output, unit.isPalindrome(input));
    }
    
    @Test
    void isPalindrome_throwsOnNull() {
    	assertThrows(NullPointerException.class, () -> unit.isPalindrome(null));
    }
}