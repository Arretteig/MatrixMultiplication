
import java.io.IOException;
import java.util.Scanner;

/**
 * 
 * @author Ayushi Patel, Bryan Arretteig, Chandler Jones
 *	A class that defines methods utilizing top-down and bottom-up approaches to multiply matrices  
 */
public class MatrixChainMultiplication {

	/**
	 * data variables
	 */
	private static int[] p = { 30, 35, 15, 5, 10, 20, 25 }; // Array of dimensions
	private static int[][] m; // Tracks lowest scalar multiplications.
	private static int[][] s; // Tracks correct path.
	private static Matrix[] array;

	/**
	 * Finds the least amount of scalar multiplications when multiplying matrices and puts the smallest amount in m
	 * s array keeps track of the shortest path
	 */
	public static void matrixChainOrder() {
		int n = p.length - 1;
		m = new int[n + 1][n + 1];
		s = new int[n + 1][n + 1];
		for (int i = 1; i <= n; i++)
			m[i][i] = 0;
		for (int l = 2; l <= n; l++) {
			for (int i = 1; i <= n - l + 1; i++) {
				int j = i + l - 1;
				m[i][j] = Integer.MAX_VALUE;
				for (int k = i; k < j; k++) {
					int q = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];
					if (q < m[i][j]) {
						m[i][j] = q;
						s[i][j] = k;
					}
				}
			}
		}
	}


	/**
	 * toString method that prints the order of operations of the fastest way to multiply matrices together
	 * @param i 
	 * @param j 
	 */
	private static String printOptimalParens(int i, int j) {
		if (i == j)
			return " A" + i + " ";
		else
			return "(" + printOptimalParens(i, s[i][j]) + printOptimalParens(s[i][j] + 1, j) + ")";
	}

	public static void printOptimal() {
		System.out.println(printOptimalParens(1, p.length - 1));
	}

	public static Matrix recursiveChainMultiply(){
		int i = 0;
		int j = array.length-1;
		return recursiveChainMultiply(i, j);
	}
	
	public static Matrix recursiveChainMultiply(int i, int j) {
		if (i == j) {
			return array[i];
		} else {
			Matrix leftChain = recursiveChainMultiply(i, s[i][j]);
			Matrix rightChain = recursiveChainMultiply(s[i][j]+1, j);
			Matrix c = matrixMultiply(leftChain, rightChain);
			return c;
		}
	}

	/**
	 * Multiplies two matrices to produce one resultant matrix
	 * @param matrix A
	 * @param matrix B
	 */
	private static Matrix matrixMultiply(Matrix A, Matrix B) {
		Matrix result = new Matrix(A.getRows(), B.getColumns());
		if (A.getColumns() != B.getRows()) {
			System.out.println("CAN NOT MULTIPLY");
		} else {
			for (int i = 0; i < A.getRows(); i++) {
				for (int j = 0; j < B.getColumns(); j++) {
					result.setValue(i, j, 0);
					for (int k = 0; k < A.getColumns(); k++) {
						result.setValue(i, j, (result.getValue(i, j) + A.getValue(i, k) * B.getValue(k, j)));
					}
				}
			}
		}
		return result;
	}

	
	public static void memoizedMatrixChain() {
		int n = p.length - 1;
		m = new int[n + 1][n + 1];
		s = new int[n + 1][n + 1];
		for (int i = 1; i <= n; i++) {
			for (int j = i; j <= n; j++) {
				m[i][j] = Integer.MAX_VALUE;
			}
		}
		lookupChain(1, n);
	}

	public static int lookupChain(int i, int j) {
		if (m[i][j] < Integer.MAX_VALUE) {
			return m[i][j];
		}
		if (i == j) {
			m[i][j] = 0;
		} else {
			for (int k = i; k <= j - 1; k++) {
				int q = lookupChain(i, k) + lookupChain(k + 1, j) + p[i - 1] * p[k] * p[j];
				if (q < m[i][j]) {
					m[i][j] = q;
					s[i][j] = k;
				}
			}
		}
		return m[i][j];
	}

	public static void menu(Scanner input){
		try {
			start(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private static void start(Scanner input) throws IOException {
		System.out.println("Options 'default' or an integer. ");	
		System.out.print("How many Matrices?: ");
		
		try{
			getDimensions(input);
		}
		catch(NumberFormatException e){
			System.out.println("\nINVALID INPUT\n");
			start(input);
			System.exit(0);
		}
		finally{
			input.close();
			System.out.println();
		}
		
		createMatrices();
		
		long timeLTR = System.nanoTime();
		Matrix print = unoptimizedMultiplication();
		timeLTR = System.nanoTime() - timeLTR;
		
		long timeMCO = System.nanoTime();
		matrixChainOrder();
		timeMCO = System.nanoTime() - timeMCO;

		long timeMMO = System.nanoTime();
		memoizedMatrixChain();
		timeMMO = System.nanoTime() - timeMMO;
		
		long timeOpt = System.nanoTime();
		recursiveChainMultiply();
		timeOpt = System.nanoTime() - timeOpt;
		
		printOptimal();
		System.out.println("Matrix chain order runtime took " + timeMCO + " nanoseconds.");
		System.out.println("Memoized matrix chain runtime took " + timeMMO + " nanoseconds.");
		System.out.println("Multiplying in order of left to right took " + timeLTR + " nanoseconds.");
		System.out.println("Multiplying in optimal order took " + timeOpt + " nanoseconds.");
		System.out.println("Memoized matrix and multiplying total: [" + (timeMMO + timeOpt) + "] nanoseconds.");
	}


	private static Matrix unoptimizedMultiplication() {
		int i = 1;
		Matrix start = array[0];
		Matrix result = unoptimizedMultiplication(i, start);
		return result;
	}
	
	
	private static Matrix unoptimizedMultiplication(int i, Matrix start) {
		if(i == array.length){
			return start;
		}
		Matrix result = matrixMultiply(start, array[i]);
		result = unoptimizedMultiplication(i+1, result);
		return result;
	}


	private static void getDimensions(Scanner input) throws IOException {
		String in = input.next();
		if(in.contains("default")){
			System.out.println("[30x35, 35x15, 15x5, 5x10, 10x20, 20x25]");
		}else{
			int n = Integer.valueOf(in) + 1;
			p = new int[n];
			System.out.print("Enter the first dimension of Matrix A(1): " );
			int dimension = input.nextInt();
			p[0] = dimension;
			for(int i = 1; i < n; i++){
				System.out.print("Enter the second dimension of Matrix A(" + i + "): ");
				dimension = input.nextInt();
				p[i] = dimension;
			}
		}
		System.out.println("Computing... ");
	}
	

	private static void createMatrices() {
		int n = p.length-1;
		array = new Matrix[n];
		for(int i = 0; i < n; i++){
			array[i] = new Matrix(p[i], p[i+1]);
			for(int row = 0; row < p[i]; row++){
				for(int col = 0; col < p[i+1]; col++){
					int value = (int) Math.floor(Math.random() * 101);
					array[i].setValue(row, col, value);
				}
			}
		}
	}

    public static String printMatrix(Matrix in){
        String s = new String();
        s = in.getRows() + " x " + in.getColumns() + "\n" + "\n";

        for(int i=0;i<in.getRows();i++)
        {
            s = s+" ";
            for(int j = 0; j<in.getColumns();j++)
            {
                s = s+in.getValue(i, j)+" ";
            }
            s = s+"\n";
        }
        return s;
    }
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		menu(input);
	}
	
}
