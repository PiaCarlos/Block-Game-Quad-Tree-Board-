package assignment3;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;

public class Block {
	private int xCoord;
	private int yCoord;
	private int size; // height/width of the square
	private int level; // the root (outer most block) is at level 0
	private int maxDepth;
	private Color color;

	private Block[] children; // {UR, UL, LL, LR}

	public static Random gen = new Random(4);

	public static void main(String[] args) {

		// create top block - level 0
		Block top_level = new Block(0, 0, 16, 0, 2, null, new Block[0]);

		// create sub blocks - level 1
		Block up_left = new Block(0, 0, 8, 1, 2, GameColors.RED, new Block[0]);
		Block up_right = new Block(8, 0, 8, 1, 2, GameColors.GREEN, new Block[0]);
		Block down_left = new Block(0, 8, 8, 1, 2, GameColors.YELLOW, new Block[0]);
		Block down_right = new Block(8, 8, 8, 1, 2, null, new Block[4]);

		// create sub blocks - level 2

		Block[] sub_blocks1 = { up_right, up_left, down_left, down_right };

		Block[] sub_blocks2 = { new Block(12, 8, 4, 2, 2, GameColors.BLUE, new Block[0]), // UR
				new Block(8, 8, 4, 2, 2, GameColors.RED, new Block[0]), // UL
				new Block(8, 12, 4, 2, 2, GameColors.YELLOW, new Block[0]), // LL
				new Block(12, 12, 4, 2, 2, GameColors.BLUE, new Block[0]), // LR
		};

		down_right.children = sub_blocks2;

		top_level.children = sub_blocks1;

		// top_level.printBlock();

		//Block blockDepth2 = new Block(0, 0);
		// blockDepth2.printColoredBlock();
		// ArrayList<BlockToDraw> blocksToDraw = blockDepth2.getBlocksToDraw();
		// blockDepth2.printBlock();
		// System.out.print(blocksToDraw.size());

		//Block blockDepth3 = new Block(0, 3);
		//blockDepth3.updateSizeAndPosition(16, 0, 0);
		// blockDepth3.printBlock();
		//Block b1 = blockDepth3.getSelectedBlock(2, 15, 1);
		//b1.printBlock();

		// blockDepth2.printBlock();
		// blockDepth2.reflect(0);
		// System.out.print("Reflected \n \n \n");
		// blockDepth2.printBlock();

		//Block blockDepth3 = new Block(0, 3);
		//blockDepth3.updateSizeAndPosition(16, 0, 0);
		//Block b1 = blockDepth3.getSelectedBlock(2, 15, 1);
		//b1.printBlock();
		//Block b2 = blockDepth3.getSelectedBlock(3, 5, 2);

		//System.out.print("exercice 2 \n \n \n");
		//b2.printBlock();
		
		//Block blockDepth3 = new Block(0,3);
		//blockDepth3.updateSizeAndPosition(16, 0, 0);
		//Block b2 = blockDepth3.getSelectedBlock(3, 5, 2);
		//b2.printBlock();
		
	
	}

	/*
	 * These two constructors are here for testing purposes.
	 */
	public Block() {
	}

	public Block(int x, int y, int size, int lvl, int maxD, Color c, Block[] subBlocks) {
		this.xCoord = x;
		this.yCoord = y;
		this.size = size;
		this.level = lvl;
		this.maxDepth = maxD;
		this.color = c;
		this.children = subBlocks;
	}

	/*
	 * Creates a random block given its level and a max depth.
	 * 
	 * xCoord, yCoord, size, and highlighted should not be initialized (i.e. they
	 * will all be initialized by default)
	 */
	public Block(int lvl, int maxDepth) {

		// creating the base values
		this.level = lvl;
		this.maxDepth = maxDepth;

			// the random number must reach this condition to be subdivided further
			if (lvl < maxDepth && gen.nextDouble() < Math.exp(-0.25 * lvl)) {

				this.children = new Block[4];
				int i = 0;

				// creating the 4 sub blocks for the current block
				while (i < 4) {
					this.children[i] = new Block(lvl + 1, maxDepth);
					i++;
				}
			}

			else {
				this.children = new Block[0];
				int randomColor = gen.nextInt(4);
				Color BlockColor = GameColors.BLOCK_COLORS[randomColor];
				this.color = BlockColor;
			}

		} 

