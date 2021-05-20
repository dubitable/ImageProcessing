public class EncoderImage extends Image{
    public EncoderImage(int[][][] pixels){
        super(pixels);
    }

    public EncoderImage(Image img){
        super(img.rgbPixels());
    }

    private static String toBinary(int num){
        String binNum = "";
        while (num > 0){
            binNum = (num % 2) + binNum;
            num /= 2;
        }
        for (int i = binNum.length(); i < 8; i++){
            binNum = "0" + binNum;
        }
        return binNum;
    }

    private static int toDecimal(String bin){
        int num = 0;
        for (int i = 0; i < bin.length(); i++){
            num += Integer.parseInt(bin.substring(i, i+1)) * Math.pow(2, bin.length() - i - 1);
        }
        return num;
    }


    public EncoderImage encode(Image toEncode){
        int percentage, toggle;

        if (toEncode.height() > toEncode.width()){
            percentage = (int) ((double) toEncode.width() / toEncode.height() * 100);
            toggle = 0;
        }
        else{
            percentage = (int) ((double) toEncode.height() / toEncode.width() * 100);
            toggle = 1;
        }

        toEncode = toEncode.resize(height(), width());

        int[][][] pixels = this.rgbPixels();
        int[][][] toEncodePixels = toEncode.rgbPixels();
        int[][][] output = new int[height()][width()][depth()];

        for (int row = 0; row < pixels.length; row++){
            for (int col = 0; col < pixels[0].length; col++){
                for (int depth = 0; depth < pixels[0][0].length; depth++){
                    String binValue = toBinary(pixels[row][col][depth]);
                    String binValueToEncode = toBinary(toEncodePixels[row][col][depth]);
                    binValue = binValue.substring(0, 4) + binValueToEncode.substring(0, 4);
                    output[row][col][depth] = toDecimal(binValue);
                }
            }
        }

        int[] meta = {percentage, toggle, output[0][0][2]};
        output[0][0] = meta;

        return new EncoderImage(output);
    }

    public EncoderImage decode(){
        return decode(0, false);
    }

    public EncoderImage decode(int ref){
        return decode(ref, true);
    }

    public EncoderImage decode(int ref, boolean resize){
        int[][][] pixels = this.rgbPixels();
        int[][][] output = new int[pixels.length][pixels[0].length][pixels[0][0].length];

        for (int row = 0; row < pixels.length; row++){
            for (int col = 0; col < pixels[0].length; col++){
                for (int depth = 0; depth < pixels[0][0].length; depth++){
                    String binValue = toBinary(pixels[row][col][depth]);
                    binValue = binValue.substring(4, 8) + binValue.substring(0, 4);
                    output[row][col][depth] = toDecimal(binValue);
                }
            }
        }

        int percentage = pixels[0][0][0], toggle = pixels[0][0][1];
        int height, width;

        if (toggle == 0){
            width = (int) ((double) percentage / 100 * ref);
            height = ref;
        }
        else{
            height = (int) ((double) percentage / 100 * ref);
            width = ref;
        }

        EncoderImage img = new EncoderImage(output);
        if (resize){
            img = new EncoderImage(img.resize(height, width));
        }
        return img;
    }
}
