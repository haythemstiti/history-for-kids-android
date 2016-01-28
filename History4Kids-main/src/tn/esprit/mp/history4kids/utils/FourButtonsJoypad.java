package tn.esprit.mp.history4kids.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class FourButtonsJoypad {
	
	private float x =0, y=0, margin=10;
	private Sprite[] sprites = new Sprite[4];
	
	public FourButtonsJoypad(float x, float y){
		this.x = x;
		this.y = y;
	}
	public FourButtonsJoypad(float x, float y, Sprite[] sprites){
		this.x = x;
		this.y = y;
		this.sprites = sprites;
		refreshSprites();
	}
	public float getMargin() {
		return margin;
	}
	public void setMargin(float margin) {
		this.margin = margin;
		refreshSprites();
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
		refreshSprites();
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
		refreshSprites();
	}
	public Sprite getFirstButtonSprite()
	{
		return sprites[0];
	}
	public Sprite getSecondButtonSprite()
	{
		return sprites[1];
	}
	public Sprite getThirdButtonSprite()
	{
		return sprites[2];
	}
	public Sprite getFourthButtonSprite()
	{
		return sprites[3];
	}
	public Sprite[] getSprites()
	{
		return sprites;
	}
	public void setSprites(Sprite[] sprites)
	{
		this.sprites = sprites;
		refreshSprites();
	}
	public void setFirstButtonSprite(Sprite sprite)
	{
		this.sprites[0] = sprite;
		refreshSprites();
	}
	public void setSecondButtonSprite(Sprite sprite)
	{
		this.sprites[1] = sprite;
		refreshSprites();
	}
	public void setThirdButtonSprite(Sprite sprite)
	{
		this.sprites[2] = sprite;
		refreshSprites();
	}
	public void setFourthButtonSprite(Sprite sprite)
	{
		this.sprites[3] = sprite;
		refreshSprites();
	}
	public void scaleSprites(float coef)
	{
		for (Sprite s: sprites)
		{
			s.setScale(coef);
		}
		refreshSprites();
	}
	public void scaleSprite(float coef, int index)
	{
		try
		{
			sprites[index].scale(coef);
			refreshSprites();
		}
		catch(Exception e){}
	}
	private void refreshSprites()
	{
		int i = 0;
		for (Sprite s : sprites)
		{
			switch (i)
			{
				case 0:
					s.setX(x);
					s.setY(y);
					break;
				case 1:
					s.setX(x+margin+(2*(getFirstButtonSprite().getWidth())));
					s.setY(y);
					break;
				case 2:
					s.setX(x+(margin/2)+(getFirstButtonSprite().getWidth()));
					s.setY(y+(getFourthButtonSprite().getHeight())+(margin/2));
					break;
				case 3:
					s.setX(x+(margin/2)+(getFirstButtonSprite().getWidth()));
					s.setY(y);
					break;
			}
			i++;
		}
	}
}