	/*
	 * Updates size and position for the block and all of its sub-blocks, while
	 * ensuring consistency between the attributes and the relationship of the
	 * blocks.
	 * 
	 * The size is the height and width of the block. (xCoord, yCoord) are the
	 * coordinates of the top left corner of the block.
	 */
	public void updateSizeAndPosition(int size, int xCoord, int yCoord) {

		this.size = size;
		this.xCoord = xCoord;
		this.yCoord = yCoord;

		// Check if it is positive and can be divided further, if not, throw an error
		if (this.level < this.maxDepth && (size < 0 || size % 2 != 0)) {
			throw new IllegalArgumentException("Invalid size for this level of depth");
		}

		// update size and position for the children if they exist
		if (this.children.length != 0) {
			size = size / 2;
			children[0].updateSizeAndPosition(size, xCoord + size, yCoord); // UR
			children[1].updateSizeAndPosition(size, xCoord, yCoord); // UL
			children[2].updateSizeAndPosition(size, xCoord, yCoord + size); // LL
			children[3].updateSizeAndPosition(size, xCoord + size, yCoord + size); // LR
		}

	}

	/*
	 * Returns a List of blocks to be drawn to get a graphical representation of
	 * this block.
	 * 
	 * This includes, for each undivided Block: - one BlockToDraw in the color of
	 * the block - another one in the FRAME_COLOR and stroke thickness 3
	 * 
	 * Note that a stroke thickness equal to 0 indicates that the block should be
	 * filled with its color.
	 * 
	 * The order in which the blocks to draw appear in the list does NOT matter.
	 */
	public ArrayList<BlockToDraw> getBlocksToDraw() {

		ArrayList<BlockToDraw> blocksArray = new ArrayList<>();

		if (this.color != null) {
			// adding one BlockToDraw in the color of the block
			blocksArray.add(new BlockToDraw(this.color, this.xCoord, this.yCoord, this.size, 0));

			// adding another BlockToDraw in the frame color
			blocksArray.add(new BlockToDraw(GameColors.FRAME_COLOR, this.xCoord, this.yCoord, this.size, 3));
		}

		// adding both blockTODraw for all blocks
		int i = 0;
		while (i < this.children.length) {
			blocksArray.addAll(this.children[i].getBlocksToDraw());
			i++;
		}

		return blocksArray;

	}

	/*
	 * This method is provided and you should NOT modify it.
	 */
	public BlockToDraw getHighlightedFrame() {
		return new BlockToDraw(GameColors.HIGHLIGHT_COLOR, this.xCoord, this.yCoord, this.size, 5);
	}

	/*
	 * Return the Block within this Block that includes the given location and is at
	 * the given level. If the level specified is lower than the lowest block at the
	 * specified location, then return the block at the location with the closest
	 * level value.
	 * 
	 * The location is specified by its (x, y) coordinates. The lvl indicates the
	 * level of the desired Block. Note that if a Block includes the location (x,
	 * y), and that Block is subdivided, then one of its sub-Blocks will contain the
	 * location (x, y) too. This is why we need lvl to identify which Block should
	 * be returned.
	 * 
	 * Input validation: - this.level <= lvl <= maxDepth (if not throw exception) -
	 * if (x,y) is not within this Block, return null.
	 */
	
