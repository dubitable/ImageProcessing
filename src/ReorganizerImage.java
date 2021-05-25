import java.util.ArrayList;
import java.util.HashMap;

public class ReorganizerImage extends Image{
    public ReorganizerImage(int[][][] pixels){
        super(pixels);
    }

    public ReorganizerImage(Image img){
        super(img.rgbPixels());
    }

    public static HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> getMap(Image img){
        int[][][] pixels = img.rgbPixels();
        HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> output = new HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>();
        for (int row = 0; row < img.height(); row++){
            for (int col = 0; col < img.width(); col++){
                int[] pixel = pixels[row][col];
                HashMap<Integer, ArrayList<Integer>> map;
                if (output.get(pixel[0]) == null){
                    map = new HashMap<Integer, ArrayList<Integer>>();
                    ArrayList<Integer> list = new ArrayList<Integer>();
                    list.add(pixel[2]);
                    map.put(pixel[1], list);
                    output.put(pixel[0], map);
                }
                else{
                    ArrayList<Integer> list;
                    map = output.get(pixel[0]);
                    if (map.get(pixel[1]) == null){
                        list = new ArrayList<Integer>();
                        list.add(pixel[2]);
                        map.put(pixel[1], list);
                   }
                   else{
                       list = map.get(pixel[1]);
                       list.add(pixel[2]);
                   }
                }
            }
        }
        return output;
    }

    private static int getKey(int[] pixel, int level, HashMap map){
        int key = -1;
        for (int i = 0; i < 255; i++){
            if (map.containsKey(pixel[level] + i)){
                key = pixel[level] + i; break;
            }
            if (map.containsKey(pixel[level] - i)){
                key = pixel[level] - i; break;
            }
        }
        return key;
    }

    public static int[] getSimilarPixel(HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> map1, int[] pixel){
        int key1 = ReorganizerImage.getKey(pixel, 0, map1);
        HashMap<Integer, ArrayList<Integer>> map2 = map1.get(key1);
        int key2 = ReorganizerImage.getKey(pixel, 1, map2);
        ArrayList<Integer> list = map2.get(key2);
        int k = 0;
        for (int i = 0; i < list.size(); i++){
            if (Math.abs(pixel[2] - list.get(i)) < Math.abs(pixel[2] - list.get(k))){
                k = i;
            }
        }
        int key3 = list.get(k);

        list.remove(k);
        if (list.size() == 0){
            map2.remove(key2);
            if (map2.keySet().size() == 0){
                map1.remove(key1);
            }
        }

        int[] newpixel = {key1, key2, key3};
        return newpixel;
    }

    public ReorganizerImage reoganize(Image img){
        ReorganizerImage toReorganize = new ReorganizerImage(this.resize(img.height(), img.width()));
        HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> map = getMap(toReorganize);
        int[][][] imgPixels = img.rgbPixels();
        int[][][] output = new int[toReorganize.height()][toReorganize.width()][toReorganize.depth()];

        for (int row = 0; row < img.height(); row++){
            for (int col = 0; col < img.width(); col++){
                int[] pixel = imgPixels[row][col];
                try{
                    output[row][col] = getSimilarPixel(map, pixel);
                }
                catch (Exception e){
                }
            }
        }
        return new ReorganizerImage(output);
    }
}
