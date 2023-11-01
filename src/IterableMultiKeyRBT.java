// --== CS400 Fall 2023 File Header Information ==--
// Name: Kyle Poage
// Email: kpoage@wisc.edu
// Group: A30
// TA: Aydan Bailey
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.Stack;

/**
 * This class represents an IterableMultiKeyRBT which holds KeyLists storing multiple (of the same) elements in each
 * node
 * @param <T>
 */
public class IterableMultiKeyRBT<T extends Comparable<T>> extends BinarySearchTree<KeyListInterface<T>> implements IterableMultiKeySortedCollectionInterface<T>{

    private Comparable<T> startPoint; // Stores the startPoint of the IterableMultiKeyRBT
    private int numKeys; // Stores the number of keys

    /**
     * Inserts value into tree that can store multiple objects per key by keeping
     * lists of objects in each node of the tree.
     * @param key object to insert
     * @return true if a new node was inserted, false if the key was added into an existing node
     */
    @Override
    public boolean insertSingleKey(Comparable key) {
        // Initialize a new KeyList
        KeyList<T> keyList = new KeyList(key);

        // Create a node to represent the node found while searching the current tree to see if it has the key
        Node<KeyListInterface<T>> foundNode = this.findNode(keyList);
        // findNode() returns null if the tree doesn't have a key, so if it is null, we know it's not there
        if (foundNode!=null){
            // The key is a duplicate so we add a new key to the keyList and return false
            foundNode.data.addKey((T) key);
            this.numKeys++;
            return false;
        }
        // The key was not found in the tree so we add the new keyList, increment numKeys, and return true
        else{
            this.insert(keyList);
            this.numKeys++;
            return true;
        }
    }

    /**
     * @return the number of values in the tree.
     */
    @Override
    public int numKeys() {
        return numKeys;
    }

    /**
     * Returns an iterator that does an in-order iteration over the tree.
     */
    @Override
    public Iterator iterator() {
        // Get the start stack of the tree
        Stack startStack = this.getStartStack();

        // return an iterator as an anonymous class and implement its methods
        return new Iterator() {

            Node<KeyListInterface<T>> current = setCurrent(); // Set the current node

            KeyList<T> keyList = setKeyList(); // Set the keylist


            Iterator keyListIterator = setKeyListIterator(); // Set the keylist iterator

            Boolean iteratorIsEmpty = false; // Boolean representing wether or not we should check the iteerator


            /*
             This private class sets the current node to null if the stack size is zero and to the top of the stack
             if there is a stack
             */

            private Node<KeyListInterface<T>> setCurrent() {
                if (startStack.size()==0){
                    return null;
                }
                return (Node<KeyListInterface<T>>) startStack.pop();
            }

            /*
             This private class sets the keylist iterator to null if the keylist is null and to the keylist.iterator()
             if there is a keylist
             */
            private Iterator setKeyListIterator() {
                if (keyList == null){
                    return null;
                }
                return keyList.iterator();
            }

            /*
             This private class sets the keylist to null if the current node is null and to the current data in the node
             if there is a current node
             */
            private KeyList<T> setKeyList() {
                if (current == null){
                    return null;
                }
                return (KeyList<T>) current.data;
            }


            /**
             * This method returns true if there is another element in the tree to iterate through and false otherwise
             * @return True if the tree has another element to iterate through, false otherwise
             */
            @Override
            public boolean hasNext() {
                // Check if the stack is not empty or the keylist iterator is not null and the keylist iterator has
                // another element
                return ((!startStack.isEmpty()) || (keyListIterator != null && keyListIterator.hasNext()));
            }

            /**
             * This method returns the next item in the tree to iterate through and progresses the iteration one more
             * step
             * @return the object that is next in line to be iterated
             */
            @Override
            public Object next() {
                // Check if we need to check the iterator
                if (!iteratorIsEmpty){
                    // If so we check if iterator has a next value and return it
                    if (keyListIterator.hasNext()){
                        return keyListIterator.next();
                    }
                    // if it doesn't, then the iterator is empty
                    iteratorIsEmpty = true;
                }
                // First check if start stack is empty
                if (!startStack.isEmpty()){
                    // Then check if the iterator is empty
                    if (iteratorIsEmpty){
                        // If so we pop from the stack and get the smallest node from the right subtree
                        current = (Node<KeyListInterface<T>>) startStack.pop();
                        Node<KeyListInterface<T>> smallest = current.down[1];
                        while (smallest != null){
                            // Keep pushing these nodes on the path to the smallest keylist
                            startStack.push(smallest);
                            smallest = smallest.down[0];
                        }
                        // The iterator should not be empty now
                        iteratorIsEmpty = false;
                        // reset the keyListIterator to the current node iterator
                        keyListIterator = current.data.iterator();
                    }
                }
                // return the next element in the iterator
                return keyListIterator.next();
            }
        };
    }

