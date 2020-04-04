package com.specialforces;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import engine.SpecialForces;

public class AndroidLauncher extends AndroidApplication {

	private Advertises advertises;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		RelativeLayout layout = new RelativeLayout(this);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.numSamples = SpecialForces.SAMPLES;
		config.useImmersiveMode = true;
		View gameView = initializeForView(SpecialForces.getInstance(), config);
		layout.addView(gameView);


		advertises = new Advertises(this);


		/*RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
		);
		adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		layout.addView(advertises.getAdView(), adParams);*/
		setContentView(layout);

		SpecialForces.getInstance().setAdHandler(advertises);
	}


}
