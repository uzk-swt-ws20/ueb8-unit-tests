package de.uzk.swt;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ChemCipherTest {
    
    static ChemCipher unit;
    
    @BeforeAll
    static void setUp() {
        unit = new ChemCipher();
    }
    
    @Test
    void chem2str() {
        assertEquals(unit.chem2str(4,90,99,"DA"), "BETHESDA", "It just works. --Todd Howard");
    }

    @Test
    void str2chem() {
        assertArrayEquals(new Object[] {15,57,10,52,7}, unit.str2chem("Planeten"));
        assertArrayEquals(new Object[] {4,90,99,"DA"}, unit.str2chem("Bethesda"));
    }

    static Stream<String> makeRandomStrings() {
        Random random = new Random();

        return Stream.generate(() -> {
            String alphabet =
                    "ABCDEFGHIJKLMNOPQRSTUVWXYZ"+
                    "abcdefghijklmnopqrstuvwxyz"+
                    "0123456789";

            int len = random.nextInt(12)+4;
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < len; i++) {
                sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
            }
            return sb.toString();
        }).limit(10);
    }

    @ParameterizedTest
    @MethodSource("de.uzk.swt.ChemCipherTest#makeRandomStrings")
    void testConsistency(String input) {
        Object[] result = unit.str2chem(input);
        System.out.println("Input: "+input+"\nEncoding: "+ Arrays.toString(result));
        assertEquals(input.toUpperCase(), unit.chem2str(result));
    }

}