public class HackImage extends Image{
    public HackImage(int[][][] pixels){
        super(pixels);
    }

    public HackImage(Image img){
        super(img.rgbPixels());
    }

    private int[][][][] sectionRow(int inter){
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

    private int[][][][] sectionCol(int inter){
        int[][][] pixels = this.rgbPixels();
        int[][][][] sections = new int[width()/inter][height()][inter][depth()];
        int c = 0;

        for (int colSection = 0; colSection < width() / inter; colSection++){
            int[][][] section = new int[height()][inter][depth()];
            for (int col = 0; col < inter; col++){
                for (int row = 0; row < height(); row++){
                    section[row][col] = pixels[row][c];
                }
                sections[colSection] = section;
                c++;
            }
        }
        return sections;
    }

    public HackImage[] seperateRow(int inter){
        int[][][][] sections = sectionRow(inter);
        int[][][] output1 = new int[(sections[0].length * sections.length) / 2][sections[0][0].length][sections[0][0][0].length];
        int[][][] output2 = new int[(sections[0].length * sections.length) / 2][sections[0][0].length][sections[0][0][0].length];
        int i1 = 0, i2 = 0;

        for (int sect = 0; sect < sections.length; sect++){
            int[][][] section = sections[sect];
            if (sect % 2 == 0){
                for (int row = 0; row < section.length; row++){
                    try{
                        output1[i1] = section[row];
                    }
                    catch(Exception e){}
                    i1++;
                }
            }
            else{
                for (int row = 0; row < section.length; row++){
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

    public HackImage[] seperateCol(int inter){
        int[][][][] sections = sectionCol(inter);
        int[][][] output1 = new int[sections[0].length][(sections[0][0].length * sections.length) / 2][sections[0][0][0].length];
        int[][][] output2 = new int[sections[0].length][(sections[0][0].length * sections.length) / 2][sections[0][0][0].length];
        int i1 = 0, i2 = 0;

        for (int sect = 0; sect < sections.length; sect++){
            int[][][] section = sections[sect];
            if (sect % 2 == 0){
                for (int col = 0; col < section[0].length; col++){
                    for (int row = 0; row < section.length; row++){
                        try{
                            output1[row][i1] = section[row][col];
                        }
                        catch (Exception e){}
                    }
                    i1++;
                }
            }
            else{
                for (int col = 0; col < section[0].length; col++){
                    for (int row = 0; row < section.length; row++){
                        try{
                            output2[row][i2] = section[row][col];
                        }
                        catch (Exception e){}
                    }
                    i2++;
                }
            }
        }
        HackImage[] output = {new HackImage(output1), new HackImage(output2)};
        return output;
    }

    public static HackImage joinRow(HackImage img1, HackImage img2, int inter){
        int[][][][] sections1 = img1.sectionRow(inter), sections2 = img2.sectionRow(inter);
        int[][][] output = new int[(sections1.length * sections1[0].length) + (sections2.length * sections2[0].length)][sections1[0][0].length][sections1[0][0][0].length];
        int i1 = 0, i2 = 0, r = 0;

        for (int sect = 0; sect < sections1.length + sections2.length; sect++){
            int[][][] section;
            if (sect % 2 == 0){
                try{
                    section = sections1[i1];
                    i1++;
                }
                catch (Exception e){
                    section = sections2[i2];
                    i2++;
                }
            }
            else{
                try{
                    section = sections2[i2];
                    i2++;
                }
                catch (Exception e){
                    section = sections1[i1];
                    i1++;
                }
            }
            for (int row = 0; row < section.length; row++){
                try{
                    output[r] = section[row];
                }
                catch (Exception e){}
                r++;
            }
        }
        return new HackImage(output);
    }
    public static HackImage joinCol(HackImage img1, HackImage img2, int inter){
        int[][][][] sections1 = img1.sectionCol(inter), sections2 = img2.sectionCol(inter);
        int[][][] output = new int[sections1[0].length][(sections1[0][0].length * sections1.length) + (sections2[0][0].length * sections2.length)][sections1[0][0][0].length];
        int i1 = 0, i2 = 0, c = 0;

        for (int sect = 0; sect < sections1.length + sections2.length; sect++){
            int[][][] section;
            if (sect % 2 == 0){
                try{
                    section = sections1[i1];
                    i1++;
                }
                catch (Exception e){
                    section = sections2[i2];
                    i2++;
                }
            }
            else{
                try{
                    section = sections2[i2];
                    i2++;
                }
                catch (Exception e){
                    section = sections1[i1];
                    i1++;
                }
            }
            for (int col = 0; col < section[0].length; col++){
                for (int row = 0; row < section.length; row++){
                    try{
                        output[row][c] = section[row][col];
                    }
                    catch (Exception e){}
                    
                }
                c++;
            }
            
        }
        return new HackImage(output);
    }
}