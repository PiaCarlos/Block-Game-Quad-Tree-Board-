package assignment3;

import java.awt.Color;

public class PerimeterGoal extends Goal{

	public PerimeterGoal(Color c) {
		super(c);
	}

	@Override
	public int score(Block board) {
		
		Color c = this.targetGoal;
		Color[][] colorsArray = board.flatten();
		int count = 0;
		int arraySize = colorsArray.length;
		
		// count the score the case that maxdepth is 0
		if(arraySize == 1) {
			count = count + 2;
			return count;
		}
		
		// count the score for other cases
		for (int i = 0; i < arraySize; i++) {
			if(colorsArray[i][0] == c) {
				count++;
			}
			if(colorsArray[0][i] == c) {
				count++;
			}
			if(colorsArray[i][arraySize-1] == c) {
				count++;
			}
			if(colorsArray[arraySize-1][i] == c) {
				count++;
			}	
		}
		
		return count;
	}

	@Override
	public String description() {
		return "Place the highest number of " + GameColors.colorToString(targetGoal) 
		+ " unit cells along the outer perimeter of the board. Corner cell count twice toward the final score!";
	}

}
