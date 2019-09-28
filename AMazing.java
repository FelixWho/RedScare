/* AMazing.java 
** 
** 2011-17 APCS
** Dr John Pais
** pais.john@gmail.com
*/

public class AMazing 
{
	private int N;                      // dimension for maze with N x N cells - with boundary (N+2)x(N+2)
	public boolean[][] cellNorthWall;  // existence of wall to the north of cell(i,j)
	public boolean[][] cellEastWall;   // existence of wall to the east of cell(i,j)
	public boolean[][] cellSouthWall;  // existence of wall to the south of cell(i,j)
	public boolean[][] cellWestWall;   // existence of wall to the west of cell(i,j)
	private boolean[][] cellVisited;    // has cell(i,j) been visited yet in the maze generation
	private boolean done = false;
	
	public AMazing(int N) 
	{
	    this.N = N;
	    StdDraw.setXscale(0, N+2);
	    StdDraw.setYscale(0, N+2);
	    initialize();
	    createMaze();// 5. After completing 1-4 below, insert appropriate code here.
	    
	}

	private void initialize() 
	{
	    // Initialize border cells as already visited since these (dummy) boundary neighbors
		// will need to have boolean values in order for the createMaze method to be able
		// to easily refer to and check all neighbors of each cell. Actual maze indexes
		// run from 1 to N.
	    cellVisited = new boolean[N+2][N+2];
	    for (int i = 0; i < N+2; i++) 
	    { 
	    	cellVisited[i][0] = cellVisited[i][N+1] = true;
	    }
	    
	    for (int j = 0; j < N+2; j++)
	    {	
	    	cellVisited[0][j] = cellVisited[N+1][j] = true;
	    }

	    // initialze all walls as present
	    cellNorthWall = new boolean[N+2][N+2];
	    cellEastWall  = new boolean[N+2][N+2];
	    cellSouthWall = new boolean[N+2][N+2];
	    cellWestWall  = new boolean[N+2][N+2];
	    for (int i = 0; i < N+2; i++)
	    {
	         for (int j = 0; j < N+2; j++)
	         {
	                cellNorthWall[i][j] = cellEastWall[i][j] = cellSouthWall[i][j] = cellWestWall[i][j] = true;
	         }
	    }
	}

    // create the maze starting from cell(1,1) as base case of recursion
    private void createMaze() 
    {
    	createMaze(1, 1);
    }

	// recursively create the maze with random wall placement
	private void createMaze(int i, int j) 
	{
	    cellVisited[i][j] = true;

	    // while cell(i,j) has an unvisited neighbor 
	    while (!cellVisited[i][j+1] || !cellVisited[i+1][j] ||
	    	   !cellVisited[i][j-1] || !cellVisited[i-1][j]) 
	    {
	         
	         double r = Math.random();
	         if (r < 0.25 && !cellVisited[i][j+1]) 
	         {
	        	
	        	cellNorthWall[i][j] = false;
	        	cellSouthWall[i][j+1] = false;
	        	createMaze(i, j+1);
	        	// 1. Insert appropriate code here to kill some walls
	        	//    and recursively call this method again.
	        	 
	        	 
	         }  
	         else if (0.25 <= r && r < 0.50 && !cellVisited[i+1][j]) 
	         {
	        	 cellEastWall[i][j] = false;
	        	 cellWestWall[i+1][j] =false;
	        	 createMaze(i+1,j);
	        	// 2. Insert appropriate code here to kill some walls
		        //    and recursively call this method again.
	        	 
	        	 
	         }
	         else if (0.5 <= r && r < 0.75 && !cellVisited[i][j-1]) 
	         {
	        	
	        	 cellSouthWall[i][j]= false;
	        	 cellNorthWall[i][j-1] =false;
	        	 createMaze(i, j-1);
	        	 // 3. Insert appropriate code here to kill some walls
		        //    and recursively call this method again.
	        	 
	        	 
	         }
	         else if (0.75 <= r && r < 1.00 && !cellVisited[i-1][j]) 
	         {
	        	 cellWestWall[i][j] = false;
	        	 cellEastWall[i-1][j] = false;
	        	 createMaze(i-1,j);
	        	// 4. Insert appropriate code here to kill some walls
		        //    and recursively call this method again.
	        	 
	        	 
	         }
	    }
	}

    // solve the maze starting from the start state as base 
    // case of recursion and depth-first search
    public void solveMaze() 
    {
        for (int i = 1; i <= N; i++)
        {
            for (int j = 1; j <= N; j++)
            {
                cellVisited[i][j] = false;  // re-initialize all internal maze cells as unvisited
            }
        }
        done = false;
        solveMaze(1, 1);
    }
	
