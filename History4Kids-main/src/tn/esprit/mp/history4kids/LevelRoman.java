package tn.esprit.mp.history4kids;

import tn.esprit.mp.history4kids.utils.FourButtonsJoypad;
import tn.esprit.mp.history4kids.utils.H;
import tn.esprit.mp.history4kids.utils.OrthogonalTiledMapRendererWithSprites;
import tn.esprit.mp.history4kids.utils.Player;
import tn.esprit.mp.history4kids.utils.Quiz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class LevelRoman implements Screen, InputProcessor {
	
	private History4Kids game;
	private SpriteBatch batch;
	private int interBook = 0;
	private boolean showS = false;
	private int currentQuiz = 0;
	private Quiz quizHolder;
	private Sprite[] books = new Sprite[10];
	private int cameraH, cameraW, currentBook = 0, activeBook = 0;
	private Texture transparent, shader;
	private boolean[] showBook = new boolean[5];
	private Texture[] textures = new Texture[20];
	private Sprite[] joypadSprites = new Sprite[20], sprites = new Sprite[20];
	private TextureAtlas[] atlases = new TextureAtlas[20];
	private Animation[] animations = new Animation[20];
	private boolean showSelect, showSelectPolitician = false, ableToMoveLeft = true, ableToMoveRight = true, ableToMoveUp = true, ableToMoveDown = true, added = true;
	private Player player;
	private OrthographicCamera camera;
	private int playerW = 35, playerH = 47;
	private int levelW = 1000, levelH = 1000;
	private FourButtonsJoypad joypad;
	private MapLayer playerLayer, joypadLayer, politicianLayer;
	private MapLayer[] booksLayers = new MapLayer[4];
	private Rectangle[] collisionRectangles = new Rectangle[10], bookRectangles = new Rectangle[10];
	private float elapsedTime = 0;
	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;
	private BitmapFont whiteFont, font, debugFont;
	private Rectangle politician;
	private boolean quizTime = false, showQuiz= false;
	
	public LevelRoman (History4Kids game)
	{
		
		//TODO FINISH LEVEL
		//TODO CHECK BOOKS PROBLEM
		
		this.game = game;
		batch = new SpriteBatch();
		cameraH = H.cameraH;
		cameraW = H.cameraW;
		
		politician = new Rectangle(420,730,70,100);
		
		transparent = game.assets.get("data/levels_media/transparent.png", Texture.class);
		shader = game.assets.get("data/levels_media/shader.png", Texture.class);

		textures[0] = game.assets.get("data/controls/flatdark_left_half.png", Texture.class);
		textures[1] = game.assets.get("data/controls/flatdark_right_half.png", Texture.class);
		textures[2] = game.assets.get("data/controls/flatdark_up_half.png", Texture.class);
		textures[3] = game.assets.get("data/controls/flatdark_down_half.png", Texture.class);
		textures[4] = game.assets.get("data/controls/flatdark_select.png", Texture.class);
		textures[5] = game.assets.get("data/levels_media/book001.png", Texture.class);
		textures[6] = game.assets.get("data/levels_media/book002.png", Texture.class);
		textures[7] = game.assets.get("data/levels_media/book003.png", Texture.class);
		textures[8] = game.assets.get("data/levels_media/book004.png", Texture.class);
		textures[9] = game.assets.get("data/dialogs/standard.png", Texture.class);
		textures[10] = game.assets.get("data/dialogs/standard_answer.png",Texture.class);
		textures[11] = game.assets.get("data/controls/flatDark15.png",Texture.class);
		textures[12] = game.assets.get("data/controls/flatDark32.png",Texture.class);
		
		joypadSprites[0] = new Sprite(textures[0]);
		joypadSprites[1] = new Sprite(textures[1]);
		joypadSprites[2] = new Sprite(textures[2]);
		joypadSprites[3] = new Sprite(textures[3]);
		
		sprites[0] = new Sprite(textures[4]);
		sprites[1] = new Sprite(textures[9]);
		sprites[3] = new Sprite(textures[11]);
		sprites[2] = new Sprite(textures[12]);
		
		atlases[0] = game.assets.get("data/spritesheets/waving_alien_half.atlas", TextureAtlas.class);
		atlases[1] = game.assets.get("data/spritesheets/walkleft_alien_half.atlas", TextureAtlas.class);
		atlases[2] = game.assets.get("data/spritesheets/walkright_alien_half.atlas", TextureAtlas.class);
		atlases[3] = game.assets.get("data/spritesheets/walktop_alien_half.atlas", TextureAtlas.class);
		atlases[4] = game.assets.get("data/spritesheets/walkbot_alien_half.atlas", TextureAtlas.class);
		
		animations[0] = new Animation(1/10f, atlases[0].getRegions());
		animations[1] = new Animation(1/20f, atlases[1].getRegions());
		animations[2] = new Animation(1/20f, atlases[2].getRegions());
		animations[3] = new Animation(6/20f, atlases[3].getRegions());
		animations[4] = new Animation(6/20f, atlases[4].getRegions());
		
		books[0] = new Sprite(textures[5]);
		books[1] = new Sprite(textures[6]);
		books[2] = new Sprite(textures[7]);
		books[3] = new Sprite(textures[8]);
	}
	private void drawPlayer()
	{
        TextureMapObject playerObject = new TextureMapObject(player.getAnimation().getKeyFrame(elapsedTime ,true));
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
        TextureMapObject joypadSecondButtonObject = new TextureMapObject(joypad.getSecondButtonSprite());
        joypadSecondButtonObject.setX(joypad.getSecondButtonSprite().getX());
        joypadSecondButtonObject.setY(joypad.getSecondButtonSprite().getY());
        joypadLayer.getObjects().add(joypadSecondButtonObject);
        TextureMapObject joypadThirdButtonObject = new TextureMapObject(joypad.getThirdButtonSprite());
        joypadThirdButtonObject.setX(joypad.getThirdButtonSprite().getX());
        joypadThirdButtonObject.setY(joypad.getThirdButtonSprite().getY());
        joypadLayer.getObjects().add(joypadThirdButtonObject);
        TextureMapObject joypadFourthButtonObject = new TextureMapObject(joypad.getFourthButtonSprite());
        joypadFourthButtonObject.setX(joypad.getFourthButtonSprite().getX());
        joypadFourthButtonObject.setY(joypad.getFourthButtonSprite().getY());
        joypadLayer.getObjects().add(joypadFourthButtonObject);
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
        TextureMapObject joypadThirdButtonObject = (TextureMapObject) joypadLayer.getObjects().get(2);
        joypadThirdButtonObject.setX(joypad.getThirdButtonSprite().getX());
        joypadThirdButtonObject.setY(joypad.getThirdButtonSprite().getY());
        TextureMapObject joypadFourthButtonObject = (TextureMapObject) joypadLayer.getObjects().get(3);
        joypadFourthButtonObject.setX(joypad.getFourthButtonSprite().getX());
        joypadFourthButtonObject.setY(joypad.getFourthButtonSprite().getY());
	}
	public void showProgress()
	{
		if (H.currentLevel != 2) {
			for (MapLayer ml : booksLayers)
				ml.setVisible(true);
			politicianLayer.setVisible(true);
			return;
		}
			if (H.progressLevel == 4)
				politicianLayer.setVisible(true);
			if (H.progressLevel <= 4)
			{
				for (int i=0; i<=H.progressLevel;i++)
				{
					booksLayers[i].setVisible(true);
				}
				currentBook = 0;
				return;
			}
	}
	public void hideProgress()
	{
		politicianLayer.setVisible(false);
		for (MapLayer ml : booksLayers)
		{
			if (ml != null)
			ml.setVisible(false);
		}
	}
	
	public void showBook(int bookNumber)
	{
		Sprite shaderSprite = new Sprite(shader);
		shaderSprite.setScale(200f);
		shaderSprite.setX( camera.position.x - (cameraW/2) );
		shaderSprite.setY( camera.position.y - (cameraH/2) );
		shaderSprite.draw(batch);
		activeBook= bookNumber;
		books[activeBook-1].setScale(1.5f);
		books[activeBook-1].setX( camera.position.x - (books[activeBook-1].getWidth()/2) );
		books[activeBook-1].setY( camera.position.y - (books[activeBook-1].getHeight()/2) );
		books[activeBook-1].draw(batch);
	}
	public void displaySelectButton(int cuBook)
	{
		if (cuBook > H.progressLevel+1 && H.currentLevel == 2) return;
		showS = true;
		interBook = cuBook;
		currentBook = cuBook;
		System.out.println("book"+cuBook);
		sprites[0].setScale(2f);
		sprites[0].setX(camera.position.x+(cameraW/2)-(sprites[0].getWidth()+10));
        sprites[0].setY(camera.position.y-(cameraH/2)+10);
        TextureMapObject selectButton = new TextureMapObject(sprites[0]);
        selectButton.setX(sprites[0].getX());
        selectButton.setY(sprites[0].getY());
        joypadLayer.getObjects().add(selectButton);
        showSelect = true;
	}
	public void showSelectPolitician()
	{
		sprites[0].setScale(2f);
		sprites[0].setX(camera.position.x+(cameraW/2)-(sprites[0].getWidth()+10));
        sprites[0].setY(camera.position.y-(cameraH/2)+10);
        TextureMapObject selectButton = new TextureMapObject(sprites[0]);
        selectButton.setX(sprites[0].getX());
        selectButton.setY(sprites[0].getY());
        joypadLayer.getObjects().add(selectButton);
        showSelectPolitician = true;
	}
	public void removeThirdButton()
	{
		try
		{
			joypadLayer.getObjects().remove(4);
			currentBook = 0;
			showS = false;
			showSelectPolitician = false;
		}
		catch (Exception e) {}
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(208,244,247,1);
	    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    showSelect = false;
	    player.setAnimation(animations[0]);
	    float speed = player.getSpeed();
	    removeThirdButton();
		hideProgress();
		showProgress();
		ableToMoveLeft = true;
		ableToMoveRight = true;
		ableToMoveUp = true;
		ableToMoveDown = true;
		Rectangle other = new Rectangle(player.getX(), player.getY(), playerW, playerH);
		if (player.movingDown) other.y -= player.getSpeed();
		if (player.movingUp) other.y += player.getSpeed();
		if (player.movingRight) other.x += player.getSpeed();
		if (player.movingLeft) other.x -= player.getSpeed();
		for (Rectangle r : collisionRectangles)
			{
				if (r != null)
				{
					if (Intersector.overlaps(other, r)) {
						if (player.movingDown) ableToMoveDown = false;
						if (player.movingUp) ableToMoveUp = false;
						if (player.movingLeft) ableToMoveLeft = false;
						if (player.movingRight) ableToMoveRight = false;
						}
				}
			}
        if (player.movingLeft && ableToMoveLeft)
		{
			player.setAnimation(animations[1]);
			if (player.getX() > speed) player.setX(player.getX()-speed);
			if (camera.position.x-(cameraW/2) > 0 && camera.position.x-(camera.position.x/4) <= player.getX()&& camera.position.x >= player.getX()) 
				{
					camera.translate(-speed,0);
					joypad.setX(joypad.getX()-speed);
					camera.update();
					refreshJoypad();
				}
		}
		if (player.movingRight && ableToMoveRight)
		{
			player.setAnimation(animations[2]);
			if (player.getX()+playerW < levelW) 
				{
					player.setX(player.getX()+speed);
				}
			if (camera.position.x+(cameraW/2) < levelW && camera.position.x <= player.getX() && camera.position.x+(camera.position.x/4) >= player.getX()) 
				{
					camera.translate(speed,0);
					joypad.setX(joypad.getX()+speed);
					camera.update();
					refreshJoypad();
				}
		}
		if (player.movingDown && ableToMoveDown)
		{
			if (player.getY() > speed) 
			{
				player.setAnimation(animations[4]);
				player.setY(player.getY()-speed);
			}
			else 
				{
					player.movingDown = false;
				}
			if (camera.position.y-(cameraH/2) > 0 && camera.position.y-(camera.position.y/4) <= player.getY()&& camera.position.y >= player.getY()) 
				{
					camera.translate(0,-speed);
					joypad.setY(joypad.getY()-speed);
					camera.update();
					refreshJoypad();
				}
		}
		if (player.movingUp  && ableToMoveUp)
		{
			if (player.getY()+playerH < levelH) 
			{
				player.setAnimation(animations[3]);
				player.setY(player.getY()+speed);
			}
			else 
				{
					player.movingUp = false;
				}
			if (camera.position.y+(cameraH/2) < levelH && camera.position.y <= player.getY() && camera.position.y+(camera.position.y/4) >= player.getY()) 
				{
					camera.translate(0,speed);
					joypad.setY(joypad.getY()+speed);
					camera.update();
					refreshJoypad();
				}
		}
		elapsedTime += Gdx.graphics.getDeltaTime();
        camera.update();
        tiledMapRenderer.setView(camera);
        int bookCounter = 0;
		for (Rectangle r : bookRectangles)
		{
			if (r != null)
			{
				bookCounter++;
				if (Intersector.overlaps(other, r)) {
					
					switch (bookCounter)
					{
						case 1:
							displaySelectButton(bookCounter);
							break;
						case 2:
							displaySelectButton(bookCounter);
							break;
						case 3:
							displaySelectButton(bookCounter);
							break;
						case 4:
							displaySelectButton(bookCounter);
							break;
					}
					
				}
			}
		}
		if (Intersector.overlaps(other, politician))
		{
			showSelectPolitician();
		}
        refreshPlayer();
        tiledMapRenderer.render();
        batch.setProjectionMatrix(camera.combined);
        joypadLayer.setVisible(true);
    	playerLayer.setVisible(true);
    	hideProgress();
    	showProgress();
    	if (activeBook != 0)
        {
        	joypadLayer.setVisible(false);
        	playerLayer.setVisible(false);
        	for (MapLayer m : booksLayers)
        	{
        		m.setVisible(false);
        	}
        }
    	batch.begin();
    	if (activeBook != 0)
        {
        	showBook(activeBook);
        }
    	Preferences prefs = Gdx.app.getPreferences("My Preferences");
		H.currentLevel = prefs.getInteger("progressLevel", 1);
		H.progressLevel = prefs.getInteger("progressInLevel", 1);
		
		joypadLayer.setVisible(true);
		if (quizTime)
		{
			joypadLayer.setVisible(false);
		quizHolder = new Quiz(batch);
		switch (currentQuiz) {
		case 0:
			currentQuiz++;
		case 1:
			quizHolder.setQuestionFont(font);
			quizHolder.setOption1Font(whiteFont);
			quizHolder.setOption2Font(whiteFont);
			quizHolder.setOption3Font(whiteFont);
			quizHolder.setBackgroundTexture(transparent);
			quizHolder.setQuestionTexture(textures[9]);
			quizHolder.setAllOptionsBackgrounds(textures[10]);
			quizHolder.questionSprite.setPosition(camera.position.x-(cameraW/2)+10, camera.position.y-100);
			quizHolder.option1Sprite.setPosition(camera.position.x-(cameraW/2)+10, camera.position.y-(20+sprites[1].getHeight()));
			quizHolder.option2Sprite.setPosition(camera.position.x-(cameraW/2)+10+200, camera.position.y-(20+sprites[1].getHeight()));
			quizHolder.option3Sprite.setPosition(camera.position.x-(cameraW/2)+10+400, camera.position.y-(20+sprites[1].getHeight()));
			quizHolder.setCorrectOption(1);
			quizHolder.setOption1("44 BC");
			quizHolder.setOption2("100 BC");
			quizHolder.setOption3("50 BC");
			quizHolder.setQuestion("Julius Cesar was murdered in");
			quizHolder.draw();
			break;

		case 2:
			quizHolder.setQuestionFont(font);
			quizHolder.setOption1Font(whiteFont);
			quizHolder.setOption2Font(whiteFont);
			quizHolder.setOption3Font(whiteFont);
			quizHolder.setBackgroundTexture(transparent);
			quizHolder.setQuestionTexture(textures[9]);
			quizHolder.setAllOptionsBackgrounds(textures[10]);
			quizHolder.questionSprite.setPosition(camera.position.x-(cameraW/2)+10, camera.position.y-100);
			quizHolder.option1Sprite.setPosition(camera.position.x-(cameraW/2)+10, camera.position.y-(20+sprites[1].getHeight()));
			quizHolder.option2Sprite.setPosition(camera.position.x-(cameraW/2)+10+200, camera.position.y-(20+sprites[1].getHeight()));
			quizHolder.option3Sprite.setPosition(camera.position.x-(cameraW/2)+10+400, camera.position.y-(20+sprites[1].getHeight()));
			quizHolder.setCorrectOption(3);
			quizHolder.setOption1("505 BC");
			quizHolder.setOption2("10 BC");
			quizHolder.setOption3("509 BC");
			quizHolder.setQuestion("The Romans drove out the last king in");
			quizHolder.draw();
			break;
		case 3:
			quizHolder.setQuestionFont(font);
			quizHolder.setOption1Font(whiteFont);
			quizHolder.setOption2Font(whiteFont);
			quizHolder.setOption3Font(whiteFont);
			quizHolder.setBackgroundTexture(transparent);
			quizHolder.setQuestionTexture(textures[9]);
			quizHolder.setAllOptionsBackgrounds(textures[10]);
			quizHolder.questionSprite.setPosition(camera.position.x-(cameraW/2)+10, camera.position.y-100);
			quizHolder.option1Sprite.setPosition(camera.position.x-(cameraW/2)+10, camera.position.y-(20+sprites[1].getHeight()));
			quizHolder.option2Sprite.setPosition(camera.position.x-(cameraW/2)+10+200, camera.position.y-(20+sprites[1].getHeight()));
			quizHolder.option3Sprite.setPosition(camera.position.x-(cameraW/2)+10+400, camera.position.y-(20+sprites[1].getHeight()));
			quizHolder.setCorrectOption(3);
			quizHolder.setOption1("Tarquin The Proud");
			quizHolder.setOption2("Hannibal");
			quizHolder.setOption3("Julius Cesar");
			quizHolder.setQuestion("Rome's best general was");
			quizHolder.draw();
			break;
		}
		
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
		System.out.println("");
		font = new BitmapFont();
		font.setColor(Color.BLACK);
		whiteFont = new BitmapFont();
		whiteFont.setColor(Color.WHITE);
		debugFont = new BitmapFont();
		debugFont.setColor(Color.RED);
		showBook[0] = false;
		showBook[1] = false;
		showBook[2] = false;
		showBook[3] = false;
		player = new Player(cameraW/2,5,animations[0]);
		player.setSpeed(3f);
		joypad = new FourButtonsJoypad(5,5,joypadSprites);
		joypad.setMargin(10);
		batch = new SpriteBatch();
		camera = new OrthographicCamera(cameraW,cameraH);
        camera.position.x = cameraW/2;
		camera.position.y = cameraH/2;
        camera.update();
        tiledMap = game.assets.get("data/levels_media/levelRomans.tmx", TiledMap.class);
        playerLayer = tiledMap.getLayers().get("player");
        politicianLayer = tiledMap.getLayers().get("politician");
		joypadLayer = tiledMap.getLayers().get("controls");
		booksLayers[0] = tiledMap.getLayers().get("scroll1");
		booksLayers[1] = tiledMap.getLayers().get("scroll2");
		booksLayers[2] = tiledMap.getLayers().get("scroll3");
		booksLayers[3] = tiledMap.getLayers().get("scroll4");
		collisionRectangles[0] = new Rectangle(350, 240, 220, 310);
		collisionRectangles[1] = new Rectangle(140, 260, 140, 140);
		collisionRectangles[2] = new Rectangle(880, 820, 150, 180);
		collisionRectangles[3] = new Rectangle(310, 800, 220, 199);
		collisionRectangles[4] = new Rectangle(590, 840, 140, 140);
		collisionRectangles[5] = new Rectangle(880, 320, 150, 100);
		bookRectangles[0] = new Rectangle(610, 320, 30, 30);
		bookRectangles[1] = new Rectangle(670, 690, 30, 30);
		bookRectangles[2] = new Rectangle(220, 820, 30, 30);
		bookRectangles[3] = new Rectangle(950, 720, 30, 30);
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

	}
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}
	@Override
	public boolean keyUp(int keycode) {
		return true;
	}
	@Override
	public boolean keyTyped(char character) {
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 v = new Vector3(screenX,screenY,0);
		camera.unproject(v);
		int i = 0;
		if (sprites[2].getBoundingRectangle().contains(v.x,v.y))
			game.setScreen(new MainMenu(game));
		if (sprites[3].getBoundingRectangle().contains(v.x,v.y))
			game.setScreen(new Levels(game));
		if (quizTime)
		{if (quizHolder.getCorrectOption() != 0)
		{
			if (quizHolder.getSprites()[quizHolder.getCorrectOption()-1].getBoundingRectangle().contains(v.x, v.y))
			{
				currentQuiz++;
				if (currentQuiz == 4)
				{
					currentQuiz++;
					quizTime = false;
					Preferences prefs = Gdx.app.getPreferences("My Preferences");
						if (H.currentLevel == 2)
						{
							prefs.putInteger("progressLevel", 3);
							prefs.putInteger("progressInLevel", 0);
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
		if (showSelectPolitician && sprites[0].getBoundingRectangle().contains(v.x, v.y))
		{
			currentQuiz = 0;
			quizTime = true;
			return true;
		}
		if (sprites[0].getBoundingRectangle().contains(v.x, v.y))
		{
			System.out.println(".... "+H.progressLevel+" "+H.currentLevel+"    BOOK: "+currentBook);
			activeBook = interBook;
			return true;
		}
		if (activeBook != 0 && books[activeBook-1].getBoundingRectangle().contains(v.x, v.y))
		{
			if (activeBook == H.progressLevel+1 && H.currentLevel == 2)
			{
				Preferences prefs = Gdx.app.getPreferences("My Preferences");
				if (H.progressLevel<4)
				{
					prefs.putInteger("progressInLevel", prefs.getInteger("progressInLevel", 1)+1);
				}
				else
				{
					prefs.putInteger("progressLevel", 3);
					prefs.putInteger("progressInLevel", 1);
				}
				prefs.flush();
				H.currentLevel = prefs.getInteger("progressLevel", 1);
				H.progressLevel = prefs.getInteger("progressInLevel", 1);
				hideProgress();
				showProgress();
				added = true;
			}
			activeBook = 0;
			return true;
		}
		for (Sprite s : joypad.getSprites())
		{
			if (s != null)
			{
				added = false;
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
						case 2:
							player.movingUp = true;
							break;
						case 3:
							player.movingDown = true;
							break;
					}
				}
			}
			i++;
		}
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		player.moving(false);
		
		return false;
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
