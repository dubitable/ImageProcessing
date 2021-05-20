public class EncoderImage extends Image{
    public EncoderImage(int[][][] pixels){
        super(pixels);
    }

    public EncoderImage(Image img){
        super(img.rgbPixels());
    }
}
