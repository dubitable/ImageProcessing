import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Image {
    private int[][] pixels;
    private int[][][] rgbPixels;
    private String mode;

    public Image(int[][] imagePixels, String m){
        pixels = imagePixels;
        rgbPixels = null;
        mode = m;
    }

    public Image(int[][][] rgb){
        pixels = null;
        rgbPixels = rgb;
        mode = "P3";
    }

    public static Image openRGB(String filename){
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
        return new Image(rgbPixels);
    }

    public void saveAs(String filename) throws IOException{
        filename = "images/" + filename;
        File file = new File(filename);
        file.createNewFile(); 
        FileWriter writer = new FileWriter(file);
        writer.write(mode + "\n");

        if (mode == "P3"){
            writer.append(rgbPixels[0].length + " " + rgbPixels.length + "\n" + 255 + "\n");

            for (int x = 0; x < rgbPixels.length; x++){
                for (int y = 0; y < rgbPixels[0].length; y++){
                    for (int z = 0; z < rgbPixels[0][0].length; z++){
                        writer.append(rgbPixels[x][y][z] + " ");
                    }
                }
                writer.append("\n");
            }
        }
        writer.close();

    }

}