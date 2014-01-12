package cannibals;


// Author: Delos Chang
// With source: Prof. Balkcom, UUSearch Problem model
// 1/14/14

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class UUSearchProblem {
	
	// used to store performance information about search runs.
	//  these should be updated during the process of searches

	// see methods later in this class to update these values
	protected int nodesExplored;
	protected int maxMemory;

	protected UUSearchNode startNode;
	
	protected interface UUSearchNode {
		public ArrayList<UUSearchNode> getSuccessors();
		public boolean goalTest();
		public int getDepth();
	}

	// breadthFirstSearch:  return a list of connecting Nodes, or null
	// no parameters, since start and goal descriptions are problem-dependent.
	//  therefore, constructor of specific problems should set up start
	//  and goal conditions, etc.
	
	public List<UUSearchNode> breadthFirstSearch(){
		// Initialize queue with start node
		Queue<UUSearchNode> queue = new LinkedList<UUSearchNode>();
		queue.add(startNode);
		HashMap<Integer, UUSearchNode> visited = new HashMap<Integer, UUSearchNode>();
		
		// set the startNode Missionary/Cannibal combo to "visited"
		int key = startNode.hashCode();
		
		// visited 'key' will contain the hashCode of the node 
		// visited 'value' will contain the node's predecessor node 
		// startNode has no predecessor
		visited.put(key, null);
		
		while(!queue.isEmpty()){
			UUSearchNode parentNode = (UUSearchNode) queue.remove();
			ArrayList<UUSearchNode> children = parentNode.getSuccessors();
			for(int i=0; i < children.size(); i++){
				// if haven't seen before
				UUSearchNode childNode = children.get(i);
				int nodeKey = childNode.hashCode();
				if (!visited.containsKey(nodeKey)){
					// mark previous node 
					visited.put(nodeKey, parentNode);
					queue.add(childNode);
				}
			}
		}
		
		
		resetStats();
	}
	
	// backchain should only be used by bfs, not the recursive dfs
//	private List<UUSearchNode> backchain(UUSearchNode node,
//	private void backchain(UUSearchNode node,
//			HashMap<UUSearchNode, UUSearchNode> visited) {
//		// you will write this method
//	}

//	public List<UUSearchNode> depthFirstMemoizingSearch(int maxDepth) {
//		resetStats(); 
//		
//		// You will write this method
//
//	}

	// recursive memoizing dfs. Private, because it has the extra
	// parameters needed for recursion.  
//	private List<UUSearchNode> dfs(UUSearchNode currentNode, HashMap<UUSearchNode, Integer> visited, 
//			int depth, int maxDepth) {
//		
//		// keep track of stats; these calls charge for the current node
//		updateMemory(visited.size());
//		incrementNodeCount();
//	
//		// you write this method.  Comments *must* clearly show the 
//		//  "base case" and "recursive case" that any recursive function has.	
//	}
	
	
	// set up the iterative deepening search, and make use of dfspc
//	public List<UUSearchNode> IDSearch(int maxDepth) {
//		resetStats();
//		// you write this method
//	}

	// set up the depth-first-search (path-checking version), 
	//  but call dfspc to do the real work
	public List<UUSearchNode> depthFirstPathCheckingSearch(int maxDepth) {
		resetStats();
		
		// I wrote this method for you.  Nothing to do.

		HashSet<UUSearchNode> currentPath = new HashSet<UUSearchNode>();

		return dfsrpc(startNode, currentPath, 0, maxDepth);

	}

	// recursive path-checking dfs. Private, because it has the extra
	// parameters needed for recursion.
	private List<UUSearchNode> dfsrpc(UUSearchNode currentNode, HashSet<UUSearchNode> currentPath,
			int depth, int maxDepth) {

		// you write this method
	
		return null;
	}

	protected void resetStats() {
		nodesExplored = 0;
		maxMemory = 0;
	}
	
	protected void printStats() {
		System.out.println("Nodes explored during last search:  " + nodesExplored);
		System.out.println("Maximum memory usage during last search " + maxMemory);
	}
	
	protected void updateMemory(int currentMemory) {
		maxMemory = Math.max(currentMemory, maxMemory);
	}
	
	protected void incrementNodeCount() {
		nodesExplored++;
	}

}
