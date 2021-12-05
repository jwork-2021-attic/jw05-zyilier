package com.anish.calabashbros;

import java.awt.Color;

public class Bullet extends Thing implements Runnable{
    
    
    public int dir;
    public int posx;
    public int posy;
    public int lastx;
    public int lasty;

    public Bullet( World world,int dir,int x,int y) {
        super(Color.gray, (char)250, world);
        hp = 1;
        this.dir = dir;
        posx = x;
        posy = y;
        world.put(this, posx, posy);
    }

    public void run(){
        
        while(dir != -1){
            try{
                Thread.sleep(300);
                lastx = posx;
                lasty = posy;
                if(dir == 1)
                posx--;
                else if(dir == 2)
                posy--;
                else if(dir == 3)
                posx++;
                else posy++;
                if(posx<0||posx>=30||posy<0||posy>=30){
                    dir = -1;
                    world.put(new Floor(world), lastx, lasty);
                    continue;
                }
                if(world.get(posx,posy).getGlyph() == (char)32){
                    world.put(this, posx, posy);
                    world.put(new Floor(world), lastx, lasty);
                }
                else if(world.get(posx,posy).getGlyph() == (char)1 || world.get(posx,posy).getGlyph() == (char)2 || world.get(posx,posy).getGlyph() == (char)177){
                    world.get(posx,posy).hp --;
                    System.out.println("mingzhong");
                    world.put(new Floor(world), lastx, lasty);
                    dir = -1;
                }
                else {
                    world.put(new Floor(world), lastx, lasty);
                    dir = -1;
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}
