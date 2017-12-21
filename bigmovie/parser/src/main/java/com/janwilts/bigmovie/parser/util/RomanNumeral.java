package com.janwilts.bigmovie.parser.util;

public class RomanNumeral {
    private static int convertRec(String s) {
        if (s.isEmpty()) return 0;
        if (s.startsWith("M"))  return 1000 + convertRec(s.substring(1));
        else if (s.startsWith("CM")) return 900  + convertRec(s.substring(2));
        else if (s.startsWith("D"))  return 500  + convertRec(s.substring(1));
        else if (s.startsWith("CD")) return 400  + convertRec(s.substring(2));
        else if (s.startsWith("C"))  return 100  + convertRec(s.substring(1));
        else if (s.startsWith("XC")) return 90   + convertRec(s.substring(2));
        else if (s.startsWith("L"))  return 50   + convertRec(s.substring(1));
        else if (s.startsWith("XL")) return 40   + convertRec(s.substring(2));
        else if (s.startsWith("X"))  return 10   + convertRec(s.substring(1));
        else if (s.startsWith("IX")) return 9    + convertRec(s.substring(2));
        else if (s.startsWith("V"))  return 5    + convertRec(s.substring(1));
        else if (s.startsWith("IV")) return 4    + convertRec(s.substring(2));
        else if (s.startsWith("I"))  return 1    + convertRec(s.substring(1));
        throw new IllegalArgumentException("Unexpected roman numerals");
    }

    public static int convert(String s) {
        if (s == null || s.isEmpty() || !s.matches("^(M{0,3})(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$"))
            return -1;
        return convertRec(s);
    }
}
