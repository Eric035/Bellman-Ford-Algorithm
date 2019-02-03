# Bellman-Ford-Algorithm

The method **BellmanFord** takes a object WGraph named g as an input and an integer that indicates the source of the paths. If the input graph g contains a negative cycle, then the method should throw an exception. Otherwise, it will return an object BellmanFord that contains the shortest path estimates (the private array of integers distances), and for each node its predecessor in the shortest path from the source (the private array of integers predecessors)

The method **shortestPath** will return the list of nodes as an array of integers along the
shortest path from the source to the node destination. If this path does not exists, the method
should throw an exception.

Use this following command to execute your input file: java BellmanFord -YOUR INPUT FILE-


Test inputs:              
1. bf1.txt                
2. bf2.txt                
3. bf3.txt                
4. bf4.txt                

Expected Outputs: 
1. 0-->2-->5-->7-->8
2. Error: there isn't a path exists between the node: 0 and the node: 7
3. Error: there isn't a path exists between the node: 0 and the node: 7
4. 0-->2-->5-->7-->6-->8

