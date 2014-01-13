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
		resetStats();
		
		// Goal node that completes the search
		UUSearchNode goalNode = null;
		UUSearchNode grandparent = null;
		
		// Initialize queue with start node
		Queue<UUSearchNode> queue = new LinkedList<UUSearchNode>();
		queue.add(startNode);
		HashMap<Integer, UUSearchNode> visited = new HashMap<Integer, UUSearchNode>();
		
		while(!queue.isEmpty()){
			System.out.println("Queue: " + queue);
			UUSearchNode parentNode = (UUSearchNode) queue.remove();
			System.out.println("Using " + parentNode);
			
			incrementNodeCount();
			
			// check if the goal has been reached
			if (parentNode.goalTest()){
				goalNode = parentNode;
				int key = parentNode.hashCode();
				visited.put(key, grandparent);
				break;
			}
			
			ArrayList<UUSearchNode> children = parentNode.getSuccessors();
			for(int i=0; i < children.size(); i++){
				// if haven't seen before
				UUSearchNode childNode = children.get(i);
				int nodeKey = childNode.hashCode();
				System.out.println(nodeKey);
				if (!visited.containsKey(nodeKey)){
					// mark previous node 
					if (!queue.contains(childNode)){
						queue.add(childNode);
					}
				} else {
					System.out.println("Already seen " + childNode);
				}
			}
			// visited 'key' will contain the hashCode of the node 
			// visited 'value' will contain the node's predecessor node 
			// startNode has no predecessor
			int key = parentNode.hashCode();
			visited.put(key, grandparent);
			
			// once all children have been added, mark this node visited
			grandparent = parentNode;
			
		}
		
		
		System.out.println(goalNode);
		
		// Check if goalNode has been found
		if (goalNode == null){
			return null;
		} else {
			// return the backchain link
			return backchain(goalNode, visited);
		}
	}
	
	// backchain should only be used by bfs, not the recursive dfs
	private List<UUSearchNode> backchain(UUSearchNode goalNode,
			HashMap<Integer, UUSearchNode> visited) {
		// you will write this method
		List<UUSearchNode> retArr = new ArrayList<UUSearchNode>();
		while (goalNode != null){
			retArr.add(goalNode);
			goalNode = visited.get(goalNode.hashCode());
		}
		return retArr;
	}

	public List<UUSearchNode> depthFirstMemoizingSearch(int maxDepth) {
		resetStats(); 
		
		// You will write this method
		List<UUSearchNode> retArr = new ArrayList<UUSearchNode>();
		HashMap<UUSearchNode, Integer>visited = new HashMap<UUSearchNode, Integer>();
		
		List<UUSearchNode> startChildren = startNode.getSuccessors();
		
		// mark startNode as visited
		visited.put(startNode, startNode.getDepth());
	
		for(int i=0; i < startChildren.size(); i++){
			UUSearchNode child = startChildren.get(i);
			if (!visited.containsKey(child)){
				retArr = dfs(child, visited, child.getDepth(), CannibalDriver.MAXDEPTH);
				if (retArr != null){
					return retArr;
				}
			}
			
		}
		return null;
	}

	// recursive memoizing dfs. Private, because it has the extra
	// parameters needed for recursion.  
	private List<UUSearchNode> dfs(UUSearchNode currentNode, HashMap<UUSearchNode, Integer> visited, 
			int depth, int maxDepth) {
		System.out.println("Following " + currentNode);
		
		// keep track of stats; these calls charge for the current node
		updateMemory(visited.size());
		incrementNodeCount();
		List<UUSearchNode> retArr= new ArrayList<UUSearchNode>();
	
		// you write this method.  Comments *must* clearly show the 
		//  "base case" and "recursive case" that any recursive function has.	
		
		// BASE CASE: currentNode is the goal Node
		if (currentNode.goalTest()){
			retArr.add(currentNode);
			return retArr;
		} else {
			// RECURSIVE CASE: not the goalNode, continue recursing down successor line
			List<UUSearchNode> currentChildren = currentNode.getSuccessors();
			visited.put(currentNode, currentNode.getDepth());
			
			// stop if the depth is exceeded
			if (depth > CannibalDriver.MAXDEPTH){
				System.out.println("Depth exceeded!");
				System.exit(1);
			}
			
			for(int i=0; i < currentChildren.size(); i++){
				UUSearchNode child = currentChildren.get(i);
				
				// if it is depth limited, we need to make sure that the DFS doesn't stop at 
				// a duplicate (compare depths)
				if (!visited.containsKey(child) || visited.get(child) > depth){
					retArr = dfs(child, visited, child.getDepth(), maxDepth);
					if (retArr != null){
						retArr.add(child);
						return retArr;
					}
				} 
			}
			return null;
		}
		
	}
	
	
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
