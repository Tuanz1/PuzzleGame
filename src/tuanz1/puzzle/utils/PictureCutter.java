package tuanz1.puzzle.utils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 用于快速剪切图片,生成拼图块
 * @author zera
 */
public class PictureCutter {
    private String path;
    private int nX, nY;
    private File imageInput;
    private Image origin;
    private Graphics g;
    private FileOutputStream output;
    JPEGImageEncoder jEncoder;
    public PictureCutter(int x, int y){
        this.nX = x;
        this.nY = y;
        this.path = "assets/default2.png";
    }
    public PictureCutter(File image, int x, int y) {
        this.imageInput = image;
        this.nX = x;
        this.nY = y;
        cut();
    }

    public void cut(){
        try {
            if(imageInput == null) {
                imageInput = new File(path);
            }
            origin = ImageIO.read(imageInput);
            int width = origin.getWidth(null);
            int height = origin.getHeight(null);
            int count = 0;
            for (int j = 0; j < nY; j++){
                for (int i = 0; i < nX; i++){
                    BufferedImage bfImage = new BufferedImage(width / nX, height / nY, BufferedImage.TYPE_INT_BGR);
                    g = bfImage.createGraphics();
                    g.drawImage(origin, width * -i / nX, height * -j /nY, width,height,null);
                    g.dispose();
                    output = new FileOutputStream( "assets/default/" + Level.getName(nX) + "/" + count + ".jpg");
                    jEncoder = JPEGCodec.createJPEGEncoder(output);
                    jEncoder.encode(bfImage);
                    output.close();
//                    System.out.println("剪切图片" + count + ".jpg");
                    count++;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
