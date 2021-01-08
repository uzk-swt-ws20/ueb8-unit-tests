package de.uzk.swt;

/**
 * Serviceklasse für Methoden, die mit Textmanipulation zu tun haben.
 */
public class TextUtils {
    /**
     * Die Funktion spiegelt den eingegebenen Text in der Mitte.
     *
     * @param text
     * @return
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
}