    // solve the maze using recursive depth-first search
    private void solveMaze(int i, int j) 
    {
        if (i == 0 || j == 0 || i == N+1 || j == N+1) return;
        if (done || cellVisited[i][j]) return;
        cellVisited[i][j] = true;

        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.filledCircle(i + 0.5, j + 0.5, 0.25);
        StdDraw.show(30);

        // reached middle
        if (i == N/2 && j == N/2) done = true;

        // Only after completing and testing parts 1-6:
        // 7. Insert code to recursively explore an appropriate direction.
        
        if(done==false &&cellVisited[i][j+1]==false && cellNorthWall[i][j] == false){
        	solveMaze(i, j+1);
        }
        // 8. Insert code to recursively explore an appropriate direction.
       if(done ==false &&cellVisited[i+1][j]==false&& cellEastWall[i][j]==false){
        	solveMaze(i+1,j);
        }
        
        // 9. Insert code to recursively explore an appropriate direction.
        if(done==false &&cellVisited[i][j-1]==false &&cellSouthWall[i][j]==false){
        	solveMaze(i, j-1);
        }
        if(done==false&&cellVisited[i-1][j]==false &&cellWestWall[i][j]==false){
        	solveMaze(i-1, j);
        }
        
        // 10. Insert code to recursively explore an appropriate direction.
       

        if (done) return;

        StdDraw.setPenColor(StdDraw.GRAY);
        StdDraw.filledCircle(i + 0.5, j + 0.5, 0.25);
        StdDraw.show(30);
    }
    
    // draw the maze without a trace
    public void drawMaze() 
    {
    	drawMaze(false);
    }

    // draw the maze with or without a trace
    public void drawMaze(boolean trace) 
    {
        int delay;
        if(trace)
        {
           delay = 500;
           StdDraw.setXscale(0, N+1);
   	       StdDraw.setYscale(0, N+1);
           StdDraw.setPenColor(StdDraw.BLUE);
	       StdDraw.line(0, 0, 0, N+1);
	       StdDraw.line(0, N+1, N+1, N+1);
	       StdDraw.line(N+1, N+1, N+1,0);
	       StdDraw.line(N+1, 0, 0, 0);   
	       StdDraw.textLeft(0, 0.5, "All border walls (i = 0 or N+1, and j = 0 or N+1) shown intially in blue.");
	       StdDraw.setPenColor(StdDraw.BLACK);
        }    
        else
        {
           delay = 0;
        }
        
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledCircle(N/2 + 0.5, N/2 + 0.5, 0.375);
        StdDraw.filledCircle(1.5, 1.5, 0.375);
        StdDraw.setPenColor(StdDraw.BLACK);
        
        for (int i = 1; i < N+1; i++) 
        {
            for (int j = 1; j < N+1; j++)
            {
            	
            	String walls = "";
                if (cellSouthWall[i][j]) 
                {	
                	walls += "S";
                	// draw south wall using cell i, j indexes as coordinates of SW corner of cell
                	StdDraw.line(i, j, i + 1, j);
                	StdDraw.show(delay); 
                }
                if (cellNorthWall[i][j]) 
                {	
                	walls += "N";
                	// draw north wall using cell i, j indexes as coordinates of SW corner of cell
                	StdDraw.line(i, j + 1, i + 1, j + 1);
                	StdDraw.show(delay); 
                }
                if (cellWestWall[i][j])
                {	
                	walls += "W";
                	// draw west wall using cell i, j indexes as coordinates of SW corner of cell
                	StdDraw.line(i, j, i, j + 1);
                	StdDraw.show(delay); 
                }
                if (cellEastWall[i][j])  
                {	
                	walls += "E";
                	// draw east wall using cell i, j indexes as coordinates of SW corner of cell
                	StdDraw.line(i + 1, j, i + 1, j + 1);	                	
                	StdDraw.show(delay); 
                }
                if(trace)
                {
                   // draw point at SW corner of cell using cell i, j indexes as coordinates of this point
                   StdDraw.setPenRadius(.01);
                   StdDraw.point(i, j);
                   StdDraw.setPenRadius();
                   // draw text for point at SW corner
                   StdDraw.text(i, j , "          (" + i + "," + j + ")");
                   // draw text coding walls around cell(i,j)
                   StdDraw.text(i + 0.5, j + 0.65, walls);
                   StdDraw.text(i + 0.5, j + 0.4, "cell(" + i + "," + j + ")");
                }
            }
        }
        StdDraw.show(1000);
    }

}
