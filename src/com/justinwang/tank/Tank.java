package com.justinwang.tank;

import java.awt.*;
import java.util.Random;

/**
 * @Auther: Justice Wang
 * @Date: 2020/7/22 23:43
 * @Description:
 */
public class Tank {
    private int x;
    private int y;
    //tank的方向
    private Dir dir;
    //tank的速度
    private int tanSpeed = 5;
    //移动属性
    private boolean moving;
    private Random random = new Random();
    //阵营
    private Group group;


    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public int getTanSpeed() {
        return tanSpeed;
    }

    public void setTanSpeed(int tanSpeed) {
        this.tanSpeed = tanSpeed;
    }

    //tank的大小
    public static final int WIDTH = ResourceMgr.goodTankL.getWidth();
    public static final int HEIGHT = ResourceMgr.goodTankL.getHeight();

    //tank的存活状态
    private boolean live = true;

    private TankFrame tf;

    public Rectangle getTankRec() {
        return tankRec;
    }

    public void setTankRec(Rectangle tankRec) {
        this.tankRec = tankRec;
    }

    //tank的rec大小
    private Rectangle tankRec = new Rectangle(x, y, WIDTH, HEIGHT);

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public Tank(int x, int y, Dir dir, TankFrame tf, boolean moving, Group group) {
        super();
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
        this.moving = moving;
        this.group = group;
    }

    /**
     * 当窗口显示出来的时候，该方法被系统自动调用
     *
     * @param g 窗口显示出来的时候，系统提供的一个画笔
     */
    public void paint(Graphics g) {
        if (!this.live) {
            tf.badTanks.remove(this);
        }

        if (this.getGroup() == Group.GOOD) {
            switch (dir) {
                case LEFT:
                    g.drawImage(ResourceMgr.goodTankL, x, y, null);
                    break;
                case RIGHT:
                    g.drawImage(ResourceMgr.goodTankR, x, y, null);
                    break;
                case UP:
                    g.drawImage(ResourceMgr.goodTankU, x, y, null);
                    break;
                case DOWN:
                    g.drawImage(ResourceMgr.goodTankD, x, y, null);
                    break;
            }
        }

        if (this.getGroup() == Group.BAD) {
            switch (dir) {
                case LEFT:
                    g.drawImage(ResourceMgr.badTankL, x, y, null);
                    break;
                case RIGHT:
                    g.drawImage(ResourceMgr.badTankR, x, y, null);
                    break;
                case UP:
                    g.drawImage(ResourceMgr.badTankU, x, y, null);
                    break;
                case DOWN:
                    g.drawImage(ResourceMgr.badTankD, x, y, null);
                    break;
            }
        }

        move();
    }

    /**
     * 控制tank的移动状态
     */
    private void move() {
        if (!moving) return;

        //自己的tank根据按键来改变方向
        switch (dir) {
            case UP:
                y -= tanSpeed;
                break;

            case DOWN:
                y += tanSpeed;
                break;

            case LEFT:
                x -= tanSpeed;
                break;

            case RIGHT:
                x += tanSpeed;
                break;

            default:
                break;
        }

        //边界检测
        boundsCheck();

        //让 badtank 在移动的过程中随机打出子弹
        if (random.nextInt(100) > 70 && this.getGroup() == Group.BAD) {
            this.fire();
        }

        //让badtank随机移动
        if (this.getGroup() == Group.BAD && random.nextInt(100) > 96) {
            this.setDir(Dir.values()[ random.nextInt(4) ]);
        }
    }

    /**
     * 边界检测
     */
    private void boundsCheck() {
        if (this.x < 2) {
            this.x = 2;
        }
        if (this.y < 28) {
            this.y = 28;
        }
        if (this.x > tf.getWidth() -Tank.getWIDTH() - 2) {
            this.x = tf.getWidth() - Tank.getWIDTH() - 2;
        }
        if (this.y > tf.getHeight() -Tank.getHEIGHT() - 2) {
            this.y = tf.getHeight() - Tank.getHEIGHT() - 2;
        }
    }


    /**
     * 开火
     */
    public void fire() {
        //子弹的初始位置
        int bulletx = this.x + Tank.getWIDTH() / 2 - Bullet.WIDTH / 2;
        int bullety = this.y + Tank.getHEIGHT() / 2 - Bullet.HEIGHT / 2;

        tf.bullets.add(new Bullet(bulletx, bullety, this.dir, this.tf, this.group));
    }


    /**
     * 让tank死亡
     */
    public void die() {
        this.live = false;
        this.explode();
    }

    /**
     * tank爆炸
     */
    public void explode() {
        tf.explode = new Explode(this.x, this.y, this.tf);
        tf.explode.setLive(true);
    }

}