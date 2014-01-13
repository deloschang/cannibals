package cannibals;
// Author: Delos Chang
// With source: Prof. Balkcom, CannibalProblem model
// 1/14/14

import java.util.ArrayList;
import java.util.Arrays;
// for the first part of the assignment, you might not extend UUSearchProblem,
//  since UUSearchProblem is incomplete until you finish it.

//public class CannibalProblem extends UUSearchProblem {
public class CannibalProblem extends UUSearchProblem {
	// the following are the only instance variables you should need.
	//  (some others might be inherited from UUSearchProblem, but worry
	//  about that later.)

	private int goalm, goalc, goalb;
	private int totalMissionaries, totalCannibals; 

	public CannibalProblem(int sm, int sc, int sb, int gm, int gc, int gb) {
		// I (djb) wrote the constructor; nothing for you to do here.
		startNode = new CannibalNode(sm, sc, 1, 0);
		goalm = gm;
		goalc = gc;
		goalb = gb;
		totalMissionaries = sm;
		totalCannibals = sc;
	}
	
	private void testSuccessors(){
		ArrayList<UUSearchNode> retArr = startNode.getSuccessors();
		System.out.println(retArr);
	}
	
	// node class used by searches.  Searches themselves are implemented
	//  in UUSearchProblem.
	private class CannibalNode implements UUSearchNode {

		// do not change BOAT_SIZE without considering how it affect
		// getSuccessors. 
		
		private final static int BOAT_SIZE = 2;
	
		// how many missionaries, cannibals, and boats
		// are on the starting shore
		private int[] state; 
		
		// how far the current node is from the start.  Not strictly required
		//  for search, but useful information for debugging, and for comparing paths
		private int depth;  

		public CannibalNode(int m, int c, int b, int d) {
			state = new int[3];
			this.state[0] = m;
			this.state[1] = c;
			this.state[2] = b;
			
			depth = d;
		}

		// checks whether the humans get eaten :(
		private boolean isSafeState(CannibalNode node){
			// miss + cannibals on starting side
			int startMissionaries = node.state[0];
			int startCannibals = node.state[1];
			// miss + cannibals on other side
			int otherMissionaries = totalMissionaries - startMissionaries;
			int otherCannibals = totalCannibals - startCannibals;
			int boatPlace = node.state[2];
			
			if (startMissionaries != 0 && otherMissionaries != 0){
				// must have more missionaries than cannibals or else eaten :(
				if (startMissionaries >= startCannibals && 
						otherMissionaries >= otherCannibals){
					return true;
				}
			}
			
			// If no missionaries are on one side, must mean missionaries are on other side
			// Therefore, we check if the missionaries are outnumbered on the other side
			// Also starting with 1 Cannibal and 0 Missionaries on starting side is NOT 
			// a valid state
			if (otherMissionaries == 0){
				return startMissionaries >= startCannibals;
			}
			
			if (startMissionaries == 0){
				return otherMissionaries >= otherCannibals;
			}
			
			// not a safe state
			return false;
		}
		
		public ArrayList<UUSearchNode> getSuccessors() {
			// the final array to be returned
			ArrayList<UUSearchNode> retArr = new ArrayList<UUSearchNode>();
			
			int boatPlace = state[2];
			int candMissionaries = -1;
			int candCannibals = -1;
			int candBoat = -1;
			
			// Determine which missionaries and cannibals can travel
			if (boatPlace == 1){
				System.out.println("Boat @ starting side");
				// the candidate missionaries that can travel on the boat
				candMissionaries = state[0];
				candCannibals = state[1];
				candBoat = 0;
			} else if (boatPlace == 0){
				candMissionaries = totalMissionaries - state[0];
				candCannibals = totalCannibals - state[1];
				candBoat = 1;
			} else {
				// not valid input for boat
				System.out.println("Boat place not valid: " + boatPlace);
				System.exit(1);
			}
			
			for(int missCount=candMissionaries; missCount>=0; missCount--){
				for(int cannCount=candCannibals; cannCount>=0; cannCount--){
					System.out.println("Checking ("+missCount+","+cannCount+")");
					CannibalNode possNode;
					
					// Must fit in boat and have at least one missionary rowing
					if (missCount + cannCount <= BOAT_SIZE && (missCount > 0 || cannCount > 0)){
						// Must have something happen
						if (missCount == 0 && cannCount == 0){
							continue;
						}
						
						if (boatPlace == 1){
							// boat on starting side so starting side is subtracted
							possNode = new CannibalNode(candMissionaries - missCount, 
									candCannibals - cannCount, candBoat, depth+1);
						} else {
							// boat on other side so starting side is added
							possNode = new CannibalNode( state[0] + missCount, 
									state[1] + cannCount, candBoat, depth+1);
						}
						System.out.println("("+possNode.state[0]+","+possNode.state[1]+","+possNode.state[2]+")");
					} else {
						continue;
					}
					
					boolean isSafe = isSafeState(possNode);
					if (isSafe){
						// State is valid, add to the array
						System.out.println("Adding " + possNode);
						retArr.add(possNode);
					} else {
						// Node was not a valid state
						System.out.println("Not a safe state!");
					}
				}
			}
			return retArr;

		}
		
		@Override
		public boolean goalTest() {
			// you write this method.  (It should be only one line long.)
			return state[0] == goalm && state[1] == goalc;
		}

		// an equality test is required so that visited lists in searches
		// can check for containment of states
		@Override
		public boolean equals(Object other) {
			return Arrays.equals(state, ((CannibalNode) other).state);
		}

		@Override
		public int hashCode() {
			return state[0] * 100 + state[1] * 10 + state[2];
		}

		@Override
		public String toString() {
			return state[0] + ", " + state[1] + ", " + state[2] + ", (" + depth + ")|";
		}

		/*
        You might need this method when you start writing 
        (and debugging) UUSearchProblem.
		*/
        
		// temporary method, to be moved later
		@Override
		public int getDepth() {
			return depth;
		}

	}

	

}
