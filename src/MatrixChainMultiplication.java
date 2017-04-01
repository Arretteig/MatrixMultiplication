public class MatrixChainMultiplication {

	private static int[][] m;
	private static int[][] s;

	public void matrixChainOrder(int[] p){
		int n = p.length - 1;
		m = new int[n][n];
		s = new int[n-1][n]; // s? [n-1]["2 to n"]
		for (int i = 0; i < n; i++) {
			m[i][i] = 0;
		}
		for (int l = 1; l < n; l++) {
			for (int i = 0; i < n - l + 1; i++) { // int i = 1 they say
				int j = i + l; // i + l - 1 they say
				m[i][j] = Integer.MAX_VALUE;
				for(int k = i; i < j; k++){
					int q = m[i][k] + m[k+1][j] + p[i-1]*p[k]*p[j]; //index off by 1?
					if(q < m[i][j]){
						m[i][j] = q;
						s[i][j] = k;
					}
				}
			}
		}
		// pseudo code says to return m and s
	}

	static int matrixChainOrder2(int[] p) {

		int n = p.length - 1;
		int[][] m = new int[n][n];
		int[][] s = new int[n][n];
		for (int i = 0; i < n; i++) {
			m[i][i] = 0;
		}
		for (int row = 1; row < n; row++) {
			for (int i = 0; i < n - row; i++) {
				int j = i + row;
				m[i][j] = Integer.MAX_VALUE;
				for (int k = i; k < j; k++) {
					int q = m[i][k] + m[k + 1][j] + p[i] * p[k+1] * p[j+1];
					if (k == i) {
						m[i][j] = q;
						s[i][j] = k;
					}
					else if (k == i + 1) {
						if (m[i][j] > q) {
							m[i][j] = q;
							s[i][j] = k;
						}
					} else {
						if (q < m[i][j]) {
							m[i][j] = q;
							s[i][j] = k;
						}
					}
				}
				if (row == p.length - 2) {
					return m[i][j];
				}
			}
		}
		return -1;
	}

	public static void printOptimalParens(int[][] s, int i, int j) {
		if (i == j) {
			System.out.println("A" + (i));
		}
		else {
			System.out.println("(");
			printOptimalParens(s, i, s[i][j]);
			printOptimalParens(s, s[i][j] + 1, j);
			System.out.println(")");
		}
	}

	public static Matrix matrixChainMultiply (Matrix[] A, int t, int i, int j) {
		if (i == j) {
			return A[i];
		}
		else{
			Matrix leftChain = matrixChainMultiply(A, t, i, s[i][j]);
			Matrix rightChain = matrixChainMultiply(A, t, s[i][j]+1, j);
			Matrix result = matrixMultiply(leftChain, rightChain);
			return result;
		}
	}

	private static Matrix matrixMultiply(Matrix A, Matrix B) {
		Matrix C = new Matrix(A.getRows(), B.getColumns());
		if(A.getColumns() != B.getColumns()){
			System.out.println("CAN NOT MULTIPLY");
			System.exit(0);
		}
		else{
			for(int i = 0; i < A.getRows(); i++){
				for(int j = 0; j < B.getColumns(); j++){
					C.setValue(i, j, 0);
					for(int k = 0; k < A.getColumns(); k++){
						C.setValue(i, j, (C.getValue(i, j) + A.getValue(i, k) * B.getValue(k, j)));
					}
				}
			}
		}
		return C;
	}
	
	public void recursiveChainMultiply() {
		
	}
	
	public void memoizedMatrixChain() {
		
	}
	
	public void lookupChain() {
		
	}
}
	
