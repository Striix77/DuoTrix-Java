package main;

import entity.Entity;
import entity.Finn;
import entity.Garnet;
import entity.Omnic;

import java.awt.*;

public class Player {

    //class for each player
    private Finn finn;
    private Garnet garnet;
    private Omnic omnic;
    private String direction;
    private CharacterSwitcher cs;
    private SoundPlayer soundPlayer;
    private float health;
    private int score = 0;
    final private float MAX_HEALTH = 1000;
    private boolean playingTransformation = false;


    public void setUtilities(CharacterSwitcher cs, SoundPlayer sp) {
        this.cs = cs;
        soundPlayer = sp;
    }

    //gettters, setters
    public Garnet getGarnet() {
        return garnet;
    }

    public void setGarnet(Garnet garnet) {
        this.garnet = garnet;
    }

    public Finn getFinn() {
        return finn;
    }

    public void setFinn(Finn finn) {
        this.finn = finn;
    }

    public Omnic getOmnic() {
        return omnic;
    }

    public void setOmnic(Omnic omnic) {
        this.omnic = omnic;
    }

    public Entity getEnabledEntity() {
        if (getOmnic().isEnabled()) {
            return getOmnic();
        }
        if (getFinn().isEnabled()) {
            return getFinn();
        }
        return getGarnet();
    }


    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getMaxHealth() {
        return MAX_HEALTH;
    }

    public float getX() {
        if (finn.isEnabled()) {
            return finn.getX();
        }
        if (garnet.isEnabled()) {
            return garnet.getX();
        }
        return omnic.getX();
    }

    public void setX(float x) {
        finn.setX(x);
        omnic.setX(x);
        garnet.setX(x);
    }

    public float getY() {
        if (finn.isEnabled()) {
            return finn.getY();
        }
        if (garnet.isEnabled()) {
            return garnet.getY();
        }
        return omnic.getY();
    }

