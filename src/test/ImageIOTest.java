package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * @Auther: Justice Wang
 * @Date: 2020/7/28 00:21
 * @Description:
 */

public class ImageIOTest {

    @Test
    public void ImageIOTest(){
        try {
//            image = ImageIO.read(ImageIOTest.class.getClassLoader().getResourceAsStream("images/tankU.gif"));
            BufferedImage image = ImageIO.read(ImageIOTest.class.getClassLoader().getResourceAsStream("images/tankD.gif"));
            assertNotNull(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}