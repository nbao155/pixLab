public class IntArrayWorker
{
  /** two dimensional matrix */
  private int[][] matrix = null;
  
  /** set the matrix to the passed one
    * @param theMatrix the one to use
    */
  public void setMatrix(int[][] theMatrix)
  {
    matrix = theMatrix;
  }
	/**
	 * Counts the number of times an input Integer is present in the matrix
	 * @param input		The input int
	 * @return 			The number of times input occurs in matrix
	 */
	public int getCount(int input){
		int counter = 0;
		for(int row = 0;row<matrix.length;row++){
			for(int col = 0;col<matrix[0].length;col++){
				if(matrix[row][col]==input){
					counter++;
				}
			}
		}
		return counter;
	}
	/**
	 * Swaps the values in each row of matrix
	 * @return 		The modified array
	 */
	public int[][] reverseRows(){
		int lastVal;
		int counter = 0;
		for(int row = 0;row<matrix.length;row++){
			while(counter<matrix[0].length-1-counter){
				lastVal = matrix[row][matrix[0].length-1-counter];
				matrix[row][matrix[0].length-1-counter] = matrix[row][counter];
				matrix[row][counter] = lastVal;
				counter++;
			}
			counter = 0;
		}	
		return matrix;
	}
	/**
	 * Returns the total of all values in a specified column in matrix
	 * @param	column		The input column
	 * @return total of all values
	 */
	public int getColTotal(int column){
		int total = 0;
		try{
			total = matrix[0][column];
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Column indice is out of bounds");
			return -1;
		}
		total = 0;
		for(int row = 0;row<matrix.length;row++){
			total += matrix[row][column];
		}
		return total;
	}
	/**
	 * Finds the largest value in matrix
	 * @return	The largest value in matrix
	 */
	public int getLargest(){
		int largest = matrix[0][0];
		for(int row = 0;row<matrix.length;row++){
			for(int col = 0;col<matrix[0].length;col++){
				if(largest<matrix[row][col]){
					largest = matrix[row][col];
				}
			}
		}
		return largest;
	}
  /**
   * Method to return the total 
   * @return the total of the values in the array
   */
  public int getTotal()
  {
    int total = 0;
    for (int row = 0; row < matrix.length; row++)
    {
      for (int col = 0; col < matrix[0].length; col++)
      {
        total = total + matrix[row][col];
      }
    }
    return total;
  }
  
  /**
   * Method to return the total using a nested for-each loop
   * @return the total of the values in the array
   */
  public int getTotalNested()
  {
    int total = 0;
    for (int[] rowArray : matrix)
    {
      for (int item : rowArray)
      {
        total = total + item;
      }
    }
    return total;
  }
  
  /**
   * Method to fill with an increasing count
   */
  public void fillCount()
  {
    int numCols = matrix[0].length;
    int count = 1;
    for (int row = 0; row < matrix.length; row++)
    {
      for (int col = 0; col < numCols; col++)
      {
        matrix[row][col] = count;
        count++;
      }
    }
  }
  
  /**
   * print the values in the array in rows and columns
   */
  public void print()
  {
    for (int row = 0; row < matrix.length; row++)
    {
      for (int col = 0; col < matrix[0].length; col++)
      {
        System.out.print( matrix[row][col] + " " );
      }
      System.out.println();
    }
    System.out.println();
  }
  
  
  /** 
   * fill the array with a pattern
   */
  public void fillPattern1()
  {
    for (int row = 0; row < matrix.length; row++)
    {
      for (int col = 0; col < matrix[0].length; 
           col++)
      {
        if (row < col)
          matrix[row][col] = 1;
        else if (row == col)
          matrix[row][col] = 2;
        else
          matrix[row][col] = 3;
      }
    }
  }
 
}
