package com.specialforces;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.google.android.gms.ads.AdView;

public class AdsViewHandler extends Handler {
    private AdView adView;
    private boolean isShowing = false;

    public AdsViewHandler(AdView adView){
        this.adView = adView;
    }

    @Override
    public void handleMessage(Message msg) {
        switch(msg.what){
            case Advertises.SHOW_ADS:
                isShowing = true;
                adView.setVisibility(View.VISIBLE);
                break;
            case Advertises.HIDE_ADS:
                isShowing = false;
                adView.setVisibility(View.GONE);
                break;
        }
    }

    public boolean isShowing(){
        return isShowing;
    }
}
