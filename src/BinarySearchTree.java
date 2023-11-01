// --== CS400 Fall 2023 File Header Information ==--
// Name: Kyle Poage
// Email: kpoage@wisc.edu
// Group: A30
// TA: Aydan Bailey
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import java.util.LinkedList;
import java.util.Stack;


/**
 * Binary Search Tree implementation with a Node inner class for representing
 * the nodes of the tree. We will turn this Binary Search Tree into a self-balancing
 * tree as part of project 1 by modifying its insert functionality.
 * In week 0 of project 1, we will start this process by implementing tree rotations.
 */
public class BinarySearchTree<T extends Comparable<T>> implements SortedCollectionInterface<T> {

    /**
     * This class represents a node holding a single value within a binary tree.
     */
    protected static class Node<T> {
        public T data;

        // up stores a reference to the node's parent
        public Node<T> up;
        // The down array stores references to the node's children:
        // - down[0] is the left child reference of the node,
        // - down[1] is the right child reference of the node.
        // The @SupressWarning("unchecked") annotation is use to supress an unchecked
        // cast warning. Java only allows us to instantiate arrays without generic
        // type parameters, so we use this cast here to avoid future casts of the
        // node type's data field.
        @SuppressWarnings("unchecked")
        public Node<T>[] down = (Node<T>[])new Node[2];
        public Node(T data) { this.data = data; }

        /**
         * @return true when this node has a parent and is the right child of
         * that parent, otherwise return false
         */
        public boolean isRightChild() {
            return this.up != null && this.up.down[1] == this;
        }

    }

    protected Node<T> root; // reference to root node of tree, null when empty
    protected int size = 0; // the number of values in the tree

    /**
     * Inserts a new data value into the tree.
     * This tree will not hold null references, nor duplicate data values.
     * @param data to be added into this binary search tree
     * @return true if the value was inserted, false if is was in the tree already
     * @throws NullPointerException when the provided data argument is null
     */
    public boolean insert(T data) throws NullPointerException {
        if (data == null)
            throw new NullPointerException("Cannot insert data value null into the tree.");
        return this.insertHelper(new Node<>(data));
    }

