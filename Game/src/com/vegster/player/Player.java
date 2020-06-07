package com.vegster.player;

import com.vegster.graphics.*;
import com.vegster.input.*;
import com.vegster.math.*;
import static org.lwjgl.glfw.GLFW.*;

public class Player {

	private VertexArray VertexMorgan;
	private SpriteTexture[] TextureMorgan;
	
	// 0 --> up
	// 1 --> down
	// 2 --> left 
	// 3 --> right
	private int texNumb = 0;
	private int AnimationNumber = 0;
	private int AniSprite = 0;
	
	public Player(Matrix4f pr_matrix, float ortograpicSize)
	{
		
		Shader.MORGAN.enable();
		Shader.MORGAN.setUnifromMat4f("pr_matrix", pr_matrix);
		Shader.MORGAN.setUniform1f("tex", 1);
		Shader.MORGAN.disable();
		
		
		/*
		 * 0<- DownMiddel 	4<- DownLeft 	8<- DownRight
		 * 1<- UpMiddel		5<- UpLeft		9<- UpRight
		 * 2<- LeftMiddel	6<- LeftLeft	10<- LeftRight
		 * 3<- RightMiddel 	7<- RightLeft 	11<- RightRight
		 */
		
		TextureMorgan = new SpriteTexture[]{
				
				new SpriteTexture("texture/morgan.png", 1, 1, ortograpicSize, 1, 0, 5.0f),
				new SpriteTexture("texture/morgan.png", 1, 1, ortograpicSize, 1, 3, 5.0f),
				new SpriteTexture("texture/morgan.png", 1, 1, ortograpicSize, 1, 1, 5.0f),
				new SpriteTexture("texture/morgan.png", 1, 1, ortograpicSize, 1, 2, 5.0f),
				
				new SpriteTexture("texture/morgan.png", 1, 1, ortograpicSize, 0, 0, 5.0f),
				new SpriteTexture("texture/morgan.png", 1, 1, ortograpicSize, 0, 3, 5.0f),
				new SpriteTexture("texture/morgan.png", 1, 1, ortograpicSize, 0, 1, 5.0f),
				new SpriteTexture("texture/morgan.png", 1, 1, ortograpicSize, 0, 2, 5.0f),
				
				new SpriteTexture("texture/morgan.png", 1, 1, ortograpicSize, 2, 0, 5.0f),
				new SpriteTexture("texture/morgan.png", 1, 1, ortograpicSize, 2, 3, 5.0f),
				new SpriteTexture("texture/morgan.png", 1, 1, ortograpicSize, 2, 1, 5.0f),
				new SpriteTexture("texture/morgan.png", 1, 1, ortograpicSize, 2, 2, 5.0f),
		};
		
		
		VertexMorgan = new VertexArray(TextureMorgan[0].getVertices(), TextureMorgan[0].getIndices(), TextureMorgan[0].getTcs());
		
		
	}
	
	
	
	public void update()
	{
		
		
		
		
		if(AnimationNumber >= 7 && AnimationNumber < 14)
		{
			AniSprite  =  4;
		}
		else
		if(AnimationNumber >= 14 && AnimationNumber < 21)
		{
			AniSprite =  0;
		}
		else
		if(AnimationNumber >= 21 && AnimationNumber < 28)
		{
			AniSprite =  8;
		}
		else if(AnimationNumber >= 28)
		{
			AniSprite =  0;
			AnimationNumber = 0;
		}
		
		
		if(Input.isKeyDown(GLFW_KEY_S))
		{
			texNumb = 0 + AniSprite;
		}
		
		if(Input.isKeyDown(GLFW_KEY_W))
		{
			texNumb = 1 + AniSprite;
		}
				
		if(Input.isKeyDown(GLFW_KEY_A))
		{
			texNumb = 2 + AniSprite;
		}
		
		if(Input.isKeyDown(GLFW_KEY_D))
		{
			texNumb = 3 + AniSprite;		
		}
		
		if(Input.isKeyDown(GLFW_KEY_S) || Input.isKeyDown(GLFW_KEY_W) || Input.isKeyDown(GLFW_KEY_A) || Input.isKeyDown(GLFW_KEY_D))
		{
			AnimationNumber++;			
		}else{
			AnimationNumber = 0;
			AniSprite = 0;
		}
		
	}
	
	public void render()
	{
	
		TextureMorgan[texNumb].bind();
		Shader.MORGAN.enable();
				Shader.MORGAN.setUnifromMat4f("vm_matrix", Matrix4f.translte(new Vector3f(0.0f,0.0f,1.0f)));
		VertexMorgan.render();
		Shader.MORGAN.disable();
		TextureMorgan[texNumb].unbind();
		
	}
	
}
