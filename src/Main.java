import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Sivert on 24.10.2017.
 */

public class Main {

    public static void main(String[] args) {

        String[] s1 = {}, s2 = {"A"}, s3 = {null,"A",null,"B",null};
        DobbeltLenketListe<String> l1 = new DobbeltLenketListe<>(s1);
        DobbeltLenketListe<String> l2 = new DobbeltLenketListe<>(s2);
        DobbeltLenketListe<String> l3 = new DobbeltLenketListe<>(s3);
        System.out.println(l1.toString() + " " + l2.toString()
                + " " + l3.toString() + " " + l1.omvendtString() + " "
                + l2.omvendtString() + " " + l3.omvendtString());

        // Utskrift:
        // Lars Anders Bodil Kari Per Berit
        // Lars Anders Bodil Kari Per Berit
    /*    System.out.println("- Main");

        String[] s = {"Ole", null, "Per", "Kari", null};
        Liste<String> liste = new DobbeltLenketListe<>(s);
        liste.leggInn(1, "");
        System.out.println(Arrays.toString(s));
        System.out.println(liste.antall() + " " + liste.tom());
/*
        Character[] c = {'A','B','C','D','E','F','G','H','I','J',};
        DobbeltLenketListe<Character> liste = new DobbeltLenketListe<>(c);
        System.out.println(liste.subliste(3,8));  // [D, E, F, G, H]
        System.out.println(liste.subliste(5,5));  // []
        System.out.println(liste.subliste(8,liste.antall()));  // [I, J]
         System.out.println(liste.subliste(0,11));  // skal kaste unntak

*/
/*

        String[] navn = {"Lars","Anders","Bodil","Kari","Per","Berit"}; Liste<String> liste = new DobbeltLenketListe<>(navn);
        liste.forEach(s -> System.out.print(s + " ")); System.out.println();
        for (String s : liste) System.out.print(s + " ");
        // Utskrift:
        // Lars Anders Bodil Kari Per Berit
        // Lars Anders Bodil Kari Per Berit

        */
      /*  DobbeltLenketListe<String> liste =
                new DobbeltLenketListe<>(new String[]
                        {"Birger","Lars","Anders","Bodil","Kari","Per","Berit"});
        liste.fjernHvis(navn -> navn.charAt(0) == 'B');
        // fjerner navn som starter med B
        System.out.println(liste + " " + liste.omvendtString());
        // Utskrift: [Lars, Anders, Kari, Per] [Per, Kari, Anders, Lars]
*/

/*        DobbeltLenketListe<String> liste = new DobbeltLenketListe<>(new String[]
                {"Birger","Lars","Anders","Bodil","Kari","Per","Berit"});
        liste.fjernHvis(navn -> navn.charAt(0) == 'B'); // fjerner navn som starter med B
        System.out.println(liste + " " + liste.omvendtString());
        // Utskrift: [Lars, Anders, Kari, Per] [Per, Kari, Anders, Lars]*/

    }
}