package main;

import entity.Finn;
import entity.Garnet;
import entity.Omnic;
import projectiles.ProjectileController;

public class PlayerTwo extends Player {
    //class for player two
    public PlayerTwo(GamePanel gp, KeyHandler keyHandler, CharacterSwitcher characterSwitcher, ProjectileController pc, SoundPlayer sp) {
        setFinn(new Finn(gp, keyHandler, sp));
        setOmnic(new Omnic(gp, keyHandler, pc, sp));
        setGarnet(new Garnet(gp, keyHandler, pc, sp));
        setUtilities(characterSwitcher, sp);
        setHealth(getMaxHealth());
    }


}
