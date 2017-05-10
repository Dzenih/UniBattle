package objects;

import android.graphics.Bitmap;

import static android.os.Build.ID;

/**
 * Created by frede on 10-May-17.
 */

public abstract class IngameObject {

    private int damage;
    private int maxHealth;
    private int healthValue;
    private Bitmap image;
    private String ID;
    private static int creationCount;

    public IngameObject(Bitmap image,int maxHealth, int damage)
    {
        ID = ""+creationCount++;
        this.maxHealth = maxHealth;
        healthValue = maxHealth;
        this.damage = damage;
        this.image = image;
    }

    public Bitmap getImage()
    {
        return image;
    }

    public int getDamage() {
        return damage;
    }
    public String getID() {
        return ID;
    }
    public int getHealthValue() {
        return healthValue;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void doDamage(int damage)
    {
        healthValue-=damage;
    }

}
