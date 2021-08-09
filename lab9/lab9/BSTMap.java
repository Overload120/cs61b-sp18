package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }
    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p==null){
            return null;
        }
        if (key.compareTo(p.key)==0){
            return p.value;
        }
        else if(key.compareTo(p.key)<0){
            return getHelper(key,p.left);
        }
        else return getHelper(key,p.right);
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key,root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if(p==null){
            size+=1;
            p=new Node(key,value);
        }
        if(key.compareTo(p.key)==0){
            p.value=value;
        }
        if(key.compareTo(p.key)<0){
            p.left=putHelper(key,value,p.left);
        }
        if(key.compareTo(p.key)>0){
            p.right=putHelper(key, value, p.right);
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root=putHelper(key,value,root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Iterator<K> m =iterator();
        HashSet<K> n=new HashSet<>();
        while (!m.hasNext()){
            n.add(m.next());
        }
        return n;
    }


    /** Returns the specific node for the key in the tree rooted in p. */
    private Node getNode(K key, Node p){
        if(p==null){
            return null;
        }
        if(key.compareTo(p.key)==0){
            return p;
        }
        if(key.compareTo(p.key)<0){
            return getNode(key,p.left);
        }
        else return getNode(key,p.right);
    }
    private Node getParent(K key, Node p){
        if(p==null||(p.left==null&&p.right==null)){
            return null;
        }
        if((p.left!=null&&key.compareTo(p.left.key)==0)||(p.right!=null&&key.compareTo(p.right.key)==0)){
            return p;
        }
        if(key.compareTo(p.key)<0){
            return getParent(key,p.left);
        }
        else return getParent(key,p.right);
    }
    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    private V removeNodeWithOneChild(Node p){
        Node parent=getParent(p.key,root);
        V value=p.value;
        if(parent==null){
            if(p.left==null){
                root=root.right;
                return value;
            }
            root=root.left;
            return value;
        }
        if(p.left==null){
            if(parent.left.key.compareTo(p.key)==0){
                parent.left=p.right;
            }
            if(parent.right.key.compareTo(p.key)==0){
                parent.right=p.right;
            }
            return value;
        }
        else{
            if(parent.left.key.compareTo(p.key)==0){
                parent.left=p.left;
            }
            if(parent.right.key.compareTo(p.key)==0){
                parent.right=p.left;
            }
            return value;
        }
    }
    private Node getBiggestLeft(Node p){
        if(p==null){
            return null;
        }
        if(p.right==null){
            return p;
        }
        Node left=p.left;
        return getBiggestLeft(p.right);
    }
    @Override
    public V remove(K key) {
        Node p=getNode(key,root);
        Node parent=getParent(key,root);
        if(p==null){
            return null;
        }
        size-=1;
        V value=p.value;
        if(p.left==null&&p.right==null){
            if(parent==null){
                root = null;
                return value;
            }
            if(parent.left!=null&&parent.left.key.compareTo(key)==0){
                parent.left=null;
            }
            parent.right=null;
            return value;
        }
        if(p.left==null||p.right==null){
            return removeNodeWithOneChild(p);
        }
        Node BL=getBiggestLeft(p);
        K keyBL=BL.key;
        V valueBL=remove(keyBL);
        p.key=keyBL;
        p.value=valueBL;
        return value;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        Node p=getNode(key,root);
        if(p.value!=value){
            return null;
        }
        return remove(key);
    }



    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
