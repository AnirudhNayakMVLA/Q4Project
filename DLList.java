import java.util.Iterator;

public class DLList<E> implements Iterable<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public DLList() {
        head = new Node<E>(null);
        tail = new Node<E>(null);
        head.setNext(tail);
        tail.setPrev(head);
        size = 0;
    }

    public void clear() {
        head.setNext(tail);
        tail.setPrev(head);
        size = 0;
    }

    private Node<E> getNode(int n) {
        if (n < 0 || n >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> current;
        if (n < size / 2) {
            current = head.next();
            for (int i = 0; i < n; i++) {
                current = current.next();
            }
        } else {
            current = tail.prev();
            for (int i = size - 1; i > n; i--) {
                current = current.prev();
            }
        }
        return current;
    }

    public boolean add(E e) {
        add(size, e);
        return true;
    }

    public void add(int n, E e) {
        Node<E> current = getNode(n);
        Node<E> newNode = new Node<E>(e);
        newNode.setNext(current);
        newNode.setPrev(current.prev());
        current.prev().setNext(newNode);
        current.setPrev(newNode);
        size++;
    }

    public E get(int n) {
        Node<E> current = getNode(n);
        return current.get();
    }

    public E remove(int n) {
        Node<E> current = getNode(n);
        current.prev().setNext(current.next());
        current.next().setPrev(current.prev());
        size--;
        return current.get();
    }

    public E remove(E e) {
        Node<E> current = head.next();
        while (current != tail) {
            if (current.get().equals(e)) {
                current.prev().setNext(current.next());
                current.next().setPrev(current.prev());
                size--;
                return current.get();
            }
            current = current.next();
        }
        return null;
    }

    public void set(int n, E e) {
        Node<E> current = getNode(n);
        current.setData(e);
    }

    public int size() {
        return size;
    }

    public String toString() {
        String comp = "";
        Node<E> current = head;
        for (int i = 0; i < size + 2; i++) {
            if (current == head) {
                comp += "[";
            } else if (current == tail) {
                comp += "]";
            } else if (current.next() == tail) {
                comp += current.get();
            } else {
                comp += current.get() + ", ";
            }
            current = current.next();
        }
        return comp;
    }

    public Iterator<E> iterator() {
        return new DLListIterator();
    }

    private class DLListIterator implements Iterator<E> {
        private Node<E> current = head.next();

        public boolean hasNext() {
            return current != tail;
        }

        public E next() {
            E data = current.get();
            current = current.next();
            return data;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
