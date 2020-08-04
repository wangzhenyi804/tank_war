package com.justinwang.tank;

import java.awt.*;

/**
 * @Auther: Justice Wang
 * @Date: 2020/7/29 23:12
 * @Description:
 */
public class Explode {
    //爆炸的宽和高
    public static int WIDTH = ResourceMgr.explodes[ 0 ].getWidth();
    public static int Height = ResourceMgr.explodes[ 0 ].getHeight();

    //爆炸的初始位置
    private int x;
    private int y;

    //爆炸的存活状态
    private boolean live = false;

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    //tankFrame属性
    TankFrame tankFrame = null;

    //画出一个完整的爆炸一共需要张图片连续播放 step指代画到了第几步
    private int step = 0;

    //构造方法
    public Explode(int x, int y, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;

        new Thread(() -> new Audio("audio/explode.wav").play()).start();
    }

    public Explode(){}

    //paint方法来画自己
    public void paint(Graphics g) {
        if (this.live) {
            g.drawImage(ResourceMgr.explodes[ step++ ], this.x, this.y, null);
        }
        if (step >= ResourceMgr.explodes.length) {
            step = 0;
            this.live = false;
        }
    }
}