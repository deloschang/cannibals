package cannibals;
// Author: Delos Chang
// With source: Prof. Balkcom, CannibalDriver model
// 1/14/14

import java.util.List;
import java.util.ArrayList;
import cannibals.UUSearchProblem.*;

public class CannibalDriver {
	public static final int MAXDEPTH = 5000;
	public static void main(String args[]) {

		// interesting starting state:  
//		  8, 5, 1  (IDS slow, but uses least memory.)


//		 set up the "standard" 331 problem:
		CannibalProblem mcProblem = new CannibalProblem(3, 3, 1, 0, 0, 0);
//		CannibalProblem mcProblem = new CannibalProblem(8, 5, 1, 0, 0, 0);
		
		// Test levels 2 and 3
//		ArrayList<UUSearchNode> retArr = mcProblem.startNode.getSuccessors();
//		System.out.println(retArr.get(0).getSuccessors().get(1).getSuccessors().get(1).getSuccessors());
		
	
		List<UUSearchProblem.UUSearchNode> path;
//		path = mcProblem.breadthFirstSearch();	
//		
//		if (path != null){
//			System.out.println("bfs path length:  " + path.size() + " " + path);
//			mcProblem.printStats();
//			System.out.println("--------");
//		} else {
//			System.out.println("--------");
//			System.out.println("No path!");
//		}


//		path = mcProblem.depthFirstMemoizingSearch(MAXDEPTH);	
//		System.out.println("dfs memoizing path length:" + path.size());
//		mcProblem.printStats();
//		System.out.println("--------");
//		
//		path = mcProblem.depthFirstPathCheckingSearch(MAXDEPTH);
//		if (path != null){
//			System.out.println("dfs path checking path length:" + path.size());
//		}
//		mcProblem.printStats();
//		
//		
		System.out.println("--------");
		path = mcProblem.IDSearch(MAXDEPTH);
		System.out.println("Iterative deepening (path checking) path length:" + path.size());
		mcProblem.printStats();
		
	}
}
