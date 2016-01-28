package tn.esprit.mp.history4kids;

import tn.esprit.mp.history4kids.utils.H;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class LoadingScreen implements Screen {
	
	private History4Kids game;
	private BitmapFont font;
	private SpriteBatch batch;
	private Texture container, filler;
	private NinePatch empty, full;
	private Camera camera;
	
	private int playerW = 70, playerH = 94;
	private int levelW = 1500, levelH = 1700;
	private float elapsedTime = 0;
	private int cameraH;
	private int cameraW;
	
	private Texture[] textures = {
			new Texture(Gdx.files.internal("data/levels_media/bckg.png")),
			new Texture(Gdx.files.internal("data/spritesheets/waving_alien.png")),
			new Texture(Gdx.files.internal("data/levels_media/king.png")),
			new Texture(Gdx.files.internal("data/levels_media/space.png")),
			new Texture(Gdx.files.internal("data/levels_media/ship.png")),
			new Texture(Gdx.files.internal("data/levels_media/light.png")),
			new Texture(Gdx.files.internal("data/levels_media/speech.png"))
	};
	
	private TextureAtlas[] atlases = {
			new TextureAtlas(Gdx.files.internal("data/spritesheets/waving_alien.atlas")),
			new TextureAtlas(Gdx.files.internal("data/spritesheets/walkleft_alien.atlas")),
			new TextureAtlas(Gdx.files.internal("data/spritesheets/walkright_alien.atlas"))};
	
	private Animation[] animations = {
			new Animation(1/10f, atlases[0].getRegions()),
			new Animation(1/20f, atlases[1].getRegions()),
			new Animation(1/20f, atlases[2].getRegions())};
	
	private Sprite[] sprites = {
			new Sprite(textures[0]),
			new Sprite(textures[1]),
			new Sprite(textures[2]),
			new Sprite(textures[3]),
			new Sprite(textures[4]),
			new Sprite(textures[5]),
			new Sprite(textures[6])
	};
	
	public LoadingScreen(History4Kids game) {
		
		//TODO ADD STORY ANIMATION 
		this.game = game;
		cameraH = H.cameraH;
		cameraW = H.cameraW;
		this.game = game;
		game.assets = new AssetManager();
		game.assets.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		
		Preferences prefs = Gdx.app.getPreferences("My Preferences");
		H.currentLevel = prefs.getInteger("progressLevel", 1);
		H.progressLevel = prefs.getInteger("progressInLevel", 1);
		
		game.assets.load("data/spritesheets/walkbot_alien.atlas", TextureAtlas.class);
		game.assets.load("data/spritesheets/walkleft_alien.atlas", TextureAtlas.class);
		game.assets.load("data/spritesheets/walkright_alien.atlas", TextureAtlas.class);
		game.assets.load("data/spritesheets/walktop_alien.atlas", TextureAtlas.class);
		game.assets.load("data/spritesheets/waving_alien.atlas", TextureAtlas.class);
		game.assets.load("data/spritesheets/walkbot_alien_half.atlas", TextureAtlas.class);
		game.assets.load("data/spritesheets/walkleft_alien_half.atlas", TextureAtlas.class);
		game.assets.load("data/spritesheets/walkright_alien_half.atlas", TextureAtlas.class);
		game.assets.load("data/spritesheets/walktop_alien_half.atlas", TextureAtlas.class);
		game.assets.load("data/spritesheets/waving_alien_half.atlas", TextureAtlas.class);
		game.assets.load("data/spritesheets/greek_marine_talking.atlas", TextureAtlas.class);
		game.assets.load("data/spritesheets/greek_officer_talking.atlas", TextureAtlas.class);
		game.assets.load("data/spritesheets/greek_politician_talking.atlas", TextureAtlas.class);
		game.assets.load("data/spritesheets/greek_trader_talking.atlas", TextureAtlas.class);
		
		game.assets.load("data/levels_media/levels-1.tmx", TiledMap.class);
		game.assets.load("data/levels_media/levels-2.tmx", TiledMap.class);
		game.assets.load("data/levels_media/levels-3.tmx", TiledMap.class);
		game.assets.load("data/levels_media/levels-4.tmx", TiledMap.class);
		game.assets.load("data/levels_media/levels-5.tmx", TiledMap.class);
		game.assets.load("data/levels_media/levelGreek.tmx", TiledMap.class);
		game.assets.load("data/levels_media/levelRomans.tmx", TiledMap.class);
		game.assets.load("data/levels_media/levelEgyptian.tmx", TiledMap.class);
		game.assets.load("data/levels_media/levelMedival.tmx", TiledMap.class);
		
		game.assets.load("data/levels_media/transparent.png", Texture.class);
		game.assets.load("data/levels_media/shader.png", Texture.class);
		game.assets.load("data/levels_media/book001.png", Texture.class);
		game.assets.load("data/levels_media/book002.png", Texture.class);
		game.assets.load("data/levels_media/book003.png", Texture.class);
		game.assets.load("data/levels_media/book004.png", Texture.class);
		game.assets.load("data/levels_media/horus.png", Texture.class);
		game.assets.load("data/levels_media/pharao.png", Texture.class);
		game.assets.load("data/levels_media/mummy.png", Texture.class);
		game.assets.load("data/levels_media/008.png", Texture.class);
		game.assets.load("data/levels_media/horus-obj.png", Texture.class);
		game.assets.load("data/levels_media/pharao-obj.png", Texture.class);
		game.assets.load("data/levels_media/pyramid-obj.png", Texture.class);
		game.assets.load("data/levels_media/mummy-obj.png", Texture.class);
		game.assets.load("data/levels_media/obj-001.png", Texture.class);
		game.assets.load("data/levels_media/obj-002.png", Texture.class);
		game.assets.load("data/levels_media/obj-003.png", Texture.class);
		game.assets.load("data/levels_media/obj-004.png", Texture.class);
		
		game.assets.load("data/dialogs/about.png", Texture.class);
		game.assets.load("data/dialogs/help1.png", Texture.class);
		game.assets.load("data/dialogs/help2.png", Texture.class);
		game.assets.load("data/dialogs/help3.png", Texture.class);
		game.assets.load("data/dialogs/key_blue.png", Texture.class);
		game.assets.load("data/dialogs/key_red.png", Texture.class);
		game.assets.load("data/dialogs/key_green.png", Texture.class);
		game.assets.load("data/dialogs/key_yellow.png", Texture.class);
		game.assets.load("data/dialogs/pyramids_1.png", Texture.class);
		game.assets.load("data/dialogs/pyramids_2.png", Texture.class);
		game.assets.load("data/dialogs/mummies.png", Texture.class);
		game.assets.load("data/dialogs/pharaos.png", Texture.class);
		game.assets.load("data/dialogs/horus.png", Texture.class);
		game.assets.load("data/dialogs/standard.png", Texture.class);
		game.assets.load("data/dialogs/standard_answer.png", Texture.class);
		
		game.assets.load("data/controls/flatdark_down.png", Texture.class);
		game.assets.load("data/controls/flatdark_left.png", Texture.class);
		game.assets.load("data/controls/flatdark_right.png", Texture.class);
		game.assets.load("data/controls/flatdark_select.png", Texture.class);
		game.assets.load("data/controls/flatdark_up.png", Texture.class);
		game.assets.load("data/controls/flatdark_down_half.png", Texture.class);
		game.assets.load("data/controls/flatdark_left_half.png", Texture.class);
		game.assets.load("data/controls/flatdark_right_half.png", Texture.class);
		game.assets.load("data/controls/flatdark_select_half.png", Texture.class);
		game.assets.load("data/controls/flatdark_up_half.png", Texture.class);
		game.assets.load("data/controls/flatdark_down_medium.png", Texture.class);
		game.assets.load("data/controls/flatdark_left_medium.png", Texture.class);
		game.assets.load("data/controls/flatdark_right_medium.png", Texture.class);
		game.assets.load("data/controls/flatdark_select_medium.png", Texture.class);
		game.assets.load("data/controls/flatdark_up_medium.png", Texture.class);
		game.assets.load("data/controls/flatDark15.png", Texture.class);
		game.assets.load("data/controls/flatDark32.png", Texture.class);
		
		game.assets.load("data/buttons/btn001.png", Texture.class);
		game.assets.load("data/buttons/btn002.png", Texture.class);
		game.assets.load("data/buttons/btn003.png", Texture.class);
		game.assets.load("data/buttons/btn004.png", Texture.class);
		game.assets.load("data/buttons/btn005.png", Texture.class);
		game.assets.load("data/buttons/btn006.png", Texture.class);
		game.assets.load("data/buttons/btn007.png", Texture.class);
		
		game.assets.load("data/backgrounds/main_menu.png", Texture.class);
		game.assets.load("data/backgrounds/main_menu_blurred.png", Texture.class);
		
		game.assets.load("data/audio/greek_trader1.mp3", Music.class);
		game.assets.load("data/audio/greek_trader2.mp3", Music.class);
		game.assets.load("data/audio/greek_trader3.mp3", Music.class);
		game.assets.load("data/audio/greek_politician1.mp3", Music.class);
		game.assets.load("data/audio/greek_politician2.mp3", Music.class);
		game.assets.load("data/audio/greek_politician3.mp3", Music.class);
		game.assets.load("data/audio/greek_officer1.mp3", Music.class);
		game.assets.load("data/audio/greek_officer2.mp3", Music.class);
		game.assets.load("data/audio/greek_officer3.mp3", Music.class);
		game.assets.load("data/audio/greek_marine1.mp3", Music.class);
		game.assets.load("data/audio/greek_marine2.mp3", Music.class);
		game.assets.load("data/audio/greek_marine3.mp3", Music.class);
		
		game.assets.load("data/audio/correct_answer.wav", Sound.class);
		game.assets.load("data/audio/wrong_answer.wav", Sound.class);
		
	}
	@Override
	public void render(float delta) {
		if(game.assets.update()){
			game.setScreen(new MainMenu(game));
		}
	
	    
		Gdx.gl.glClearColor(135, 135, 137, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		elapsedTime += Gdx.graphics.getDeltaTime();
		if(elapsedTime<32){
        sprites[0].draw(batch);
		}
		if(elapsedTime > 42.5){
			sprites[0].draw(batch);
	        batch.draw(animations[0].getKeyFrame(elapsedTime, true), sprites[2].getX()-80,sprites[2].getY()+5);
		}
        if(elapsedTime<6.4){
        sprites[2].draw(batch);
        batch.draw(animations[2].getKeyFrame(elapsedTime, true), 10+elapsedTime*50, 100);
        }
        else if(elapsedTime >6.4 && elapsedTime <8){
        	sprites[2].draw(batch);
        	batch.draw(animations[0].getKeyFrame(elapsedTime, true), sprites[2].getX()-80,sprites[2].getY()+5);
        	
        }
        else if(elapsedTime >8 && elapsedTime <20){
        	sprites[2].draw(batch);
        	sprites[6].draw(batch);
        	font.draw(batch, "Translation: I am heading towards Earth to bring some news", camera.position.x-200, camera.position.y-100);
        	batch.draw(animations[0].getKeyFrame(elapsedTime, true), sprites[2].getX()-80,sprites[2].getY()+5);
        	
        }
        else if(elapsedTime >20 && elapsedTime <25){
        	sprites[2].draw(batch);
        	batch.draw(animations[0].getKeyFrame(elapsedTime, true), sprites[2].getX()-80,sprites[2].getY()+5);
        	
        }
        else if(elapsedTime>25 && elapsedTime <32){
        	sprites[5].draw(batch);
        	sprites[4].draw(batch);
        	
        	 batch.draw(animations[1].getKeyFrame(elapsedTime, true), sprites[2].getX()+600-elapsedTime*20, sprites[2].getY()-20);
        }
        else if(elapsedTime > 32 && elapsedTime < 42.5){
            sprites[3].draw(batch);
            sprites[4].draw(batch);
            sprites[4].setPosition(camera.position.x+600-elapsedTime*18, camera.position.y-30);
            
            }
        Gdx.app.log("time"," "+	elapsedTime);
        
        empty.draw(batch, 40, 20, 560, 20);
		full.draw(batch, 40, 20, game.assets.getProgress()*560, 20);
		
		font.draw(batch, (int)(game.assets.getProgress()*100)+"%", H.cameraW/2-3, full.getTotalHeight()+13);
        
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		font = new BitmapFont();
		batch = new SpriteBatch();
		container = new Texture(Gdx.files.internal("data/loading/container.png"));
		filler = new Texture(Gdx.files.internal("data/loading/filler.png"));
		empty = new NinePatch(new TextureRegion(container,24,24),8,8,8,8);
		full = new NinePatch(new TextureRegion(filler,24,24),8,8,8,8);
		camera = new OrthographicCamera(H.cameraW, H.cameraH);
		camera.position.x = H.cameraW/2;
		camera.position.y = H.cameraH/2;
		camera.update();
		font.setColor(Color.BLACK);
		batch = new SpriteBatch();
		sprites[0].setScale(50f);
	    sprites[2].setPosition(camera.position.x+100, camera.position.y-70);
	    sprites[5].setPosition(camera.position.x-240, camera.position.y-sprites[4].getHeight()-50);
	    sprites[4].setPosition(camera.position.x-200, camera.position.y+20);
	    sprites[6].setPosition(camera.position.x+-150, camera.position.y+50);
        Gdx.app.log("position","position x :"+sprites[1].getX()+" position y :"+sprites[1].getY());
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
		font.dispose();
		batch.dispose();
		container.dispose();
		filler.dispose();
	}

}