    /**
     * This method returns the starting stack for the iteration of an Iterable RBT. This method pushes all nodes
     * greater than or equal to the startpoint along the path from the root to the startpoint into the stack
     * @return  The start stack
     */
    protected Stack getStartStack(){
        // Initialize the stack to return
        Stack stack = new Stack();
        Node<KeyListInterface<T>> current = this.root; // Current node is the root
        // If startPoint is null, return all the nodes along the path from the root to the smallest key
        if (startPoint==null) {
            // Return all nodes along the left subtree
            while(current!=null){
                stack.push(current);
                current = current.down[0];
            }
            return stack;
        }
        // Otherwise, we look for the start point and return nodes greater than or equal to the start point
        else {
            KeyList keylist = new KeyList(startPoint); // Make keylist to compare to each element
            // While not null, keep searching
            while (current!=null){
                // Search right if the current node is less than keylist
                if (current.data.compareTo(keylist)<0){
                    current = current.down[1];
                }
                // Search left if the current node is greater than keylist
                else if (current.data.compareTo(keylist)>0){
                    stack.push(current);
                    current = current.down[0];
                }
                // If equal, make current null so the loop breaks
                else{
                    stack.push(current);
                    current = null;
                }
            }
        }
        return stack;
    }

    /**
     * Sets the starting point for iterations. Future iterations will start at the
     * starting point or the key closest to it in the tree. This setting is remembered
     * until it is reset. Passing in null disables the starting point.
     * @param startPoint the start point to set for iterations
     */
    @Override
    public void setIterationStartPoint(Comparable startPoint) {
        this.startPoint = startPoint;
    }

    @Override
    public void clear(){
        this.clear();
        this.numKeys = 0;
    }

    /**
     * This method tests the functionality of insertSingleKey() and numKeys()
     */
    @Test
    public void testInsertSingleKeyAndNumKeys(){
        // Create new Iterable multikey RBT
        IterableMultiKeyRBT<Integer> tree = new IterableMultiKeyRBT<>();
        // Insert 8, 3, 10 to the tree
        tree.insertSingleKey(8);
        tree.insertSingleKey(3);
        Assertions.assertTrue(tree.insertSingleKey(10)); // Inserting 10 should return true
        Assertions.assertFalse(tree.insertSingleKey(8)); // Inserting another 8 should return false
        Assertions.assertEquals(tree.numKeys(), 4); // There should be 4 keys in the tree
    }

    /**
     * This method tests the functionality of iterator()
     */
    @Test
    public void testIterator(){
        // Create new Iterable multikey RBT
        IterableMultiKeyRBT<Integer> tree = new IterableMultiKeyRBT<>();

        // Insert 8, 3, 10, 10, 4, and 2 to the tree
        tree.insertSingleKey(8);
        tree.insertSingleKey(3);
        tree.insertSingleKey(10);
        tree.insertSingleKey(10);
        tree.insertSingleKey(4);
        tree.insertSingleKey(2);

        // Create a new iterator
        Iterator iterator = tree.iterator();

        String output = "";

        // Check the output of the iterator
        while (iterator.hasNext()){
            output+=iterator.next()+", ";
        }
        // The iterator should return an in order traversal of the tree
        String expected = "2, 3, 4, 8, 10, 10, ";
        Assertions.assertEquals(expected, output);
    }

    /**
     * This method tests the functionality of setIterationStartPoint()
     */
    @Test
    public void testSetIterationStartPoint(){
        // Create new Iterable multikey RBT
        IterableMultiKeyRBT<Integer> tree = new IterableMultiKeyRBT<>();

        // Insert 8, 3, 10, 10, 4, 2, 20, and 9 to the tree
        tree.insertSingleKey(8);
        tree.insertSingleKey(3);
        tree.insertSingleKey(10);
        tree.insertSingleKey(10);
        tree.insertSingleKey(4);
        tree.insertSingleKey(2);
        tree.insertSingleKey(20);
        tree.insertSingleKey(9);

        // Set the iteration start point to 8
        tree.setIterationStartPoint(9);

        // Create a new iterator
        Iterator iterator = tree.iterator();


        String output = "";

        // Check the iterator output
        while (iterator.hasNext()){
            output+=iterator.next()+", ";
        }

        // The iterator should return an in order traversal after the key with integer 8
        String expected = "9, 10, 10, 20, ";
        Assertions.assertEquals(expected, output);
    }
}
