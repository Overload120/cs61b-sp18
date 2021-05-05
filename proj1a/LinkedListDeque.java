
public class LinkedListDeque<T> {
    /** The nested class inside the deque. */
    private class Node{
        private T item;
        private Node prev;
        private Node next;
        /** Constructs the node class with parameters. */
        private Node(T a, Node b, Node c){
            item=a;
            prev=b;
            next=c;
        }
        /** Constructs the node class without parameters.(especially for sentinel) */
        private Node(){
            prev=next=null;
        }
    }
    /** Indicates the size of the deque. */
    private int size=0;
    private Node sentinel=new Node();
    /** Constructs the LinkedListDeque class without parameters. */
    public LinkedListDeque(){
        sentinel.next=sentinel;
        sentinel.prev=sentinel;
        }

    /** Adds an element of class T to the front of the deque. */
    public void addFirst(T a){
        Node p=sentinel.next;
        sentinel.next=new Node(a,sentinel,p);
        p.prev=sentinel.next;
        ++size;
    }
    /** Adds an element of class T to the back of the deque. */
    public void addLast(T a){
        Node p=sentinel.prev;
        sentinel.prev=new Node(a,p,sentinel);
        p.next=sentinel.prev;
        ++size;
    }
    /** Indicates the state of the deque(empty or not empty). */
    public boolean isEmpty(){
        return size==0;
    }
    /** Tells the current size of the deque. */
    public int size(){
        return size;
    }
    /** Prints all of the deque's elements in order. */
    public void printDeque(){
        Node p=sentinel.next;
        while (p!=sentinel){
            System.out.println(p.item);
            p=p.next;
        }
    }
    /** Removes the first element in the deque. */
    public T removeFirst(){
        if(isEmpty()){
            return null;
        }
        T a=sentinel.next.item;
        sentinel.next.next.prev=sentinel;
        sentinel.next=sentinel.next.next;
        --size;
        return a;
    }
    /** Removes the last element in the deque. */
    public T removeLast(){
        if(isEmpty()){
            return null;
        }
        T a=sentinel.prev.item;
        sentinel.prev.prev.next=sentinel;
        sentinel.prev=sentinel.prev.prev;
        --size;
        return a;
    }
    /** Gets the specific element(namely, number 'index') in the deque. */
    public T get(int index){
        if(index>=size){
            return null;
        }
        Node p=sentinel.next;
        for(int i=0;i<index;++i){
            p=p.next;
        }
        return p.item;
    }
    /** The helper method for 'public T getRecursive(int index)'. */
    private Node getRecursive(Node p,int index){
        if(index==0){
            return p;
        }
        return getRecursive(p.next,index-1);
    }
    /** Gets the specific element(namely, number 'index') in the deque using recursion. */
    public T getRecursive(int index){
        if(index>=size){
            return null;
        }
        return getRecursive(sentinel.next,index).item;
    }
}
