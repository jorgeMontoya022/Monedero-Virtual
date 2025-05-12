package co.edu.uniquindio.monedero_virtual.ownStructures.ownTrees;

public class OwnTreeAVL<T extends Comparable<T>>{
    private Nodo<T> raiz;

    public boolean estaVacio() {
        return raiz == null;
    }

    public void insertar(T dato) throws Exception {
        raiz = insertarRec(raiz, dato);
    }

    private Nodo<T> insertarRec(Nodo<T> nodo, T dato) throws Exception {
        if (nodo == null) {
            return new Nodo<>(dato);
        }

        int cmp = dato.compareTo(nodo.getDato());
        if (cmp < 0) {
            nodo.setHijoIzquierdo(insertarRec(nodo.getHijoIzquierdo(), dato));
        } else if (cmp > 0) {
            nodo.setHijoDerecho(insertarRec(nodo.getHijoDerecho(), dato));
        } else {
            throw new Exception("No se permiten duplicados.");
        }

        actualizarAltura(nodo);
        return balancear(nodo);
    }

    private void actualizarAltura(Nodo<T> nodo) {
        int izquierda = altura(nodo.getHijoIzquierdo());
        int derecha = altura(nodo.getHijoDerecho());
        nodo.setAltura(1 + Math.max(izquierda, derecha));
    }

    private int altura(Nodo<T> nodo) {
        return (nodo == null) ? 0 : nodo.getAltura();
    }

    private int getBalance(Nodo<T> nodo) {
        return (nodo == null) ? 0 : altura(nodo.getHijoIzquierdo()) - altura(nodo.getHijoDerecho());
    }

    private Nodo<T> balancear(Nodo<T> nodo) {
        int balance = getBalance(nodo);

        // Rotación izquierda
        if (balance < -1 && getBalance(nodo.getHijoDerecho()) <= 0) {
            return rotacionIzquierda(nodo);
        }

        // Rotación derecha
        if (balance > 1 && getBalance(nodo.getHijoIzquierdo()) >= 0) {
            return rotacionDerecha(nodo);
        }

        // Rotación derecha-izquierda
        if (balance < -1 && getBalance(nodo.getHijoDerecho()) > 0) {
            nodo.setHijoDerecho(rotacionDerecha(nodo.getHijoDerecho()));
            return rotacionIzquierda(nodo);
        }

        // Rotación izquierda-derecha
        if (balance > 1 && getBalance(nodo.getHijoIzquierdo()) < 0) {
            nodo.setHijoIzquierdo(rotacionIzquierda(nodo.getHijoIzquierdo()));
            return rotacionDerecha(nodo);
        }

        return nodo;
    }

    private Nodo<T> rotacionDerecha(Nodo<T> y) {
        Nodo<T> x = y.getHijoIzquierdo();
        Nodo<T> T2 = x.getHijoDerecho();

        x.setHijoDerecho(y);
        y.setHijoIzquierdo(T2);

        actualizarAltura(y);
        actualizarAltura(x);

        return x;
    }

    private Nodo<T> rotacionIzquierda(Nodo<T> x) {
        Nodo<T> y = x.getHijoDerecho();
        Nodo<T> T2 = y.getHijoIzquierdo();

        y.setHijoIzquierdo(x);
        x.setHijoDerecho(T2);

        actualizarAltura(x);
        actualizarAltura(y);

        return y;
    }
    
}
