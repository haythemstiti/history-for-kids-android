package tn.esprit.mp.history4kids;

import tn.esprit.mp.history4kids.utils.H;
import tn.esprit.mp.history4kids.utils.OrthogonalTiledMapRendererWithSprites;
import tn.esprit.mp.history4kids.utils.Player;
import tn.esprit.mp.history4kids.utils.TwoButtonsJoypad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.Vector3;

public class Levels implements Screen, InputProcessor{

	private boolean climbing = false;
	private int target = 0;
	private int currentDoor = 1, level = 0;
	private boolean selectShown = false, topShown = false, downShown = false;
	private History4Kids game;
	private SpriteBatch batch;
	private int playerW = 70, playerH = 94;
	private int levelW = 1500, levelH = 1700;
	private OrthographicCamera camera;
	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;
	private float elapsedTime = 0;
	private int cameraW, cameraH;
	private MapLayer playerLayer, joypadLayer;
	public static int[][] levelsDoors = {
		{300, 130, 370, 270},
		{1210, 130, 1280, 270},
		{1180, 950, 1250, 1090},
		{250, 950, 320, 1090},
		{480, 1370, 550, 1510}
	};
	private Texture[] textures = new Texture[10];
	private Sprite[] sprites = new Sprite[10];
	private Sprite[] joypadSprites = new Sprite[2];
	private TextureAtlas[] atlases = new TextureAtlas[10];
	private Animation[] animations = new Animation[10];
	private Player player;
	private TwoButtonsJoypad joypad;
	
