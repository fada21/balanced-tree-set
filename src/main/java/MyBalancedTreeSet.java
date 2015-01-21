import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class MyBalancedTreeSet<T extends Comparable<T>> {
    public static final boolean RED = true;
    public static final boolean BLACK = false;

    private class Node<T> {
        private T item;
        private Node<T> left;
        private Node<T> right;
        private boolean color;

        private Node(T item) {
            this.item = item;
            this.color = MyBalancedTreeSet.RED;
        }

        public T getItem() {
            return item;
        }
    }

    private Node<T> root;

    public void add(T item) {
        if (item == null) {
            return;
        }
        root = add(root, item);
        root.color = BLACK;
    }

    private Node<T> add(Node<T> node, T item) {
        if (node == null) {
            return new Node<T>(item);
        }

        if (isFourNode(node)) {
            node = splitFourNode(node);
        }

        int cmp = item.compareTo(node.getItem());
        if (cmp < 0) {
            node.left = add(node.left, item);
        } else if (cmp > 0) {
            node.right = add(node.right, item);
        } // else -> is equal -> do nothing

        if (isRed(node.right)) {
            node = leanLeft(node);
        }

        return node;
    }

    private boolean isFourNode(Node<T> node) {
        return isRed(node.left) && isRed(node.left.left);
    }

    private boolean isRed(Node<T> node) {
        return node != null && node.color == RED;
    }

    private Node<T> splitFourNode(Node<T> node) {
        node = rotateRight(node);
        node.left.color = BLACK;
        return node;
    }

    private Node<T> leanLeft(Node<T> node) {
        node = rotateLeft(node);
        node.color = node.left.color;
        node.left.color = RED;
        return node;
    }

    private Node<T> rotateLeft(Node<T> node) {
        Node<T> top = node.right;
        node.right = top.left;
        top.left = node;
        return top;
    }

    private Node<T> rotateRight(Node<T> node) {
        Node<T> top = node.left;
        node.left = top.right;
        top.right = node;
        return top;
    }

    public void addList(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    public List<T> asList() { // should implement iterable, but this is enough I think
        if (root!=null) {
            return asList(root, new ArrayList<T>());
        }
        return Collections.emptyList();
    }

    private List<T> asList(Node<T> node, List<T> list) {
        if (node.left != null) {
            asList(node.left, list);
        }
        list.add(node.getItem());
        if (node.right != null) {
            asList(node.right, list);
        }
        return list;
    }


    public void printAll() {
        if (root != null) printNode(root, 1);
    }

    private void printNode(Node<T> node, int height) {
        if (node.left != null) {
            printNode(node.left, height + 1);
        }
        System.out.println("item: " + node.getItem() + ", height:" + height);
        if (node.right != null) {
            printNode(node.right, height + 1);
        }
    }

    /**
     * diagnostic method
     *
     * @return
     */
    public HeightSize computeHeightAndSize() {
        if (root != null) {
            return computeHeightAndSize(root, 1, 1);
        }
        return new HeightSize(0, 0);
    }

    public static class HeightSize {
        public int height;
        public int size;

        public HeightSize(int height, int size) {
            this.height = height;
            this.size = size;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            HeightSize that = (HeightSize) o;

            if (height != that.height) return false;
            if (size != that.size) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = height;
            result = 31 * result + size;
            return result;
        }
    }

    private HeightSize computeHeightAndSize(Node<T> node, int height, int size) {
        HeightSize leftHeightSize = null;
        if (node.left != null) {
            leftHeightSize = computeHeightAndSize(node.left, height + 1, 1);
        }
        HeightSize rightHeightSize = null;
        if (node.right != null) {
            rightHeightSize = computeHeightAndSize(node.right, height + 1, 1);
        }
        int leftHeight = leftHeightSize != null ? leftHeightSize.height : height;
        int leftSize = leftHeightSize != null ? leftHeightSize.size : 0;
        int rightHeight = rightHeightSize != null ? rightHeightSize.height : height;
        int rightSize = rightHeightSize != null ? rightHeightSize.size : 0;
        return new HeightSize(Math.max(leftHeight, rightHeight), size + leftSize + rightSize);
    }

    public static void main(String[] args) {
        MyBalancedTreeSet<Integer> integerRBTree = new MyBalancedTreeSet<Integer>();
        List<Integer> integers = Arrays.asList(1, 2, 7, 9, 3, 4, 5, 6, 8, 0);
        for (Integer i : integers) {
            System.out.println("Adding: " + i);
            integerRBTree.add(i);
        }
        System.out.println("Tree items in order:");
        integerRBTree.printAll();


        System.out.println("Please pass int as an input to add:");
        MyBalancedTreeSet<Integer> integerRBTreeInput = new MyBalancedTreeSet<Integer>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String input;
            while ((input = br.readLine()) != null) {
                try {
                    int parsedInt = Integer.parseInt(input);
                    integerRBTreeInput.add(parsedInt);
                } catch (NumberFormatException nfe) {
                    System.out.println("Not a valid input! Please");
                }
                integerRBTreeInput.printAll();
                HeightSize heightSize = (HeightSize) integerRBTreeInput.computeHeightAndSize();
                System.out.println("Height: " + heightSize.height + ", Size: " + heightSize.size);

            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}