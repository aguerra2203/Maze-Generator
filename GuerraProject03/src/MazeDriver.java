
public class MazeDriver {

	public static void main(String[] args) {
		Maze testMaze = new Maze(101, 101);
		testMaze.generateMaze();
		testMaze.solveMaze();
	}

}
