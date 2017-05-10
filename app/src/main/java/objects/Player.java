package objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.dzenitahasic.unibattle.R;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/**
 * Created by dzenitahasic on 03/05/2017.
 */

public class Player extends IngameObject{

    private String nickname;

    public Player(Bitmap image, int maxHealth, int damage) {

        super(image,maxHealth, damage);

    }

}
