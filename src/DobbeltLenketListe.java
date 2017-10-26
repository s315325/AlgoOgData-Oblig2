

import java.util.*;

public class DobbeltLenketListe<T> implements Liste<T>
{
    private static final class Node<T>   // en indre nodeklasse
    {
        // instansvariabler
        private T verdi;
        private Node<T> forrige, neste;

        private Node(T verdi, Node<T> forrige, Node<T> neste)  // konstruktør
        {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;


        }

        protected Node(T verdi)  // konstruktør
        {
            this(verdi, null, null);
        }

    } // Node

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;          // antall endringer i listen

    // hjelpemetode
    private Node<T> finnNode(int indeks) {
        Node<T> node;

        if (indeks < antall/2) {
            node = hode;  // leter fra hode mot høyre
            for (int i = 0; i < indeks; i++) {
                node = node.neste;
            }
        }
        else {
            node = hale;  // leter fra hale mot venstre
            for (int i = antall - 1; i > indeks; i--) {
                node = node.forrige;
            }
        }
        return node;
    }

    // konstruktør
    public DobbeltLenketListe() {
        hode = hale = null;
        antall = 0;
        endringer = 0;
    }

    // konstruktør
    public DobbeltLenketListe(T[] a) {
        Objects.requireNonNull(a, "Tabellen a er null!");
        hode = hale = new Node<>(null);

        for(int i = 0; i < a.length; i++){{
            T verdi = a[i];
            if (verdi != null ){
                hale = hale.neste = new Node<>(verdi, hale, null);
                antall++;
            }
        }
        }
        if (antall == 0){
            hode = hale = null;
        }
        else {
            hode = hode.neste;
            hode.forrige = null;
        }
    }

    // subliste
    public Liste<T> subliste(int fra, int til) {
        fratilKontroll(antall, fra, til);
        DobbeltLenketListe<T> liste = new DobbeltLenketListe<>();

        Node<T> node = finnNode(fra);
        for (int i = fra; i < til; i++){
            liste.leggInn(node.verdi);
            node = node.neste;
        }
        return liste;
    }
    private static void fratilKontroll(int antall, int fra, int til) {
        if (fra < 0)                                  // fra er negativ
            throw new IndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");

        if (til > antall)                             // til er utenfor tabellen
            throw new IndexOutOfBoundsException
                    ("til(" + til + ") > antall(" + antall + ")");

        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }

    @Override
    public int antall()
    {
        return antall;
    }

    @Override
    public boolean tom()
    {
        return antall == 0;
    }

    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "null-verdier ikke tillatt");
        Node<T> node = new Node<>(verdi, hale, null);
        if (tom()){
            hale = hode = node;
        }
        else{
            hale = hale.neste = node;
        }
        antall++;
        endringer++;

        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "Ikke null-verdier!");

        indeksKontroll(indeks, true);

        if (tom())
            hode = hale = new Node<>(verdi, null, null);
        else if (indeks == 0)
            hode = hode.forrige = new Node<>(verdi, null, hode);
        else if (indeks == antall)
            hale = hale.neste = new Node<>(verdi, hale, null);
        else {
            Node<T> node = finnNode(indeks);
            node.forrige = node.forrige.neste = new Node<>(verdi, node.forrige, node);
        }
        antall++;
        endringer++;
    }

    @Override
    public boolean inneholder(T verdi) {
        return indeksTil(verdi) != -1;
    }

    @Override
    public T hent(int indeks)
    {
        indeksKontroll(indeks, false);

        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi) {
        if (verdi == null) return -1;
        Node<T> node = this.hode;

        for (int i = 0; i < antall; i++ ) {
            if (node.verdi.equals(verdi)){
                return i;
            }
            node = node.neste;
        }

        return -1;
    }

    @Override
    public T oppdater(int indeks, T nyVerdi)
    {
        Objects.requireNonNull(nyVerdi, "NyVerdi kan ikke være null!");
        indeksKontroll(indeks, false);

        Node<T> node = finnNode(indeks);
        T gammelVerdi = node.verdi;
        node.verdi = nyVerdi;

        endringer++;
        return gammelVerdi;
    }

    @Override
    public boolean fjern(T verdi) {
        if (verdi == null) return false;
        Node<T> node = hode;
        for (int i = 0; i < antall;i++) {
            if (node == null) break;
            if (node.verdi.equals(verdi)) {
                fjerneNode(node);
                return true;
            }

            node = node.neste;
        }
        return false;
    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks, false);

        return fjerneNode(finnNode(indeks));
    }
    private T fjerneNode(Node<T> node){
        if (node == hode){
            if (antall == 1)
                hode = hale = null;
            else {
                hode = hode.neste;
                hode.forrige = null;
            }
        }
        else if (node == hale) {
            hale = hale.forrige;
            hale.neste = null;
        }
        else {
            node.forrige.neste = node.neste;
            node.neste.forrige = node.forrige;
        }
        antall--;
        endringer++;

        return node.verdi;

    }

    //Denne metoden tok kortere tid en, måte 2.
    @Override
    public void nullstill() {
        Node<T> node = hode;
        while (node != null) {
            node.verdi = null;
            node.neste = null;
            node.forrige = null;
            node = node.neste;
        }
        hode = null;
        hale = null;

        antall = 0;
        endringer++;

    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (Node<T> node = hode; node != null; node = node.neste) {
            sb.append(String.valueOf(node.verdi));
            if (node.neste != null){
                sb.append(", ");
            }
        }
        sb.append(']');
        return sb.toString();
    }

    public String omvendtString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (Node<T> node = hale; node != null; node = node.forrige){
            sb.append(String.valueOf(node.verdi));
            if (node.forrige != null){
                sb.append(", ");
            }
        }
        sb.append(']');
        return sb.toString();
    }



    @Override
    public Iterator<T> iterator()
    {
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks)
    {
        indeksKontroll(indeks, false);
        return new DobbeltLenketListeIterator(indeks);
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // denne starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            denne = finnNode(indeks);
            fjernOK = false;
            iteratorendringer = endringer;
        }

        @Override
        public boolean hasNext() {
            return denne != null;  // denne koden skal ikke endres!
        }

        @Override
        public T next() {
            if (endringer != iteratorendringer)
                throw new ConcurrentModificationException("Listen er endret!");
            if (!hasNext())
                throw new NoSuchElementException("Ingen neste verdi!");
            fjernOK = true;

            T temp = denne.verdi;
            denne = denne.neste;
            return temp;
        }

        @Override
        public void remove() {
            if (!fjernOK)
                throw new IllegalStateException("Verdien kan ikke fjernes nå!");
            if (endringer != iteratorendringer)
                throw new ConcurrentModificationException("Listen har blitt endret!");
            fjernOK = false;
            if (denne == null){
                fjerneNode(hale);
            }else {
                fjerneNode(denne.forrige);
            }
            iteratorendringer++;
        }

    } // DobbeltLenketListeIterator



    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        for (int i = liste.antall(); i > 0; i--) {
            Iterator<T> iterator = liste.iterator();
            int m = 0;
            T min = iterator.next();
            for (int j = 1; j < i; j++) {
                T verdi = iterator.next();
                if (c.compare(verdi,min) < 0) {
                    m = j;
                    min = verdi;
                }
            }
            liste.leggInn(liste.fjern(m));
        }
    }

} // DobbeltLenketListe
