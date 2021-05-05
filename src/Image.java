import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Image {
    private int[][][] pixels;
    private String mode;
    private int type;

    public Image(int[][][] imagePixels, String m){
        pixels = imagePixels;
        mode = m;
        if (mode.equals("P1")){
            type = 1;
        }
        else if (mode.equals("P2") || mode.equals("P3")){
            type = 255;
        }
    }

    public int height(){
        return  pixels.length;
    }

    public int width(){
        return pixels[0].length;
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
                rgbPixels[row][col][0] = model.getRed(pixel);
                rgbPixels[row][col][1] = model.getGreen(pixel);
                rgbPixels[row][col][2] = model.getBlue(pixel);
            }
        }
        return new Image(rgbPixels, "P3");
    }

    public Image toNegative(){
        int[][][] output = new int[pixels.length][pixels[0].length][pixels[0][0].length];
        for (int x = 0; x < pixels.length; x++){
            for (int y = 0; y < pixels[0].length; y++){
                for (int z = 0; z < pixels[0][0].length; z++){
                    output[x][y][z] = type - pixels[x][y][z];
                }
            }
        }
        return new Image(output, mode);
    }

    public Image toGrayScale(){
        int[][][] output = new int[pixels.length][pixels[0].length][1];
        for (int x = 0; x < pixels.length; x++){
            for (int y = 0; y < pixels[0].length; y++){
                output[x][y][0] = (pixels[x][y][0] + pixels[x][y][1] + pixels[x][y][2]) / 3;
            }
        }
        return new Image(output, "P2");
    }

    public Image toBW(){
        int[][][] output = new int[pixels.length][pixels[0].length][1];
        for (int x = 0; x < pixels.length; x++){
            for (int y = 0; y < pixels[0].length; y++){
                int sum = 0;
                for (int z = 0; z < pixels[0][0].length; z++){
                    sum += pixels[x][y][z];
                }
                if ((sum / pixels[0][0].length) > 127){
                    output[x][y][0] = 0;
                }
                else{
                    output[x][y][0] = 1;
                }
            }
        }
        return new Image(output, "P1");
    }

    public void saveAs(String filename) throws IOException{
        filename = "images/" + filename;
        File file = new File(filename);
        file.createNewFile(); 
        FileWriter writer = new FileWriter(file);
        writer.write(mode + "\n" + pixels[0].length + " " + pixels.length + "\n" + type + "\n");

        for (int x = 0; x < pixels.length; x++){
            for (int y = 0; y < pixels[0].length; y++){
                for (int z = 0; z < pixels[0][0].length; z++){
                    writer.append(pixels[x][y][z] + " ");
                }
            }
            writer.append("\n");
        }
        writer.close();
    }

}