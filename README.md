
**readMe for Ex1 at OOP-Course at Ariel university.**

**WGraph_DS**
this class implement's weighted_graph interface.
It represent a weighted graph. 
I used a hashmp that it keys are the nodes, and it values are another hashmap which it keys are the neighbors, and it values are the edge weight.<node, <neighbor,weight>>.

at this class there is an inner class NodeInfo which inplements node_info interface. 
At this inner class we have method for nodes, s.e: remove/add neighbore, toString,..
Note: The tag of each node is to save his path weigh from a given node. So NodeInfo comparTo, is to compare between the tag of the nodes.

**WGraph_Algo**
this class implement's weighted_graph_algorithm interface.
It represent a set of algorithm's on a weighted graph.

The algorithm for checking if a graph is connected, uses a private method that implements DFS algorithm.

We have here also algorithms for shortest path, but it is different because in weighted graph's the distance is determine by the weight of the path, not by the path number of edges.
Because of this definition I also implemented private methos which implements the Dijkstra's algorithm, that can find a shortest path between two nodes in weighted graph.
My implementation of Dijkstra's algorithm use priority queue which prioritize by each node tag.
