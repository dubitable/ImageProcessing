import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Image {
    private int[][][] pixels;

    public Image(int[][][] imagePixels){
        pixels = imagePixels;
    }

    public int height(){
        return pixels.length;
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
        int[][][] output = new int[pixels.length][pixels[0].length][pixels[0][0].length];
        for (int x = 0; x < pixels.length; x++){
            for (int y = 0; y < pixels[0].length; y++){
                for (int z = 0; z < pixels[0][0].length; z++){
                    output[x][y][z] = 255 - pixels[x][y][z];
                }
            }
        }
        return new Image(output);
    }

    public Image toGrayScale(){
        int[][][] output = new int[pixels.length][pixels[0].length][pixels[0][0].length];
        for (int x = 0; x < pixels.length; x++){
            for (int y = 0; y < pixels[0].length; y++){
                int newpixel = (pixels[x][y][0] + pixels[x][y][1] + pixels[x][y][2]) / 3;
                int[] pixel = {newpixel, newpixel, newpixel};
                output[x][y] = pixel;
            }
        }
        return new Image(output);
    }

    public Image toBW(){
        int[][][] output = new int[pixels.length][pixels[0].length][1];
        for (int x = 0; x < pixels.length; x++){
            for (int y = 0; y < pixels[0].length; y++){
                int sum = 0, pixel = 255;

                for (int z = 0; z < pixels[0][0].length; z++){
                    sum += pixels[x][y][z];
                }
                if ((sum / pixels[0][0].length) > 127){
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
        int[][][] output = pixels;
        for (int x = startx; (x - startx) < this.pixels.length &&  (x - startx) < image.pixels.length; x++){
            for (int y = starty; (y - starty) < this.pixels[0].length &&  (y - starty) < image.pixels[0].length; y++){
                for (int z = 0; z < this.pixels[0][0].length &&  z < image.pixels[0][0].length; z++){
                    output[x][y][z] = image.pixels[x - startx][y - starty][z];
                }
            }
        }
        return new Image(output);
    }

    public Image resize(int newheight, int newwidth){
        int[][][] output = new int[newheight][newwidth][pixels[0][0].length];

        if (newheight < height()){
            for (int x = 0;  x < height(); x++){
                int newx = (int) ((double) (x * newheight) / height());
                if (newwidth < height()){
                    for (int y = 0; y < width(); y++){
                        int newy = (int) ((double) (y * newwidth) / width());
                        output[newx][newy] = pixels[x][y];
                    }
                }
                else{
                    for (int y = 0; y < newwidth; y++){
                        int newy = (int) ((double) (y * width()) / newwidth);
                        output[newx][y] = pixels[x][newy];
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
                        output[x][newy] = pixels[newx][y];
                    }
                }
                else{
                    for (int y = 0; y < newwidth; y++){
                        int newy = (int) ((double) (y * width()) / newwidth);
                        output[x][y] = pixels[newx][newy];
                    }
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
        writer.write("P3\n" + pixels[0].length + " " + pixels.length + "\n255\n");

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