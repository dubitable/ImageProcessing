import java.io.IOException;

public class EncoderImageTester { 
    public static void test() throws IOException{
        String img1 = "/Users/pierrequereuil/Desktop/anatole.png";
        String img2 = "/Users/pierrequereuil/Desktop/head.jpeg";

        EncoderImage anatole = new EncoderImage(Image.open(img1));
        EncoderImage head = new EncoderImage(Image.open(img2));
       

        EncoderImage encoded = head.encode(anatole);
        EncoderImage decoded = encoded.decode(200);
        
        Image.setDir("images/encoding");
        encoded.saveAs("anatole_head.jpg");
        decoded.saveAs("anatole_head_decoded.jpg");
    }
}
