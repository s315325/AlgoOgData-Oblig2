import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.util.Arrays;

/**
 * Created by Sivert on 24.10.2017.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("- Main");

        String[] s = {"Ole", null, "Per", "Kari", null};
        Liste<String> liste = new DobbeltLenketListe<>(s);
        System.out.println(liste.antall() + " " + liste.tom());

    }
}
