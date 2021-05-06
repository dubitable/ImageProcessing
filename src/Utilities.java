public class Utilities {

    public static double[][] flou = {
        {(double) 1/9, (double) 1/9, (double) 1/9},
        {(double) 1/9, (double) 1/9, (double) 1/9},
        {(double) 1/9, (double) 1/9, (double) 1/9}
    };

    public static double[][] bords = {
        {-1, -1, -1},
        {-1, 8, -1},
        {-1, -1, -1}
    };

    public static double[][] bas = {
        {0, 1, 0},
        {0, 0, 0},
        {0, 0, 0},
    };

    public static int[][][] test1 = {
        {{1}, {2}, {3}, {4}},
        {{5}, {6}, {7}, {8}},
        {{9}, {10}, {11}, {12}},
    };

    public static int[][][] test2 = {
        {{101}, {102}, {103}, {104}},
        {{201}, {151}, {101}, {51}},
        {{50}, {100}, {150}, {200}},
    };


    
    public static void printArray(int[][][] matrix){
        for (int[][] array : matrix){
            for (int[] arr : array){
                System.out.print("(");
                for (int elem : arr){
                    System.out.print(" " + elem + " ");
                }
                System.out.print(")");
            }
            System.out.println();
        }
    }
}
