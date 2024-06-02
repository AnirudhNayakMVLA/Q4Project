import java.io.Serializable;

public class Queue<E> implements Serializable{
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

    public void shuffle(){
        Node<E> current = head;
        Node<E> temp;
        int rand;
        for(int i = 0; i < size; i++){
            rand = (int)(Math.random() * size);
            temp = current;
            for(int j = 0; j < rand; j++){
                temp = temp.next();
            }
            E data = current.get();
            current.setData(temp.get());
            temp.setData(data);
            current = current.next();
        }
    }
}