
public class Matrix {

	private int rows;
	private int columns;
	
	private int[][] matrix;
	
	public Matrix(int c, int r){
		this.rows = r;
		this.columns = c;
	}
	
	public int getRows(){
		return rows;
	}

	public int getColumns(){
		return columns;
	}
	
	public void setValue(int row, int col, int value){
		matrix[row][col] = value;
	}
	
	public int getValue(int row, int col){
		return matrix[row][col];
	}
	
}
