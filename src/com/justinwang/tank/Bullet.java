package com.justinwang.tank;

import java.awt.*;

/**
 * @Auther: Justice Wang
 * @Date: 2020/7/23 00:51
 * @Description:
 */
public class Bullet {
    private boolean live = true;

    //子弹的速度
    private static final int bulletSpeed = 30;
    //子弹的大小
    public static final int WIDTH = ResourceMgr.BulletL.getWidth();
    public static final int HEIGHT = ResourceMgr.BulletL.getHeight();

    //子弹的初始位置
    private int x, y;
    //子弹的方向
    private Dir dir;

    private TankFrame tf;

    //子弹的rec大小（用来判断是否碰撞）
    private Rectangle bulletRec = new Rectangle(x,y,WIDTH,HEIGHT);

    //子弹的阵营
    private Group group;

    public Bullet(int x, int y, Dir dir, TankFrame tf, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
        this.group = group;
    }

    public void paint(Graphics g) {
        if (!live) {
            tf.bullets.remove(this);
        }

//        Color color = g.getColor();
//        g.setColor(Color.GRAY);
//        g.fillOval(x, y, WIDTH, HEIGHT);
//        g.setColor(color);

        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.BulletL,x,y,null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.BulletR,x,y,null);
                break;
            case UP:
                g.drawImage(ResourceMgr.BulletU,x,y,null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.BulletD,x,y,null);
                break;
        }

        move();
    }

    /**
     * 控制tank的移动状态
     */
    private void move() {

        switch (dir) {
            case UP:
                y -= bulletSpeed;
                break;

            case DOWN:
                y += bulletSpeed;
                break;

            case LEFT:
                x -= bulletSpeed;
                break;

            case RIGHT:
                x += bulletSpeed;
                break;

            default:
                break;
        }

        //子弹飞出边界
        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT ){
            this.die();
        }
    }

    //让子弹死亡
    private void die() {
        live = false;
    }


    /**
     * 碰撞检测
     * @param tank
     */
    public void collideWith(Tank tank) {
        if (this.group == tank.getGroup()) return;
        Rectangle recb = new Rectangle(x,y,WIDTH,HEIGHT);
        Rectangle rect = new Rectangle(tank.getX(),tank.getY(),Tank.WIDTH, Tank.HEIGHT);

        if (recb.intersects(rect)) {
            this.die();

            //让tank死亡，同时爆炸
            tank.die();
        }
    }
}