/////////// DobbeltLenketListe ////////////////////////////////////
import com.sun.istack.internal.localization.NullLocalizable;

import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

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
    private Node hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;   // antall endringer i listen

    // hjelpemetode
    private Node<T> finnNode(int indeks)
    {
        Node<T> tNode;

        if (indeks < antall / 2)
        {
            // hode Fra høyre
            tNode = hode;
            for (int i = 0; i < indeks; i++)
            {
                tNode = tNode.neste;
            }
        }
        else
        {
            // hale fra venstre
            tNode = hale;
            for (int i = antall - 1; i > indeks; i--)
            {
                tNode = tNode.forrige;
            }
        }
        return tNode;
    }


    // konstruktør
    public DobbeltLenketListe()
    {
        hode = hale = null;
        antall = 0;
        endringer = 0;
    }

    // konstruktør
    public DobbeltLenketListe(T[] a) {
        this();         //Kaller på konstruktøren i Node-klassen
        //Objects.requireNonNull(a, "Tabllen er null!");

        hode = hale = new Node<>(null); //Lager en ny Node
        //for (T verdi : a)
        for(int i = 0; i< a.length; i++) //Lager en for-løkke som går igjennom T[]
        {
            T verdi = a[i];
            if (verdi != null )
            {
                hale = hale.neste = new Node<>(verdi, hale, null);
                antall++;
            }
        }
        if (antall == 0) hode = hale = null;        //Fjerner Noden
        else (hode = hode.neste).forrige = null;
    }

    //hjelpemetode fra kompendiet
    private static void fratilKontroll(int tabelLengde, int fra, int til)
    {
        if (fra < 0)                                  // fra er negativ
            throw new IndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");
        if (til > tabelLengde)                             // til er utenfor tabellen
            throw new IndexOutOfBoundsException
                    ("til(" + til + ") > antall(" + tabelLengde + ")");
        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }


    public Liste<T> subliste(int fra, int til)
    {

        fratilKontroll(antall, fra, til);

        DobbeltLenketListe<T> liste = new DobbeltLenketListe<>();

        Node<T> t = finnNode(fra);

        for (int i = fra; i < til; i++)   // [fra:til>
        {
            liste.leggInn(t.verdi);
            t = t.neste;
        }

        return liste;
    }


    @Override
    public int antall()
    {
       for( Node T = hode; T != null; T = T.neste)
           antall++;
       return antall;
    }

    @Override
    public boolean tom()
    {

       // return (hode == null); //Sjekker om første lenken er tom
        return antall == 0;

    }

    @Override
    public boolean leggInn(T verdi)
    {
        Objects.requireNonNull(verdi, "Tabellen er null!");
        Node<T> p = new Node<>(verdi, hale, null);
        hale = tom() ? (hode = p) : (hale.neste = p);
        antall++;
        endringer++;
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi)
    {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");
        indeksKontroll(indeks, true);
        if (tom())                              // tom liste
        {
            hode = hale = new Node<>(verdi, null, null);
        }
        else if (indeks == 0)                   // ny verdi forrest
        {
            hode = hode.forrige = new Node<>(verdi, null, hode);
        }
        else if (indeks == antall)              // ny verdi bakerst
        {
            hale = hale.neste = new Node<>(verdi, hale, null);
        }
        else                                    // ny verdi på plass indeks
        {
            Node<T> p = finnNode(indeks);     // ny verdi skal til venstre for p
            p.forrige = p.forrige.neste = new Node<>(verdi, p.forrige, p);
        }
        antall++;            // ny verdi i listen
        endringer++;   // en endring i listen
    }

    @Override
    public T hent(int indeks)
    {
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    @Override
    public T oppdater(int indeks, T nyverdi)
    {
        Objects.requireNonNull(nyverdi, "null verdier kan ikke legges inn!");
        indeksKontroll(indeks, false);
        Node<T> n = finnNode(indeks);
        T gammelverdi = n.verdi;
        n.verdi = nyverdi;
        endringer++;
        return gammelverdi;
    }

    @Override
    public boolean fjern(T verdi)
    {
        if (verdi == null) return false;  // ingen nullverdier i listen
        for (Node<T> p = hode; p != null; p = p.neste)
        {
            if (p.verdi.equals(verdi))
            {
                fjernNode(p);   // bruker den private hjelpemetoden
                return true;
            }
        }
        return false;  // verdi ligger ikke i listen
    }

    @Override
    public T fjern(int indeks)
    {
        indeksKontroll(indeks, false);
        return fjernNode(finnNode(indeks)); // bruker de to hjelpemetodene
    }

    private T fjernNode(Node<T> p)  // private hjelpemetode
    {
        if (p == hode)
        {
            if (antall == 1) hode = hale = null;      // kun en verdi i listen
            else (hode = hode.neste).forrige = null;  // fjerner den første
        }
        else if (p == hale) (hale = hale.forrige).neste = null;  // fjerner den siste
        else (p.forrige.neste = p.neste).forrige = p.forrige;    // fjerner p
        antall--;     // en mindre i listen
        endringer++;  // en endring
        return p.verdi;
    }

    @Override
    public void nullstill()
    {
        Node<T> p = hode;
        while (p != null)
        {
            Node<T> q = p.neste;
            p.verdi = null;
            p.forrige = null;
            p.neste = null;
            p = q;
        }
        hode = hale = null;
        antall = 0;
        endringer++;
    }
    // Det viser seg at det er liten forskjell i effektivitet
    // mellom nullstill() slik den er koden ovenfor og slik
    // den er kodet under (som nullstill2)f

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (Node<T> i = hode; i != null; i = i.neste) {
            sb.append(String.valueOf(i.verdi)+ ",");
        }
        sb.append(']');
        return sb.toString();

    }

    public String omvendtString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (Node<T> i = hale; i != null; i = i.forrige){
            sb.append(String.valueOf(i.verdi)+ ",");
        }
        sb.append(']');
        return sb.toString();
    }

    // bruker en variant av utvalgssortering
    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c)
    {
        for (int n = liste.antall(); n > 0; n--)
        {
            Iterator<T> iterator = liste.iterator();
            int m = 0;
            T minverdi = iterator.next();
            for (int i = 1; i < n; i++)
            {
                T verdi = iterator.next();
                if (c.compare(verdi,minverdi) < 0)
                {
                    m = i; minverdi = verdi;
                }
            }
            liste.leggInn(liste.fjern(m));
        }
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

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator()
        {
            denne = hode;     // denne starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks)
        {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

        @Override
        public boolean hasNext()
        {
            return denne != null;  // denne koden skal ikke endres!
        }

        public T next()
        {
            if (!hasNext()) throw new NoSuchElementException("Ingen verdier!");
            if (endringer != iteratorendringer)
                throw new ConcurrentModificationException("Listen er endret!");
            T tempverdi = denne.verdi;
            denne = denne.neste;
            fjernOK = true;
            return tempverdi;
        }


        @Override
        public void remove()
        {
            if (!fjernOK) throw
                    new IllegalStateException("Kan ikke fjerne en verdi nå!");
            if (iteratorendringer != endringer) throw
                    new ConcurrentModificationException("Listen har blitt endret!");
            // kommer vi hit, må next ha blitt kalt og en node kan fjernes
            fjernOK = false;
            fjernNode(denne == null ? hale : denne.forrige);  //den private hjelpemetoden
            iteratorendringer++;  // en endring i iteratoren
        }

    } // DobbeltLenketListeIterator

    //// Oppgave 4 ////

    @Override
    public int indeksTil(T verdi)
    {
        if (verdi == null) return -1;
        Node<T> p = hode;
        for (int indeks = 0; indeks < antall; indeks++, p = p.neste)
        {
            if (p.verdi.equals(verdi)) return indeks;
        }
        return -1;
    }

    public boolean inneholder(T verdi) {

        return indeksTil(verdi) != -1;
    }

    //// Oppgave 5 /////

} // DobbeltLenketListe
