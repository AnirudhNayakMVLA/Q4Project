public class Node<E>{
    private Node<E> prev, next;
    private E data;

    public Node(E data){
        this.data = data;
    }

    public Node<E> prev() {
        return prev;
    }

    public Node<E> setPrev(Node<E> prev) {
        this.prev = prev;
        return this;
    }

    public Node<E> next() {
        return next;
    }

    public Node<E> setNext(Node<E> next) {
        this.next = next;
        return this;
    }

    public E get() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
    
}
