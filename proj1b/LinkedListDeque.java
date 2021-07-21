public class LinkedListDeque<Item> implements Deque<Item>{
    /** The nested class inside the deque. */
    private class Node{
        private Item item;
        private Node prev;
        private Node next;
        /** Constructs the node class with parameters. */
        private Node(Item a, Node b, Node c){
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
    @Override
    public void addFirst(Item a){
        Node p=sentinel.next;
        sentinel.next=new Node(a,sentinel,p);
        p.prev=sentinel.next;
        ++size;
    }
    /** Adds an element of class T to the back of the deque. */
    @Override
    public void addLast(Item a){
        Node p=sentinel.prev;
        sentinel.prev=new Node(a,p,sentinel);
        p.next=sentinel.prev;
        ++size;
    }
    /** Indicates the state of the deque(empty or not empty). */
    @Override
    public boolean isEmpty(){
        return size==0;
    }
    /** Tells the current size of the deque. */
    @Override
    public int size(){
        return size;
    }
    /** Prints all of the deque's elements in order. */
    @Override
    public void printDeque(){
        Node p=sentinel.next;
        while (p!=sentinel){
            System.out.println(p.item);
            p=p.next;
        }
    }
    /** Removes the first element in the deque. */
    @Override
    public Item removeFirst(){
        if(isEmpty()){
            return null;
        }
        Item a=sentinel.next.item;
        sentinel.next.next.prev=sentinel;
        sentinel.next=sentinel.next.next;
        --size;
        return a;
    }
    /** Removes the last element in the deque. */
    @Override
    public Item removeLast(){
        if(isEmpty()){
            return null;
        }
        Item a=sentinel.prev.item;
        sentinel.prev.prev.next=sentinel;
        sentinel.prev=sentinel.prev.prev;
        --size;
        return a;
    }
    /** Gets the specific element(namely, number 'index') in the deque. */
    @Override
    public Item get(int index){
        if(index>=size){
            return null;
        }
        Node p=sentinel.next;
        for(int i=0;i<index;++i){
            p=p.next;
        }
        return p.item;
    }
    /** The helper method for 'public Item getRecursive(int index)'. */
    private Node getRecursive(Node p,int index){
        if(index==0){
            return p;
        }
        return getRecursive(p.next,index-1);
    }
    /** Gets the specific element(namely, number 'index') in the deque using recursion. */
    @Override
    public Item getRecursive(int index){
        if(index>=size){
            return null;
        }
        return getRecursive(sentinel.next,index).item;
    }
}

