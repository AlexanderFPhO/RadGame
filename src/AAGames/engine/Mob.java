package src.AAGames.engine;
//TODO: This class should probably extend entity and not be an attribute of Entity, but I'm running out of time to get stuff done
public class Mob {

    public float[] position;
    public float[] rotation;
    public float[] velocity;
    public float attackDmg;
    public float health;

    public float dmgTimer;

    public Mob(float[] position, float[] rotation, float[] velocity, float attackDmg, float health) {
        this.position = position;
        this.rotation = rotation;
        this.velocity = velocity;
        this.attackDmg = attackDmg;
        this.health = health;
    }

    public void damageMob(float dmg) {
        health -= dmg; dmgTimer=0;
    }

    public boolean isAlive (float dmg) {
        return health >= 0;
    }

    public void update(long diffTimeMillis) {
        dmgTimer += diffTimeMillis;
        if (dmgTimer > 2500 && health < 20f) {health += .5f; dmgTimer = 0;}

    }

}
