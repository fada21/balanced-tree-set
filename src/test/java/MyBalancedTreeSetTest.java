import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;


public class MyBalancedTreeSetTest {

    private MyBalancedTreeSet<Integer> tree;
    private MyBalancedTreeSet<Integer> treeNotEmpty;

    @Before
    public void setup() {
        tree = new MyBalancedTreeSet<Integer>();
        treeNotEmpty = new MyBalancedTreeSet<Integer>();
        treeNotEmpty.add(0);
    }

    @Test
    public void testAddingNull() {
        MyBalancedTreeSet.HeightSize heightSize1 = tree.computeHeightAndSize();
        tree.add(null);
        MyBalancedTreeSet.HeightSize heightSize2 = tree.computeHeightAndSize();
        assertEquals(heightSize1, heightSize2);
    }

    @Test
    public void testZeroHeightAndSizeIfEmpty() {
        assertEquals(new MyBalancedTreeSet.HeightSize(0, 0), tree.computeHeightAndSize());
    }

    @Test
    public void testNotEmpty() {
        tree.add(1);
        assertEquals(new MyBalancedTreeSet.HeightSize(1, 1), tree.computeHeightAndSize());
    }

    @Test
    public void testSorted() {
        List<Integer> sortedInts = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        tree.addList(sortedInts);
        assertEquals(sortedInts, tree.asList());
    }

    @Test
    public void testUnsorted() {
        List<Integer> ints = Arrays.asList(2, 3, 1, 9, 7, 5, 6, 4, 8);
        tree.addList(ints);
        Collections.sort(ints);
        assertEquals(ints, tree.asList());
    }

    @Test
    public void testDuplicates() {
        List<Integer> intsWithDuplicates = Arrays.asList(2, 3, 1, 1, 1, 5, 6, 4, 8);
        tree.addList(intsWithDuplicates);
        Set<Integer> set = new TreeSet<Integer>(intsWithDuplicates);
        assertEquals(new ArrayList<Integer>(set), tree.asList());
    }


    @Test
    public void testBoardExample() {
        List<Integer> example = Arrays.asList(25, 0, 15, 16, 255, 1, 9);
        tree.addList(example);
        Collections.sort(example);
        assertEquals(example, tree.asList());
    }


}