	public Levels(History4Kids game)
	{
		
		//TODO ADD RETURN BUTTON
		
		this.game = game;
		cameraH = (int) Math.round(H.cameraH*1.25);
		cameraW = (int) Math.round(H.cameraW*1.25);
		
		textures[0] = game.assets.get("data/controls/flatdark_left.png", Texture.class);
		textures[1] = game.assets.get("data/controls/flatdark_right.png", Texture.class);
		textures[2] = game.assets.get("data/controls/flatdark_up.png", Texture.class);
		textures[3] = game.assets.get("data/controls/flatdark_down.png", Texture.class);
		textures[4] = game.assets.get("data/controls/flatdark_select.png", Texture.class);
		textures[5] = game.assets.get("data/controls/flatDark32.png", Texture.class);
		
		sprites[0] = new Sprite(textures[2]);
		sprites[1] = new Sprite(textures[3]);
		sprites[2] = new Sprite(textures[4]);
		sprites[3] = new Sprite(textures[5]);
		
		joypadSprites[0] = new Sprite(textures[0]);
		joypadSprites[1] = new Sprite(textures[1]);
		
		atlases[0] = game.assets.get("data/spritesheets/waving_alien.atlas", TextureAtlas.class);
		atlases[1] = game.assets.get("data/spritesheets/walkleft_alien.atlas", TextureAtlas.class);
		atlases[2] = game.assets.get("data/spritesheets/walkright_alien.atlas", TextureAtlas.class);
		atlases[3] = game.assets.get("data/spritesheets/walktop_alien.atlas", TextureAtlas.class);
		atlases[4] = game.assets.get("data/spritesheets/walkbot_alien.atlas", TextureAtlas.class);
		
		animations[0] = new Animation(1/10f, atlases[0].getRegions());
		animations[1] = new Animation(1/20f, atlases[1].getRegions());
		animations[2] = new Animation(1/20f, atlases[2].getRegions());
		animations[3] = new Animation(6/20f, atlases[3].getRegions());
		animations[4] = new Animation(6/20f, atlases[4].getRegions());
	}
	private void drawPlayer()
	{
        TextureMapObject playerObject = new TextureMapObject(player.getAnimation().getKeyFrame(elapsedTime,true));
        playerObject.setX(player.getX());
        playerObject.setY(player.getY());
        playerW = playerObject.getTextureRegion().getRegionWidth();
        playerH = playerObject.getTextureRegion().getRegionHeight();
        playerLayer.getObjects().add(playerObject);
	}
	private void drawJoypad()
	{
        TextureMapObject joypadFirstButtonObject = new TextureMapObject(joypad.getFirstButtonSprite());
        joypadFirstButtonObject.setX(joypad.getFirstButtonSprite().getX());
        joypadFirstButtonObject.setY(joypad.getFirstButtonSprite().getY());
        joypadLayer.getObjects().add(joypadFirstButtonObject);
        joypadLayer = tiledMap.getLayers().get("controls");
        TextureMapObject joypadSecondButtonObject = new TextureMapObject(joypad.getSecondButtonSprite());
        joypadSecondButtonObject.setX(joypad.getSecondButtonSprite().getX());
        joypadSecondButtonObject.setY(joypad.getSecondButtonSprite().getY());
        joypadLayer.getObjects().add(joypadSecondButtonObject);
	}
	public void refreshPlayer()
	{
		TextureMapObject playerObject = (TextureMapObject) playerLayer.getObjects().get(0);
		playerObject.setX(player.getX());
        playerObject.setY(player.getY());
        playerW = playerObject.getTextureRegion().getRegionWidth();
        playerH = playerObject.getTextureRegion().getRegionHeight();
        playerObject.setTextureRegion(player.getAnimation().getKeyFrame(elapsedTime,true));
	}
	public void refreshJoypad()
	{
		TextureMapObject joypadFirstButtonObject = (TextureMapObject) joypadLayer.getObjects().get(0);
		joypadFirstButtonObject.setX(joypad.getFirstButtonSprite().getX());
        joypadFirstButtonObject.setY(joypad.getFirstButtonSprite().getY());
        TextureMapObject joypadSecondButtonObject = (TextureMapObject) joypadLayer.getObjects().get(1);
        joypadSecondButtonObject.setX(joypad.getSecondButtonSprite().getX());
        joypadSecondButtonObject.setY(joypad.getSecondButtonSprite().getY());
	}
	public void displaySelectButton(int setlevel)
	{
		selectShown = true;
		currentDoor = setlevel;
		sprites[2].setScale(2f);
		sprites[2].setX(camera.position.x+(cameraW/2)-(sprites[2].getWidth()*2));
        sprites[2].setY(camera.position.y-(cameraH/2)+(sprites[2].getHeight()/2));
        TextureMapObject selectButton = new TextureMapObject(sprites[2]);
        selectButton.setX(sprites[2].getX());
        selectButton.setY(sprites[2].getY());
        joypadLayer.getObjects().add(selectButton);
	}
	public void displayTopButton()
	{
		topShown = true;
		sprites[0].setScale(2f);
		sprites[0].setX(camera.position.x+(cameraW/2)-(sprites[0].getWidth())-10);
        sprites[0].setY(camera.position.y-(cameraH/2)+5);
        TextureMapObject selectButton = new TextureMapObject(sprites[0]);
        selectButton.setX(sprites[0].getX());
        selectButton.setY(sprites[0].getY());
        joypadLayer.getObjects().add(selectButton);
	}
	public void displayDownButton()
	{
		downShown = true;
		sprites[1].setScale(2f);
		sprites[1].setX(camera.position.x+(cameraW/2)-(sprites[1].getWidth())-10);
        sprites[1].setY(camera.position.y-(cameraH/2)+5);
        TextureMapObject selectButton = new TextureMapObject(sprites[1]);
        selectButton.setX(sprites[1].getX());
        selectButton.setY(sprites[1].getY());
        joypadLayer.getObjects().add(selectButton);
	}
	public void removeThirdButton()
	{
		try
		{
			joypadLayer.getObjects().remove(2);
			currentDoor = 1;
			selectShown = false;
			topShown = false;
			downShown = false;
		}
		catch (Exception e) {}
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(208,244,247,1);
	    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    if (player.getY()<900) level = 0;
	    else if (player.getY()<1300) level = 1;
	    else level = 2;
		player.setAnimation(animations[0]);
		float speed = player.getSpeed();
		if (player.movingDown)
		{
			player.setAnimation(animations[3]);
			if (player.getY() > target) player.setY(player.getY()-speed);
			else 
				{
					player.movingDown = false;
					climbing = false;
					target = 0;
				}
			if (camera.position.y-(cameraH/2) > 0 && camera.position.y-speed <= player.getY()+playerH && camera.position.y+speed >= player.getY()+playerH) 
				{
					camera.translate(0,-70);
					joypad.setY(joypad.getY()-70);
					camera.update();
					refreshJoypad();
				}
		}
		if (player.movingUp)
		{
			player.setAnimation(animations[3]);
			if (player.getY() < target) player.setY(player.getY()+speed);
			else 
				{
					player.movingUp = false;
					climbing = false;
					target = 0;
				}
			if (camera.position.y+(cameraH/2) < levelH && camera.position.y-speed <= player.getY() && camera.position.y+speed >= player.getY()) 
				{
					camera.translate(0,70);
					joypad.setY(joypad.getY()+70);
					camera.update();
					refreshJoypad();
				}
		}
		if (player.movingLeft)
		{
			player.setAnimation(animations[1]);
			if (player.getX() > speed) player.setX(player.getX()-speed);
			if (camera.position.x-(cameraW/2) > 0 && camera.position.x == player.getX()+playerW) 
				{
					camera.translate(-70,0);
					joypad.setX(joypad.getX()-70);
					camera.update();
					refreshJoypad();
				}
		}
		if (player.movingRight)
		{
			player.setAnimation(animations[2]);
			if (player.getX()+playerW < levelW)
				{	
					if (level == 2)
					{
						if (player.getX()+playerW < 640)
						{
							player.setX(player.getX()+speed);
						}
					}
					else
					{
						player.setX(player.getX()+speed);
					}
				}
			if (camera.position.x+(cameraW/2) < levelW && camera.position.x == player.getX()-playerW) 
				{
					camera.translate(70,0);
					joypad.setX(joypad.getX()+70);
					camera.update();
					refreshJoypad();
				}
		}
		removeThirdButton();
		switch (level)
		{
			case 0:
				if (player.getX()+(playerW/2)>=levelsDoors[0][0] && player.getX()+(playerW/2)<=levelsDoors[0][2])
				{
					displaySelectButton(0);
				}
				if (player.getX()+(playerW/2)>=levelsDoors[1][0] && player.getX()+(playerW/2)<=levelsDoors[1][2])
				{
					if (H.currentLevel>=2) displaySelectButton(1);
				}
				if (player.getX()+(playerW)>=levelW)
				{
					if (!climbing) displayTopButton();
				}
				break;
			case 1:
				if (player.getX()+(playerW/2)>=levelsDoors[2][0] && player.getX()+(playerW/2)<=levelsDoors[2][2])
				{
					if (H.currentLevel>=3) displaySelectButton(2);
				}
				if (player.getX()+(playerW/2)>=levelsDoors[3][0] && player.getX()+(playerW/2)<=levelsDoors[3][2])
				{
					if (H.currentLevel>=4) displaySelectButton(3);
				}
				if (player.getX()+(playerW/2)>=levelW-70)
				{
					if (!climbing) displayDownButton();
				}
				if (player.getX()+(playerW/2)<=70)
				{
					if (!climbing) displayTopButton();
				}
				break;
			case 2:
				if (player.getX()+(playerW/2)>=levelsDoors[4][0] && player.getX()+(playerW/2)<=levelsDoors[4][2])
				{
					if (H.currentLevel>=5) displaySelectButton(2);
				}
				if (player.getX()+(playerW/2)<=70)
				{
					if (!climbing) displayDownButton();
				}
				break;
		}
		elapsedTime += Gdx.graphics.getDeltaTime();
        
		 refreshPlayer();
		 batch.setProjectionMatrix(camera.combined);
		camera.update();
       
        
        tiledMapRenderer.setView(camera);
        //refreshPlayer();
        tiledMapRenderer.render();
        
        batch.begin();
        sprites[3].setX(camera.position.x+5-(cameraW/2));
        sprites[3].setY(camera.position.y-5+(cameraH/2)-sprites[3].getWidth());
        sprites[3].draw(batch);
        batch.end();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		
		player = new Player(40,140,animations[0]);
		player.setSpeed(2f);
		joypad = new TwoButtonsJoypad(0,5,joypadSprites);
		joypad.scaleSprites(2f);
		joypad.setMargin(10);
		batch = new SpriteBatch();
        camera = new OrthographicCamera(cameraW,cameraH);
        camera.position.x = cameraW/2;
		camera.position.y = cameraH/2;
        camera.update();
        switch (H.currentLevel)
        {
        	case 1:
        		tiledMap = game.assets.get("data/levels_media/levels-1.tmx", TiledMap.class);
        		break;
        	case 2:
        		tiledMap = game.assets.get("data/levels_media/levels-2.tmx", TiledMap.class);
        		break;
        	case 3:
        		tiledMap = game.assets.get("data/levels_media/levels-3.tmx", TiledMap.class);
        		break;
        	case 4:
        		tiledMap = game.assets.get("data/levels_media/levels-4.tmx", TiledMap.class);
        		break;
        	default:
        		tiledMap = game.assets.get("data/levels_media/levels-5.tmx", TiledMap.class);
        		break;
        }
        playerLayer = tiledMap.getLayers().get("player");
		joypadLayer = tiledMap.getLayers().get("controls");
        tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap);
        Gdx.input.setInputProcessor(this);
        drawPlayer();
        drawJoypad();
        
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
		for (TextureAtlas ta : atlases)
		{
			ta.dispose();
		}
		for (Texture t : textures)
		{
			t.dispose();
		}
	}
	
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}
	@Override
	public boolean keyUp(int keycode) {
		return false;
	}
	@Override
	public boolean keyTyped(char character) {
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (climbing) return true;
		Vector3 v = new Vector3(screenX,screenY,0);
		camera.unproject(v);
		int i = 0;
		if (sprites[3].getBoundingRectangle().contains(v.x,v.y))
			game.setScreen(new MainMenu(game));
		if (topShown)
		{
			if (sprites[0].getBoundingRectangle().contains(v.x, v.y))
			{
				player.movingUp = true;
				climbing = true;
				switch (level)
				{
					case 0:
						target = 960;
						break;
					case 1:
						target = 1380;
						break;
				}
			}
		}
		if (downShown)
		{
			if (sprites[1].getBoundingRectangle().contains(v.x, v.y))
			{
				player.movingDown = true;
				climbing = true;
				switch (level)
				{
					case 1:
						target = 140;
						break;
					case 2:
						target = 960;
						break;
				}
			}
		}
		if (selectShown)
		{
			if (sprites[2].getBoundingRectangle().contains(v.x, v.y))
			{
				switch (currentDoor)
				{
					case 0: 
						game.setScreen(new levelGreek(game));
						break;
					case 1: 
						game.setScreen(new LevelRoman(game));
						break;
					case 2: 
						//game.setScreen(level_egyptian);
						break;
					case 3: 
						//game.setScreen(level_dark_ages);
						break;
					case 4: 
						//game.setScreen(level_final);
						break;
				}
				return true;
			}
		}
		for (Sprite s : joypad.getSprites())
		{
			if (s.getBoundingRectangle().contains(v.x, v.y))
			{
				switch (i)
				{
					case 0: 
						player.movingLeft = true;
						break;
					case 1:
						player.movingRight = true;
						break;
				}
			}
			i++;
		}
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		player.movingLeft = false;
		player.movingRight = false;
		return true;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
