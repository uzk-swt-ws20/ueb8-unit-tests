package de.uzk.swt;

/**
 * Serviceklasse für Methoden, die mit Textmanipulation zu tun haben.
 */
public class TextUtils {
    /**
     * Die Funktion spiegelt den eingegebenen Text in der Mitte.
     * <br><br>
     * <b>Beispiel:</b> <code>"Anton" -> "notnA"</code>
     * @param text Text der gespiegelt werden soll
     * @return Gespiegelter Text
     */
    public String reverse(String text) {
        char[] chars = text.toCharArray();
        for(int i = 0; 2*i < chars.length; i++) {
            char tmp = chars[i];
            chars[i] = chars[chars.length-1-i];
            chars[chars.length-1-i] = tmp;
        }
        return String.valueOf(chars);
    }

    /**
     * Zählt wie oft jeder Buchstabe im String vorkommt.
     * <br><br>
     * <b>Beispiel:</b> <code>"abacaba" -> [4,2,1,0,...,0], "Baba" -> [2,2,0,...,0]</code>
     *
     * @param text Zu betrachtender Text
     * @return {@code int[26]} Anzahl der Vorkommen aller Buchstaben in alphabetischer Reihenfolge
     */
    public int[] countLetters(String text) {
        int[] res = new int[26];
        for(int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if('a' <= c && c <= 'z') {
                res[(int)c-(int)'a']++;
            }
        }
        return res;
    }

    /**
     * Ein Palindrom ist ein String, der sich von vorne genauso liest wie von hinten.
     * Diese Funktion ermittelt, ob der übergebene Text ein Palindrom ist.
     *
     * <br><br>
     * <b>Beispiel:</b> <code>"abacaba" -> true, "Baba" -> false, "Nebelleben" -> true</code>
     *
     * @param text Zu betrachtender Text
     * @return Ist {@code text} ein Palindrom?
     */
    public boolean isPalindrome(String text) {
        for(int i = 0; 2*i < text.length(); i++) {
            if(text.charAt(i) != text.charAt(text.length()-1-i))
                return false;
        }
        return true;
    }
}