    /**
     * Performs a naive insertion into a binary search tree: adding the new node
     * in a leaf position within the tree. After this insertion, no attempt is made
     * to restructure or balance the tree.
     * @param newNode the new node to be inserted
     * @return true if the value was inserted, false if is was in the tree already
     * @throws NullPointerException when the provided node is null
     */
    protected boolean insertHelper(Node<T> newNode) throws NullPointerException {
        if(newNode == null) throw new NullPointerException("new node cannot be null");

        if (this.root == null) {
            // add first node to an empty tree
            root = newNode;
            size++;
            return true;
        } else {
            // insert into subtree
            Node<T> current = this.root;
            while (true) {
                int compare = newNode.data.compareTo(current.data);
                if (compare == 0) {
                    return false;
                } else if (compare < 0) {
                    // insert in left subtree
                    if (current.down[0] == null) {
                        // empty space to insert into
                        current.down[0] = newNode;
                        newNode.up = current;
                        this.size++;
                        return true;
                    } else {
                        // no empty space, keep moving down the tree
                        current = current.down[0];
                    }
                } else {
                    // insert in right subtree
                    if (current.down[1] == null) {
                        // empty space to insert into
                        current.down[1] = newNode;
                        newNode.up = current;
                        this.size++;
                        return true;
                    } else {
                        // no empty space, keep moving down the tree
                        current = current.down[1];
                    }
                }
            }
        }
    }

    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a left child of the provided parent, this
     * method will perform a right rotation. When the provided child is a
     * right child of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this method
     * will throw an IllegalArgumentException.
     * @param child is the node being rotated from child to parent position
     *      (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *      (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *      node references are not initially (pre-rotation) related that way
     */
    protected void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {

        // Check if either child or parent are null
        if (parent == null || child == null){
            throw new IllegalArgumentException("Child and parent nodes cannot be null");
        }

        // Check if parent and child nodes are initially related that way
        if (parent.down[0] != child && parent.down[1] != child) {
            throw new IllegalArgumentException("child and parent nodes must be related that way");
        }
        // Left rotation
        if (child.isRightChild()) {
            // If the child does not have a left child, the parent node's right child will be null
            if (child.down[0]==null){
                parent.down[1] = null;
            }
            // The parent is the root node
            if (parent.up == null) {
                // If the right child's left child is not null, must reassign it to the parent node's right child
                if (child.down[0] != null) {
                    parent.down[1] = child.down[0];
                    child.down[0].up = parent;
                }
                // If The right child's left child is null, we can ignore this step
                child.down[0] = parent; // Rotate the child and parent node's to the left
                parent.up = child;
                this.root = child; // Because we are at the root, the root value must be reassigned to the child node
            }
            // Parent is not at the root node
            else {
                // If not null, assign all nodes after the child node's left child to the right of the parent node
                if (child.down[0] != null) {
                    parent.down[1] = child.down[0];
                }
                // If the parent is a right child assign the parent parameter's parent's right child value to the child
                if (parent.isRightChild()) {
                    parent.up.down[1] = child;
                }
                // If the parent is a left child, assign the parent parameter's parent's left child value to the child
                else {
                    parent.up.down[0] = child;
                }
                // Reassign the child node to where the parent node was, shifting the nodes to the left
                child.up = parent.up;
                child.down[0] = parent;
                parent.up = child;
            }
        }
        //Right rotation
        else {
            // If the child does not have a right child, the parent node's left child will be null
            if (child.down[1]==null){
                parent.down[0] = null;
            }
            // Parent is the root node
            if (parent.up == null) {
                // If the left child's right child is not null, must reassign it to the parent node's left child
                if (child.down[1] != null) {
                    parent.down[0] = child.down[1];
                    child.down[1].up = parent;
                }
                // If The left child's right child is null, we can ignore this step
                child.down[1] = parent; // Rotate the child and parent node's to the right
                parent.up = child;
                this.root = child; // Because we are at the root, the root value must be reassigned to the child node
            }
            // Parent is not the root node
            else {
                // If not null, assign all nodes after the child node's right child to the left of the parent node
                if (child.down[1] != null) {
                    parent.down[0] = child.down[1];
                }
                // If the parent is a right child assign the parent parameter's parent's right child value to the child
                if (parent.isRightChild()) {
                    parent.up.down[1] = child;
                }
                // If the parent is a left child, assign the parent parameter's parent's left child value to the child
                else {
                    parent.up.down[0] = child;
                }
                // Reassign the child node to where the parent node was, shifting the nodes to the right
                child.up = parent.up;
                child.down[1] = parent;
                parent.up = child;
            }
        }
    }

    /**
     * Get the size of the tree (its number of nodes).
     * @return the number of nodes in the tree
     */
    public int size() {
        return size;
    }

    /**
     * Method to check if the tree is empty (does not contain any node).
     * @return true of this.size() returns 0, false if this.size() != 0
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Checks whether the tree contains the value *data*.
     * @param data a comparable for the data value to check for
     * @return true if *data* is in the tree, false if it is not in the tree
     */
    public boolean contains(Comparable<T> data) {
        // null references will not be stored within this tree
        if (data == null) {
            throw new NullPointerException("This tree cannot store null references.");
        } else {
            Node<T> nodeWithData = this.findNode(data);
            // return false if the node is null, true otherwise
            return (nodeWithData != null);
        }
    }

    /**
     * Removes all keys from the tree.
     */
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Helper method that will return the node in the tree that contains a specific
     * key. Returns null if there is no node that contains the key.
     * @param data the data value for which we want to find the node that contains it
     * @return the node that contains the data value or null if there is no such node
     */
    protected Node<T> findNode(Comparable<T> data) {
        Node<T> current = this.root;
        while (current != null) {
            int compare = data.compareTo(current.data);
            if (compare == 0) {
                // we found our value
                return current;
            } else if (compare < 0) {
                if (current.down[0] == null) {
                    // we have hit a null node and did not find our node
                    return null;
                }
                // keep looking in the left subtree
                current = current.down[0];
            } else {
                if (current.down[1] == null) {
                    // we have hit a null node and did not find our node
                    return null;
                }
                // keep looking in the right subtree
                current = current.down[1];
            }
        }
        return null;
    }

    /**
     * This method performs an inorder traversal of the tree. The string
     * representations of each data value within this tree are assembled into a
     * comma separated string within brackets (similar to many implementations
     * of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
     * @return string containing the ordered values of this tree (in-order traversal)
     */
    public String toInOrderString() {
        // generate a string of all values of the tree in (ordered) in-order
        // traversal sequence
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (this.root != null) {
            Stack<Node<T>> nodeStack = new Stack<>();
            Node<T> current = this.root;
            while (!nodeStack.isEmpty() || current != null) {
                if (current == null) {
                    Node<T> popped = nodeStack.pop();
                    sb.append(popped.data.toString());
                    if(!nodeStack.isEmpty() || popped.down[1] != null) sb.append(", ");
                    current = popped.down[1];
                } else {
                    nodeStack.add(current);
                    current = current.down[0];
                }
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    /**
     * This method performs a level order traversal of the tree. The string
     * representations of each data value
     * within this tree are assembled into a comma separated string within
     * brackets (similar to many implementations of java.util.Collection).
     * This method will be helpful as a helper for the debugging and testing
     * of your rotation implementation.
     * @return string containing the values of this tree in level order
     */
    public String toLevelOrderString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (this.root != null) {
            LinkedList<Node<T>> q = new LinkedList<>();
            q.add(this.root);
            while(!q.isEmpty()) {
                Node<T> next = q.removeFirst();
                if(next.down[0] != null) q.add(next.down[0]);
                if(next.down[1] != null) q.add(next.down[1]);
                sb.append(next.data.toString());
                if(!q.isEmpty()) sb.append(", ");
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    public String toString() {
        return "level order: " + this.toLevelOrderString() +
                "\nin order: " + this.toInOrderString();
    }

    // Implement at least 3 tests using the methods below. You can
    // use your notes from lecture for ideas of rotation examples to test with.
    // Make sure to include rotations at the root of a tree in your test cases.
    // Give each of the methods a meaningful header comment that describes what is being
    // tested and make sure your tests have inline comments that help with reading your test code.
    // If you'd like to add additional tests, then name those methods similar to the ones given below.
    // Eg: public static boolean test4() {}
    // Do not change the method name or return type of the existing tests.
    // You can run your tests through the static main method of this class.
    /**
     * Submission check which tests a right rotation at the root node of a tree.
     * @return True if right rotation works as expected, false if it fails
     */
    public static boolean test1() {

        //Create tree and insert data
        BinarySearchTree<Integer> test = new BinarySearchTree<>();

        test.insert(7);
        test.insert(3);
        test.insert(9);
        test.insert(1);
        test.insert(4);

        //Find root node and left child node.
        Node<Integer> parent = test.root;
        Node<Integer> child = parent.down[0];

        //Rotate 7 and 3
        test.rotate(child, parent);

        //Check level-order string of tree after rotation
        if (!test.toLevelOrderString().equals("[ 3, 1, 7, 4, 9 ]") || !test.toInOrderString().equals(
                "[ 1, 3, 4, 7, 9 ]")) {
            System.out.println(test.toLevelOrderString());
            System.out.println("After inserting 7, 3, 9, 1, 4 into an empty tree and rotating 7 and 3, level order is" +
                    " not [ 3, 1, 7, 4, 9 ], but should be");
            return false;
        }

        return true;
    }

    /**
     * Submission check which tests a left rotation not at the root node of a tree and a left rotation at the root
     * of a tree
     * @return True if left rotation works as expected, false if it fails
     */
    public static boolean test2() {
        //Create tree and insert data
        BinarySearchTree<Integer> test = new BinarySearchTree<>();

        test.insert(5);
        test.insert(3);
        test.insert(11);
        test.insert(1);
        test.insert(4);
        test.insert(8);
        test.insert(13);

        //Find 13 and 11.
        Node<Integer> root = test.root;
        Node<Integer> eleven = root.down[1];
        Node<Integer> thirteen = eleven.down[1];

        //Rotate 13 and 11
        test.rotate(thirteen, eleven);
        //Check level-order string of tree after rotation
        if (!test.toLevelOrderString().equals("[ 5, 3, 13, 1, 4, 11, 8 ]") || !test.toInOrderString().equals(
                "[ 1, 3, 4, 5, 8, 11, 13 ]")) {
            System.out.println(test.toLevelOrderString());
            System.out.println("After inserting 5, 3, 11, 1, 4, 8, 13 into an empty tree and rotating 11 and 13, level order is" +
                    " not [ 5, 3, 13, 1, 4, 11, 8 ], but should be");
            return false;
        }

        //Create tree and insert data
        BinarySearchTree<Integer> testAtRoot = new BinarySearchTree<>();

        testAtRoot.insert(5);
        testAtRoot.insert(3);
        testAtRoot.insert(10);
        testAtRoot.insert(1);
        testAtRoot.insert(4);
        testAtRoot.insert(8);
        testAtRoot.insert(13);
        testAtRoot.insert(12);

        //Find root and 10.
        Node<Integer> root2 = testAtRoot.root;
        Node<Integer> ten = root2.down[1];

        //Rotate root and 10
        testAtRoot.rotate(ten, root2);
        //Check level-order string of tree after rotation at the root of the tree
        if (!testAtRoot.toLevelOrderString().equals("[ 10, 5, 13, 3, 8, 12, 1, 4 ]") ||
                !testAtRoot.toInOrderString().equals("[ 1, 3, 4, 5, 8, 10, 12, 13 ]")) {
            System.out.println(test.toLevelOrderString());
            System.out.println("After inserting 5, 3, 10, 1, 4, 8, 13 into an empty tree and rotating 5 and 10, level order is" +
                    " not [ 10, 5, 13, 3, 8, 1, 4 ], but should be");
            return false;
        }


        return true;
    }

    /**
     * Submission check which tests a right rotation not at the root node of a tree.
     * @return True if left rotation works as expected, false if it fails
     */
    public static boolean test3() {
        //Create tree and insert data
        BinarySearchTree<Integer> test = new BinarySearchTree<>();

        test.insert(7);
        test.insert(5);
        test.insert(9);
        test.insert(1);
        test.insert(6);

        //Find 1 and 5.
        Node<Integer> root = test.root;
        Node<Integer> five = root.down[0];
        Node<Integer> one = five.down[0];

        //Rotate 1 and 5
        test.rotate(one, five);

        //Check level-order string of tree after rotation
        if (!test.toLevelOrderString().equals("[ 7, 1, 9, 5, 6 ]") || !test.toInOrderString().equals(
                "[ 1, 5, 6, 7, 9 ]")) {
            System.out.println("After inserting 7, 5, 9, 1, 6 into an empty tree and rotating 1 and 5, level order is" +
                    " not [ 7, 1, 9, 5, 6 ], but should be");
            return false;
        }

        return true;
    }

    /**
     * Main method to run tests. If you'd like to add additional test methods, add a line for each
     * of them.
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Test 1 passed: " + test1());
        System.out.println("Test 2 passed: " + test2());
        System.out.println("Test 3 passed: " + test3());
    }

}
