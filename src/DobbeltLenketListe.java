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
        throw new UnsupportedOperationException("Tabellen a er null!");
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

    // subliste
    public Liste<T> subliste(int fra, int til)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
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
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public void leggInn(int indeks, T verdi)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    /*@Override
    public boolean inneholder(T verdi)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }*/

    @Override
    public T hent(int indeks)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    /*@Override
    public int indeksTil(T verdi)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }*/

    @Override
    public T oppdater(int indeks, T nyverdi)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public boolean fjern(T verdi)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public T fjern(int indeks)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public void nullstill()
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public String toString()
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    public String omvendtString()
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public Iterator<T> iterator()
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    public Iterator<T> iterator(int indeks)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
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

        @Override
        public T next()
        {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

    } // DobbeltLenketListeIterator

    //// Oppgave 4 ////

    public int indeksTil(T verdi) {

        if (verdi == null)  return -1;

        Node<T> a = hode;

        for (int indeks = 0; indeks < antall; indeks++, a = a.neste) {
            if (IKKE FERDIG) return indeks;
        } // a.verdi.equals(verdi) istedenfor IKKE FERDIG?

        return -1;
    }


    public boolean inneholder(T verdi) {

        if indeksTil(verdi) // what? will try something else
        return indeksTil(verdi) != -1;
    }

    //// Oppgave 5 /////

} // DobbeltLenketListe
