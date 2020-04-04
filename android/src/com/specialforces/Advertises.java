package com.specialforces;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import engine.utils.advertise.AdHandler;

public class Advertises implements AdHandler {

    static final String TAG = "AndroidLauncher";
    static final int SHOW_ADS = 1;
    static final int HIDE_ADS = 0;

    private AndroidApplication context;
    //private AdView adView;
    private InterstitialAd interstitial;
    private AdRequest.Builder builder;

    private AdsViewHandler adsViewHandler;

    Advertises(AndroidApplication context){
        this.context = context;
        //interstitial
        interstitial = new InterstitialAd(context);
        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }
        });
        interstitial.setAdUnitId("ca-app-pub-7939490013905253/1338050925");
        builder = new AdRequest.Builder();
        builder.addTestDevice("A23D9A26D310874B92359815BAA41B29");
        interstitial.loadAd(builder.build());

        //banner
        /*adView = new AdView(context);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                int visibility = adView.getVisibility();
                adView.setVisibility(AdView.GONE);
                adView.setVisibility(visibility);
                Log.i(TAG, "(Ad) Banner Loaded...");
            }
        });
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-7939490013905253/2021525321");
        adView.loadAd(builder.build());

        adsViewHandler = new AdsViewHandler(adView);
        showBanner(false);*/
    }

    private void showInterstitial() {
        if(Looper.myLooper() != Looper.getMainLooper()) {
            context.runOnUiThread(new Runnable() {
                @Override public void run() {
                    doShowInterstitial();
                }
            });
        } else {
            doShowInterstitial();
        }
    }
    private void doShowInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
            Log.d(TAG, "(Ads) Interstitial is showing");
        } else {
            Log.d(TAG, "(Ads) Interstitial ad is not loaded yet");
        }
    }

    private void loadInterstitial() {
        if(Looper.myLooper() != Looper.getMainLooper()) {
            context.runOnUiThread(new Runnable() {
                @Override public void run() {
                    interstitial.loadAd(builder.build());
                }
            });
        } else {
            interstitial.loadAd(builder.build());
        }
    }

    @Override
    public void showAd() {
        showInterstitial();
    }

    @Override
    public void loadAd() {
        loadInterstitial();
    }

    @Override
    public void showBanner(boolean show) {
        adsViewHandler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
    }

    public InterstitialAd getInterstitialAd(){
        return interstitial;
    }

    public AdRequest.Builder getAdRequestBuilder(){
        return builder;
    }

    /*public AdView getAdView(){
        return adView;
    }*/

}
