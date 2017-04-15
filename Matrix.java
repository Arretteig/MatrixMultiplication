package TermProject;

/**
 * @author Ayushi Patel, Bryan Arretteig, Chandler Jones
 * A matrix class to create an object that represents a matrix
 */
public class Matrix {

	// A 2x3 Matrix has 2 rows and 3 columns. 
	
	/**
	 * data variables
	 */
	private int rows;
	private int columns;
	
	private int[][] matrix;
	/**
	 * Constructor for matrix
	 * @param r (rows)
	 * @param c (columns)
	 */
	public Matrix(int r, int c){
		this.rows = r;
		this.columns = c;
		this.matrix = new int[r][c];
	}
	
	/**
	 * Gets number of rows
	 * @return rows
	 */
	public int getRows(){
		return rows;
	}
	
	/**
	 * Gets number of columns
	 * @return columns
	 */
	public int getColumns(){
		return columns;
	}
	
	/**
	 * Sets value at specific location
	 * @param row
	 * @param col
	 * @param value
	 */
	public void setValue(int row, int col, int value){
		matrix[row][col] = value;
	}
	
	/**
	 * Get value at specific location
	 * @param row
	 * @param col
	 * @return value 
	 */
	public int getValue(int row, int col){
		return matrix[row][col];
	}
	
}
