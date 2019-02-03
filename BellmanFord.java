import java.util.*;

public class BellmanFord{
	/**
	 * Utility class. Don't use.
	 */
	public class BellmanFordException extends Exception{
		private static final long serialVersionUID = -4302041380938489291L;
		public BellmanFordException() {super();}
		public BellmanFordException(String message) {
			super(message);
		}
	}
	
	/**
	 * Custom exception class for BellmanFord algorithm
	 * 
	 * Use this to specify a negative cycle has been found 
	 */
	public class NegativeWeightException extends BellmanFordException{
		private static final long serialVersionUID = -7144618211100573822L;
		public NegativeWeightException() {super();}
		public NegativeWeightException(String message) {
			super(message);
		}
	}
	
	/**
	 * Custom exception class for BellmanFord algorithm
	 *
	 * Use this to specify that a path does not exist
	 */
	public class PathDoesNotExistException extends BellmanFordException{
		private static final long serialVersionUID = 547323414762935276L;
		public PathDoesNotExistException() { super();} 
		public PathDoesNotExistException(String message) {
			super(message);
		}
	}
	
    private int[] distances = null;
    private int[] predecessors = null;
    private int source;

    BellmanFord(WGraph g, int source) throws BellmanFordException{
        /*  Constructor, input a graph and a source
         *  Computes the Bellman Ford algorithm to populate the attributes 
         *  distances - at position "n" the distance of node "n" to the source is kept
         *  predecessors - at position "n" the predecessor of node "n" on the path
         *  to the source is kept
         *  source - the source node
         *
         *  If the node is not reachable from the source, the
         *  distance value must be Integer.MAX_VALUE
         */
        
        
        int[] dist = new int[g.getNbNodes()];                       // To avoid nullPointerException, we create two local arrays to store our values.
        int[] pred = new int[g.getNbNodes()];

        for (int i = 0; i < dist.length; i++)
        {
            dist[i] = Integer.MAX_VALUE;                            // We initialize distances from source to all other nodes to infinity.
        }
        dist[source] = 0;

        for (int i = 0; i < pred.length; i++)
        {
            pred[i] = -1;
        }


        for (int i = 1; i <= g.getNbNodes() - 1; i++)
        {   
            for (int j = 0; j < g.getEdges().size(); j++) 
            { 
                int u = g.getEdges().get(j).nodes[0];               // Get node u, which is the first element of the Edge object
                int v = g.getEdges().get(j).nodes[1];               // and get node v, the second element of the Edge at the ArrayList position j.
                int weight = g.getEdges().get(j).weight;            // Get the weight of the Edge from the ArrayList Edges at index j.
                if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) 
                {
                    dist[v] = dist[u] + weight;                     // relax the edge
                    pred[v] = u;
                }
            } 
        }

        for (int i = 0; i < g.getEdges().size(); i++)               // Check if input g has negative weight cycle.
        {
            int u = g.getEdges().get(i).nodes[0];
            int v = g.getEdges().get(i).nodes[1];
            int weight = g.getEdges().get(i).weight;
            if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v])
            {
                throw new NegativeWeightException ("The input graph g contains a negative weight cycle.");
            }
        }

        distances = dist;
        predecessors = pred;
    }

    public int[] shortestPath(int destination) throws BellmanFordException{
        /*  Returns the list of nodes along the shortest path from 
         *  the object source to the input destination
         *  If not path exists an Exception is thrown
         */
    
        Stack<Integer> tempStack = new Stack<Integer>();            // We create a temporary stack to store the path that we would want to return backwardly. ie: pushing the destination (our last node) into the stack first.
        int[] shortestPath;                                         // array to return 
        tempStack.push(destination);
        
        int currentNode = destination;

        while (predecessors[currentNode] != -1) 
        {
			currentNode = predecessors[currentNode];                // Get the predecessor of each node from the predecessor array
			tempStack.push(currentNode);                            // Push our result into our temp stack.
		}

        if (tempStack.peek() == destination)                        // Check if the top of our stack is our input destination, if so, that means there isn't a path exists between our source and destination.
        {
			throw (new PathDoesNotExistException ("Error: there isn't a path exists between the node: " + this.source + " and the node: " + destination + "."));
		}
        
        shortestPath = new int[tempStack.size()];                   // Our return array will have the same size as our tempStack.
        int i = 0;
        while (tempStack.isEmpty() == false) 
        {
			currentNode = tempStack.pop();                          // Popping nodes from our temp stack 
			shortestPath[i] = currentNode;                          // and store them into our return array. By doing this, we will have the correct order of nodes that forms a path between our source and destination.
			i++;
		}
		return shortestPath;
    }

    public void printPath(int destination){
        /*Print the path in the format s->n1->n2->destination
         *if the path exists, else catch the Error and 
         *prints it
         */
        try {
            int[] path = this.shortestPath(destination);
            for (int i = 0; i < path.length; i++){
                int next = path[i];
                if (next == destination){
                    System.out.println(destination);
                }
                else {
                    System.out.print(next + "-->");
                }
            }
        }
        catch (BellmanFordException e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        try{
            BellmanFord bf = new BellmanFord(g, g.getSource());
            bf.printPath(g.getDestination());
        }
        catch (BellmanFordException e){
            System.out.println(e);
        }

   } 
}
