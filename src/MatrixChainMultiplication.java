import java.util.Arrays;

public class MatrixChainMultiplication {

	private static int[] p = { 30, 35, 15, 5, 10, 20, 25 };
	private static int[][] m;
	private static int[][] s;
	private static Matrix c;

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

	private static String printOptimalParens(int i, int j) {
		if (i == j)
			return "A[" + i + "]";
		else
			return "(" + printOptimalParens(i, s[i][j]) + printOptimalParens(s[i][j] + 1, j) + ")";
	}

	public static void printOptimal() {
		System.out.println(printOptimalParens(1, p.length - 1));
	}

	public static Matrix matrixChainMultiply(Matrix[] A, int t, int i, int j) {
		if (i == j) {
			return A[i];
		} else {
			Matrix leftChain = matrixChainMultiply(A, t, i, s[i][j]);
			Matrix rightChain = matrixChainMultiply(A, t, s[i][j] + 1, j);
			matrixMultiply(leftChain, rightChain);
			return c;
		}
	}

	private static void matrixMultiply(Matrix A, Matrix B) {
		if (A.getColumns() != B.getColumns()) {
			System.out.println("CAN NOT MULTIPLY");
		} else {
			c = new Matrix(A.getRows(), B.getColumns());
			for (int i = 0; i < A.getRows(); i++) {
				for (int j = 0; j < B.getColumns(); j++) {
					c.setValue(i, j, 0);
					for (int k = 0; k < A.getColumns(); k++) {
						c.setValue(i, j, (c.getValue(i, j) + A.getValue(i, k) * B.getValue(k, j)));
					}
				}
			}
		}
	}

	public void recursiveChainMultiply() {

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
		int x = lookupChain(1, n);
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

	public static void main(String[] args) {
		matrixChainOrder();
		System.out.print("Matrix Chain Order: \n");
		printOptimal();
		System.out.println();

		memoizedMatrixChain();
		System.out.print("Memoized Matrix Chain: \n");
		printOptimal();
	}
}
