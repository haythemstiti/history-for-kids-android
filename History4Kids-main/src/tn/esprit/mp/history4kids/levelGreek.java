package tn.esprit.mp.history4kids;

import tn.esprit.mp.history4kids.utils.H;
import tn.esprit.mp.history4kids.utils.OrthogonalTiledMapRendererWithSprites;
import tn.esprit.mp.history4kids.utils.Player;
import tn.esprit.mp.history4kids.utils.Quiz;
import tn.esprit.mp.history4kids.utils.TwoButtonsJoypad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.Vector3;

public class levelGreek implements Screen, InputProcessor {
	
	private History4Kids game;
	private SpriteBatch batch;
	private Quiz quizHolder;
	private BitmapFont font, whiteFont;
	private boolean quizTime = false;
	private String text = "";
	private boolean selectShown = false, showLesson1 = false, showLesson2 = false, showLesson3 = false, showLesson4 = false;
	private int playerW = 35, /*playerH = 47,*/ currentCharacter = 1, currentQuiz = 0;
	/*private int levelW = 2500, levelH = 400;*/
	private int[] charactersX = {260,1020,1920,2300};
	private Texture transparent;
	private OrthographicCamera camera;
	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;
	private float elapsedTime = 0, lessonTime = 0;
	private int cameraW, cameraH;
	private MapLayer playerLayer, joypadLayer, merchantLayer, politicianLayer, officerLayer, marineLayer;
	private Texture[] textures = new Texture[10];
	private Sprite[] sprites = new Sprite[10];
	private Sprite[] joypadSprites = new Sprite[2];
	private TextureAtlas[] atlases = new TextureAtlas[10];
	private Animation[] animations = new Animation[10];
	private Sound[] sounds = new Sound[10];
	private Music[] traderSounds = new Music[10], politicianSounds = new Music[10], officerSounds = new Music[10], marineSounds = new Music[10];
	private int currentTraderSound = 0, currentPoliticianSound = 0, currentOfficerSound = 0, currentMarineSound = 0;
	private Player player;
	private TwoButtonsJoypad joypad;
	
