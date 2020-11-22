package ex1;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class WGraph_DS_Test {
    public static Random rnd =null;

    @Test
    void nodeSize() {
        weighted_graph g = new WGraph_DS();

        int s = g.nodeSize();
        g.addNode(0);
        g.addNode(1);
        g.addNode(1);
        g.addNode(2);
        g.addNode(-5);

        s = g.nodeSize();

        assertEquals(3, s);
        g.removeNode(2);
        g.removeNode(1);
        g.removeNode(2);
        g.removeNode(-2);
        s = g.nodeSize();
        assertEquals(1, s);

        g.removeNode(0);
        s = g.nodeSize();
        assertEquals(0, s);
    }

    @Test
    void edgeSize() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0, 1, 1);
        g.connect(0, 2, 2);
        g.connect(0, 3, 3);
        g.connect(0, 1, 1);
        g.connect(1, 3, 2.5);
        g.connect(2, 3, 0.75);
        g.connect(0, 0, 3);


        double w01 = g.getEdge(0, 1);
        g.connect(0, 1, 2);
        double w10 = g.getEdge(0, 1);
        assertEquals(2, w10);
        int e_size = g.edgeSize();
        assertEquals(5, e_size);
        double w03 = g.getEdge(0, 3);
        double w30 = g.getEdge(3, 0);
        assertEquals(w03, w30);
        assertEquals(w03, 3);
    }

    @Test
    void getV() {
        weighted_graph g = new WGraph_DS();
        for (int i = 0; i < 8; i++) {
            g.addNode(i);
            g.addNode(i + 1);
            g.addNode(i + 2);

            g.connect(i, i + 1, (double) (i + (i + 1) / 2));
            g.connect(i + 1, i + 2, (double) ((i + 2) + (i + 1) / 2));
        }

        Collection<node_info> s = g.getV();
        Iterator<node_info> iter = s.iterator();
        while (iter.hasNext()) {
            node_info n = iter.next();
            assertNotNull(n);
        }

        g.removeNode(10);
        g.removeNode(6);
        g.removeNode(6);
        s = g.getV();

        Iterator<node_info> iter1 = s.iterator();
        while (iter1.hasNext()) {
            node_info n = iter1.next();
            assertNotNull(n);
        }
    }

    @Test
    void hasEdge() {
        weighted_graph g = new WGraph_DS();
        for (int i = 0; i < 8; i++) {
            g.addNode(i);
            g.addNode(i + 1);
            g.addNode(i + 2);

            g.connect(i, i + 1, (double) (i + (i + 1) / 2));
            g.connect(i + 1, i + 2, (double) ((i + 2) + (i + 1) / 2));
        }

        for (int i = 0; i < 8; i++) {
            boolean e = g.hasEdge(i, i + 1);
            boolean e1 = g.hasEdge(i + 1, i + 2);
            assertTrue(e);
            assertTrue(e1);
            boolean e2 = g.hasEdge(i, i + 2);
            assertFalse(e2);
        }
    }

    @Test
    void connect() {
        weighted_graph g = new WGraph_DS();
        for (int i = -1; i < 8; i++) {
            g.addNode(i);
            g.addNode(i + 1);
            g.addNode(i + 2);

            g.connect(i, i + 1, (double) (i + (i + 1) / 2));
            g.connect(i + 1, i + 2, (double) (i + (i + 1) / 2));
            g.connect(i + 1, i + 1, (double) ((i + 1) + (i + 1) / 2));
        }

        int e = g.edgeSize();
        assertEquals(9, e);
    }


    @Test
    void removeNode() {
        weighted_graph g = new WGraph_DS();
        assertEquals(null, g.removeNode(0));

        for (int i = -1; i < 8; i++) {
            g.addNode(i);
            g.addNode(i + 1);
            g.addNode(i + 2);

            g.connect(i, i + 1, (double) (i + (i + 1) / 2));
            g.connect(i + 1, i + 2, (double) (i + (i + 1) / 2));
            g.connect(i + 1, i + 1, (double) ((i + 1) + (i + 1) / 2));
        }

        int e = g.edgeSize();
        int n = g.nodeSize();
        assertEquals(10, n);

        g.removeNode(0);
        g.removeNode(1);
        g.removeNode(2);
        g.removeNode(0);

        n = g.nodeSize();
        e = g.edgeSize();
        assertEquals(7, n);
        assertEquals(6, e);
    }

    @Test
    void removeEdge() {
        weighted_graph g = new WGraph_DS();
        assertEquals(null, g.removeNode(0));

        for (int i = -1; i < 8; i++) {
            g.addNode(i);
            g.addNode(i + 1);
            g.addNode(i + 2);

            g.connect(i, i + 1, (double) (i + (i + 1) / 2));
            g.connect(i + 1, i + 2, (double) (i + (i + 1) / 2));
            g.connect(i + 1, i + 1, (double) ((i + 1) + (i + 1) / 2));
        }

        g.removeEdge(0, 1);
        g.removeEdge(1, 2);
        g.removeEdge(0, 0);
        g.removeEdge(0, -1);
        g.removeEdge(0, 1);
        g.removeEdge(2, 3);

        int e = g.edgeSize();
        assertEquals(6, e);

    }

    @Test
    void compareTo() {
        weighted_graph g = new WGraph_DS();
        for (int i = -1; i < 8; i++) {
            g.addNode(i);
            g.addNode(i + 1);
            g.addNode(i + 2);

            g.connect(i, i + 1, (double) (i + (i + 1) / 2));
            g.connect(i + 1, i + 2, (double) (i + (i + 1) / 2));
            g.connect(i + 1, i + 1, (double) ((i + 1) + (i + 1) / 2));
        }

        weighted_graph g1 = new WGraph_DS();
        for (int i = -1; i < 8; i++) {
            g1.addNode(i);
            g1.addNode(i + 1);
            g1.addNode(i + 2);

            g1.connect(i, i + 1, (double) (i + (i + 1) / 2));
            g1.connect(i + 1, i + 2, (double) (i + (i + 1) / 2));
            g1.connect(i + 1, i + 1, (double) ((i + 1) + (i + 1) / 2));
        }

        assertTrue(g.equals(g1));

        g1.removeEdge(0, 1);

        assertFalse(g.equals(g1));
    }

    ///////////////////////////////////
    /**
     * Generate a random graph with v_size nodes and e_size edges
     * @param v_size
     * @param e_size
     * @param seed
     * @return
     */
    public static weighted_graph graph_creator(int v_size, int e_size, int seed) {
        weighted_graph g = new WGraph_DS();
        rnd = new Random(seed);
        for(int i=0;i<v_size;i++) {
            g.addNode(i);
        }
        // Iterator<node_data> itr = V.iterator(); // Iterator is a more elegant and generic way, but KIS is more important
        int[] nodes = nodes(g);
        while(g.edgeSize() < e_size) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            int i = nodes[a];
            int j = nodes[b];
            double w = rnd.nextDouble();
            g.connect(i,j, w);
        }
        return g;
    }
    static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    static double nextRnd(double min, double max) {
        double d = rnd.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }
    /**
     * Simple method for returning an array with all the node_data of the graph,
     * Note: this should be using an Iterator<node_edge> to be fixed in Ex1
     * @param g
     * @return
     */
    static int[] nodes(weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_info> V = g.getV();
        node_info[] nodes = new node_info[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }

}
