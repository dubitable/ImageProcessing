import java.io.IOException;

public class HackImageTester {
    public static void topbreeder() throws IOException{
        HackImage.setDir("images/hackathon");
        String img = "/Users/pierrequereuil/Downloads/h7n-nsi-01-main/images/chien.bmp";

        HackImage birthday = new HackImage(Image.open(img));
        HackImage[] results = birthday.seperateCol(10);
        HackImage[] results1 = results[0].seperateRow(10), results2 = results[1].seperateRow(10);
        
        results1[0].saveAsPBM("topbreeder1.jpg");
        results1[1].saveAsPBM("topbreeder2.jpg");
        results2[0].saveAsPBM("topbreeder3.jpg");
        results1[1].saveAsPBM("topbreeder4.jpg");
    } 

    public static void everydayismybirthday() throws IOException{
        HackImage.setDir("images/hackathon");
        String img = "/Users/pierrequereuil/Downloads/h7n-nsi-01-main/images/birthday.png";

        HackImage birthday = new HackImage(Image.open(img));
        HackImage[] results = birthday.seperateRow(10);
        HackImage.joinCol(results[0], results[1], 10).saveAsPBM("EverydayismyBirthday.jpg");
    }
}
