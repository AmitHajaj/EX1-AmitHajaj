package ex1;

import org.w3c.dom.Node;

import java.io.Serializable;
import java.util.*;

public class WGraph_DS implements weighted_graph, Serializable {
    int numOfEdges, MC;
    HashMap<Integer, HashMap<Integer, Double>> map;
    HashMap<Integer, node_info> nodes = new HashMap<>();

    //Constructor
    public WGraph_DS(){
        this.numOfEdges = 0;
        this.MC = 0;
        this.map = new HashMap<>();
        this.nodes = new HashMap<>();
    }

    /**
     * this a copy constructor. It used at the copy method of WGraph_Algo class.
     * for a given weighted graph, we create a deep copy of it.
     * @param g
     * @return WGraph_DS
     */
    public WGraph_DS(weighted_graph g){
        this.numOfEdges=g.edgeSize();
        this.MC=0;
        this.map = new HashMap<>();
        this.nodes = new HashMap<>();

        //Adding each node from the given graph to the new graph. Without his neighbors.
        for(node_info n : g.getV()){
            NodeInfo temp = new NodeInfo((NodeInfo) n);
            this.addNode(n.getKey());
            this.nodes.put(n.getKey(), n);

            //Here we will deep copy the mapping of the given graph.
            HashMap<Integer, Double> tempMap = new HashMap<>();
            for (HashMap.Entry<Integer, Double> entry : ((NodeInfo) n).neighbor.entrySet()) {
                tempMap.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public node_info getNode(int key) {
        if (this.nodes.containsKey(key)) {
            return this.nodes.get(key);
        }
        return null;
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        return map.get(node1).containsKey(node2);
    }

    @Override
    public double getEdge(int node1, int node2) {
        if(!hasEdge(node1, node2)){
            return -1;
        }
        else{
            return map.get(node1).get(node2);
        }
    }

    @Override
    public void addNode(int key) {
        if(key >= 0 && !this.map.containsKey(key)) {
            NodeInfo n = new NodeInfo(key);
            this.map.put(key, n.neighbor);
            this.nodes.put(key, n);
            MC++;
        }
    }

    @Override
    public void connect(int node1, int node2, double w) {
       if(this.map.containsKey(node1) && this.map.containsKey(node2)) {
           if (node1 != node2 && w >= 0) {
               //If this edge already exists, we don't touch edge size.
               if(!this.hasEdge(node1, node2)) {
                   numOfEdges++;
               }
               this.map.get(node1).put(node2, w);
               this.map.get(node2).put(node1, w);

               ((NodeInfo) this.nodes.get(node1)).addNi((NodeInfo) this.nodes.get(node2));
               ((NodeInfo) this.nodes.get(node2)).addNi((NodeInfo) this.nodes.get(node1));

               MC++;
           }
       }
    }

    @Override
    public Collection<node_info> getV() {
        return this.nodes.values();
    }

    @Override
    public Collection<node_info> getV(int node_id) {
        NodeInfo temp = (NodeInfo) this.nodes.get(node_id);
        return temp.getNi();
    }

    @Override
    public node_info removeNode(int key) {
        if (this.nodes.containsKey(key) && key >= 0) {
            node_info n = this.nodes.get(key);
            //Remove given node from all of his neighbors.
            for (int neighbor : this.map.get(key).keySet()) {
                WGraph_DS.NodeInfo temp = (NodeInfo) this.nodes.get(neighbor);
                this.map.get(neighbor).remove(key);
                temp.removeNi(n);
                numOfEdges--;
                MC++;
            }
            //Remove given node from this graph.
            this.map.remove(key);
            MC++;
            return this.nodes.remove(key);
        }
        else{
            return null;
        }
    }

    @Override
    public void removeEdge(int node1, int node2) {
        //Remove only if this connection exists.
        if(this.hasEdge(node1, node2)){
            this.map.get(node1).remove(node2);
            this.map.get(node2).remove(node1);
            numOfEdges--;
            MC++;
        }
    }

    @Override
    public int nodeSize() {
        return this.nodes.size();
    }

    @Override
    public int edgeSize() {
        return numOfEdges;
    }

    @Override
    public int getMC() {
        return MC;
    }

    /**
     * This is a normal equals function for a weighted a graph.
     * return true if both graph's has the same nodes and same edges.
     * @param g
     * @return boolean
     */
    public boolean equals (Object g){
        if(!(g instanceof weighted_graph)){ return false;}
        if(g == null) return false;
        WGraph_DS g1 = (WGraph_DS) g;
        //If they have same #nodes and #edges.
        if(this.nodeSize() != g1.nodeSize() && this.edgeSize() != g1.edgeSize()){
            return false;
        }

        for(int k : this.map.keySet()){
            //If g1 don't contains this key.
            if(!g1.map.containsKey(k)){
                return false;
            }
            //If they don't have the same #neighbors at current node.
            if(this.map.get(k).keySet().size() != ((WGraph_DS) g).map.get(k).keySet().size()){
                return false;
            }
            //Iterate over all of current node neighbor.
            for(int l : this.map.get(k).keySet()){
                if(!g1.hasEdge(k, l)){
                    return false;
                }
                if(this.getEdge(k, l) != g1.getEdge(k, l)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This is an inner class which implements node_info, Comparable and Serializable.
     */

    static class NodeInfo implements node_info, Comparable<node_info>, Serializable{
        private int key;
        private String info;
        private double tag;
        private HashMap<Integer, Double> neighbor;
        private HashSet<node_info> Ni;

        public NodeInfo (){
            this.key = 0;
            this.tag = 0;
            this.info = "";
        }

        public NodeInfo(int key){
            this.key = key;
            this.info = "";
            this.tag = 0;
            this.neighbor = new HashMap<>();
            this.Ni = new HashSet<>();
        }

        //Copy constructor.
        public NodeInfo(NodeInfo node) {
            NodeInfo n = new NodeInfo(node.getKey());
            this.tag = node.getTag();
            this.info = node.getInfo();
        }
        @Override
        public int getKey () {
            return key;
        }

        @Override
        public String getInfo () {
            return this.info;
        }

        @Override
        public void setInfo (String s){
            this.info = s;
        }

        @Override
        public double getTag () {
            return this.tag;
        }

        @Override
        public void setTag ( double t){
            this.tag = t;
        }

        /**
         * remove a given node from this node neighbors.
         * @param key
         */
        public void removeNi (node_info key){
            this.neighbor.remove(key.getKey());
            this.Ni.remove(key);
        }

        /**
         * return a collection of this node neighbors.
         * @return
         */
        public Collection<node_info> getNi (){
            return this.Ni;
        }

        /**
         * add new neighbor to this node.
         * @param n
         */
        public void addNi(node_info n){
            this.Ni.add(n);
        }

        /**
         * normal toString method.
         * Ex: [key-->{neighbors}]
         * @return String
         */

        @Override
        public int compareTo(node_info n) {
            if(this.getTag() < n.getTag()){
                return -1;
            }
            else if(this.getTag() == n.getTag()){
                return 0;
            }
            else{
                return 1;
            }
        }
    }
}

