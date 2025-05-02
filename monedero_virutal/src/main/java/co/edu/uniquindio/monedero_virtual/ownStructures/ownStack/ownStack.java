package co.edu.uniquindio.monedero_virtual.ownStructures.ownStack;

import java.util.EmptyStackException;

import co.edu.uniquindio.monedero_virtual.ownStructures.Node;

public class ownStack<T> {

    private Node<T> top;  // Apunta al elemento superior de la pila
    private int size;     // Almacena el tamaño de la pila

    public ownStack() {
        this.top = null;  // Inicialmente la pila está vacía
        this.size = 0;    // El tamaño inicial es 0
    }

    /**
     * Agrega un nuevo elemento al tope de la pila.
     * @param data El dato a agregar en la pila.
     * @throws IllegalArgumentException Si el dato es nulo.
     */
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        Node<T> newNode = new Node<>(data);
        newNode.setNextNode(top); // El siguiente nodo de "newNode" es el nodo superior actual
        top = newNode;  // "newNode" pasa a ser el nodo superior
        size++;         // Incrementamos el tamaño
    }

    /**
     * Elimina y devuelve el elemento en el tope de la pila.
     * @return El elemento eliminado de la pila.
     * @throws EmptyStackException Si la pila está vacía.
     */
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();  // Lanza excepción si la pila está vacía
        }
        T data = top.getData();  // Almacenamos el dato a devolver
        top = top.getNextNode(); // Movemos el puntero del tope al siguiente nodo
        size--;  // Reducimos el tamaño
        return data;
    }

    /**
     * Devuelve el elemento en el tope de la pila sin eliminarlo.
     * @return El elemento en el tope de la pila.
     * @throws EmptyStackException Si la pila está vacía.
     */
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();  // Lanza excepción si la pila está vacía
        }
        return top.getData();  // Devuelve el dato del nodo superior
    }

    /**
     * Verifica si la pila está vacía.
     * @return true si la pila está vacía, false de lo contrario.
     */
    public boolean isEmpty() {
        return top == null;  // Si el nodo superior es null, la pila está vacía
    }

    /**
     * Devuelve el tamaño actual de la pila.
     * @return El tamaño de la pila.
     */
    public int getSize() {
        return size;  // Devuelve el tamaño de la pila
    }

    /**
     * Elimina todos los elementos de la pila.
     */
    public void clear() {
        top = null;  // Vacía la pila
        size = 0;    // Restablece el tamaño a 0
    }

    /**
     * Devuelve el nodo superior de la pila.
     * @return El nodo superior de la pila.
     * @throws EmptyStackException Si la pila está vacía.
     */
    public Node<T> getTop() {
        if (isEmpty()) {
            throw new EmptyStackException();  // Lanza excepción si la pila está vacía
        }
        return top;  // Devuelve el nodo superior
    }

    /**
     * Verifica si un elemento está presente en la pila.
     * @param data El valor a buscar.
     * @return true si el elemento está en la pila, false si no.
     * @throws IllegalArgumentException Si el dato es nulo.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        Node<T> current = top;
        while (current != null) {
            if (current.getData().equals(data)) {
                return true;  // El dato está presente en la pila
            }
            current = current.getNextNode();  // Avanzamos al siguiente nodo
        }
        return false;  // El dato no está presente
    }

    /**
     * Muestra todos los elementos de la pila.
     * @throws EmptyStackException Si la pila está vacía.
     */
    public void showStack() {
        if (isEmpty()) {
            throw new EmptyStackException();  // Lanza excepción si la pila está vacía
        }
        Node<T> current = top;
        while (current != null) {
            System.out.println(current.getData());  // Muestra el dato de cada nodo
            current = current.getNextNode();  // Avanzamos al siguiente nodo
        }
    }

   
}
