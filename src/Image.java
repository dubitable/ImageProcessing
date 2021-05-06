import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Image {
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

    public int[][][] rgbPixels(){
        return rgbPixels;
    }

    public static Image open(String filename){
        BufferedImage buffImg;

        try{ buffImg = ImageIO.read(new File(filename)); }
        catch(Exception exception){ return null; }

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

    public Image toNegative(){
        int[][][] output = new int[rgbPixels.length][rgbPixels[0].length][rgbPixels[0][0].length];
        for (int x = 0; x < rgbPixels.length; x++){
            for (int y = 0; y < rgbPixels[0].length; y++){
                for (int z = 0; z < rgbPixels[0][0].length; z++){
                    output[x][y][z] = 255 - rgbPixels[x][y][z];
                }
            }
        }
        return new Image(output);
    }
    
    public Image toGrayScale(){
        int[][][] output = new int[rgbPixels.length][rgbPixels[0].length][rgbPixels[0][0].length];
        for (int x = 0; x < rgbPixels.length; x++){
            for (int y = 0; y < rgbPixels[0].length; y++){
                int newpixel = (rgbPixels[x][y][0] + rgbPixels[x][y][1] + rgbPixels[x][y][2]) / 3;
                int[] pixel = {newpixel, newpixel, newpixel};
                output[x][y] = pixel;
            }
        }
        return new Image(output);
    }
    
    public Image toBW(){
        int[][][] output = new int[rgbPixels.length][rgbPixels[0].length][1];
        for (int x = 0; x < rgbPixels.length; x++){
            for (int y = 0; y < rgbPixels[0].length; y++){
                int sum = 0, pixel = 255;
    
                for (int z = 0; z < rgbPixels[0][0].length; z++){
                    sum += rgbPixels[x][y][z];
                }
                if ((sum / rgbPixels[0][0].length) > 127){
                    pixel = 0;
                }
                int[] p = {pixel, pixel, pixel};
                output[x][y] = p;
    
            }
        }
        return new Image(output);
    }
    
    public Image insert(Image image){
        return insert(image, 0, 0);
    }
    
    public Image insert(Image image, String alignment){
        String alignmentX = "", alignmentY = "";
    
        if (alignment.equals("center")){
            alignmentX = "center";
            alignmentY = "center";
        }
        return insert(image, alignmentX, alignmentY);
    }
    
    public Image insert(Image image, String alignementX, String alignmentY){
        return insert(image, alignementX, alignmentY, 0, 0);
    }
    
    public Image insert(Image image, String alignementX, String alignmentY, int offsetx, int offsety){
        int startx = 0, starty = 0;
    
        if (alignementX.equals("center")){
            startx = (this.height() / 2) -  (image.height() / 2);
        }
    
        if (alignmentY.equals("center")){
            starty = (this.width() / 2) -  (image.width() / 2);
        }
        startx += offsetx;
        starty += offsety;
        return insert(image, startx, starty);
    }
    
    public Image insert(Image image, int startx, int starty){
        int[][][] output = rgbPixels;
        for (int x = startx; (x - startx) < this.rgbPixels.length &&  (x - startx) < image.rgbPixels.length; x++){
            for (int y = starty; (y - starty) < this.rgbPixels[0].length &&  (y - starty) < image.rgbPixels[0].length; y++){
                for (int z = 0; z < this.rgbPixels[0][0].length &&  z < image.rgbPixels[0][0].length; z++){
                    output[x][y][z] = image.rgbPixels[x - startx][y - starty][z];
                }
            }
        }
        return new Image(output);
    }
    
    public Image resize(int newheight, int newwidth){
        int[][][] output = new int[newheight][newwidth][rgbPixels[0][0].length];
    
        if (newheight < height()){
            for (int x = 0;  x < height(); x++){
                int newx = (int) ((double) (x * newheight) / height());
                if (newwidth < height()){
                    for (int y = 0; y < width(); y++){
                        int newy = (int) ((double) (y * newwidth) / width());
                        output[newx][newy] = rgbPixels[x][y];
                    }
                }
                else{
                    for (int y = 0; y < newwidth; y++){
                        int newy = (int) ((double) (y * width()) / newwidth);
                        output[newx][y] = rgbPixels[x][newy];
                    }
                }
            }
        }
        else{
            for (int x = 0;  x < newheight; x++){
                int newx = (int) ((double) (x * height()) / newheight);
                if (newwidth < height()){
                    for (int y = 0; y < width(); y++){
                        int newy = (int) ((double) (y * newwidth) / width());
                        output[x][newy] = rgbPixels[newx][y];
                    }
                }
                else{
                    for (int y = 0; y < newwidth; y++){
                        int newy = (int) ((double) (y * width()) / newwidth);
                        output[x][y] = rgbPixels[newx][newy];
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
        int[][][] output = new int[rgbPixels.length][rgbPixels[0].length][rgbPixels[0][0].length];
        int offseti = ((matrix.length - 1) / 2), offsetj = ((matrix[0].length - 1) / 2);
        ColorModel model = ColorModel.getRGBdefault();
    
        for (int x = 0; x < height(); x++){
            for (int y = 0; y < width(); y++){
                for (int z = 0; z < rgbPixels[0][0].length; z++){
                    double sum = 0;
                    for (int i = 0; i < matrix.length; i++){
                        for (int j = 0; j < matrix[0].length; j++){
                            int newx = Math.floorMod((x + (i - offseti)), height()), newy = Math.floorMod((y + (j - offsetj)), width()); 
                            sum += rgbPixels[newx][newy][z] * matrix[i][j];
                        }
                    }
                    if (sum > 255){ sum = 255; }
                    if (sum < 0) { sum = 0; }
                    output[x][y][z] = (int) sum;
                }
                
            }
        }
        return new Image(output);
    }
    
    public void saveAs(String filename) throws IOException{
        filename = "images/" + filename;
        File file = new File(filename);
        file.createNewFile(); 
        FileWriter writer = new FileWriter(file);
        writer.write("P3\n" + rgbPixels[0].length + " " + rgbPixels.length + "\n255\n");
    
        for (int x = 0; x < rgbPixels.length; x++){
            for (int y = 0; y < rgbPixels[0].length; y++){
                for (int z = 0; z < rgbPixels[0][0].length; z++){
                    writer.append(rgbPixels[x][y][z] + " ");
                }
            }
            writer.append("\n");
        }
        writer.close();
    }

}