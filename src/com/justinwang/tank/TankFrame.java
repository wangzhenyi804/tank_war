package com.justinwang.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Justice Wang
 * @Date: 2020/7/20 22:56
 * @Description:
 */
public class TankFrame extends Frame {

    //游戏画面大小
    static final int GAME_WIDTH = 1080;
    static final int GAME_HEIGHT = 960;

    //自己的tank
    Tank myTank = new Tank(300, 400, Dir.UP, this, false, Group.GOOD);

    //子弹
    List<Bullet> bullets = new ArrayList<>();

    //敌方tank
    List<Tank> badTanks = new ArrayList<>();

    //爆炸
    Explode explode = new Explode();

    //tank的方向
    boolean tankUp = false;
    boolean tankDown = false;
    boolean tankLeft = false;
    boolean tankRight = false;

    //总的tank数量
    private int tankSum = badTanks.size() + 1;

    /**
     * 键盘的监听处理类
     */
    class MykeyListener extends KeyAdapter {

        /**
         * 按键响应
         *
         * @param e
         */
        @Override
        public void keyPressed(KeyEvent e) {
            /*x += 10;
            //刷新窗口，通过该方法来调用paint方法
            repaint();*/


            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_RIGHT:
                    tankRight = true;
                    break;
                case KeyEvent.VK_LEFT:
                    tankLeft = true;
                    break;
                case KeyEvent.VK_UP:
                    tankUp = true;
                    break;
                case KeyEvent.VK_DOWN:
                    tankDown = true;
                    break;
                default:
                    break;
            }
            //设置tank的方向
            setMainTankDir();
        }

        /**
         * 按键抬起的时候响应
         *
         * @param e
         */
        @Override
        public void keyReleased(KeyEvent e) {

            int keyCode = e.getKeyCode();

            switch (keyCode) {
                case KeyEvent.VK_RIGHT:
                    tankRight = false;
                    break;
                case KeyEvent.VK_LEFT:
                    tankLeft = false;
                    break;
                case KeyEvent.VK_UP:
                    tankUp = false;
                    break;
                case KeyEvent.VK_DOWN:
                    tankDown = false;
                    break;
                case KeyEvent.VK_CONTROL:
                    myTank.fire();
                    break;
                default:
                    break;
            }
            //设置tank的方向
            setMainTankDir();
        }
    }

    /**
     * 设置Tank的方向
     */
    private void setMainTankDir() {


        if (!tankUp && !tankDown && !tankLeft && !tankRight) {
            myTank.setMoving(false);
        } else {
            myTank.setMoving(true);
            if (tankUp) myTank.setDir(Dir.UP);
            if (tankDown) myTank.setDir(Dir.DOWN);
            if (tankLeft) myTank.setDir(Dir.LEFT);
            if (tankRight) myTank.setDir(Dir.RIGHT);
        }
    }

    public TankFrame() {
        //设置窗口大小
        setSize(GAME_WIDTH, GAME_HEIGHT);
        //设置窗口大小是否可变
        setResizable(false);
        setTitle("tank war");
        //设置窗口可见
        setVisible(true);

        //添加一个键盘监听器
        this.addKeyListener(new MykeyListener());

        //设置一个窗口监听器
        addWindowListener(new WindowAdapter() {

            /**
             * 监听窗口closing事件
             * @param e
             */
            @Override
            public void windowClosing(WindowEvent e) {
                //当点击窗口上的小X时，系统退出
                System.exit(0);
            }
        });
    }


    Image offScreenImage = null;

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    /**
     * 当窗口显示出来的时候，该方法被系统自动调用
     *
     * @param g 窗口显示出来的时候，系统提供的一个画笔
     */
    @Override
    public void paint(Graphics g) {
        
        //子弹计数器
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量:" + bullets.size(), 10, 60);
        g.drawString("敌方坦克数量:" + badTanks.size(), 10, 80);
        g.setColor(c);

        //tank
        myTank.setTanSpeed(20);
        myTank.paint(g);

        //爆炸
        if (explode.isLive()){
            explode.paint(g);
        }



        //子弹
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }

        //敌方tank
        for (int i = 0; i < badTanks.size(); i++) {
            badTanks.get(i).paint(g);
        }
        
        //子弹和tank的碰撞检测
        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < badTanks.size(); j++) {
                bullets.get(i).collideWith(badTanks.get(j));
            }
        }
        


        //这样写会报错
//        for (com.justinwang.tank.Bullet bullet : bullets) {
//            bullet.paint(g);
//        }
    }
}