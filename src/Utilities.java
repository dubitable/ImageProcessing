public class Utilities {    
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
