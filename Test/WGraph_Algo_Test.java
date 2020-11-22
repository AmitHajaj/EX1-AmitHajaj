package ex1;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class WGraph_Algo_Test {

    @Test
    void isConnected(){
        weighted_graph g = new WGraph_DS();
        weighted_graph_algorithms ga = new WGraph_Algo();

        ga.init(g);
        assertTrue(ga.isConnected());

        g.addNode(2);
        ga.init(g);
        assertTrue(ga.isConnected());

        g.addNode(1);
        ga.init(g);
        assertFalse(ga.isConnected());

        weighted_graph g1 = smallWG();
        ga.init(g1);
        assertTrue(ga.isConnected());

        g1.removeNode(8);
        ga.init(g1);
        assertTrue(ga.isConnected());

        g1.removeNode(9);
        ga.init(g1);
        assertFalse(ga.isConnected());

        g1.addNode(8);
        g1.addNode(9);

        g1.connect(8, 10, 1);
        g1.connect(7, 9, 1);
        g1.connect(9, 11, 1);
        g1.connect(6, 8, 1);
        ga.init(g1);
        assertTrue(ga.isConnected());
    }

    @Test
    void shortestPathDist_shortestPath(){
        weighted_graph g = new WGraph_DS();
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);

        //Null graph
        assertEquals(-1, ga.shortestPathDist(0,1));
        assertEquals(null, ga.shortestPath(0, 1));

        g.addNode(2);
        ga.init(g);

        //Node to itself
        assertEquals(0, ga.shortestPathDist(2,2));
        List<node_info> p3 = ga.shortestPath(2, 2);
        int [] expect = {2};
        assertEquals(expect[0], p3.get(0).getKey());

        g.addNode(1);
        ga.init(g);

        assertEquals(-1, ga.shortestPathDist(2,1));
        assertEquals(null, ga.shortestPath(2, 1));

        weighted_graph g1 = smallWG();
        ga.init(g1);

        assertEquals(2, ga.shortestPathDist(1, 2));
        List<node_info> p2 = ga.shortestPath(1, 2);
        expect = new int[]{1, 2};
        int i = 0;
        for(node_info n: p2) {
            assertEquals(n.getKey(), expect[i]);
            i++;
        }

        g1.removeNode(2);
        g1.removeNode(3);
        ga.init(g1);
        assertEquals(-1, ga.shortestPathDist(1, 5));
        assertEquals(null, ga.shortestPath(1, 5));

        g1.addNode(2);
        g1.addNode(3);
        g1.connect(1, 3, 2);
        g1.connect(3, 2, 2);
        g1.connect(3, 5, 2);
        ga.init(g1);

        assertEquals(5,ga.shortestPathDist(0, 2));
        List<node_info> p1 = ga.shortestPath(0, 2);
        expect = new int[]{0, 1, 3, 2};
        i = 0;
        for(node_info n: p1) {
            assertEquals(n.getKey(), expect[i]);
            i++;
        }

        assertEquals(27,ga.shortestPathDist(6, 11));
        List<node_info> p = ga.shortestPath(6, 11);
        expect = new int[]{6, 7, 9, 11};
        i = 0;
        for(node_info n: p) {
            assertEquals(n.getKey(), expect[i]);
            i++;
        }
    }

    @Test
    void save_load(){
        weighted_graph g = new WGraph_DS();
        weighted_graph_algorithms ga = new WGraph_Algo();

        ga.save("Test_graph.obj");

        g = smallWG();
        ga.init(g);

        ga.save("Test_graph1.obj");

        weighted_graph gc = smallWG();
        ga.load("Test_graph1.obj");
        assertEquals(g, gc);
        g.removeEdge(9, 10);
        assertNotEquals(g, gc);
        gc.removeEdge(9, 10);
        assertEquals(g, gc);
    }


    private weighted_graph smallWG(){
        weighted_graph sg = WGraph_DS_Test.graph_creator(12, 0, 1);

        for(int i = 0; i<10; i++){
            sg.connect(i, i+1, i+1);
            sg.connect(i, i+2, i+2);
        }
        return sg;
    }

    private static weighted_graph bigGrpah(){
        weighted_graph g = new WGraph_DS();
        weighted_graph_algorithms ga = new WGraph_Algo();

        int vSize = 1000000;
        int eSize = 10000000;
        WGraph_DS_Test.rnd = new Random(1);

        //Generating 10^6 nodes to the graph.
        for(int i=0; i<vSize; i++){
            g.addNode(i);
        }

        for(int i=0; i<vSize-6; i++){
            g.connect(i, i+1, i+1);
            g.connect(i, i+2, i+2);
            g.connect(i, i+3, i+3);
            g.connect(i, i+4, i+4);
            g.connect(i, i+5, i+5);
            g.connect(i, i+6, i+6);
            g.connect(i, i+7, i+7);
            g.connect(i, i+8, i+8);
            g.connect(i, i+9, i+9);
            g.connect(i, i+10, i+10);


        }
//
//        int[] nodes = WGraph_DS_Test.nodes(g);
//        while(g.edgeSize() < eSize) {
//            int a = WGraph_DS_Test.nextRnd(0,vSize);
//            int b = WGraph_DS_Test.nextRnd(0,vSize);
//            int i = nodes[a];
//            int j = nodes[b];
//            double w = WGraph_DS_Test.rnd.nextDouble();
//            g.connect(i,j, w);
//        }
        return g;
    }

    public static void main (String[] args){
        weighted_graph g0 = new WGraph_DS();
        long startTime = System.currentTimeMillis();

        g0 = bigGrpah();

        long endTime = System.currentTimeMillis();

        double duration = (endTime - startTime)/1000.0;
        System.out.println("Generating 10^6 nodes and 10^7 edges took: " + duration + "sec.");
    }
}