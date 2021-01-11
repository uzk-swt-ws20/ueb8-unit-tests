package de.uzk.swt;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Lustiges kleines Modul, das Text durch Atomsymbole bzw. durch die zugehörige
 * Atomzahl darstellt.
 * <br><br>
 * Dieses Modul verwendet meine {@link LinkedList}
 */
public class ChemCipher {

    public static final List<String> periodicTable = Arrays.stream(new String[]{"",
            "H",                                                                                                  "He",
            "Li", "Be",                                                             "B",  "C",  "N",  "O",  "F",  "Ne",
            "Na", "Mg",                                                             "Al", "Si", "P",  "S",  "Cl", "Ar",
            "K",  "Ca", "Sc", "Ti", "V",  "Cr", "Mn", "Fe", "Co", "Ni", "Cu", "Zn", "Ga", "Ge", "As", "Se", "Br", "Kr",
            "Rb", "Sr", "Y",  "Zr", "Nb", "Mo", "Tc", "Ru", "Rh", "Pd", "Ag", "Cd", "In", "Sn", "Sb", "Te", "I",  "Xe",
            "Cs", "Ba", "La",
            "Ce", "Pr", "Nd", "Pm", "Sm", "Eu", "Gd", "Tb", "Dy", "Ho", "Er", "Tm", "Yb", "Lu",
                              "Hf", "Ta", "W",  "Re", "Os", "Ir", "Pt", "Au", "Hg", "Tl", "Pb", "Bi", "Po", "At", "Rn",
            "Fr", "Ra", "Ac",
            "Th", "Pa", "U",  "Np", "Pu", "Am", "Cm", "Bk", "Cf", "Es", "Fm", "Md", "No", "Lr",
                              "Rf", "Db", "Sg", "Bh", "Hs", "Mt", "Ds", "Rg", "Cn", "Nh", "Fl", "Mc", "Lv", "Ts", "Og"})
            .map(String::toUpperCase).collect(Collectors.toList());

    /**
     * Wandelt eine Liste von Atomzahlen und Klartextschnipseln in einen String um.
     *
     * <br><br>
     * <b>Beispiel:</b> <code>{4,90,99,"DA"} -> "Be Th Es DA" -> "BETHESDA"</code>
     * <br> It just works.
     *
     * @param in {@code (String|Integer)...} Abwechselnd {@link Integer} für Atomzahlen
     *                                     und {@link String Strings} für Klartext
     * @return Der ursprüngliche String
     * @see #str2chem(String)
     */
    public String chem2str(Object... in) {
        StringBuilder result = new StringBuilder();

        for(Object el : in) {
            if(el instanceof Integer)
                result.append(periodicTable.get((Integer)el));
            else
                result.append(el);
        }

        return result.toString();
    }

    /**
     * Wandelt einen Eingabestring um in eine Darstellung mit chemischen Symbolen.
     * Da nicht jede Buchstabenkombination mit chemischen Symbolen geschrieben werden kann,
     * müssen einige Segmente in Klartext verbleiben; ein Kürzester-Weg-Algorithmus
     * maximiert die Amzahl Umwandlungen im Ergebnis.
     * <br><br>
     * <b>Beispiel:</b> <code>"Planeten" -> "P La Ne Te N" -> {15,57,10,52,7}, 
     *                        "Bethesda" -> "Be Th Es DA" -> {4,90,99,"DA"}</code>
     *
     * @param in Der String der ungewandelt werden soll
     * @return {@code (String|Integer)...}: Abwechselnd entweder ein {@link Integer} (für eine Atomzahl)
     *         oder ein {@link String} (für übriggebliebenen Klartext)
     */
    public Object[] str2chem(String in) {
        in = in.toUpperCase(Locale.ROOT);

        int[] dist = new int[in.length()+1];
        int[] prev = new int[in.length()+1];
        prev[0] = -1;

        // Essenziell Dijkstra Schritt 1
        for(int i = 0; i < in.length(); i++) {
            if(dist[i] > dist[i+1]) {
                dist[i + 1] = dist[i];
                prev[i + 1] = i;
            }
            if(periodicTable.contains(in.substring(i,i+1)) && dist[i] + 1 > dist[i+1]) {
                dist[i + 1] = dist[i] + 1;
                prev[i + 1] = i;
            }
            if(periodicTable.contains(in.substring(i,i+2)) && dist[i] + 2 > dist[i+2]) {
                dist[i + 2] = dist[i] + 2;
                prev[i + 2] = i;
            }
        }

        // Dijkstra Schritt 2: Weg rückverfolgen
        int[] instructions = new int[in.length()];
        for(int i = in.length(); i > 0; i = prev[i]) {
            instructions[prev[i]] = dist[i]-dist[prev[i]];
        }

        List<Object> result = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < in.length(); ) {
            if(instructions[i] == 0) {
                // Keine Konvertierungsmöglichkeit #sadlife
                sb.append(in.charAt(i));
                i++;
            } else {
                // Schreibe eventuellen Plaintext weg
                if(sb.length() != 0) {
                    result.add(sb.toString());
                    sb = new StringBuilder();
                }

                result.add(periodicTable.indexOf(in.substring(i,i+instructions[i])));
                i+=instructions[i];
            }
        }
        if(sb.length() != 0) {
            result.add(sb.toString());
        }

        return result.toArray();
    }
}
