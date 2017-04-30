package allen.brett.plana;

import android.graphics.Bitmap;

/**
 * Created by Brett on 4/30/2017 at 11:11 AM.
 */

public class ThumbnailRenderer {
    private Bitmap thumbnail;

    public void createThumbnail(Bitmap thumbnail){
        this.thumbnail = thumbnail;
    }

    public  Bitmap getThumbnail(){
        return thumbnail;
    }
}
