package engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

import java.sql.Time;
import java.util.Date;

import engine.utils.Maths;
import engine.utils.advertise.AdHandler;

public class Adverts {

    public static final int SHOW_AD_INTERVAL_SEC = 100;//40

    private AdHandler ads;

    private long lastShowTime = 0;

    public Adverts(){

    }

    public void showAd(){
        if (ads == null)
            return;
        long time = new Date().getTime();
        long after = SHOW_AD_INTERVAL_SEC * 1000 - (time - lastShowTime);
        if (after <= 0){
            lastShowTime = time;
            ads.loadAd();
            ads.showAd();
        }
    }

    public void setAdHandler(AdHandler ads){
        this.ads = ads;
    }
}