	public levelGreek(History4Kids game)
	{
		
		//TODO ADD SKIP
		//TODO ADD RETURN BUTTON
		//TODO ADD INSTRUCTIONS
		
		this.game = game;
		batch = new SpriteBatch();
		cameraH = H.cameraH;
		cameraW = H.cameraW;
		
		transparent = game.assets.get("data/levels_media/transparent.png", Texture.class);
		
		textures[0] = game.assets.get("data/controls/flatdark_left_half.png", Texture.class);
		textures[1] = game.assets.get("data/controls/flatdark_right_half.png", Texture.class);
		textures[2] = game.assets.get("data/controls/flatdark_select.png", Texture.class);
		textures[3] = game.assets.get("data/dialogs/standard.png", Texture.class);
		textures[4] = game.assets.get("data/dialogs/standard_answer.png",Texture.class);
		textures[5] = game.assets.get("data/controls/flatDark15.png",Texture.class);
		textures[6] = game.assets.get("data/controls/flatDark32.png",Texture.class);
		
		joypadSprites[0] = new Sprite(textures[0]);
		joypadSprites[1] = new Sprite(textures[1]);
		
		sprites[0] = new Sprite(textures[2]);
		sprites[1] = new Sprite(textures[3]);
		sprites[3] = new Sprite(textures[5]);
		sprites[2] = new Sprite(textures[6]);
		
		atlases[0] = game.assets.get("data/spritesheets/waving_alien_half.atlas", TextureAtlas.class);
		atlases[1] = game.assets.get("data/spritesheets/walkleft_alien_half.atlas", TextureAtlas.class);
		atlases[2] = game.assets.get("data/spritesheets/walkright_alien_half.atlas", TextureAtlas.class);
		atlases[3] = game.assets.get("data/spritesheets/greek_trader_talking.atlas", TextureAtlas.class);
		atlases[4] = game.assets.get("data/spritesheets/greek_politician_talking.atlas", TextureAtlas.class);
		atlases[5] = game.assets.get("data/spritesheets/greek_officer_talking.atlas", TextureAtlas.class);
		atlases[6] = game.assets.get("data/spritesheets/greek_marine_talking.atlas", TextureAtlas.class);
		
		animations[0] = new Animation(1/10f, atlases[0].getRegions());
		animations[1] = new Animation(1/20f, atlases[1].getRegions());
		animations[2] = new Animation(1/20f, atlases[2].getRegions());
		animations[3] = new Animation(1/6f, atlases[3].getRegions());
		animations[4] = new Animation(1/6f, atlases[4].getRegions());
		animations[5] = new Animation(1/6f, atlases[5].getRegions());
		animations[6] = new Animation(1/6f, atlases[6].getRegions());
		
		traderSounds[0] = game.assets.get("data/audio/greek_trader1.mp3", Music.class);
		traderSounds[1] = game.assets.get("data/audio/greek_trader2.mp3", Music.class);
		traderSounds[2] = game.assets.get("data/audio/greek_trader3.mp3", Music.class);
		
		politicianSounds[0] = game.assets.get("data/audio/greek_politician1.mp3", Music.class);
		politicianSounds[1] = game.assets.get("data/audio/greek_politician2.mp3", Music.class);
		politicianSounds[2] = game.assets.get("data/audio/greek_politician3.mp3", Music.class);
		
		officerSounds[0] = game.assets.get("data/audio/greek_officer1.mp3", Music.class);
		officerSounds[1] = game.assets.get("data/audio/greek_officer2.mp3", Music.class);
		officerSounds[2] = game.assets.get("data/audio/greek_officer3.mp3", Music.class);
		
		marineSounds[0] = game.assets.get("data/audio/greek_marine1.mp3", Music.class);
		marineSounds[1] = game.assets.get("data/audio/greek_marine2.mp3", Music.class);
		marineSounds[2] = game.assets.get("data/audio/greek_marine3.mp3", Music.class);
	
		sounds[0] = game.assets.get("data/audio/correct_answer.wav", Sound.class);
		sounds[1] = game.assets.get("data/audio/correct_answer.wav", Sound.class);
	}
	public void displaySelectButton(int character)
	{
		selectShown = true;
		currentCharacter = character;
		sprites[0].setScale(2f);
		sprites[0].setX(camera.position.x+(cameraW/2)-(sprites[0].getWidth()+10));
        sprites[0].setY(5);
        TextureMapObject selectButton = new TextureMapObject(sprites[0]);
        selectButton.setX(sprites[0].getX());
        selectButton.setY(sprites[0].getY());
        joypadLayer.getObjects().add(selectButton);
	}
	public void removeThirdButton()
	{
		try
		{
			joypadLayer.getObjects().remove(2);
			currentCharacter = 1;
			selectShown = false;
		}
		catch (Exception e) {}
	}
	private void drawPlayer()
	{
        TextureMapObject playerObject = new TextureMapObject(player.getAnimation().getKeyFrame(elapsedTime,true));
        playerObject.setX(player.getX());
        playerObject.setY(player.getY());
        playerW = playerObject.getTextureRegion().getRegionWidth();
        //playerH = playerObject.getTextureRegion().getRegionHeight();
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
        //playerH = playerObject.getTextureRegion().getRegionHeight();
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
	public void hideProgress()
	{
		merchantLayer.setVisible(false);
		politicianLayer.setVisible(false);
		officerLayer.setVisible(false);
		marineLayer.setVisible(false);
	}
	public void showProgress()
	{
		if (H.currentLevel != 1) return;
		switch (H.progressLevel)
		{
			case 1:
				merchantLayer.setVisible(true);
				break;
			case 2:
				politicianLayer.setVisible(true);
				break;
			case 3:
				officerLayer.setVisible(true);
				break;
			case 4:
				marineLayer.setVisible(true);
				break;
		}
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(208,244,247,1);
	    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    player.setAnimation(animations[0]);
		float speed = player.getSpeed();
		hideProgress();
		if (!showLesson1 && !showLesson2 && !showLesson3 && !showLesson4)
		{
			lessonTime = 0;
			showProgress();
			joypadLayer.setVisible(true);
			if (player.movingLeft)
			{
				player.setAnimation(animations[1]);
				if (player.getX() > speed) player.setX(player.getX()-speed);
				if (camera.position.x-(cameraW/2) > 0 && camera.position.x-(camera.position.x/4) <= player.getX()&& camera.position.x >= player.getX()) 
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
				if (player.getX()+playerW < 2360) 
					{
						if (!((H.currentLevel == 1 && H.progressLevel == 1 && player.getX()+playerW>charactersX[0]) || 
								(H.currentLevel == 1 && H.progressLevel == 2 && player.getX()+playerW>charactersX[1]) ||
								(H.currentLevel == 1 && H.progressLevel == 3 && player.getX()+playerW>charactersX[2]) ||
								(H.currentLevel == 1 && H.progressLevel == 4 && player.getX()+playerW>charactersX[3])))
						player.setX(player.getX()+speed);
					}
				if (camera.position.x+(cameraW/2) < 2450 && camera.position.x <= player.getX() && camera.position.x+(camera.position.x/4) >= player.getX()) 
					{
						camera.translate(70,0);
						joypad.setX(joypad.getX()+70);
						camera.update();
						refreshJoypad();
					}
			}
			removeThirdButton();
			if (player.getX()+(playerW)>=charactersX[0] && player.getX()+(playerW)<=charactersX[0]+50)
			{
				displaySelectButton(1);
			}
			if (player.getX()+(playerW)>=charactersX[1] && player.getX()+(playerW)<=charactersX[1]+50)
			{
				displaySelectButton(2);
			}
			if (player.getX()+(playerW)>=charactersX[2] && player.getX()+(playerW)<=charactersX[2]+50)
			{
				displaySelectButton(3);
			}
			if (player.getX()+(playerW)>=charactersX[3] && player.getX()+(playerW)<=charactersX[3]+50)
			{
				displaySelectButton(4);
			}
		}
		else
		{
			removeThirdButton();
			joypadLayer.setVisible(false);
		}
		elapsedTime += Gdx.graphics.getDeltaTime();
        camera.update();
        tiledMapRenderer.setView(camera);
        refreshPlayer();
        tiledMapRenderer.render();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        if (showLesson1)
		{
        	sprites[1].setX(camera.position.x-(cameraW/2)+40);
        	lessonTime += Gdx.graphics.getDeltaTime();
			showLesson1();
		}
		else if (showLesson2)
		{
			sprites[1].setX(camera.position.x-(cameraW/2)+40);
			lessonTime += Gdx.graphics.getDeltaTime();
			showLesson2();
		}
		else if (showLesson3)
		{
			sprites[1].setX(camera.position.x-(cameraW/2)+40);
			lessonTime += Gdx.graphics.getDeltaTime();
			showLesson3();
		}
		else if (showLesson4)
		{
			sprites[1].setX(camera.position.x-(cameraW/2)+40);
			lessonTime += Gdx.graphics.getDeltaTime();
			showLesson4();
		}
        sprites[3].setX(camera.position.x+10-(cameraW/2)+sprites[2].getWidth());
        sprites[3].setY(camera.position.y-5+(cameraH/2)-sprites[3].getHeight());
        sprites[3].draw(batch);
        sprites[2].setX(camera.position.x+5-(cameraW/2));
        sprites[2].setY(camera.position.y-5+(cameraH/2)-sprites[2].getHeight());
        sprites[2].draw(batch);
        batch.end();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		
		font = new BitmapFont();
		font.setColor(Color.BLACK);
		whiteFont = new BitmapFont();
		whiteFont.setColor(Color.WHITE);
		sprites[1].setX(40);
		sprites[1].setY(5);
		player = new Player(40,70,animations[0]);
		player.setSpeed(2f);
		joypad = new TwoButtonsJoypad(0,5,joypadSprites);
		joypad.setMargin(10);
		batch = new SpriteBatch();
		camera = new OrthographicCamera(cameraW,cameraH);
        camera.position.x = cameraW/2;
		camera.position.y = cameraH/2;
        camera.update();
        tiledMap = game.assets.get("data/levels_media/levelGreek.tmx", TiledMap.class);
        playerLayer = tiledMap.getLayers().get("player");
		joypadLayer = tiledMap.getLayers().get("controls");
		merchantLayer = tiledMap.getLayers().get("merchant");
		politicianLayer = tiledMap.getLayers().get("politician");
		officerLayer = tiledMap.getLayers().get("officer");
		marineLayer = tiledMap.getLayers().get("marine");
		hideProgress();
		showProgress();
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
		font.dispose();
		whiteFont.dispose();
		for (TextureAtlas ta : atlases)
		{
			ta.dispose();
		}
		for (Texture t : textures)
		{
			t.dispose();
		}
		for (Sound s : sounds)
		{
			s.dispose();
		}
		for (Music m : traderSounds)
		{
			m.dispose();
		}
		for (Music m : politicianSounds)
		{
			m.dispose();
		}
		for (Music m : officerSounds)
		{
			m.dispose();
		}
		for (Music m : marineSounds)
		{
			m.dispose();
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
		
		Vector3 v = new Vector3(screenX,screenY,0);
		camera.unproject(v);
		if (sprites[2].getBoundingRectangle().contains(v.x,v.y))
			game.setScreen(new MainMenu(game));
		if (sprites[3].getBoundingRectangle().contains(v.x,v.y))
			game.setScreen(new Levels(game));
		if ((showLesson1 || showLesson2 || showLesson3 || showLesson4) && quizTime)
		{
			int thisProgressLevel = 1;
			if (showLesson1) thisProgressLevel = 1;
			if (showLesson2) thisProgressLevel = 2;
			if (showLesson3) thisProgressLevel = 3;
			if (showLesson4) thisProgressLevel = 4;
			if (quizHolder.getCorrectOption() != 0)
			{
				if (quizHolder.getSprites()[quizHolder.getCorrectOption()-1].getBoundingRectangle().contains(v.x, v.y))
				{
					currentQuiz++;
					if (currentQuiz == 3)
					{
						currentQuiz = 0;
						quizTime = false;
						showLesson1 = false;
						showLesson2 = false;
						showLesson3 = false;
						showLesson4 = false;
						Preferences prefs = Gdx.app.getPreferences("My Preferences");
						if (H.progressLevel<4)
						{
							if (H.currentLevel == 1 && H.progressLevel == thisProgressLevel)
							prefs.putInteger("progressInLevel", prefs.getInteger("progressInLevel", 1)+1);
						}
						else
						{
							if (H.currentLevel == 1)
							{
								prefs.putInteger("progressLevel", 2);
								prefs.putInteger("progressInLevel", 0);
							}
						}
						prefs.flush();
						H.currentLevel = prefs.getInteger("progressLevel", 1);
						H.progressLevel = prefs.getInteger("progressInLevel", 1);
						hideProgress();
						showProgress();
					}
				}
			}
			return true;
		}
		if (selectShown)
		{
			if (sprites[0].getBoundingRectangle().contains(v.x, v.y))
			{
				switch (currentCharacter)
				{
					case 1: 
						lessonTime = 0;
						showLesson1 = true;
						return true;
					case 2:
						lessonTime = 0;
						showLesson2 = true;
						return true;
					case 3:
						lessonTime = 0;
						showLesson3 = true;
						return true;
					case 4:
						lessonTime = 0;
						showLesson4 = true;
						return true;
				}
			}
		}
		int i = 0;
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
	private void showLesson4() {
		if (lessonTime < 23)
		{
			text = "Greek ships had sails, and were pushed along by the wind. Small trading ships usually stayed close to the shore, so the sailors did not get lost." +
					" Before a voyage, the sailors prayed to the sea god Poseidon, for a safe journey.";
			if (currentMarineSound == 0)
				{
					marineSounds[0].play();
					currentMarineSound++;
				}
		}
		else if (22 < lessonTime && lessonTime < 72)
		{
			text = "The Greeks used their ships to sail off and found colonies. One colony called Massalia (Marseille), " +
					"in what is now France, was founded around 600 BC. The Greeks who landed here were called Phokaians. " +
					"Their 50-oared ships were the fastest ships in the Mediterranean.\nIt was useful to have a fast ship," +
					" because there were lots of pirates! If pirates caught a trading ship, they would steal the cargo (which might be wine or copper or gold). " +
					"They would sell the crew and passengers as slaves. ";
			if (currentMarineSound == 1)
			{
				marineSounds[1].play();
				currentMarineSound++;
			}
		}
		else if (71 < lessonTime && lessonTime < 123)
		{
			text = "In a battle, the triremes tried to get close to the enemy ships and if possible crash into them. " +
					"A trireme was steered by long steering oars at the stern or back of the ship. " +
					"The captain ordered the ship to steer straight at an enemy ship. Fixed to the front of the trireme was a sharp metal-covered point or ram. " +
					"When the trireme struck the side of an enemy ship, the ram smashed a hole in the wooden planks. Water flooded in and the damaged ship either sank or had to be beached on the nearest shore. " +
					"The trireme's soldiers sometimes jumped onto a damaged ship and captured it. ";
			if (currentMarineSound == 2)
			{
				marineSounds[2].play();
				currentMarineSound = 0;
			}
		}
		else 
		{
			quizTime = true;
			quizHolder = new Quiz(batch);
			switch (currentQuiz) {
			case 0:
				quizHolder.setQuestionFont(font);
				quizHolder.setOption1Font(whiteFont);
				quizHolder.setOption2Font(whiteFont);
				quizHolder.setOption3Font(whiteFont);
				quizHolder.setBackgroundTexture(transparent);
				quizHolder.setQuestionTexture(textures[3]);
				quizHolder.setAllOptionsBackgrounds(textures[4]);
				quizHolder.questionSprite.setPosition(sprites[1].getX(), camera.position.y-100);
				quizHolder.option1Sprite.setPosition(sprites[1].getX(), camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.option2Sprite.setPosition(sprites[1].getX()+200, camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.option3Sprite.setPosition(sprites[1].getX()+400, camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.setCorrectOption(2);
				quizHolder.setOption1("Cruisers");
				quizHolder.setOption2("Triremes");
				quizHolder.setOption3("Destroyers");
				quizHolder.setQuestion("The Greek warships were called");
				quizHolder.draw();
				break;

			case 1:
				quizHolder.setQuestionFont(font);
				quizHolder.setOption1Font(whiteFont);
				quizHolder.setOption2Font(whiteFont);
				quizHolder.setOption3Font(whiteFont);
				quizHolder.setQuestionFont(font);
				quizHolder.setBackgroundTexture(transparent);
				quizHolder.setQuestionTexture(textures[3]);
				quizHolder.setAllOptionsBackgrounds(textures[4]);
				quizHolder.questionSprite.setPosition(sprites[1].getX(), camera.position.y-100);
				quizHolder.option1Sprite.setPosition(sprites[1].getX(), camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.option2Sprite.setPosition(sprites[1].getX()+200, camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.option3Sprite.setPosition(sprites[1].getX()+400, camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.setCorrectOption(1);
				quizHolder.setOption1("Massalia");
				quizHolder.setOption2("New York");
				quizHolder.setOption3("Rome");
				quizHolder.setQuestion("One of the famous Greek colonies was");
				quizHolder.draw();
				break;
			case 2:
				quizHolder.setQuestionFont(font);
				quizHolder.setOption1Font(whiteFont);
				quizHolder.setOption2Font(whiteFont);
				quizHolder.setOption3Font(whiteFont);
				quizHolder.setBackgroundTexture(transparent);
				quizHolder.setQuestionTexture(textures[3]);
				quizHolder.setAllOptionsBackgrounds(textures[4]);
				quizHolder.questionSprite.setPosition(sprites[1].getX(), camera.position.y-100);
				quizHolder.option1Sprite.setPosition(sprites[1].getX(), camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.option2Sprite.setPosition(sprites[1].getX()+200, camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.option3Sprite.setPosition(sprites[1].getX()+400, camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.setCorrectOption(1);
				quizHolder.setOption1("Pirates");
				quizHolder.setOption2("Submarines");
				quizHolder.setOption3("Romans");
				quizHolder.setQuestion("The ships needed to be fast because there were alot of");
				quizHolder.draw();
				break;
			}
			return;
		}
		if (122 > lessonTime)
		{
			Sprite tmp = new Sprite(animations[6].getKeyFrame(elapsedTime,true));
			tmp.setScale(2f);
			tmp.setX(sprites[1].getX()+sprites[1].getWidth()-90);
			tmp.setY(sprites[1].getHeight()+(tmp.getHeight()/2)-10);		
			sprites[1].draw(batch);
			tmp.draw(batch);
			font.drawWrapped(batch, text, sprites[1].getX()+10, sprites[1].getHeight()-15, 545);
		}
	}
	private void showLesson3() {
		if (lessonTime < 26)
		{
			text = "Only a very powerful ruler could control all Greece. " +
					"One man did in the 300s BC. He was Alexander the Great, from Macedonia. " +
					"Alexander led his army to conquer not just Greece but an empire that reached as far as Afghanistan and India.";
			if (currentOfficerSound == 0)
				{
					officerSounds[0].play();
					currentOfficerSound++;
				}
		}
		else if (25 < lessonTime && lessonTime < 65)
		{
			text = "I'm a Greek officer, and I'm happy to introduce you to the Greek art of war." +
					"The Greek states often fought each other. Sparta and Athens fought a long war, " +
					"called the Peloponnesian War, from 431 to 404 BC. Sparta won." +
					"Only the threat of invasion by a foreign enemy made the Greeks " +
					"forget their quarrels and fight on the same side. Their main enemy was Persia.";
			if (currentOfficerSound == 1)
			{
				officerSounds[1].play();
				currentOfficerSound++;
			}
		}
		else if (64 < lessonTime && lessonTime < 102)
		{
			text = "The backbone of the Greek army was the hoplite. He was a foot-soldier, " +
					"and his weapons were a long spear and a sword. He also had a round shield. " +
					"Hoplites fought in lines or ranks. Eight to ten ranks made a formation called the phalanx and enemy soldiers saw only a mass of spears and shields," +
					" that was hard to break through - and hard to stop once it started moving forward.\n";
			if (currentOfficerSound == 2)
			{
				officerSounds[2].play();
				currentOfficerSound = 0;
			}
		}
		else if (102 < lessonTime && lessonTime < 131)
		{
			text = "A hoplite had to pay for his armour, unless his father was killed in battle. Then he was given his father's weapons and armour." +
					" Rich men had metal armour, shaped to the chest, but others wore cheap armour made of linen cloth." +
					" Layers of cloth were glued together, to make a tough, bendy jacket.";
		}
		else 
		{
			quizTime = true;
			quizHolder = new Quiz(batch);
			switch (currentQuiz) {
			case 0:
				quizHolder.setQuestionFont(font);
				quizHolder.setOption1Font(whiteFont);
				quizHolder.setOption2Font(whiteFont);
				quizHolder.setOption3Font(whiteFont);
				quizHolder.setBackgroundTexture(transparent);
				quizHolder.setQuestionTexture(textures[3]);
				quizHolder.setAllOptionsBackgrounds(textures[4]);
				quizHolder.questionSprite.setPosition(sprites[1].getX(), camera.position.y-100);
				quizHolder.option1Sprite.setPosition(sprites[1].getX(), camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.option2Sprite.setPosition(sprites[1].getX()+200, camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.option3Sprite.setPosition(sprites[1].getX()+400, camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.setCorrectOption(2);
				quizHolder.setOption1("Archers");
				quizHolder.setOption2("Hoptiles");
				quizHolder.setOption3("Elephants");
				quizHolder.setQuestion("Who were the backbone of the Greek army?");
				quizHolder.draw();
				break;

			case 1:
				quizHolder.setQuestionFont(font);
				quizHolder.setOption1Font(whiteFont);
				quizHolder.setOption2Font(whiteFont);
				quizHolder.setOption3Font(whiteFont);
				quizHolder.setBackgroundTexture(transparent);
				quizHolder.setQuestionTexture(textures[3]);
				quizHolder.setAllOptionsBackgrounds(textures[4]);
				quizHolder.questionSprite.setPosition(sprites[1].getX(), camera.position.y-100);
				quizHolder.option1Sprite.setPosition(sprites[1].getX(), camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.option2Sprite.setPosition(sprites[1].getX()+200, camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.option3Sprite.setPosition(sprites[1].getX()+400, camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.setCorrectOption(3);
				quizHolder.setOption1("In the 600s BC");
				quizHolder.setOption2("In the 100s BC");
				quizHolder.setOption3("In the 300s BC");
				quizHolder.setQuestion("Alexander the Great controlled all of Greece");
				quizHolder.draw();
				break;
			case 2:
				quizHolder.setQuestionFont(font);
				quizHolder.setOption1Font(whiteFont);
				quizHolder.setOption2Font(whiteFont);
				quizHolder.setOption3Font(whiteFont);
				quizHolder.setBackgroundTexture(transparent);
				quizHolder.setQuestionTexture(textures[3]);
				quizHolder.setAllOptionsBackgrounds(textures[4]);
				quizHolder.questionSprite.setPosition(sprites[1].getX(), camera.position.y-100);
				quizHolder.option1Sprite.setPosition(sprites[1].getX(), camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.option2Sprite.setPosition(sprites[1].getX()+200, camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.option3Sprite.setPosition(sprites[1].getX()+400, camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.setCorrectOption(1);
				quizHolder.setOption1("The phalanx");
				quizHolder.setOption2("The ranks");
				quizHolder.setOption3("The tanks");
				quizHolder.setQuestion("Formations of Hoptiles are called");
				quizHolder.draw();
				break;
			}
			return;
		}
		if (130 > lessonTime)
		{
			Sprite tmp = new Sprite(animations[5].getKeyFrame(elapsedTime,true));
			tmp.setScale(2f);
			tmp.setX(sprites[1].getX()+sprites[1].getWidth()-90);
			tmp.setY(sprites[1].getHeight()+(tmp.getHeight()/2)-10);		
			sprites[1].draw(batch);
			tmp.draw(batch);
			font.drawWrapped(batch, text, sprites[1].getX()+10, sprites[1].getHeight()-15, 545);
		}
	}
	private void showLesson2() {
		if (lessonTime < 18)
		{
			text = "There was not one country called \"Ancient Greece\"." +
					" Instead, there were small 'city-states'. Each city-state had its own government." +
					" Sometimes the city-states fought one another, sometimes they joined together against a bigger enemy.";
			if (currentPoliticianSound == 0)
				{
					politicianSounds[0].play();
					currentPoliticianSound++;
				}
		}
		else if (17 < lessonTime && lessonTime < 35)
		{
			text = "People in ancient Greece believed that there were invisible, " +
					"powerful gods and spirits that could control what happened to you. " +
					"Most people also thought that you could control those gods and spirits through sacrifice, prayer, and living a good life.";
			if (currentPoliticianSound == 1)
			{
				politicianSounds[1].play();
				currentPoliticianSound++;
			}
		}
		else if (34 < lessonTime && lessonTime < 63)
		{
			text = "Sometime people who felt they needed a fresh start with the gods joined a mystery cult, " +
					"where you did special rituals and sacrifices and tried to form a special, closer relationship with a particular god or spirit. " +
					"For most people in ancient Greece, the gods were always around them, paying attention to everything they did, " +
					"and an important part of success in life was keeping on the right side of the gods.";
			if (currentPoliticianSound == 2)
			{
				politicianSounds[2].play();
				currentPoliticianSound = 0;
			}
		}
		else 
		{
			quizTime = true;
			quizHolder = new Quiz(batch);
			switch (currentQuiz) {
			case 0:
				quizHolder.setQuestionFont(font);
				quizHolder.setOption1Font(whiteFont);
				quizHolder.setOption2Font(whiteFont);
				quizHolder.setOption3Font(whiteFont);
				quizHolder.setBackgroundTexture(transparent);
				quizHolder.setQuestionTexture(textures[3]);
				quizHolder.setAllOptionsBackgrounds(textures[4]);
				quizHolder.questionSprite.setPosition(sprites[1].getX(), camera.position.y-100);
				quizHolder.option1Sprite.setPosition(sprites[1].getX(), camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.option2Sprite.setPosition(sprites[1].getX()+200, camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.option3Sprite.setPosition(sprites[1].getX()+400, camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.setCorrectOption(1);
				quizHolder.setOption1("Many Gods");
				quizHolder.setOption2("One God");
				quizHolder.setOption3("No Gods");
				quizHolder.setQuestion("People in Ancient Greece believed in and worshiped");
				quizHolder.draw();
				break;

			case 1:
				quizHolder.setQuestionFont(font);
				quizHolder.setOption1Font(whiteFont);
				quizHolder.setOption2Font(whiteFont);
				quizHolder.setOption3Font(whiteFont);
				quizHolder.setBackgroundTexture(transparent);
				quizHolder.setQuestionTexture(textures[3]);
				quizHolder.setAllOptionsBackgrounds(textures[4]);
				quizHolder.questionSprite.setPosition(sprites[1].getX(), camera.position.y-100);
				quizHolder.option1Sprite.setPosition(sprites[1].getX(), camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.option2Sprite.setPosition(sprites[1].getX()+200, camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.option3Sprite.setPosition(sprites[1].getX()+400, camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.setCorrectOption(3);
				quizHolder.setOption1("Ra");
				quizHolder.setOption2("Thor");
				quizHolder.setOption3("Zues");
				quizHolder.setQuestion("Which of these is a Greek God?");
				quizHolder.draw();
				break;
			case 2:
				quizHolder.setQuestionFont(font);
				quizHolder.setOption1Font(whiteFont);
				quizHolder.setOption2Font(whiteFont);
				quizHolder.setOption3Font(whiteFont);
				quizHolder.setBackgroundTexture(transparent);
				quizHolder.setQuestionTexture(textures[3]);
				quizHolder.setAllOptionsBackgrounds(textures[4]);
				quizHolder.questionSprite.setPosition(sprites[1].getX(), camera.position.y-100);
				quizHolder.option1Sprite.setPosition(sprites[1].getX(), camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.option2Sprite.setPosition(sprites[1].getX()+200, camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.option3Sprite.setPosition(sprites[1].getX()+400, camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.setCorrectOption(3);
				quizHolder.setOption1("Castles");
				quizHolder.setOption2("Dungeons");
				quizHolder.setOption3("Temples");
				quizHolder.setQuestion("Greek Gods where worshiped in");
				quizHolder.draw();
				break;
			}
			return;
		}
		if (63 > lessonTime)
		{
			Sprite tmp = new Sprite(animations[4].getKeyFrame(elapsedTime,true));
			tmp.setScale(2f);
			tmp.setX(sprites[1].getX()+sprites[1].getWidth()-90);
			tmp.setY(sprites[1].getHeight()+(tmp.getHeight()/2)-10);		
			sprites[1].draw(batch);
			tmp.draw(batch);
			font.drawWrapped(batch, text, sprites[1].getX()+10, sprites[1].getHeight()-15, 545);
		}
	}
	private void showLesson1() {
		if (lessonTime < 28)
		{
			text = "\t\tWelcome to ancient Greece!\n Ancient Greece is called 'the birthplace of Western civilisation'." +
			" About 2500 years ago, the Greeks created a way of life that other people admired and copied." +
			" The Romans copied Greek art and Greek gods, for example." +
			"I'm a Greek trader, and I'm happy to be your first mentor.\n" +
			"I'm going to speak to you about Greek economy.";
			if (currentTraderSound == 0)
				{
					traderSounds[0].play();
					currentTraderSound++;
				}
		}
		else if (27 < lessonTime && lessonTime < 56)
		{
			text = "Ancient Greece had a warm, dry climate, as Greece does today. People lived by farming, fishing, and trade." +
					" Some were soldiers. Others were scholars, scientists or artists. Many Greeks were poor. Life was hard because farmland, " +
					"water and timber for building were all scarce. That's why many Greeks sailed off to find new lands to settle.";
			if (currentTraderSound == 1)
			{
				traderSounds[1].play();
				currentTraderSound++;
			}
		}
		else if (55 < lessonTime && lessonTime < 76)
		{
			text = "Beginning around 600 BC, each Greek city-state minted (made) its own kinds of coins. A lot of the Greek coins were silver and the pictures on them were different for each city-state." +
					" Just like today, some coins were worth more than others." +
					"\n Now let's take a small quiz.";
			if (currentTraderSound == 2)
			{
				traderSounds[2].play();
				currentTraderSound = 0;
			}
		}
		else 
		{
			quizTime = true;
			quizHolder = new Quiz(batch);
			switch (currentQuiz) {
			case 0:
				quizHolder.setQuestionFont(font);
				quizHolder.setOption1Font(whiteFont);
				quizHolder.setOption2Font(whiteFont);
				quizHolder.setOption3Font(whiteFont);
				quizHolder.setBackgroundTexture(transparent);
				quizHolder.setQuestionTexture(textures[3]);
				quizHolder.setAllOptionsBackgrounds(textures[4]);
				quizHolder.questionSprite.setPosition(sprites[1].getX(), camera.position.y-100);
				quizHolder.option1Sprite.setPosition(sprites[1].getX(), camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.option2Sprite.setPosition(sprites[1].getX()+200, camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.option3Sprite.setPosition(sprites[1].getX()+400, camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.setCorrectOption(3);
				quizHolder.setOption1("Around 800 BC");
				quizHolder.setOption2("Around 100 BC");
				quizHolder.setOption3("Around 600 BC");
				quizHolder.setQuestion("When did the Greek start using coins?");
				quizHolder.draw();
				break;

			case 1:
				quizHolder.setQuestionFont(font);
				quizHolder.setOption1Font(whiteFont);
				quizHolder.setOption2Font(whiteFont);
				quizHolder.setOption3Font(whiteFont);
				quizHolder.setQuestionFont(font);
				quizHolder.setBackgroundTexture(transparent);
				quizHolder.setQuestionTexture(textures[3]);
				quizHolder.setAllOptionsBackgrounds(textures[4]);
				quizHolder.questionSprite.setPosition(sprites[1].getX(), camera.position.y-100);
				quizHolder.option1Sprite.setPosition(sprites[1].getX(), camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.option2Sprite.setPosition(sprites[1].getX()+200, camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.option3Sprite.setPosition(sprites[1].getX()+400, camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.setCorrectOption(1);
				quizHolder.setOption1("Warm and dry");
				quizHolder.setOption2("Cold and snowy");
				quizHolder.setOption3("Foggy and rainy");
				quizHolder.setQuestion("Was the climate in Greece?");
				quizHolder.draw();
				break;
			case 2:
				quizHolder.setQuestionFont(font);
				quizHolder.setOption1Font(whiteFont);
				quizHolder.setOption2Font(whiteFont);
				quizHolder.setOption3Font(whiteFont);
				quizHolder.setBackgroundTexture(transparent);
				quizHolder.setQuestionTexture(textures[3]);
				quizHolder.setAllOptionsBackgrounds(textures[4]);
				quizHolder.questionSprite.setPosition(sprites[1].getX(), camera.position.y-100);
				quizHolder.option1Sprite.setPosition(sprites[1].getX(), camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.option2Sprite.setPosition(sprites[1].getX()+200, camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.option3Sprite.setPosition(sprites[1].getX()+400, camera.position.y-(20+sprites[1].getHeight()));
				quizHolder.setCorrectOption(2);
				quizHolder.setOption1("Scientist");
				quizHolder.setOption2("President");
				quizHolder.setOption3("Doctor");
				quizHolder.setQuestion("None of the ancient Greek was a");
				quizHolder.draw();
				break;
			}
			return;
		}
		if (76 > lessonTime)
		{
			Sprite tmp = new Sprite(animations[3].getKeyFrame(elapsedTime,true));
			tmp.setScale(2f);
			tmp.setX(sprites[1].getWidth()-50);
			tmp.setY(sprites[1].getHeight()+(tmp.getHeight()/2)-10);		
			sprites[1].draw(batch);
			tmp.draw(batch);
			font.drawWrapped(batch, text, 50, sprites[1].getHeight()-15, 545);
		}
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		player.moving(false);
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