	public Block getSelectedBlock(int x, int y, int lvl) {
		
		// Check if the level provided is valid
		if (lvl < 0 || this.level > lvl || lvl > this.maxDepth) {
			throw new IllegalArgumentException("Invalid level for this block");
		}

		// Check if the position is within the block
		if ((x < this.xCoord || x >= this.xCoord + this.size) || (y < this.yCoord || y >= this.yCoord + this.size)) {
			return null;
		}

		if (lvl == this.level || this.children.length == 0) {
			return this;
		}

		else {
			int i = 0;
			while(i < this.children.length) {
				Block selectedBlock = this.children[i].getSelectedBlock(x, y, lvl);
					if (selectedBlock != null) {
					return selectedBlock;
					}
				i++;
			}
		}

		return null;
	}

	/*
	 * Swaps the child Blocks of this Block. If input is 1, swap vertically. If 0,
	 * swap horizontally. If this Block has no children, do nothing. The swap should
	 * be propagate, effectively implementing a reflection over the x-axis or over
	 * the y-axis.
	 * 
	 */
	public void reflect(int direction) {

		if (direction != 0 && direction != 1) {
			throw new IllegalArgumentException("Invalid direction");
		}

		// to reflect, takes the center as the origin
		if (direction == 0) {

			if (this.children.length != 0) { // reflect on x-axis

				Block temp_UR = this.children[0];
				Block temp_UL = this.children[1];
				this.children[0] = this.children[3];
				this.children[1] = this.children[2];
				this.children[2] = temp_UL;
				this.children[3] = temp_UR;
			}

		} else {

			if (this.children.length != 0) { // reflect on y-axis
				Block temp_UR = this.children[0];
				Block temp_LR = this.children[3];
				this.children[0] = this.children[1];
				this.children[1] = temp_UR;
				this.children[3] = this.children[2];
				this.children[2] = temp_LR;
			}

		}

		this.updateSizeAndPosition(this.size, this.xCoord, this.yCoord);

		int i = 0;
		while (i < this.children.length) {
			this.children[i].reflect(direction);
			i++;
		}

	}

	/*
	 * Rotate this Block and all its descendants. If the input is 1, rotate
	 * clockwise. If 0, rotate counterclockwise. If this Block has no children, do
	 * nothing.
	 */
	public void rotate(int direction) {

		if (direction != 0 && direction != 1) {
			throw new IllegalArgumentException("Invalid direction");
		}

		if (direction == 1) { // clockwise

			if (this.children.length != 0) { // rotate on x-axis

				Block temp_UR = this.children[0];
				this.children[0] = this.children[1];
				this.children[1] = this.children[2];
				this.children[2] = this.children[3];
				this.children[3] = temp_UR;
			}
		} else {

			if (this.children.length != 0) { // rotate on y-axis
				Block temp_LR = this.children[3];
				this.children[3] = this.children[2];
				this.children[2] = this.children[1];
				this.children[1] = this.children[0];
				this.children[0] = temp_LR;
			}

		}

		this.updateSizeAndPosition(this.size, this.xCoord, this.yCoord);

		int i = 0;
		while (i < this.children.length) {
			this.children[i].rotate(direction);
			i++;
		}

	}

	/*
	 * Smash this Block.
	 * 
	 * If this Block can be smashed, randomly generate four new children Blocks for
	 * it. (If it already had children Blocks, discard them.) Ensure that the
	 * invariants of the Blocks remain satisfied.
	 * 
	 * A Block can be smashed iff it is not the top-level Block and it is not
	 * already at the level of the maximum depth.
	 * 
	 * Return True if this Block was smashed and False otherwise.
	 * 
	 */
	public boolean smash() {

		if (this.level == this.maxDepth || this.level == 0) {
			return false;
		}

		// Create and place the new children of the block
		this.children = null;
		this.children = new Block[4];
		int i = 0;
		while (i < this.children.length) {
			this.children[i] = new Block(this.level + 1, this.maxDepth);
			i++;
		}
		// update the children location
		 children[0].updateSizeAndPosition(size / 2, xCoord + size / 2, yCoord); // UR
		 children[1].updateSizeAndPosition(size / 2, xCoord, yCoord); // UL
		 children[2].updateSizeAndPosition(size / 2, xCoord, yCoord + size / 2); // LL
		 children[3].updateSizeAndPosition(size / 2, xCoord + size / 2, yCoord + size / 2); // LR
	
		return true;
	}

