package com.justinwang.tank;


import java.util.Random;

/**
 * @Auther: Justice Wang
 * @Date: 2020/7/20 22:35
 * @Description:
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tankFrame = new TankFrame();
        Random random = new Random();

        //初始化敌方坦克
        for (int i = 0; i < 6; i++) {
            tankFrame.badTanks.add(new Tank(random.nextInt(1080), random.nextInt(960), Dir.DOWN, tankFrame, true, Group.BAD));
        }

        new Thread(() -> new Audio("audio/war1.wav").loop()).start();
        
        //让方块每隔100mills自动移动
        while(true){
            Thread.sleep(100);
            tankFrame.repaint();
        }
    }
}