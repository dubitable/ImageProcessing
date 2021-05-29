import java.io.IOException;

public class EncoderImageTester { 
    public static void test() throws IOException{
        String img1 = "/Users/pierrequereuil/Desktop/anatole.png";
        String img2 = "/Users/pierrequereuil/Desktop/head.jpeg";

        EncoderImage anatole = new EncoderImage(Image.open(img1));
        EncoderImage head = new EncoderImage(Image.open(img2));
    
        Image.setDir("images/encoding");

        EncoderImage encoded = head.encode(anatole);
        EncoderImage decoded = encoded.decode(200);
       
        encoded.saveAs("anatole_head.jpg");
        decoded.saveAs("anatole_head_decoded.jpg");

        encoded = anatole.encode(head);
        decoded = encoded.decode(200);
       
        encoded.saveAs("head_anatole.jpg");
        decoded.saveAs("head_anatole_decoded.jpg");

    }
}
