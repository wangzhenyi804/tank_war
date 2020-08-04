package com.justinwang.tank;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Auther: Justice Wang
 * @Date: 2020/7/28 23:24
 * @Description:
 */
public class ResourceMgr {
    public static BufferedImage goodTankL;
    public static BufferedImage goodTankR;
    public static BufferedImage goodTankU;
    public static BufferedImage goodTankD;

    public static BufferedImage badTankL;
    public static BufferedImage badTankR;
    public static BufferedImage badTankU;
    public static BufferedImage badTankD;

    public static BufferedImage BulletL;
    public static BufferedImage BulletR;
    public static BufferedImage BulletU;
    public static BufferedImage BulletD;

    //爆炸图片
    public static BufferedImage[] explodes = new BufferedImage[ 16 ];

    static {
        try {
            //加载tank图片
            goodTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
            goodTankD = ImageUtil.rotateImage(goodTankU, 180);
            goodTankL = ImageUtil.rotateImage(goodTankU, -90);
            goodTankR = ImageUtil.rotateImage(goodTankU, 90);

            //加载tank图片
            badTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/BadTank1.png"));
            badTankD = ImageUtil.rotateImage(badTankU, 180);
            badTankL = ImageUtil.rotateImage(badTankU, -90);
            badTankR = ImageUtil.rotateImage(badTankU, 90);

            //加载子弹图片
            BulletU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.png"));
            BulletD = ImageUtil.rotateImage(BulletU, 180);
            BulletL = ImageUtil.rotateImage(BulletU, -90);
            BulletR = ImageUtil.rotateImage(BulletU, 90);

            //加载爆炸图片
            for (int i = 0; i < explodes.length; i++) {
                explodes[ i ] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/e" + (i + 1) + ".gif"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}