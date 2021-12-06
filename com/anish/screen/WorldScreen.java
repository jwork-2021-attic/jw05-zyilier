package com.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Random;

import com.anish.calabashbros.Bullet;
import com.anish.calabashbros.Calabash;
import com.anish.calabashbros.MinionSorter;
import com.anish.calabashbros.Minions;
import com.anish.calabashbros.World;
import com.anish.calabashbros.Floor;

import asciiPanel.AsciiPanel;

public class WorldScreen implements Screen {

    private World world;
    private Calabash[] bros;
    private Minions[][] minions;
    String[] sortSteps;
    int posx;
    int posy;
    public Calabash A;
    public Thread[] B;
    

    public WorldScreen() {
        world = new World();

        // bros = new Calabash[7];
        // minions = new Minions[4][4];

        // int l=16;
        // int[] order = new int[l];
        // Random R = new Random();
        // for(int i=0;i<l;i++){
        //     int randomNum = R.nextInt(l);
        //     while(existed(randomNum, order, i)) {
        //         randomNum = R.nextInt(l);
        //     }
        //     order[i]=randomNum;
        // }
        // int t = 0;

        // for(int i=0;i<4;i++){
        //     for(int j=0;j<4;j++){
        //         minions[i][j]=new Minions(new Color(order[t]*15,255-order[t]*15,0), order[t], world);
        //         world.put(minions[i][j],10 + j*2,10 + i*2);
        //         t++;
        //     }
        // }
        

        // MinionSorter<Minions> m = new MinionSorter<>();
        // m.load(minions);
        // m.sort();
    
        B = new Thread[5];
        // sortSteps = this.parsePlan(m.getPlan());
        A = new Calabash(new Color(255,255,0),1,world);
        for(int i=0;i<5;i++){
            B[i] = new Thread(new Minions(new Color(i*15,255-i*15,0), i, world));
            B[i].start();
        }

        world.put(A,0,0);
        posx = 0;
        posy = 0;
        hpthread hhh = new hpthread();
        hhh.start();

    }

    private String[] parsePlan(String plan) {
        return plan.split("\n");
    }

    private void execute(Calabash[] bros, String step) {
        String[] couple = step.split("<->");
        getMinByRank(minions, Integer.parseInt(couple[0])).swap(getMinByRank(minions, Integer.parseInt(couple[1])));
    }

    private Minions getMinByRank(Minions[][] bros, int rank) {
        for (int i=0;i<bros.length;i++) {
            for(int j=0;j<bros[i].length;j++)
            if (bros[i][j].getRank() == rank) {
                return bros[i][j];
            }
        }
        return null;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {

        for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {

                terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());

            }
        }
        displayMessages(terminal);     
    }
    
    private void displayMessages(AsciiPanel terminal) {
       
        String s2 = Integer.toString(world.score);

        String s1 = Integer.toString(A.hp);

        terminal.write("Hp:",32, 1);

        terminal.write( s1 , 39 , 1);

        terminal.write("Scores:", 32, 3);

        terminal.write( s2 , 39 , 3);
    
    }




    int i = 0;

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        if(world.if_win == -1)
            return new LoseScreen();
        else if(world.if_win == 1){
            return new WinScreen();
        }

        int code = key.getKeyCode();
        System.out.println(code);
        switch(code){
            case 40:
            A.direction = 4;
                if(judge(posx,posy+1)){
                    if(world.get(posx,posy+1).getGlyph()==(char)250){
                        A.hp--;
                    }
                    world.put(A,posx,posy+1);
                    world.put(new Floor(world), posx, posy);
                    posy++;
                }
                break;
            case 38:
            A.direction = 2;
            if(judge(posx,posy-1)){
                if(world.get(posx,posy-1).getGlyph()==(char)250){
                    A.hp--;
                }
                world.put(A,posx,posy-1);
                world.put(new Floor(world), posx, posy);
                posy--;
            }
            break;
            case 39:
            A.direction = 3;
            if(judge(posx+1,posy)){
                if(world.get(posx+1,posy).getGlyph()==(char)250){
                    A.hp--;
                }
                world.put(A,posx+1,posy);
                world.put(new Floor(world), posx, posy);
                posx++;
            }
            break;
            case 37:
            A.direction = 1;
            if(judge(posx-1,posy)){
                if(world.get(posx-1,posy).getGlyph()==(char)250){
                    A.hp--;
                }
                world.put(A,posx-1,posy);
                world.put(new Floor(world), posx, posy);
                posx--;
            }
            break;
            case 32:
            int tarx = -1,tary = -1;
            if(A.direction == 1){
                if(judge(posx-1, posy)){
                    tarx = posx -1;
                    tary = posy;
                }
            }
            else if(A.direction == 2){
                if(judge(posx, posy-1)){
                    tarx = posx;
                    tary = posy - 1;
                }
            }
            else if(A.direction == 3){
                if(judge(posx+1, posy)){
                    tarx = posx +1;
                    tary = posy;
                }
            }
            else{
                if(judge(posx, posy + 1)){
                    tarx = posx;
                    tary = posy + 1;
                }
            }
            if(tarx != -1 && tary != -1){
                System.out.println("successfully attack");
                if(world.get(tarx,tary).getGlyph() == (char)1){
                   world.get(tarx,tary).hp--;
                }
                else{
                    Thread t = new Thread (new Bullet(world, A.direction,tarx,tary));
                    t.start();
                }
            }
            break;
        }

        return this;
    }
    public boolean judge(int x,int y){
        if(x>=0&&y>=0&&x<world.HEIGHT&&y<world.WIDTH){
            if(world.get(x,y).getColor()!=AsciiPanel.cyan){
                return true;
            }
        }
        return false;
    }
    public static boolean existed(int num, int[] array, int index) {
		for(int i=0; i<index; i++) {
			if(num == array[i]) {
				return true;
			}
		}
		return false;
	}

    public class hpthread extends Thread{
        public void run(){
            while(world.if_win == 0){
                try{
                    Thread.sleep(50);
                    world.playerx = A.getX();
                    world.playery = A.getY();
                    System.out.println(world.score);
                    if(world.score == 5){
                        world.if_win = 1;
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
        
    }

}
