package tn.esprit.mp.history4kids.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Quiz {
	private Texture backgroundTexture, questionTexture, option1Texture, option2Texture, option3Texture;
	public Sprite backgroudSprite, questionSprite, option1Sprite, option2Sprite, option3Sprite;
	private BitmapFont questionFont, option1Font, option2Font, option3Font;
	private String question, option1, option2, option3;
	private int correctOption;
	private SpriteBatch batch;
	
	public Quiz(SpriteBatch batch)
	{
		this.batch = batch;
	}
	public Sprite[] getSprites(){
		Sprite[] tmp = {option1Sprite, option2Sprite, option3Sprite};
		return tmp;
	}
	public Texture getBackgroundTexture() {
		return backgroundTexture;
	}

	public void setBackgroundTexture(Texture backgroundTexture) {
		this.backgroundTexture = backgroundTexture;
		this.backgroudSprite = new Sprite(backgroundTexture);
	}

	public Texture getQuestionTexture() {
		return questionTexture;
	}

	public void setQuestionTexture(Texture questionTexture) {
		this.questionTexture = questionTexture;
		this.questionSprite = new Sprite(questionTexture);
	}

	public Texture getOption1Texture() {
		return option1Texture;
	}

	public void setOption1Texture(Texture option1Texture) {
		this.option1Texture = option1Texture;
		this.option1Sprite = new Sprite(option1Texture);
	}

	public Texture getOption2Texture() {
		return option2Texture;
	}

	public void setOption2Texture(Texture option2Texture) {
		this.option2Texture = option2Texture;
		this.option2Sprite = new Sprite(option2Texture);
	}

	public Texture getOption3Texture() {
		return option3Texture;
	}

	public void setOption3Texture(Texture option3Texture) {
		this.option3Texture = option3Texture;
		this.option3Sprite = new Sprite(option3Texture);
	}

	public BitmapFont getQuestionFont() {
		return questionFont;
	}

	public void setQuestionFont(BitmapFont questionFont) {
		this.questionFont = questionFont;
	}

	public BitmapFont getOption1Font() {
		return option1Font;
	}

	public void setOption1Font(BitmapFont option1Font) {
		this.option1Font = option1Font;
	}

	public BitmapFont getOption2Font() {
		return option2Font;
	}

	public void setOption2Font(BitmapFont option2Font) {
		this.option2Font = option2Font;
	}

	public BitmapFont getOption3Font() {
		return option3Font;
	}

	public void setOption3Font(BitmapFont option3Font) {
		this.option3Font = option3Font;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}
	
	public void setAllFonts(BitmapFont font)
	{
		this.option1Font = font;
		this.option2Font = font;
		this.option3Font = font;
		this.questionFont = font;
	}
	
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public int getCorrectOption() {
		return correctOption;
	}

	public void setCorrectOption(int correctOption) {
		this.correctOption = correctOption;
	}
	public void setAllOptionsBackgrounds(Texture texture){
		setOption1Texture(texture);
		setOption2Texture(texture);
		setOption3Texture(texture);
	}
	public void draw()
	{
		backgroudSprite.draw(batch);
		questionSprite.draw(batch);
		questionFont.draw(batch, question, questionSprite.getX()+20, questionSprite.getY()+questionSprite.getHeight()/2);
		option1Sprite.draw(batch);
		questionFont.draw(batch, option1, option1Sprite.getX()+10, option1Sprite.getY()+option1Sprite.getHeight()/2);
		option2Sprite.draw(batch);
		questionFont.draw(batch, option2, option2Sprite.getX()+10, option2Sprite.getY()+option2Sprite.getHeight()/2);
		option3Sprite.draw(batch);
		questionFont.draw(batch, option3, option3Sprite.getX()+10, option3Sprite.getY()+option3Sprite.getHeight()/2);
	}
	
	public Rectangle[] getRightCordinates ()
	{
		Rectangle[] result = new Rectangle[4];
		switch (correctOption) {
		case 1:
			result[0] = option1Sprite.getBoundingRectangle();
			break;
		case 2:
			result[0] = option2Sprite.getBoundingRectangle();
			break;
		case 3:
			result[0] = option3Sprite.getBoundingRectangle();
			break;
		}
		return result;
	}
	public void dispose()
	{
		this.batch.dispose();
		this.backgroundTexture.dispose();
		this.option1Font.dispose();
		this.option1Texture.dispose();
		this.option2Font.dispose();
		this.option2Texture.dispose();
		this.option3Font.dispose();
		this.option3Texture.dispose();
		this.questionFont.dispose();
		this.questionTexture.dispose();
	}
}
