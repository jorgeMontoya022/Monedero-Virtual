package co.edu.uniquindio.monedero_virtual.ownStructures.ownQueues;
import java.util.NoSuchElementException;

public class ownQueue<T> {

    private Node<T> front;  // Apunta al primer nodo de la cola (frente)
    private Node<T> rear;   // Apunta al último nodo de la cola (final)
    private int size;       // Tamaño de la cola

    public ownQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    /**
     * Agrega un elemento al final de la cola.
     * @param data El dato a agregar en la cola.
     * @throws IllegalArgumentException Si el dato es nulo.
     */
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        Node<T> newNode = new Node<>(data);
        if (rear == null) {
            front = rear = newNode;  // Si la cola está vacía, el nuevo nodo es el frente y el final
        } else {
            rear.setNextNode(newNode);  // Agrega el nuevo nodo al final
            rear = newNode;  // El nuevo nodo es ahora el final
        }
        size++;  // Incrementa el tamaño
    }

    /**
     * Elimina y devuelve el primer elemento de la cola.
     * @return El elemento eliminado.
     * @throws NoSuchElementException Si la cola está vacía.
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty");
        }
        T data = front.getData();  // Obtiene el dato del primer nodo
        front = front.getNextNode();  // El siguiente nodo pasa a ser el nuevo frente
        if (front == null) {
            rear = null;  // Si la cola queda vacía, rear debe ser null
        }
        size--;  // Decrementa el tamaño
        return data;
    }

    /**
     * Devuelve el primer elemento de la cola sin eliminarlo.
     * @return El primer elemento de la cola.
     * @throws NoSuchElementException Si la cola está vacía.
     */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty");
        }
        return front.getData();  // Devuelve el dato del frente sin eliminarlo
    }

    /**
     * Verifica si la cola está vacía.
     * @return true si la cola está vacía, false de lo contrario.
     */
    public boolean isEmpty() {
        return front == null;  // Si el frente es null, la cola está vacía
    }

    /**
     * Devuelve el tamaño actual de la cola.
     * @return El tamaño de la cola.
     */
    public int getSize() {
        return size;
    }

    /**
     * Elimina todos los elementos de la cola.
     */
    public void clear() {
        front = rear = null;  // Elimina la referencia a los nodos
        size = 0;  // Restablece el tamaño a 0
    }

    /**
     * Muestra los elementos de la cola.
     * @throws NoSuchElementException Si la cola está vacía.
     */
    public void showQueue() {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty");
        }
        Node<T> current = front;
        while (current != null) {
            System.out.println(current.getData());  // Muestra el dato de cada nodo
            current = current.getNextNode();  // Avanza al siguiente nodo
        }
    }

    // Clase interna para representar los nodos de la cola
    public static class Node<T> {
        private T data;
        private Node<T> nextNode;

        public Node(T data) {
            this.data = data;
            this.nextNode = null;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node<T> getNextNode() {
            return nextNode;
        }

        public void setNextNode(Node<T> nextNode) {
            this.nextNode = nextNode;
        }
    }
}
