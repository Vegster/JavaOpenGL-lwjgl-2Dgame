package com.vegster.testrendering;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;

import com.vegster.entity.test.*;
import com.vegster.font.*;
import com.vegster.graphics.*;
import com.vegster.input.*;
import com.vegster.main.*;
import com.vegster.math.*;
import com.vegster.player.*;

public class Testrendering {

	private VertexArray texVertexArray;
	private VertexArray BackgroundVertexArray;
	private SpriteTexture background;
	
	private Text txt;

	private float startingPosX = 10.0f;
	private float startingPosY = 10.0f;
	
	private float ortographicSize = 10.0f;
	private float yScroll = -startingPosY + 1;
	private float xScroll = -startingPosX + 1;
	private float speed = 0.07f;
	
	
	//Background sprite variables
	private float backgroundSpriteSize = 10.0f;
	private int NumberOfSpritesBackgroundX = 30;
	private int NumberOfSpritesBackgroundY = 20;
	
	//position coordinates
	private float xCord = startingPosX; 
	private float yCord = startingPosY; 
	
	//Entity to World
	private Player morgan; 
	private Barrel berral;
	
	
	
	public Testrendering(){}
	
	public void endableShaders()
	{
	
		Matrix4f pr_matrix = Matrix4f.orthographic(-ortographicSize, ortographicSize,  -ortographicSize *  9.0f / 16.0f, ortographicSize *  9.0f / 16.0f, -1.0f, 1.0f);
		Shader.TEST.enable();
		Shader.TEST.setUnifromMat4f("pr_matrix", pr_matrix);
		Shader.TEST.setUniform1f("tex", 1);
		Shader.TEST.disable();
		
		Shader.BACKGROUND.enable();
		Shader.BACKGROUND.setUnifromMat4f("pr_matrix", pr_matrix);
		Shader.BACKGROUND.setUniform1f("tex", 1);
		Shader.BACKGROUND.disable();
		
		morgan = new Player(pr_matrix, ortographicSize);
		berral = new Barrel(pr_matrix, ortographicSize);
		
		/*
		float[] vertices = new float[]
				{
						-10.0f, -10.0f * 9.0f / 16.0f, 0.0f,
						-10.0f, 10.0f * 9.0f / 16.0f, 0.0f,
						0.0f, 10.0f * 9.0f / 16.0f, 0.0f,
						0.0f, -10.0f * 9.0f / 16.0f, 0.0f,
				};
		*/
		
		
		/*
		
		float[] vertices = new float[]
				{
						-1.0f, -0.5f, 0.0f,
						-1.0f, 0.5f , 0.0f,
						1.0f, 0.5f, 0.0f,
						1.0f, -0.5f , 0.0f,
				};
		
		byte[] indices = new byte[]
				{
						0,1,2,
						2,3,0
				};
		
		float[] tcs = new float[]
				{
						0, 1,
						0, 0,
						1, 0,
						1, 1
				};
		*/
		
		txt = new Text("",ortographicSize,60);
		background = new SpriteTexture("texture/random.png",NumberOfSpritesBackgroundX,NumberOfSpritesBackgroundY,ortographicSize,0,0,backgroundSpriteSize);
		
		texVertexArray = new VertexArray(txt.getVertices(), txt.getIndices(), txt.getTcs());
		BackgroundVertexArray = new VertexArray(background.getVertices(),background.getIndices(),background.getTcs());
	}
	
	
	public void render()
	{
		
		//FIX THIS
		/*
		try( MemoryStack stack = stackPush())
		{
			
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			
			glfwGetWindowSize(Main.window, pWidth, pHeight);
			
			width = pWidth.get(0);
			height = pHeight.get(0);
			
			this.endableShaders();
		}
		*/

		
		background.bind();
		Shader.BACKGROUND.enable();
			Shader.BACKGROUND.setUnifromMat4f("vm_matrix", Matrix4f.translte(new Vector3f(xScroll,yScroll,0.9f)));
		BackgroundVertexArray.render();
		Shader.BACKGROUND.disable();
		background.unbind();
		
		
		txt.bind();
		Shader.TEST.enable();		
			Shader.TEST.setUnifromMat4f("vm_matrix", Matrix4f.translte(new Vector3f(-1.0f,5.3f,0.9f)));
		
		texVertexArray.render();
		Shader.TEST.disable();
		txt.unbind();
			
		
		berral.render(xScroll,yScroll);
		morgan.render();
		
		
	}
	
	
	//0 --> UP
	//1 --> DOWN
	//2 --> LEFT
	//3 --> RIGHT
	//-1 --> No collision
	public boolean[] collision()
	{
		boolean collisionArry[] = new boolean[4];
		if( yScroll <= - NumberOfSpritesBackgroundY * backgroundSpriteSize / ortographicSize)
			collisionArry[0] = true;
		
		if( yScroll >= 0 + 0.25f)
			collisionArry[1] = true;
		
		if( xScroll >= (0) + 0.60f)
			collisionArry[2] = true; 		
		
		if(xScroll <= - (NumberOfSpritesBackgroundX * backgroundSpriteSize / ortographicSize) + 0.45f)
			collisionArry[3] = true;
		
		return collisionArry;
		
	}
	
	public void update()
	{
		
		boolean[] collision = collision();
		//boolean[] collision = berral.collision(yScroll, xScroll);
		
		if(Input.isKeyDown(GLFW_KEY_D) && !collision[3])
		{

			xScroll = xScroll - speed;
			xCord = xCord + speed; 
		}
		if(Input.isKeyDown(GLFW_KEY_W) && !collision[0])
		{

			yScroll = yScroll - speed;
			yCord = yCord + speed; 
		}
		if(Input.isKeyDown(GLFW_KEY_S) && !collision[1])
		{

			yScroll = yScroll + speed;
			yCord = yCord - speed; 
		}
		
		if(Input.isKeyDown(GLFW_KEY_A) && !collision[2])
		{
			
			xScroll = xScroll + speed;
			xCord = xCord - speed; 
		}
		
		if(Input.isKeyDown(GLFW_KEY_LEFT_SHIFT))
		{
			speed = 0.12f;
		}
		else
		{
			speed = 0.07f;
		}

			morgan.update();
		
			txt = new Text("Frames: " + Main.frames + " Updates: " + Main.updates + " x: " + (int) xCord + " y: " + (int) yCord ,ortographicSize,50);
			texVertexArray = new VertexArray(txt.getVertices(), txt.getIndices(), txt.getTcs());
			
		
	}	
	
}
