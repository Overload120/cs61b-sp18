public class ArrayDeque <T>{
    private T[] items;
    private double usage;
    private int first;
    private int last;
    private int size;

    /** Creates an ArrayDeque. */
    public ArrayDeque(){
        items= (T[]) new Object[8];
        usage=0;
        first=0;
        last=0;
        size=0;
    }
    /** Resizes the deque. */
    private void resizing(int Space){
        T[] a= (T[]) new Object[Space];
        if(last<first) {
            System.arraycopy(items, first, a, 0, items.length - first);
            System.arraycopy(items, 0, a, items.length - first, last + 1);
        }
        else if(size!=1){
            System.arraycopy(items,first,a,0,size);
        }
        else{
            a[0]=items[first];
        }
        first=0;
        last=size-1;
        items=a;
        usage=(double)size/items.length;
    }
    private void checkUsage(){
        if(size==items.length){
            resizing(size*2);
        }
        if(size>=16 && usage<0.25){
            resizing(size/2);
        }
    }
    public void addFirst(T item) {
        checkUsage();
        if(isEmpty()){
            items[first]=item;
            size++;
            usage=(double)size/items.length;
            return;
        }
        if (first == 0) {
            first = items.length-1;
        }
        else {
            first--;
        }
        items[first]=item;
        size++;
        usage=(double)size/items.length;
    }
    public void addLast(T item){
        checkUsage();
        if(isEmpty()){
            items[last]=item;
            size++;
            usage=(double)size/items.length;
            return;
        }
        last=(last+1)%items.length;
        items[last]=item;
        size++;
        usage=(double)size/items.length;
    }
    public boolean isEmpty(){
        return size==0;
    }
    public int size(){
        return size;
    }
    public void printDeque(){
        if(last<first){
            for(int i=first;i<items.length;++i){
                System.out.println(items[i]);
            }
            for(int i=0;i<=last;++i){
                System.out.println(items[i]);
            }
        }
        if(last>=first && !isEmpty()){
            for(int i=first;i<=last;++i){
                System.out.println(items[i]);

            }
        }
    }
    public T removeFirst(){
        if(isEmpty()){
            return null;
        }
        T a=items[first];
        if(size==1){
            size--;
            usage=(double)size/items.length;
            return a;
        }
        first=(first+1)%items.length;
        size--;
        usage=(double)size/items.length;
        checkUsage();
        return a;
    }
    public T removeLast(){
        if(isEmpty()){
            return null;
        }
        T a=items[last];
        if(size==1){
            size--;
            usage=(double)size/items.length;
            return a;
        }
        if(last==0){
            last=items.length-1;
        }
        else{
            last--;
        }
        size--;
        usage=(double)size/items.length;
        checkUsage();
        return a;
    }
    public T get(int index){
        if(index>=size){
            return null;
        }
        return items[(first+index)%items.length];
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> a =new ArrayDeque<>();
        a.addLast(0);
        a.addLast(1);
        a.addLast(2);
        a.size();
        a.addFirst(4);
        a.addFirst(5);
        a.addFirst(6);
        a.addFirst(7);
        a.addFirst(8);
        a.addLast(9);
    }
}



