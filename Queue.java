public class Queue<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;
    public Queue(){
        head = null;
        tail = null;
        size = 0;
    }
    public void add(E data){
        Node<E> newNode = new Node<E>(data);
        if(size == 0){
            head = newNode;
            tail = newNode;
        }else{
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }
    
    public E pop(){
        if(size == 0){
            return null;
        }
        E data = head.get();
        head = head.next();
        size--;
        return data;
    }

    public E peek(){
        if(size == 0){
            return null;
        }
        return head.get();
    }

    public boolean isEmpty(){
        return size == 0;
    }
    public int size(){
        return size;
    }
}