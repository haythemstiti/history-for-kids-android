package tn.esprit.mp.history4kids.utils;

import com.badlogic.gdx.graphics.g2d.Animation;

public class Player {
	
	private float x=0, y=0;
	private Animation animation;
	private float speed = 1;
	public boolean movingRight = false, movingLeft = false, movingUp = false, movingDown = false;
	public Player() {
		
	}
	
	public Player(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Player(float x, float y, Animation animation) {
		this.x = x;
		this.y = y;
		this.animation = animation;
	}

	public void moving (boolean state)
	{
		movingDown = state;
		movingLeft = state;
		movingRight = state;
		movingUp = state;
	}
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}
	
	@Override
	public String toString()
	{
		return "x: "+x+",y: "+y;
	}
	
}
