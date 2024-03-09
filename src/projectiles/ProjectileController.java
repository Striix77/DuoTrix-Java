package projectiles;

import levels.Level;
import main.GamePanel;
import main.SoundPlayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ProjectileController {

    //class for each projectile
    private ArrayList<OmnicBomb> bombList = new ArrayList<>();
    private ArrayList<GarnetGlove> gloveList = new ArrayList<>();
    private GamePanel gp;

    private OmnicBomb tempBomb;
    private GarnetGlove tempGlove;
    private SoundPlayer soundPlayer;

    public ProjectileController(GamePanel gp, SoundPlayer soundPlayer) {
        this.gp = gp;
        this.soundPlayer=soundPlayer;
    }

    //updating the position of the projectiles
    public void updateProjectiles(){
        Iterator<OmnicBomb> iterator1 = bombList.iterator();
        while(iterator1.hasNext()){
            tempBomb=iterator1.next();
            tempBomb.updatePosition();
            if(tempBomb.getX()> gp.getWidth() || tempBomb.getY()> Level.groundLevel- (float) GamePanel.tileSize /2){
                iterator1.remove();
            }
        }

        Iterator<GarnetGlove> iterator2 = gloveList.iterator();
        while(iterator2.hasNext()){
            tempGlove=iterator2.next();
            tempGlove.updatePosition();
            if(tempGlove.getX()> gp.getWidth() || tempGlove.getY()> Level.groundLevel- (float) GamePanel.tileSize /2){
                iterator2.remove();
            }
        }
    }

    //getters
    public ArrayList<OmnicBomb> getBombList() {
        return bombList;
    }

    public ArrayList<GarnetGlove> getGloveList() {
        return gloveList;
    }

    public OmnicBomb getTempBomb() {
        return tempBomb;
    }
    public GarnetGlove getTempGlove() {
        return tempGlove;
    }

    //drawing the projectiles
    public void drawProjectiles(Graphics2D g2){

        bombList.stream().forEach(e->{
            e.drawBomb(g2);
        });
        gloveList.stream().forEach(e->{
            e.drawGlove(g2);
        });
    }

    //adding projectiles
    public void addBomb(int dir, int speed, float x, float y){
        if(dir==1) {
            bombList.add(new OmnicBomb(ProjectileSprites.omnicBombRight, dir, speed, x, y));
        }
        else{
            bombList.add(new OmnicBomb(ProjectileSprites.omnicBombLeft, dir, speed, x-50, y));
        }
        soundPlayer.playBombSound();
    }

    public void addGlove(int dir, int speed, float x, float y){
        if(dir==1) {
            gloveList.add(new GarnetGlove(ProjectileSprites.garnetGlove, dir, speed, x, y));
        }
        else{
            gloveList.add(new GarnetGlove(ProjectileSprites.garnetGlove, dir, speed, x-50, y));
        }
        soundPlayer.playGloveSound();
    }

    //removing projectiles
    public void removeBomb(OmnicBomb bomb){
        bombList.remove(bomb);
    }
    public void removeGlove(GarnetGlove bomb){
        gloveList.remove(bomb);
    }

}
