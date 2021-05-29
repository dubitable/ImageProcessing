import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Image {
    public static double[][] blur = {
        {(double) 1/9, (double) 1/9, (double) 1/9},
        {(double) 1/9, (double) 1/9, (double) 1/9},
        {(double) 1/9, (double) 1/9, (double) 1/9}
    };
    public static double[][] blurGaussian = {
        {(double) 1/16, (double) 2/16, (double) 1/16},
        {(double) 2/16, (double) 4/16, (double) 2/16},
        {(double) 1/16, (double) 2/16, (double) 1/16}
    };

    public static double[][] sharpen = {
        {0, -1, 0},
        {-1, 5, -1},
        {0, -1, 0}
    };

    public static double[][] edges = {
        {-1, -1, -1},
        {-1, 8, -1},
        {-1, -1, -1}
    };

    public static double[][] edges2 = {
        {0, 1, 0},
        {1, -4, 1},
        {0, 1, 0}
    };

    public static double[][] edges3 = {
        {1, 0, -1},
        {0, 0, 0},
        {-1, 0, 1}
    };

    public static double[][] stamped = {
        {-2, -1, 0},
        {-1, 1, 1},
        {0, 1, 2}
    };

    public static double[][] incline1 = {
        {3, 1},
        {1, 5}
    };

    public static double[][] incline2 = {
        {5, 2},
        {1, 2}
    };

    public static double[][] randomConvolution(){
        double[][] matrix = {
            {(Math.random() * 10) -5, (Math.random() * 10) -5},
            {(Math.random() * 10) -5, (Math.random() * 10) -5}
        };
        return matrix;
    }

    public static double[][] horizontalSym = {
        {1, 0},
        {0, -1}
    };

    public static double[][] verticalSym = {
        {-1, 0},
        {0, 1}
    };

    public static double[][] dilatation(double kX, double kY){
        double[][] dilatation = {
            {kX, 0},
            {0, kY}
        };
        return dilatation;
    } 

    public static double[][] rotation(double theta){
        double[][] rotation = {
            {Math.cos(theta), -1 * Math.sin(theta)},
            {Math.sin(theta), Math.cos(theta)}
        };
        return rotation;
    }

    public static double[][] applyRandomTransformation(){
        double[][] matrix = {
            {(Math.random() * 10) -5, (Math.random() * 10) -5},
            {(Math.random() * 10) -5, (Math.random() * 10) -5}
        };
        return matrix;
    }

    private static String dir = "";
    private int[][][] rgbPixels;

    public Image(int[][][] pixels){
        rgbPixels = pixels;
    }

    public int height(){
        return rgbPixels.length;
    }

    public int width(){
        return rgbPixels[0].length;
    }

    public int depth(){
        return rgbPixels[0][0].length;
    }

    public int[][][] rgbPixels(){
        return rgbPixels;
    }

    public static void setDir(String directory){
        dir = directory;
    }

    public static Image open(String filename){
        BufferedImage buffImg;

        try{ buffImg = ImageIO.read(new File(filename)); }
        catch(Exception exception){ return null; }

        return Image.open(buffImg);
    }

    public static Image open(BufferedImage buffImg){
        int numRows = buffImg.getHeight(), numCols = buffImg.getWidth();
        int[][][] rgbPixels = new int[numRows][numCols][3];
        ColorModel model = ColorModel.getRGBdefault();

        for (int row = 0; row < numRows; row++){
            for (int col = 0; col < numCols; col++){
                int pixel = buffImg.getRGB(col, row);
                int[] p = {model.getRed(pixel), model.getGreen(pixel), model.getBlue(pixel)};
                
                if ((model.getAlpha(pixel) == 0) && (p[0] + p[1] + p[2] == 0)){
                    p[0] = 255; p[1] = 255; p[2] = 255;
                }
                rgbPixels[row][col] = p;      
            }
        }
        return new Image(rgbPixels);
    }

    public static Image newImage(int height, int width){
        int[][][] output = new int[height][width][3];
        return new Image(output);
    }

    public Image toNegative(){
        int[][][] output = new int[height()][width()][depth()];
        for (int row = 0; row < height(); row++){
            for (int col = 0; col < width(); col++){
                for (int depth = 0; depth < depth(); depth++){
                    output[row][col][depth] = 255 - rgbPixels[row][col][depth];
                }
            }
        }
        return new Image(output);
    }
    
    public Image toGrayScale(){
        int[][][] output = new int[height()][width()][depth()];
        for (int row = 0; row < height(); row++){
            for (int col = 0; col < width(); col++){
                int newpixel = (rgbPixels[row][col][0] + rgbPixels[row][col][1] + rgbPixels[row][col][2]) / 3;
                int[] pixel = {newpixel, newpixel, newpixel};
                output[row][col] = pixel;
            }
        }
        return new Image(output);
    }

    public Image toBW(){
        return toBW(127);
    }

    public Image toBW(int threshold){
        int[][][] output = new int[height()][width()][1];
        for (int row = 0; row < height(); row++){
            for (int col = 0; col < width(); col++){
                int sum = 0, pixel = 255;
    
                for (int depth = 0; depth < depth(); depth++){
                    sum += rgbPixels[row][col][depth];
                }
                if ((sum / depth()) > threshold){
                    pixel = 0;
                }
                int[] p = {pixel, pixel, pixel};
                output[row][col] = p;
    
            }
        }
        return new Image(output);
    }
    
    public Image insert(Image image){
        return insert(image, 0, 0);
    }
    
    public Image insert(Image image, int startRow, int startCol){
        int[][][] output = rgbPixels;
        for (int row = startRow; (row - startRow) < this.height() &&  (row - startRow) < image.height(); row++){
            for (int col = startCol; (col - startCol) < this.width() &&  (col - startCol) < image.width(); col++){
                for (int depth = 0; depth < this.depth() &&  depth < image.depth(); depth++){
                    output[row][col][depth] = image.rgbPixels[row - startRow][col - startCol][depth];
                }
            }
        }
        return new Image(output);
    }
    
    public Image resize(int newheight, int newwidth){
        int[][][] output = new int[newheight][newwidth][depth()];
    
        if (newheight < height()){
            for (int row = 0;  row < height(); row++){
                int newRow = (int) ((double) (row * newheight) / height());
                if (newwidth < height()){
                    for (int col = 0; col < width(); col++){
                        int newCol = (int) ((double) (col * newwidth) / width());
                        output[newRow][newCol] = rgbPixels[row][col];
                    }
                }
                else{
                    for (int col = 0; col < newwidth; col++){
                        int newCol = (int) ((double) (col * width()) / newwidth);
                        output[newRow][col] = rgbPixels[row][newCol];
                    }
                }
            }
        }
        else{
            for (int row = 0;  row < newheight; row++){
                int newRow = (int) ((double) (row * height()) / newheight);
                if (newwidth < height()){
                    for (int col = 0; col < width(); col++){
                        int newCol = (int) ((double) (col * newwidth) / width());
                        output[row][newCol] = rgbPixels[newRow][col];
                    }
                }
                else{
                    for (int col = 0; col < newwidth; col++){
                        int newy = (int) ((double) (col * width()) / newwidth);
                        output[row][col] = rgbPixels[newRow][newy];
                    }
                }
            }
        }
       
        return new Image(output);
    }

    public Image applyConvolution(double[][] matrix, int iterations){
        Image output = this;
        for (int i = 0; i < iterations; i++){
            output = output.applyConvolution(matrix);
        }
        return output;
    }
    
    public Image applyConvolution(double[][] matrix){
        int[][][] output = new int[height()][width()][depth()];
        int offseti = ((matrix.length - 1) / 2), offsetj = ((matrix[0].length - 1) / 2);
    
        for (int row = 0; row < height(); row++){
            for (int col = 0; col < width(); col++){
                for (int depth = 0; depth < depth(); depth++){
                    double sum = 0;
                    for (int i = 0; i < matrix.length; i++){
                        for (int j = 0; j < matrix[0].length; j++){
                            int newRow = Math.floorMod((row + (i - offseti)), height()), newCol = Math.floorMod((col + (j - offsetj)), width()); 
                            sum += rgbPixels[newRow][newCol][depth] * matrix[i][j];
                        }
                    }
                    if (sum > 255){ sum = 255; }
                    if (sum < 0) { sum = 0; }
                    output[row][col][depth] = (int) sum;
                }
                
            }
        }
        return new Image(output);
        
    }

    public static int[] multiplyMatrix(int[] coords, double[][] matrix){
        int[] newcoords = {
            (int) ((coords[0] * matrix[0][0]) + (coords[1] * matrix[0][1])), 
            (int) ((coords[0] * matrix[1][0]) + (coords[1] * matrix[1][1]))
        };
        return newcoords;
    }

    public int[] calculateSize(double[][] matrix){
        int[][] corners = {
            {0, 0}, 
            {width(), 0},
            {width(), height()}, 
            {0, height()}
        };
        int[][] newcorners = new int[corners.length][corners[0].length];
        for (int i = 0; i < corners.length; i++){
            newcorners[i] = Image.multiplyMatrix(corners[i], matrix);
        }
        int xMin = Math.min(Math.min(newcorners[0][0], newcorners[1][0]), Math.min(newcorners[2][0], newcorners[3][0]));
        int xMax = Math.max(Math.max(newcorners[0][0], newcorners[1][0]), Math.max(newcorners[2][0], newcorners[3][0]));
        int yMin = Math.min(Math.min(newcorners[0][1], newcorners[1][1]), Math.min(newcorners[2][1], newcorners[3][1]));
        int yMax = Math.max(Math.max(newcorners[0][1], newcorners[1][1]), Math.max(newcorners[2][1], newcorners[3][1]));

        int[] output = {xMin, xMax, yMin, yMax};
        return output;
    } 

    public static double[][] inverseMatrix(double[][] matrix){
        double dt = (matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]);
        double[][] output = {
            {matrix[1][1] / dt, (-1 * matrix[0][1]) / dt},
            {(-1 * matrix[1][0]) / dt, matrix[0][0] / dt}
        };
        return output;
    }

    public Image applyTransformation(double[][] matrix){
        int[] size = calculateSize(matrix);
        int newheight = size[3] - size[2], newwidth = size[1] - size[0];
        int xMin = size[0], yMin = size[2];
        int[][][] output = new int[newheight][newwidth][rgbPixels[0][0].length];
        double[][] inverse = Image.inverseMatrix(matrix);

        if (newwidth < width()){
            for (int x = 0; x < width(); x++){
                if (newheight < height()){
                    for (int y = 0; y < height(); y++){
                        int newx = (int) ((x * matrix[0][0]) + (y * matrix[0][1]));
                        int newy = (int) ((x * matrix[1][0]) + (y * matrix[1][1]));
                        try{
                            output[newy][newx] = rgbPixels[y][x];
                        }
                        catch(Exception e){}
                    }
                }
                else{
                    for (int y = 0; y < newheight; y++){
                        int newx = (int) ((x * matrix[0][0]) + (y * matrix[0][1]));
                        int newy = (int) (((x + xMin) * inverse[1][0]) + (((y + yMin) * inverse[1][1])));
                        try{
                            output[y][newx] = rgbPixels[newy][x];
                        }
                        catch(Exception e){}
                    }
                }
            }
        }
        else{
            for (int x = 0; x < newwidth; x++){
                if (newheight < height()){
                    for (int y = 0; y < height(); y++){
                        int newx = (int) (((x + xMin) * inverse[0][0]) + ((y + yMin) * inverse[0][1]));
                        int newy = (int) ((x * matrix[1][0]) + (y * matrix[1][1]));
                        try{
                            output[newy][x] = rgbPixels[y][newx];
                        }
                        catch(Exception e){}
                    }
                }
                else{
                    for (int y = 0; y < newheight; y++){
                        int newx = (int) (((x + xMin) * inverse[0][0]) + ((y + yMin) * inverse[0][1])) ;
                        int newy = (int) (((x + xMin) * inverse[1][0]) + ((y + yMin) * inverse[1][1]));
                        try{
                            output[y][x] = rgbPixels[newy][newx];
                        }
                        catch(Exception e){}
                    }
                }
            }
        }
        
        return new Image(output);
    }
    
    public void saveAsPBM(String filename) throws IOException{
        filename = dir + "/" + filename;
        System.out.println(filename);
        File file = new File(filename);
        file.createNewFile(); 
        FileWriter writer = new FileWriter(file);
        writer.write("P3\n" + width() + " " + height() + "\n255\n");
    
        for (int row = 0; row < height(); row++){
            for (int col = 0; col < width(); col++){
                for (int depth = 0; depth < depth(); depth++){
                    writer.append(rgbPixels[row][col][depth] + " ");
                }
            }
            writer.append("\n");
        }
        writer.close();
    }

    public void saveAs(String filename) throws IOException{
        filename = dir + "/" + filename;
        BufferedImage img = new BufferedImage(width(), height(), BufferedImage.TYPE_INT_RGB);
        System.out.println(filename);
        for (int row = 0; row < height(); row++){
            for (int col = 0; col < width(); col++){
                int[] pixel = rgbPixels[row][col];
                int rgb = pixel[0];
                rgb = (rgb << 8) + pixel[1];
                rgb = (rgb << 8) + pixel[2];
                img.setRGB(col, row, rgb);
            }
        }
        File file = new File(filename);
        ImageIO.write(img, "jpg", file);
    }
}