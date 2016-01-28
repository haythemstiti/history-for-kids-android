package tn.esprit.mp.history4kids;

import tn.esprit.mp.history4kids.utils.H;
import tn.esprit.mp.history4kids.utils.OrthogonalTiledMapRendererWithSprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class levelEgyptian implements Screen, GestureListener{

	private History4Kids game;
	private int currentDialog = 0;
	private boolean levelFinished = false, levelCompleted = false;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private int cameraH, cameraW;
	private Texture[] textures = new Texture[25];
	private Sprite[] sprites = new Sprite[25];
	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;
	private int levelW = 1000, levelH = 500;
	private Texture shader;
	private boolean showHorus = true, showPharao = true, showPyramid = true, showMummy = true, done = false;
	
	public levelEgyptian (History4Kids game)
	{
		//TODO SHOW INSTRUCTIONS AT BEGINING 
		//TODO ADD RETURN BUTTON
		
		this.game = game;
		
		cameraH = H.cameraH;
		cameraW = H.cameraW;
		
		if (H.currentLevel != 3) levelFinished = true;
		else levelFinished = false;
		
		textures[0] = game.assets.get("data/levels_media/horus.png", Texture.class);
		textures[1] = game.assets.get("data/levels_media/pharao.png", Texture.class);
		textures[2] = game.assets.get("data/levels_media/mummy.png", Texture.class);
		textures[3] = game.assets.get("data/levels_media/008.png", Texture.class);
		textures[4] = game.assets.get("data/levels_media/horus-obj.png", Texture.class);
		textures[5] = game.assets.get("data/levels_media/pharao-obj.png", Texture.class);
		textures[6] = game.assets.get("data/levels_media/mummy-obj.png", Texture.class);
		textures[7] = game.assets.get("data/levels_media/pyramid-obj.png", Texture.class);
		textures[8] = game.assets.get("data/levels_media/obj-001.png", Texture.class);
		textures[9] = game.assets.get("data/levels_media/obj-002.png", Texture.class);
		textures[10] = game.assets.get("data/levels_media/obj-003.png", Texture.class);
		textures[11] = game.assets.get("data/levels_media/obj-004.png", Texture.class);
		textures[12] = game.assets.get("data/dialogs/key_yellow.png", Texture.class);
		textures[13] = game.assets.get("data/buttons/btn004.png",Texture.class);
		textures[14] = game.assets.get("data/dialogs/pyramids_1.png",Texture.class);
		textures[15] = game.assets.get("data/dialogs/pyramids_2.png",Texture.class);
		textures[16] = game.assets.get("data/dialogs/horus.png",Texture.class);
		textures[17] = game.assets.get("data/dialogs/pharaos.png",Texture.class);
		textures[18] = game.assets.get("data/dialogs/mummies.png",Texture.class);
		textures[19] = game.assets.get("data/buttons/btn005.png",Texture.class);
		textures[20] = game.assets.get("data/buttons/btn006.png",Texture.class);
		textures[21] = game.assets.get("data/controls/flatDark15.png",Texture.class);
		textures[22] = game.assets.get("data/controls/flatDark32.png",Texture.class);
		textures[23] = game.assets.get("data/controls/flatDark15.png",Texture.class);
		textures[24] = game.assets.get("data/controls/flatDark32.png",Texture.class);
		
		sprites[0] = new Sprite(textures[0]);
		sprites[1] = new Sprite(textures[1]);
		sprites[2] = new Sprite(textures[2]);
		sprites[3] = new Sprite(textures[3]);
		sprites[4] = new Sprite(textures[4]);
		sprites[5] = new Sprite(textures[5]);
		sprites[6] = new Sprite(textures[6]);
		sprites[7] = new Sprite(textures[7]);
		sprites[8] = new Sprite(textures[8]);
		sprites[9] = new Sprite(textures[9]);
		sprites[10] = new Sprite(textures[10]);
		sprites[11] = new Sprite(textures[11]);
		sprites[12] = new Sprite(textures[12]);
		sprites[13] = new Sprite(textures[13]);
		sprites[14] = new Sprite(textures[14]);
		sprites[15] = new Sprite(textures[15]);
		sprites[16] = new Sprite(textures[16]);
		sprites[17] = new Sprite(textures[17]);
		sprites[18] = new Sprite(textures[18]);
		sprites[19] = new Sprite(textures[19]);
		sprites[20] = new Sprite(textures[20]);
		sprites[21] = new Sprite(textures[23]);
		sprites[22] = new Sprite(textures[24]);
		
		shader = game.assets.get("data/levels_media/shader.png", Texture.class);
	}
	
	@Override
	public void render(float delta) {
		
		if (!showHorus && !showMummy && !showPharao && !showPyramid && !done && currentDialog==0 && !levelFinished) 
		{
			levelCompleted = true;
		}
		Gdx.gl.glClearColor(208,244,247,1);
	    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    camera.update();
        tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
        batch.setProjectionMatrix(camera.combined);
        sprites[4].setX((cameraW/2)+camera.position.x-sprites[4].getWidth());
        sprites[4].setY((cameraH/2)+camera.position.y-sprites[4].getHeight());
        sprites[5].setX((cameraW/2)+camera.position.x-sprites[5].getWidth()-sprites[4].getWidth());
        sprites[5].setY((cameraH/2)+camera.position.y-sprites[5].getHeight());
        sprites[6].setX((cameraW/2)+camera.position.x-sprites[6].getWidth()-sprites[5].getWidth()-sprites[4].getWidth());
        sprites[6].setY((cameraH/2)+camera.position.y-sprites[6].getHeight());
        sprites[7].setX((cameraW/2)+camera.position.x-sprites[7].getWidth()-sprites[5].getWidth()-sprites[4].getWidth()-sprites[6].getWidth());
        sprites[7].setY((cameraH/2)+camera.position.y-sprites[7].getHeight());
        sprites[8].setX((cameraW/2)+camera.position.x-sprites[8].getWidth()-sprites[10].getWidth());
        sprites[8].setY((cameraH/2)+camera.position.y-sprites[8].getHeight());
        sprites[9].setX((cameraW/2)+camera.position.x-sprites[9].getWidth()-sprites[8].getWidth()-sprites[10].getWidth());
        sprites[9].setY((cameraH/2)+camera.position.y-sprites[9].getHeight());
        sprites[10].setX((cameraW/2)+camera.position.x-sprites[10].getWidth());
        sprites[10].setY((cameraH/2)+camera.position.y-sprites[10].getHeight());
        sprites[11].setX((cameraW/2)+camera.position.x-sprites[11].getWidth()-sprites[9].getWidth()-sprites[8].getWidth()-sprites[10].getWidth());
        sprites[11].setY((cameraH/2)+camera.position.y-sprites[11].getHeight());
        batch.begin();
        if (currentDialog != 0)
        {
        	Sprite shaderSprite = new Sprite(shader);
        	switch (currentDialog)
        	{
        		case 1:
            		shaderSprite.setScale(200f);
            		shaderSprite.setX( camera.position.x - (cameraW/2) );
            		shaderSprite.setY( camera.position.y - (cameraH/2) );
            		shaderSprite.draw(batch);
            		sprites[14].setX( camera.position.x - (sprites[14].getWidth()/2) );
            		sprites[14].setY( camera.position.y - (sprites[14].getHeight()/2) );
            		sprites[14].draw(batch);
            		sprites[13].setX( sprites[14].getX()+sprites[14].getWidth()-50 );
            		sprites[13].setY( sprites[14].getY()+sprites[14].getHeight()-50 );
            		sprites[13].draw(batch);
            		sprites[19].setX( sprites[14].getX()+sprites[14].getWidth()+75 );
            		sprites[19].setY( sprites[14].getY()+(sprites[14].getHeight()/2)-sprites[19].getHeight() );
            		sprites[19].draw(batch);
        			break;
        		case 2:
        			shaderSprite.setScale(200f);
            		shaderSprite.setX( camera.position.x - (cameraW/2) );
            		shaderSprite.setY( camera.position.y - (cameraH/2) );
            		shaderSprite.draw(batch);
            		sprites[15].setX( camera.position.x - (sprites[15].getWidth()/2) );
            		sprites[15].setY( camera.position.y - (sprites[15].getHeight()/2) );
            		sprites[15].draw(batch);
            		sprites[13].setX( sprites[15].getX()+sprites[15].getWidth()-50 );
            		sprites[13].setY( sprites[15].getY()+sprites[15].getHeight()-50 );
            		sprites[13].draw(batch);
            		sprites[20].setX( sprites[15].getX()-75 );
            		sprites[20].setY( sprites[15].getY()+(sprites[15].getHeight()/2)-sprites[20].getHeight() );
            		sprites[20].draw(batch);
        			break;
        		case 3:
        			shaderSprite.setScale(200f);
            		shaderSprite.setX( camera.position.x - (cameraW/2) );
            		shaderSprite.setY( camera.position.y - (cameraH/2) );
            		shaderSprite.draw(batch);
            		sprites[16].setX( camera.position.x - (sprites[16].getWidth()/2) );
            		sprites[16].setY( camera.position.y - (sprites[16].getHeight()/2) );
            		sprites[16].draw(batch);
            		sprites[13].setX( sprites[16].getX()+sprites[16].getWidth()-50 );
            		sprites[13].setY( sprites[16].getY()+sprites[16].getHeight()-50 );
            		sprites[13].draw(batch);
            		break;
        		case 4:
        			shaderSprite.setScale(200f);
            		shaderSprite.setX( camera.position.x - (cameraW/2) );
            		shaderSprite.setY( camera.position.y - (cameraH/2) );
            		shaderSprite.draw(batch);
            		sprites[17].setX( camera.position.x - (sprites[17].getWidth()/2) );
            		sprites[17].setY( camera.position.y - (sprites[17].getHeight()/2) );
            		sprites[17].draw(batch);
            		sprites[13].setX( sprites[17].getX()+sprites[17].getWidth()-50 );
            		sprites[13].setY( sprites[17].getY()+sprites[17].getHeight()-50 );
            		sprites[13].draw(batch);
            		break;
        		case 5:
        			shaderSprite.setScale(200f);
            		shaderSprite.setX( camera.position.x - (cameraW/2) );
            		shaderSprite.setY( camera.position.y - (cameraH/2) );
            		shaderSprite.draw(batch);
            		sprites[18].setX( camera.position.x - (sprites[18].getWidth()/2) );
            		sprites[18].setY( camera.position.y - (sprites[18].getHeight()/2) );
            		sprites[18].draw(batch);
            		sprites[13].setX( sprites[18].getX()+sprites[18].getWidth()-50 );
            		sprites[13].setY( sprites[18].getY()+sprites[18].getHeight()-50 );
            		sprites[13].draw(batch);
        			break;
        	}
        }
        else
        {
        	if (levelCompleted)
            {
            	if (!levelFinished)
            	{
            		Sprite shaderSprite = new Sprite(shader);
            		shaderSprite.setScale(200f);
            		shaderSprite.setX( camera.position.x - (cameraW/2) );
            		shaderSprite.setY( camera.position.y - (cameraH/2) );
            		shaderSprite.draw(batch);
            		sprites[12].setX( camera.position.x - (sprites[12].getWidth()/2) );
            		sprites[12].setY( camera.position.y - (sprites[12].getHeight()/2) );
            		sprites[12].draw(batch);
            		sprites[13].setX( sprites[12].getX()+sprites[12].getWidth()-50 );
            		sprites[13].setY( sprites[12].getY()+sprites[12].getHeight()-50 );
            		sprites[13].draw(batch);
            	}
            	else
            	{
            		levelCompleted = false;
            	}
            }
            else
            {
            	if (showHorus)
                {
                	sprites[0].draw(batch);
                	sprites[4].draw(batch);
                }
                else
                {
                	sprites[10].draw(batch);
                }
                if (showPharao)
                {
                	sprites[1].draw(batch);
                	sprites[5].draw(batch);
                }
                else
                {
                	sprites[8].draw(batch);
                }
                if (showMummy)
                {
                	sprites[2].draw(batch);
                	sprites[6].draw(batch);
                }
                else
                {
                	sprites[9].draw(batch);
                }
                if (showPyramid)
                {
                	sprites[7].draw(batch);
                	sprites[3].draw(batch);
                }
                else
                {
                	sprites[11].draw(batch);
                }
            }
        }
        sprites[3].setX(camera.position.x+5-(cameraW/2));
        sprites[3].setY(camera.position.y-5+(cameraH/2)-sprites[3].getWidth());
        sprites[3].draw(batch);
        sprites[22].setX(camera.position.x+10-(cameraW/2)+sprites[22].getWidth());
        sprites[22].setY(camera.position.y-5+(cameraH/2)-sprites[22].getHeight());
        sprites[22].draw(batch);
        sprites[21].setX(camera.position.x+5-(cameraW/2));
        sprites[21].setY(camera.position.y-5+(cameraH/2)-sprites[21].getHeight());
        sprites[21].draw(batch);
        batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		
		batch = new SpriteBatch();
		camera = new OrthographicCamera(cameraW,cameraH);
        camera.position.x = cameraW/2;
		camera.position.y = cameraH/2;
        camera.update();
        tiledMap = game.assets.get("data/levels_media/levelEgyptian.tmx", TiledMap.class);
        tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap);
        GestureDetector gd = new GestureDetector(this);
        Gdx.input.setInputProcessor(gd);
        sprites[0].setX(20);
        sprites[0].setY(140);
        sprites[1].setX(920);
        sprites[1].setY(250);
        sprites[2].setX(740);
        sprites[2].setY(390);
        sprites[3].setX(620);
        sprites[3].setY(20);
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
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		Vector3 v = new Vector3(x, y, 0);
		camera.unproject(v);
		if (sprites[21].getBoundingRectangle().contains(v.x,v.y))
			game.setScreen(new MainMenu(game));
		if (sprites[22].getBoundingRectangle().contains(v.x,v.y))
			game.setScreen(new Levels(game));
		if (levelCompleted && sprites[13].getBoundingRectangle().contains(v.x, v.y))
		{
			levelCompleted = false;
			currentDialog = 0;
			done = true;
			Preferences prefs = Gdx.app.getPreferences("My Preferences");
			if (H.currentLevel == 3)
			{
				prefs.putInteger("progressLevel", 4);
				prefs.putInteger("progressInLevel", 1);
			}
			prefs.flush();
			return true;
		}
		if (currentDialog != 0 && sprites[13].getBoundingRectangle().contains(v.x, v.y))
		{
			currentDialog = 0;
			return true;
		}
		if (currentDialog == 1  && sprites[19].getBoundingRectangle().contains(v.x, v.y))
		{
			currentDialog = 2;
			return true;
		}
		if (currentDialog == 2  && sprites[20].getBoundingRectangle().contains(v.x, v.y))
		{
			currentDialog = 1;
			return true;
		}
		if (sprites[0].getBoundingRectangle().contains(v.x,v.y))
		{
			showHorus = false;
			currentDialog = 3;
			return true;
		}
		if (!showHorus && currentDialog == 0 && !levelCompleted && sprites[10].getBoundingRectangle().contains(v.x,v.y))
		{
			currentDialog = 3;
			return true;
		}
		if (!showPharao && currentDialog == 0 && !levelCompleted && sprites[8].getBoundingRectangle().contains(v.x,v.y))
		{
			currentDialog = 4;
			return true;
		}
		if (!showPyramid && currentDialog == 0 && !levelCompleted && sprites[11].getBoundingRectangle().contains(v.x,v.y))
		{
			currentDialog = 1;
			return true;
		}
		if (!showMummy && currentDialog == 0 && !levelCompleted && sprites[9].getBoundingRectangle().contains(v.x,v.y))
		{
			currentDialog = 5;
			return true;
		}
		if (sprites[1].getBoundingRectangle().contains(v.x,v.y))
		{
			showPharao = false;
			currentDialog = 4;
			return true;
		}
		if (sprites[2].getBoundingRectangle().contains(v.x,v.y))
		{
			showMummy = false;
			currentDialog = 5;
			return true;
		}
		if (sprites[3].getBoundingRectangle().contains(v.x,v.y)) 
		{
			showPyramid = false;
			currentDialog = 1;
			return true;
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
		if (levelCompleted) return true;
		if ((camera.position.x-(cameraW/2)-deltaX > 0) && camera.position.x+(cameraW/2)-deltaX < levelW
				&& (camera.position.y-(cameraH/2)+deltaY > 0) && camera.position.y+(cameraH/2)+deltaY < levelH)
		{camera.translate(-deltaX,deltaY);
		camera.update();
		return true;}
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
