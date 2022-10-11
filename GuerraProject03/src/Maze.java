import java.util.ArrayList;
import java.util.Collections;

import edu.du.dudraw.DUDraw;

public class Maze {
	private enum CellValue {
		WALL, OPEN, EXPLORED
	};

	private CellValue[][] cells;
	private DLLStack<Cell> cellStack;
	private DLLQueue<Cell> cellQueue;
	private Cell currentCell;
	private Cell northCell;
	private Cell southCell;
	private Cell westCell;
	private Cell eastCell;
	private int row;
	private int col;

	public Maze(int r, int c) {
		// sets the number of rows and columns
		col = c;
		row = r;
		DUDraw.enableDoubleBuffering();
		// creates a canvas for the maze
		DUDraw.setCanvasSize(col * 10, row * 10);
		DUDraw.setXscale(-0.5, r - 0.5);
		DUDraw.setYscale(-0.5, c - 0.5);
		cells = new CellValue[r][c];
		cellStack = new DLLStack<Cell>();
		// instantiates every CellValue to be WALL
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				cells[i][j] = CellValue.WALL;
			}
		}

	}

	public void draw() {
		DUDraw.clear();
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				// change the pen color based on the CellValue and redraw the rectangle
				if (cells[i][j] == CellValue.WALL) {
					DUDraw.setPenColor(DUDraw.BLACK);
				} else if (cells[i][j] == CellValue.OPEN) {
					DUDraw.setPenColor(DUDraw.WHITE);
				} else if (cells[i][j] == CellValue.EXPLORED) {
					DUDraw.setPenColor(DUDraw.GREEN);
				}
				DUDraw.filledRectangle(j, i, 0.5, 0.5);
			}
		}
		DUDraw.show();
	}

	public void generateMaze() {
		int xStart = 15;
		int yStart = 15;


		int xPos = xStart;
		int yPos = yStart;
		currentCell = new Cell(xStart, yStart);
		ArrayList<Cell> neighbors = null;

		// set the currentCell to Open
		cells[yStart][xStart] = CellValue.OPEN;
		// push currentCell on the stack
		cellStack.push(currentCell);
		// while stack is not empty
		while (!cellStack.isEmpty()) {

			// pop the stack into currentCell
			currentCell = cellStack.pop();
			// initialize arrayList neighbors
			neighbors = new ArrayList<Cell>();
			// do the following for all of the neighboring cells c of currentCell
			// (Note that the neighbors are to the north, south, east, west.
			// and Neighbors are two away in each direction from this cell.)
			// Check if c is within the array and has value Wall (see picture below)

			// North cell
			if (yPos + 2 < cells.length) {
				if (cells[yPos + 2][xPos].equals(CellValue.WALL)) {
					// set the current neighbor cell c to open and add it to neighbors.
					northCell = new Cell(xPos, yPos + 2);
					cells[yPos + 2][xPos] = CellValue.OPEN;
					neighbors.add(northCell);
					// set the cell between currentCell and c (labeled "wall to remove" in picture
					// below) to open (don't add to neighbors)
					cells[yPos + 1][xPos] = CellValue.OPEN;
				}
			}

			// South cell
			if (yPos - 2 > 0) {
				if (cells[yPos - 2][xPos].equals(CellValue.WALL)) {
					// set the current neighbor cell c to open and add it to neighbors.
					southCell = new Cell(xPos, yPos - 2);
					cells[yPos - 2][xPos] = CellValue.OPEN;
					neighbors.add(southCell);
					// set the cell between currentCell and c (labeled "wall to remove" in picture
					// below) to open (don't add to neighbors)
					cells[yPos - 1][xPos] = CellValue.OPEN;
				}
			}

			// West cell
			if (xPos - 2 > 0) {
				if (cells[yPos][xPos - 2].equals(CellValue.WALL)) {
					// set the current neighbor cell c to open and add it to neighbors.
					westCell = new Cell(xPos - 2, yPos);
					cells[yPos][xPos - 2] = CellValue.OPEN;
					neighbors.add(westCell);
					// set the cell between currentCell and c (labeled "wall to remove" in picture
					// below) to open (don't add to neighbors)
					cells[yPos][xPos - 1] = CellValue.OPEN;
				}
			}

			// East cell
			if (xPos + 2 < cells[0].length) {
				if (cells[yPos][xPos + 2].equals(CellValue.WALL)) {
					// set the current neighbor cell c to open and add it to neighbors.
					eastCell = new Cell(xPos + 2, yPos);
					cells[yPos][xPos + 2] = CellValue.OPEN;
					neighbors.add(eastCell);
					// set the cell between currentCell and c to open (don't add to neighbors)
					cells[yPos][xPos + 1] = CellValue.OPEN;
				}
			}

			// shuffle the ArrayList neighbors
			Collections.shuffle(neighbors);

			// push all the content of neighbors on the stack
			for (int i = 0; i < neighbors.size(); i++) {
				cellStack.push(neighbors.get(i));
			}

			// update the x and y positions to the cell at the top of the stack
			if (!cellStack.isEmpty()) {
				xPos = cellStack.top().getX();
				yPos = cellStack.top().getY();
			}

			// draw the updated maze
			draw();
			DUDraw.show();
		}
	}

	public void solveMaze() {
		Cell start = new Cell(1, 1);
		Cell goal = new Cell(col - 2, row - 2);
		int xPos = 1;
		int yPos = 1;
		currentCell = start;
		cellQueue = new DLLQueue<Cell>();
		// set the currentCell to Explored
		cells[1][1] = CellValue.EXPLORED;
		// enqueue currentCell on the queue
		cellQueue.enqueue(currentCell);
		// while queue is not empty
		while (!cellQueue.isEmpty()) {
			draw();
			// update x and y if the queue is not empty
			if (!cellQueue.isEmpty()) {
				xPos = cellQueue.top().getX();
				yPos = cellQueue.top().getY();
			}
			// dequeue the queue into currentCell
			currentCell = cellQueue.dequeue();
			// if currentCell is the goal then return (we found the target)
			if (currentCell.equals(goal)) {
				return;
			}

//				    for each neighboring Cell n (those are cells that are one step away) that is not Explored:
//				        set neighbor n to Explored and enqueue it on the queue

			// North cell
			if (yPos + 1 < cells.length) {
				if (cells[yPos + 1][xPos].equals(CellValue.OPEN)) {
					// set the current neighbor cell n to explored and enqueue it.
					cells[yPos + 1][xPos] = CellValue.EXPLORED;
					northCell = new Cell(xPos, yPos + 1);
					cellQueue.enqueue(northCell);
					draw();
				}
			}

			// South cell
			if (yPos - 1 > 0) {
				if (cells[yPos - 1][xPos].equals(CellValue.OPEN)) {
					// set the current neighbor cell n to explored and enqueue it.
					cells[yPos - 1][xPos] = CellValue.EXPLORED;
					southCell = new Cell(xPos, yPos - 1);
					cellQueue.enqueue(southCell);
					draw();
				}
			}

			// West cell
			if (xPos - 1 > 0) {
				if (cells[yPos][xPos - 1].equals(CellValue.OPEN)) {
					// set the current neighbor cell n to explored and enqueue it
					cells[yPos][xPos - 1] = CellValue.EXPLORED;
					westCell = new Cell(xPos - 1, yPos);
					cellQueue.enqueue(westCell);
					draw();
				}
				
			}

			// East cell
			if (xPos + 1 < cells[0].length) {
				if (cells[yPos][xPos + 1].equals(CellValue.OPEN)) {
					// set the current neighbor cell n to explored and enqueue it					
					cells[yPos][xPos + 1] = CellValue.EXPLORED;
					eastCell = new Cell(xPos + 1, yPos);
					cellQueue.enqueue(eastCell);
					draw();
				}
			}

		} 
	}

	public class Cell {
		private int xPosition;
		private int yPosition;

		public Cell(int x, int y) {
			xPosition = x;
			yPosition = y;
		}

		public int getX() {
			return xPosition;
		}

		public int getY() {
			return yPosition;
		}

		public String toString() {
			return "xPosition = " + xPosition + "\tyPosition = " + yPosition;

		}
	}
}
