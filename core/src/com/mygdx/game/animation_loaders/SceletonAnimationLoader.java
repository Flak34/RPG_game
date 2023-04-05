package com.mygdx.game.animation_loaders;

import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.HashMap;

public class SceletonAnimationLoader extends AbstractAnimationLoader{

    protected static HashMap<String, Animation> walkAnimations;
    protected static HashMap<String, Animation> attackAnimations;

    @Override
    public HashMap<String, Animation> getWalkAnimations() {
        if(walkAnimations == null) {
            walkAnimations = new HashMap<>();
            loadWalkAnimations();
        }
        return walkAnimations;
    }

    @Override
    public HashMap<String, Animation> getAttackAnimations() {
        if(attackAnimations == null) {
            attackAnimations = new HashMap<>();
            loadAttackAnimations();
        }
        return attackAnimations;
    }
    @Override
    protected void loadWalkAnimations() {
        walkAnimations.put("walking_north",
                loadOneDirection("assets/sceleton_walking/walking_n", 8, 0.08f, true));
        walkAnimations.put("walking_north_west",
                loadOneDirection("assets/sceleton_walking/walking_nw", 8, 0.08f, true));
        walkAnimations.put("walking_north_east",
                loadOneDirection("assets/sceleton_walking/walking_ne", 8, 0.08f, true));
        walkAnimations.put("walking_east",
                loadOneDirection("assets/sceleton_walking/walking_e", 8, 0.08f, true));
        walkAnimations.put("walking_south",
                loadOneDirection("assets/sceleton_walking/walking_s", 8, 0.08f, true));
        walkAnimations.put("walking_south_west",
                loadOneDirection("assets/sceleton_walking/walking_sw", 8, 0.08f, true));
        walkAnimations.put("walking_south_east",
                loadOneDirection("assets/sceleton_walking/walking_se", 8, 0.08f, true));
        walkAnimations.put("walking_west",
                loadOneDirection("assets/sceleton_walking/walking_w", 8, 0.08f, true));
    }

    @Override
    protected void loadAttackAnimations() {
        attackAnimations.put("attack_north",
                loadOneDirection("assets/sceleton_attacking/attack_n", 10, 0.05f, false));
        attackAnimations.put("attack_north_west",
                loadOneDirection("assets/sceleton_attacking/attack_nw", 10, 0.05f, false));
        attackAnimations.put("attack_north_east",
                loadOneDirection("assets/sceleton_attacking/attack_ne", 10, 0.05f, false));
        attackAnimations.put("attack_east",
                loadOneDirection("assets/sceleton_attacking/attack_e", 10, 0.05f, false));
        attackAnimations.put("attack_south",
                loadOneDirection("assets/sceleton_attacking/attack_s", 10, 0.05f, false));
        attackAnimations.put("attack_south_west",
                loadOneDirection("assets/sceleton_attacking/attack_sw", 10, 0.05f, false));
        attackAnimations.put("attack_south_east",
                loadOneDirection("assets/sceleton_attacking/attack_se", 10, 0.05f, false));
        attackAnimations.put("attack_west",
                loadOneDirection("assets/sceleton_attacking/attack_w", 10, 0.05f, false));
    }
}

