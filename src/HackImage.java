import java.io.IOException;

public class HackImage extends Image{
    public HackImage(int[][][] pixels){
        super(pixels);
    }

    public HackImage(Image img){
        super(img.rgbPixels());
    }

    public int[][][][] sectionRow(int inter){
        int[][][] pixels = this.rgbPixels();
        int[][][][] sections = new int[height()/inter][inter][width()][depth()];
        int r = 0;

        for (int rowSection = 0; rowSection < height() / inter; rowSection++){
            int[][][] section = new int[inter][width()][depth()];
            for (int row = 0; row < inter; row++){ 
                section[row] = pixels[r];
                r++;
            }
            sections[rowSection] = section;
        }
        return sections;
    }

    public int[][][][] sectionCol(int inter){
        int[][][] pixels = this.rgbPixels();
        int[][][][] sections = new int[width()/inter][height()][inter][depth()];
        int c = 0;
        for (int colSection = 0; colSection < width() / inter; colSection++){
            int[][][] section = new int[height()][inter][depth()];
            for (int col = 0; col < inter; col++){
                for (int row = 0; row < height(); row++){
                    section[row][col] = pixels[row][c];
                    
                }
                c++;
            }
            sections[colSection] = section;
        }
        return sections;
    }

    public HackImage[] seperateRow(int[][][][] sections){
        int inter = sections[0].length;
        int[][][] output1 = new int[(inter * sections.length) / 2][sections[0][0].length][sections[0][0][0].length];
        int[][][] output2 = new int[(inter * sections.length) / 2][sections[0][0].length][sections[0][0][0].length];
        int i1 = 0, i2 = 0;

        for (int sect = 0; sect < sections.length; sect++){
            int[][][] section = sections[sect];
            if (sect % 2 == 0){
                for (int row = 0; row < sections[0].length; row++){
                    try{
                        output1[i1] = section[row];
                    }
                    catch(Exception e){}
                    i1++;
                }
            }
            else{
                for (int row = 0; row < sections[0].length; row++){
                    try{
                        output2[i2] = section[row];
                    }
                    catch(Exception e){}
                    i2++;
                }
            }
        }
        HackImage[] output = {new HackImage(output1), new HackImage(output2)};
        return output;
    }
    
    public HackImage[] seperateCol(int[][][][] sections){
        int[][][] output1 = new int[height()][width()/2][depth()];
        int[][][] output2 = new int[height()][width()/2][depth()];
        int i1 = 0, i2 = 0;

        for (int sect = 0; sect < sections.length; sect++){
            int[][][] section = sections[sect];
            
        }
        HackImage[] output = {new HackImage(output1), new HackImage(output2)};
        return output;
    }
}
