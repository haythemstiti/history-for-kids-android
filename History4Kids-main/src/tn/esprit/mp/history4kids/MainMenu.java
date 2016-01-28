package tn.esprit.mp.history4kids;

import tn.esprit.mp.history4kids.utils.H;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class MainMenu implements Screen, GestureListener{
	
	private boolean [] navButtonIsShown = {false, false}; 
	private Levels levels;
	private History4Kids game;
	private SpriteBatch batch;
	private int currentScreen = 0;
	private Camera camera;
	private int cameraW = H.cameraW, cameraH = H.cameraH;
	private float elapsedTime = 0;
	private Texture[] textures = new Texture[20];
	private Sprite[] sprites = new Sprite[10];
	private TextureAtlas[] atlases = new TextureAtlas[5];
	private Animation[] animations = new Animation[5];
	private Animation animation;
	
	public MainMenu (History4Kids game)
	{
		//TODO FINISH CREDITS & ABOUT
		
		this.levels = new Levels(game);
		this.game = game;
		textures[0] = game.assets.get("data/backgrounds/main_menu.png", Texture.class);
		textures[1] = game.assets.get("data/buttons/btn001.png",Texture.class);
		textures[2] = game.assets.get("data/buttons/btn002.png",Texture.class);
		textures[3] = game.assets.get("data/buttons/btn003.png",Texture.class);
		textures[4] = game.assets.get("data/backgrounds/main_menu_blurred.png",Texture.class);
		textures[5] = game.assets.get("data/dialogs/help1.png",Texture.class);
		textures[6] = game.assets.get("data/dialogs/about.png",Texture.class);
		textures[7] = game.assets.get("data/buttons/btn004.png",Texture.class);
		textures[8] = game.assets.get("data/dialogs/help2.png",Texture.class);
		textures[9] = game.assets.get("data/dialogs/help3.png",Texture.class);
		textures[10] = game.assets.get("data/buttons/btn005.png",Texture.class);
		textures[11] = game.assets.get("data/buttons/btn006.png",Texture.class);
		textures[12] = game.assets.get("data/buttons/btn007.png",Texture.class);
		
		sprites[0] = new Sprite(textures[0]);
		sprites[1] = new Sprite(textures[1]);
		sprites[2] = new Sprite(textures[2]);
		sprites[3] = new Sprite(textures[3]);
		sprites[4] = new Sprite(textures[7]);
		sprites[5] = new Sprite(textures[10]);
		sprites[6] = new Sprite(textures[11]);
		sprites[7] = new Sprite(textures[12]);
				
		atlases[0] = game.assets.get("data/spritesheets/waving_alien.atlas",TextureAtlas.class);
		
		animations[0] = new Animation(1/10f, atlases[0].getRegions());
		
		animation = animations[0];
		
	}
	private void showHelp1()
	{
		batch.draw(textures[5], 170, 30);
	    sprites[4].draw(batch);
	    sprites[5].draw(batch);
	    navButtonIsShown[0] = true;
	}
	private void showHelp2()
	{
		batch.draw(textures[8], 170, 30);
	    sprites[4].draw(batch);
	    sprites[5].draw(batch);
	    sprites[6].draw(batch);
	    navButtonIsShown[0] = true;
	    navButtonIsShown[1] = true;
	}
	private void showHelp3()
	{
		batch.draw(textures[9], 170, 30);
	    sprites[4].draw(batch);
	    sprites[6].draw(batch);
	    navButtonIsShown[1] = true;
	}
	private void showAbout()
	{
		batch.draw(textures[6], 170, 30);
	    sprites[4].draw(batch);
	}
	private void showCredits()
	{
		batch.draw(textures[6], 170, 30);
	    sprites[4].draw(batch);
	}
	@Override
	public void render(float delta) {
		
		navButtonIsShown[0] = false;
	    navButtonIsShown[1] = false;
		Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    camera.position.x = cameraW/2;
		camera.position.y = cameraH/2;
		camera.update();
	    batch.setProjectionMatrix(camera.combined);
	    batch.begin();
	    elapsedTime += Gdx.graphics.getDeltaTime();
	    switch (currentScreen)
	    {
	    	case 0:
	    		batch.draw(textures[0], 0, 0);
	    		sprites[1].draw(batch);
	    	    sprites[2].draw(batch);
	    	    sprites[3].draw(batch);
	    	    sprites[7].draw(batch);
	    		break;
	    	default:
	    		batch.draw(textures[4], 0, 0);
	    		break;
	    }
		batch.draw(animation.getKeyFrame(elapsedTime,true),280,10);
		
		if (currentScreen != 0)
		{
			switch (currentScreen) {
			case 1:
				showAbout();
				break;
			case 2:
				showHelp1();
				break;
			case 3:
				showHelp2();
				break;
			case 4:
				showHelp3();
				break;
			case 5:
				showCredits();
				break;
			default:
				currentScreen = 0;
				break;
			}
		}
		
		batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		
		batch = new SpriteBatch();
		camera = new OrthographicCamera(cameraW, cameraH);
		camera.position.x = cameraW/2;
		camera.position.y = cameraH/2;
		sprites[1].setPosition(420, 285);
		sprites[2].setPosition(420, 235);
		sprites[3].setPosition(420, 185);
		sprites[4].setPosition(420, 275);
		sprites[5].setPosition(506, (cameraH/2)-(38/2));
		sprites[6].setPosition(98, (cameraH/2)-(38/2));
		sprites[7].setPosition(561, 10);
		camera.update();
		Gdx.input.setInputProcessor(new GestureDetector(this));
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
		batch.dispose();
		for (Texture t : textures)
		{
			t.dispose();
		}
		for (TextureAtlas a : atlases)
		{
			a.dispose();
		}
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		Vector3 v = new Vector3(x,y,0);
		camera.unproject(v);
		x = Math.round(v.x);
		y = Math.round(v.y);
		if (currentScreen != 0)
		{
			if (sprites[4].getBoundingRectangle().contains(x, y))
			{
				currentScreen = 0;
				return true;
			}
			if (sprites[5].getBoundingRectangle().contains(x, y) && navButtonIsShown[0])
			{
				currentScreen ++;
				return true;
			}
			if (sprites[6].getBoundingRectangle().contains(x, y) && navButtonIsShown[1])
			{
				currentScreen --;
				return true;
			}
		}
		else 
		{
			if (sprites[1].getBoundingRectangle().contains(x, y))
			{
				game.setScreen(levels);
				return true;
			}
			if (sprites[2].getBoundingRectangle().contains(x, y))
			{
				currentScreen = 2;
				return true;
			}
			if (sprites[3].getBoundingRectangle().contains(x, y))
			{
				currentScreen = 1;
				return true;
			}
			if (sprites[7].getBoundingRectangle().contains(x, y))
			{
				currentScreen = 5;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		return false;
	}

}