    public void setY(float y) {
        finn.setY(y);
        omnic.setY(y);
        garnet.setY(y);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDirection() {
        return finn.getDirection();
    }

    public void setDirection(String direction) {
        finn.setDirection(direction);
        omnic.setDirection(direction);
        garnet.setDirection(direction);
    }

    public void setPosition(float x, float y, String direction, float speedX, float speedY) {
        finn.setPosition(x, y, direction, speedX, speedY);
        omnic.setPosition(x, y, direction, speedX, speedY);
        garnet.setPosition(x, y, direction, speedX, speedY);
    }


    //updates each entity and the transformation
    public void update() {
        if (getFinn().isEnabled()) {
            getFinn().update();

            //Finn->Omnic
            if (cs.switchCharacters == 1 && !getOmnic().isEnabled()) {
                getFinn().setTransforming(true);
                cs.switching = true;
                if (!playingTransformation) {
                    soundPlayer.playTransformSound();
                    playingTransformation = true;
                }
                if (cs.switched) {
                    getOmnic().setEnabled(true);
                    getOmnic().setDirection(getFinn().getDirection());
                    cs.switched = false;
                    cs.switching = false;
                    getFinn().setTransforming(false);
                    getFinn().setEnabled(false);
                    playingTransformation = false;
                }
                getEnabledEntity().keyH.shooting = false;
                getOmnic().setLeftOrRight(getFinn().isLeftOrRight());
                cs.updateTransformationFlash(getX(), getY());
                getOmnic().setPosition(getX(), getY(), getDirection(), getFinn().getSpeedX(), getFinn().getSpeedY());

            }

            //Finn->Garnet

            if (cs.switchCharacters == 2 && !getGarnet().isEnabled()) {
                getFinn().setTransforming(true);
                cs.switching = true;
                if (!playingTransformation) {
                    soundPlayer.playTransformSound();
                    playingTransformation = true;
                }
                if (cs.switched) {
                    getGarnet().setEnabled(true);
                    getGarnet().setDirection(getFinn().getDirection());
                    cs.switched = false;
                    cs.switching = false;
                    getFinn().setTransforming(false);
                    getFinn().setEnabled(false);
                    playingTransformation = false;
                }
                getEnabledEntity().keyH.shooting = false;
                getGarnet().setLeftOrRight(getFinn().isLeftOrRight());
                cs.updateTransformationFlash(getX(), getY());
                getGarnet().setPosition(getX(), getY(), getDirection(), getFinn().getSpeedX(), getFinn().getSpeedY());

            }
        }

        if (getOmnic().isEnabled()) {
            getOmnic().update();

            //Omnic->Finn
            if (cs.switchCharacters == 0 && !getFinn().isEnabled()) {
                getOmnic().setTransforming(true);
                cs.switching = true;
                if (!playingTransformation) {
                    soundPlayer.playTransformSound();
                    playingTransformation = true;
                }
                if (cs.switched) {
                    getFinn().setEnabled(true);
                    getFinn().setDirection(getOmnic().getDirection());
                    cs.switched = false;
                    cs.switching = false;
                    getOmnic().setTransforming(false);
                    getOmnic().setEnabled(false);
                    playingTransformation = false;
                }
                getEnabledEntity().keyH.shooting = false;
                getFinn().setLeftOrRight(getOmnic().isLeftOrRight());
                cs.updateTransformationFlash(getOmnic().getX(), getOmnic().getY());
                getFinn().setPosition(getOmnic().getX(), getOmnic().getY(), getOmnic().getDirection(), getOmnic().getSpeedX(), getOmnic().getSpeedY());
            }

            //Omnic->Garnet
            if (cs.switchCharacters == 2 && !getGarnet().isEnabled()) {
                getOmnic().setTransforming(true);
                cs.switching = true;
                if (!playingTransformation) {
                    soundPlayer.playTransformSound();
                    playingTransformation = true;
                }
                if (cs.switched) {
                    getGarnet().setEnabled(true);
                    getGarnet().setDirection(getOmnic().getDirection());
                    cs.switched = false;
                    cs.switching = false;
                    getOmnic().setTransforming(false);
                    getOmnic().setEnabled(false);
                    playingTransformation = false;
                }
                getEnabledEntity().keyH.shooting = false;
                getGarnet().setLeftOrRight(getOmnic().isLeftOrRight());
                cs.updateTransformationFlash(getOmnic().getX(), getOmnic().getY());
                getGarnet().setPosition(getOmnic().getX(), getOmnic().getY(), getOmnic().getDirection(), getOmnic().getSpeedX(), getOmnic().getSpeedY());
            }


        }

        if (getGarnet().isEnabled()) {
            getGarnet().update();

            //Garnet->Finn
            if (cs.switchCharacters == 0 && !getFinn().isEnabled()) {
                getGarnet().setTransforming(true);
                cs.switching = true;
                if (!playingTransformation) {
                    soundPlayer.playTransformSound();
                    playingTransformation = true;
                }
                if (cs.switched) {
                    getFinn().setEnabled(true);
                    getFinn().setDirection(getGarnet().getDirection());
                    cs.switched = false;
                    cs.switching = false;
                    getGarnet().setTransforming(false);
                    getGarnet().setEnabled(false);
                    playingTransformation = false;
                }
                getEnabledEntity().keyH.shooting = false;
                cs.updateTransformationFlash(getGarnet().getX(), getGarnet().getY());
                getFinn().setLeftOrRight(getGarnet().isLeftOrRight());
                getFinn().setPosition(getGarnet().getX(), getGarnet().getY(), getGarnet().getDirection(), getGarnet().getSpeedX(), getGarnet().getSpeedY());
            }

            //Garnet->Omnic
            if (cs.switchCharacters == 1 && !getOmnic().isEnabled()) {
                getGarnet().setTransforming(true);
                cs.switching = true;
                if (!playingTransformation) {
                    soundPlayer.playTransformSound();
                    playingTransformation = true;
                }
                if (cs.switched) {
                    getOmnic().setEnabled(true);
                    getOmnic().setDirection(getGarnet().getDirection());
                    cs.switched = false;
                    cs.switching = false;
                    getGarnet().setTransforming(false);
                    getGarnet().setEnabled(false);
                    playingTransformation = false;
                }
                getEnabledEntity().keyH.shooting = false;
                cs.updateTransformationFlash(getGarnet().getX(), getGarnet().getY());
                getOmnic().setLeftOrRight(getGarnet().isLeftOrRight());
                if (garnet.getSpeedY() < 0) {
                    getOmnic().setPosition(getGarnet().getX(), getGarnet().getY(), getGarnet().getDirection(), getGarnet().getSpeedX(), getGarnet().getSpeedY());
                } else {

                    getOmnic().setPosition(getGarnet().getX(), getGarnet().getY(), getGarnet().getDirection(), getGarnet().getSpeedX(), getOmnic().getSpeedY());
                }
            }
        }


    }

    public void draw(Graphics2D g2) {
        //draws each entity
        if (getFinn().isEnabled()) {
            getFinn().draw(g2);
        }
        if (getOmnic().isEnabled()) {
            getOmnic().draw(g2);
        }
        if (getGarnet().isEnabled()) {
            getGarnet().draw(g2);
        }
        if (cs.switching) {
            cs.drawFlash(g2);
        }


    }


}
