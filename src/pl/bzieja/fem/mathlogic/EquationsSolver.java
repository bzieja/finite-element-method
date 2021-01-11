package pl.bzieja.fem.mathlogic;

public class EquationsSolver {
    /**
     * Column principal component Gauss elimination method
     */
    double A[][];
    double b[];
    double x[];

    int n;    //n Represents the number of unknowns
    int n_2;    //Record the number of line breaks

    public EquationsSolver(double[][] A, double[][] B) {
        this.A = A;
        this.b = B[0]; //because vectorP is [][]
        this.n = A.length;
        x = new double[n];


//        System.out.println("--------------The number of unknowns of input equations---------------");
//        Scanner sc = new Scanner(System.in);
//        n = sc.nextInt();
//
//        A = new double[n][n];
//        b = new double[n];
//        x = new double[n];

//        System.out.println("--------------Coefficient matrix of input equations A:---------------");
//        for(int i = 0; i < n; i++) {
//            for(int j = 0; j < n; j++) {
//                A[i][j] = sc.nextDouble();
//            }
//        }
//
//        System.out.println("--------------Constant vector of input equations b:---------------");
//        for(int i = 0; i < n; i++) {
//            b[i] = sc.nextDouble();
//        }

        Elimination();
        BackSubstitution();
        //PrintRoot();
    }


    //Elimination method
    public void Elimination() {
        //PrintA();
        for(int k = 0; k < n; k++) {
            WrapRow(k);
            for(int i = k+1; i < n; i++) {
                double l = A[i][k] / A[k][k];
                A[i][k] = 0;

                for(int j = k+1; j < n; j++) {
                    A[i][j] = A[i][j] - l * A[k][j];
                }
                b[i] = b[i] - l * b[k];
            }
            //System.out.println("The first" + k + "After secondary elimination:");
            //PrintA();
        }
    }

    //Huidai method
    public void    BackSubstitution() {
        x[n-1] = b[n-1] / A[n-1][n-1];
        for(int i = n - 2; i >= 0; i--) {
            x[i] = (b[i] - solve(i)) / A[i][i];
        }
    }

    public double solve(int i) {
        double result = 0.0;
        for(int j = i; j < n; j++)
            result += A[i][j] * x[j];
        return result;
    }


    //Roots of output equations
//    public static void PrintRoot() {
//        //System.out.println("--------------The root of the equations is---------------");
//        for(int  i = 0; i < n; i++) {
//            System.out.println("x" + (i+1) + " = " + x[i]);
//        }
//    }

    //exchange Swap function???
    public void Swap(double[] ar, int x, int y) {
        Double tmp = ar[x];
        ar[x] = ar[y];
        ar[y] = tmp;
    }

//    public static void PrintA() {    //output A augmented matrix
//        //System.out.println("--------------Augmented matrix---------------");
//        for(int i = 0; i < n; i++) {
//            for(int j = 0; j < n; j++) {
//                System.out.print(A[i][j] + " ");
//            }
//            System.out.println(b[i]);
//        }
//    }

    //Row of commutative matrix
    public void WrapRow(int k) {    //k Denotes the k+1 Rotational elimination
        double maxElement = Math.abs(A[k][k]);

        int WrapRowIndex = k;    //    Remember the lines to swap
        for(int i = k + 1; i < n; i++) {
            if (Math.abs(A[i][k]) > maxElement) {
                WrapRowIndex = i;
                maxElement = A[i][k];
            }
        }
        if (WrapRowIndex != k) {    //Exchange to find the largest principal element
            n_2 += 1;
            //System.out.println("k = " + k + "When," + "The behavior to be exchanged" + k + "and"+ WrapRowIndex);

            //Exchange first A
            for(int j = k; j < n; j++) {
                double[] arr = {A[k][j], A[WrapRowIndex][j]};
                Swap(arr, 0, 1);
                A[k][j] = arr[0]; A[WrapRowIndex][j] = arr[1];
//                double tmp = A[k][j];
//                A[k][j] = A[WrapRowIndex][j];
//                A[WrapRowIndex][j] = tmp;
            }

            //Re exchange b
            double[] arr = {b[k], b[WrapRowIndex]};
            Swap(arr, 0, 1);
            b[k] = arr[0]; b[WrapRowIndex] = arr[1];
//            double tmp = b[k];
//            b[k] = b[WrapRowIndex];
//            b[WrapRowIndex] = tmp;
            //System.out.println("--------------After exchange---------------");
            //PrintA();
        }
    }

    public double[][] getX() {
        double[][] output = new double[1][n];
        output[0] = x;
        return output;
    }
}
