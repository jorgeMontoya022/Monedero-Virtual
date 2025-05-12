package co.edu.uniquindio.monedero_virtual.ownStructures.ownTrees;

public class Nodo<T extends Comparable<T>> {
    
    private T dato;
    private Nodo<T> hijoIzquierdo;
    private Nodo<T> hijoDerecho;
    private int altura;

    public Nodo(T dato) {
        this.dato = dato;
        this.altura = 1;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public Nodo<T> getHijoIzquierdo() {
        return hijoIzquierdo;
    }

    public void setHijoIzquierdo(Nodo<T> hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }

    public Nodo<T> getHijoDerecho() {
        return hijoDerecho;
    }

    public void setHijoDerecho(Nodo<T> hijoDerecho) {
        this.hijoDerecho = hijoDerecho;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
}
