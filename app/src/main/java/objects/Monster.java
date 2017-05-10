package objects;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

import static android.os.Build.ID;

/**
 * Created by dzenitahasic on 03/05/2017.
 */

public class Monster extends IngameObject{

    private String name;
    private LatLng position;

    public Monster(Bitmap image, int maxHealth, int damage, String name, LatLng position) {

        super(image,maxHealth, damage);
        this.position = position;
        this.name = name;
    }

    public LatLng getPosition()
    {
        return position;
    }

    public String getName() {
        return name;
    }


}
