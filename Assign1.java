import java.util.Random;
/* Name: Ali H. Iqbal
*  Course: CSC-130
*  Instructor: Professor Jinsong Ouyang
*  Assignment #1: Matrix Multiplication  
*  Due Date: 26 February 2016
*/
public class Assign1{															
	public static void main(String[]args){										
		int [][] arr;															
		Matrix z = new Matrix(2); //matrix size
		z.divide();
		System.out.println("Divide and Conquer Multiplication:");
		arr = z.MMult(z.M1, z.M2);
		
		z.print(arr);
		System.out.println("\nStrassen Multiplication:");
		arr = z.Strassen(z.M1, z.M2);
		
		z.print(arr);
	}
	
	public static class Matrix
    {
        int N;
        int M1[][];
        int M2[][];

        public Matrix(int N)
        {
            this.N = N;
            M1 = new int[N][N];
            M2 = new int[N][N];
        }

        public void divide()
        {
            Random r = new Random();
            for(int a = 0; a<N; a++) {
                for(int b = 0; b<N; b++) {
                    M1[a][b] = r.nextInt(7);
                    M2[a][b] = r.nextInt(7);
                }
            }
        }

        public void divide(int[][] parent, int[][] child,
                int iB, int jB)
        {
            for(int a = 0, a2 = iB; a<child.length; a++, a2++) {  
                for(int b = 0, b2 = jB; b<child.length; b++, b2++) {  
                    child[a][b] = parent[a2][b2];
                }
            }
        }

        public int[][] MMult(int [][] a, int [][] b)
        {  
            int n = a.length;
            int [][] c = new int[n][n];

            if (n == 1) {
                c[0][0] = a[0][0] * b[0][0];
            }
            else {
                int[][] a11 = new int[n/2][n/2];
                int[][] a12 = new int[n/2][n/2];
                int[][] a21 = new int[n/2][n/2];
                int[][] a22 = new int[n/2][n/2];
                int[][] b11 = new int[n/2][n/2];
                int[][] b12 = new int[n/2][n/2];
                int[][] b21 = new int[n/2][n/2];
                int[][] b22 = new int[n/2][n/2];

                divide(a, a11, 0 , 0);
                divide(a, a12, 0 , n/2);
                divide(a, a21, n/2, 0);
                divide(a, a22, n/2, n/2);
                divide(b, b11, 0 , 0);
                divide(b, b12, 0 , n/2);
                divide(b, b21, n/2, 0);
                divide(b, b22, n/2, n/2);
                
                int [][] x1 = MMult(a11, b11);
                int [][] x2 = MMult(a12, b21);
                int [][] x3 = MMult(a11, b12);
                int [][] x4 = MMult(a12, b22);
                int [][] x5 = MMult(a21, b11);
                int [][] x6 = MMult(a22, b21);
                int [][] x7 = MMult(a21, b12);
                int [][] x8 = MMult(a22, b22);
                
                int[][] c11 = add(x1, x2);
                int[][] c12 = add(x3, x4);
                int[][] c21 = add(x5, x6);
                int[][] c22 = add(x7, x8);

                store(c11, c, 0 , 0);
                store(c12, c, 0 , n/2);
                store(c21, c, n/2, 0);
                store(c22, c, n/2, n/2);
            }
            return c;
        }

        public int[][] Strassen(int [][] a, int [][] b)
        {
            
            int n = a.length;
            int [][] c = new int[n][n];

            if (n == 1) {
                c[0][0] = a[0][0] * b[0][0];
            }
        else {
            int[][] a11 = new int[n/2][n/2];
            int[][] a12 = new int[n/2][n/2];
            int[][] a21 = new int[n/2][n/2];
            int[][] a22 = new int[n/2][n/2];
            int[][] b11 = new int[n/2][n/2];
            int[][] b12 = new int[n/2][n/2];
            int[][] b21 = new int[n/2][n/2];
            int[][] b22 = new int[n/2][n/2];

            divide(a, a11, 0 , 0);
            divide(a, a12, 0 , n/2);
            divide(a, a21, n/2, 0);
            divide(a, a22, n/2, n/2);
            divide(b, b11, 0 , 0);
            divide(b, b12, 0 , n/2);
            divide(b, b21, n/2, 0);
            divide(b, b22, n/2, n/2);
            
            
            int[][] p1 = Strassen(a11, sub(b12, b22));
            int[][] p2 = Strassen(add(a11, a12), b22);
            int[][] p3 = Strassen(add(a21, a22), b11);
            int[][] p4 = Strassen(a22, sub(b21, b11));
            int[][] p5 = Strassen(add(a11, a22), add(b11, b22));
            int[][] p6 = Strassen(sub(a12, a22), add(b21, b22));
            int[][] p7 = Strassen(sub(a21, a11), add(b11, b12));
            

            int[][] c11 = add(sub(add(p5, p4), p2), p6);
            int[][] c12 = add(p1, p2);
            int[][] c21 = add(p3, p4);
            int[][] c22 = add(sub(add(p1, p5), p3), p7);

            store(c11, c, 0 , 0);
            store(c12, c, 0 , n/2);
            store(c21, c, n/2, 0);
            store(c22, c, n/2, n/2);

        	}
            return c;
        }
        
        public int[][] add(int[][] M1, int[][] M2)
        {
            int result[][] = new int[M1.length][M1.length];
            for(int a = 0; a<M1.length; a++) {
                for(int b = 0; b<M1.length; b++) {
                    result[a][b] = M1[a][b] + M2[a][b];
                }
            }
            return result;
        }

        public int[][] sub(int[][] M1, int[][] M2)
        {
            int result[][] = new int[M1.length][M1.length];
            for(int a = 0; a<M1.length; a++) {
                for(int b = 0; b<M1.length; b++) {
                    result[a][b] = M1[a][b] - M2[a][b];
                }
            }
            return result;
        }
        
        public void store(int[][] child, int[][] parent,
                int iB, int jB)
        {
            for(int a = 0, a2 = iB; a<child.length; a++, a2++) {
                for(int b = 0, b2 = jB; b<child.length; b++, b2++) {
                    parent[a2][b2] = child[a][b];
                }
            }
        }
        
        public void print(int matrix[][])
        {
            for(int a = 0; a<matrix.length; a++) {
                for(int b = 0; b<matrix.length; b++) {
                    System.out.print(matrix[a][b] + "  ");
                }
                System.out.println();
            }
        }
    }//matrix class end. 
}//Assign1 class end.