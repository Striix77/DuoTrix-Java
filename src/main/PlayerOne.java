package main;

import entity.*;
import projectiles.ProjectileController;

public class PlayerOne extends Player {
    //class for player one
    public PlayerOne(GamePanel gp, KeyHandler keyHandler, CharacterSwitcher characterSwitcher, ProjectileController pc, SoundPlayer sp) {
        setFinn(new Finn(gp, keyHandler, sp));
        setOmnic(new Omnic(gp, keyHandler, pc, sp));
        setGarnet(new Garnet(gp, keyHandler, pc, sp));
        setUtilities(characterSwitcher, sp);
        setHealth(getMaxHealth());
    }


}
