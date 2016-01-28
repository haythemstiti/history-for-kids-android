package tn.esprit.mp.history4kids;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class History4Kids extends Game  {
	
	public AssetManager assets;

	@Override
	public void create() {
	
		Texture.setEnforcePotImages(false);
		setScreen(new LoadingScreen(this));
		
	}

}
