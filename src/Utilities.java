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
    public static void printArray(double[][] matrix){
        for (double[] arr : matrix){
            System.out.print("(");
            for (double elem : arr){
                System.out.print(" " + elem + " ");
            }
            System.out.println(")");
        }
    }
    public static void printArray(int[][] matrix){
        for (int[] arr : matrix){
            System.out.print("(");
            for (int elem : arr){
                System.out.print(" " + elem + " ");
            }
            System.out.println(")");
        }
    }

    public static void printArray(int[] array){
        System.out.print("[");
        for (int elem : array){
            System.out.print(" " + elem + " ");
        }
        System.out.println("]");
    }
}
