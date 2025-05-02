package co.edu.uniquindio.monedero_virtual.ownStructures;

public class Node<T> {
    private T data;
    private Node<T> nextNode;

    public Node(T data) {
        this.data = data;
        this.nextNode = null;
    }

    public T getData() {
        return data;
    }

    public Node<T> getNextNode() {
        return nextNode;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setNextNode(Node<T> nextNode) {
        this.nextNode = nextNode;
    }
}