	/*
	 * Return a two-dimensional array representing this Block as rows and columns of
	 * unit cells.
	 * 
	 * Return and array arr where, arr[i] represents the unit cells in row i,
	 * arr[i][j] is the color of unit cell in row i and column j.
	 * 
	 * arr[0][0] is the color of the unit cell in the upper left corner of this
	 * Block.
	 */
	public Color[][] flatten() {

		int sizeArray = (int) Math.pow(2, this.maxDepth - this.level);
		Color[][] blocksArray = new Color[sizeArray][sizeArray];

		if (this.level == this.maxDepth) {
			blocksArray[0][0] = this.color;
		}

		else if (this.children.length == 0) {
			for (int i = 0; i < sizeArray; i++) {
				for (int j = 0; j < sizeArray; j++) {
					blocksArray[i][j] = this.color;
				}
			}
		}

		else {
			Color[][] flat0 = this.children[0].flatten();
			Color[][] flat1 = this.children[1].flatten();
			Color[][] flat2 = this.children[2].flatten();
			Color[][] flat3 = this.children[3].flatten();

			int flat1Size = flat1.length;
			for (int i = 0; i < flat1Size; i++) {
				for (int j = 0; j < flat1Size; j++) {
					blocksArray[i][j] = flat1[i][j];
				}
			}

			int flat0Size = flat0.length;
			for (int i = 0; i < flat0Size; i++) {
				for (int j = 0; j < flat0Size; j++) {
					blocksArray[i][j + flat1Size] = flat0[i][j];
				}
			}

			int flat2Size = flat2.length;
			for (int i = 0; i < flat2Size; i++) {
				for (int j = 0; j < flat2Size; j++) {
					blocksArray[i + flat1Size][j] = flat2[i][j];
				}
			}
			int flat3Size = flat3.length;
			for (int i = 0; i < flat3Size; i++) {
				for (int j = 0; j < flat3Size; j++) {
					blocksArray[i + flat0Size][j + flat2Size] = flat3[i][j];
				}
			}

		}
		return blocksArray;
	}

	// These two get methods have been provided. Do NOT modify them.
	public int getMaxDepth() {
		return this.maxDepth;
	}

	public int getLevel() {
		return this.level;
	}

	/*
	 * The next 5 methods are needed to get a text representation of a block. You
	 * can use them for debugging. You can modify these methods if you wish.
	 */
	public String toString() {
		return String.format("pos=(%d,%d), size=%d, level=%d", this.xCoord, this.yCoord, this.size, this.level);
	}

	public void printBlock() {
		this.printBlockIndented(0);
	}

	private void printBlockIndented(int indentation) {
		String indent = "";
		for (int i = 0; i < indentation; i++) {
			indent += "\t";
		}

		if (this.children.length == 0) {
			// it's a leaf. Print the color!
			String colorInfo = GameColors.colorToString(this.color) + ", ";
			System.out.println(indent + colorInfo + this);
		} else {
			System.out.println(indent + this);
			for (Block b : this.children)
				b.printBlockIndented(indentation + 1);
		}
	}

	private static void coloredPrint(String message, Color color) {
		System.out.print(GameColors.colorToANSIColor(color));
		System.out.print(message);
		System.out.print(GameColors.colorToANSIColor(Color.WHITE));
	}

	public void printColoredBlock() {
		Color[][] colorArray = this.flatten();
		for (Color[] colors : colorArray) {
			for (Color value : colors) {
				String colorName = GameColors.colorToString(value).toUpperCase();
				if (colorName.length() == 0) {
					colorName = "\u2588";
				} else {
					colorName = colorName.substring(0, 1);
				}
				coloredPrint(colorName, value);
			}
			System.out.println();
		}
	}

}
