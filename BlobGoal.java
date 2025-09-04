package assignment3;

import java.awt.Color;

public class BlobGoal extends Goal{

	public BlobGoal(Color c) {
		super(c);
	}

	@Override
	public int score(Block board) {
		
		Color[][] blocksArray = board.flatten();
		int rowLength = blocksArray.length;
		int colLength = blocksArray[0].length;
		boolean[][] visited = new boolean[rowLength][colLength];
		
		int maxScore = 0; // the minimum possible score is 0 points 
		
		for (int i = 0; i < rowLength; i++) {
			for (int j = 0; j < colLength; j++) {
				int score = undiscoveredBlobSize(i, j, blocksArray, visited);
				if (score > maxScore) { // update max score
					maxScore = score;
				}
			}
		}
			
		return maxScore;
	}

	@Override
	public String description() {
		return "Create the largest connected blob of " + GameColors.colorToString(targetGoal) 
		+ " blocks, anywhere within the block";
	}


	public int undiscoveredBlobSize(int i, int j, Color[][] unitCells, boolean[][] visited) {

		Color cGoal = this.targetGoal; 
		int rowLength = unitCells.length;
		int colLength = unitCells[0].length;
		
	    // Check if the element is out of bounds 
		if(i < 0 || j < 0 || i >= rowLength || j >= colLength ) {
			return 0;
		}
		
		// Check if its the correct color
		if(!unitCells[i][j].equals(cGoal)) {
			return 0;
        }
		
		// to avoid double count 
		if(visited[i][j]) {
		    return 0;
		}
		
		visited[i][j] = true; // to show this one has been visited 
		int count = 1;
		
		
		// visit the neighbours cells of the unit cell
		
		count = count + undiscoveredBlobSize(i, j - 1,unitCells, visited); // visit the cell above
		count = count + undiscoveredBlobSize(i, j + 1,unitCells, visited); // visit the cell below 
		count = count + undiscoveredBlobSize(i + 1, j,unitCells, visited); // visit the cell in the right
		count = count + undiscoveredBlobSize(i - 1, j,unitCells, visited); // visit the cell in the left
		
		
		return count;

	}

}
