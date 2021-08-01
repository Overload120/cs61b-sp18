package synthesizer;
// TODO: Make sure to make this class a part of the synthesizer package
// package <package name>;

import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T>  extends AbstractBoundedQueue<T>{
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        first=0;
        last=0;
        fillCount=0;
        this.capacity=capacity;
        rb=(T[]) new Object[capacity];
    }

    public class ArrayIterator implements Iterator<T>{
        int currentPos;
        public ArrayIterator(){
            currentPos=first-1;
        }
        @Override
        public boolean hasNext() {

            if(currentPos+1==capacity){
                return !(last==0);
            }
            return !(last==currentPos+1);
        }

        @Override
        public T next() {
            if(currentPos+1==capacity){
                currentPos=0;
            }
            else currentPos+=1;
            return rb[currentPos];
        }
    }
    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     * @param x
     */
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        if(fillCount==capacity){
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last]=x;
        last+=1;
        fillCount+=1;
        if(last==capacity){
            last=0;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update
        if(fillCount==0){
            throw new RuntimeException("Ring buffer underflow");
        }
        fillCount-=1;
        T x=rb[first];
        first+=1;
        if(first==capacity){
            first=0;
        }
        return x;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        if(fillCount==0){
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.
}
