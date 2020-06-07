package com.vegster.graphics;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

import javax.imageio.ImageIO;

import com.vegster.util.BufferUtils;

public class SpriteTexture {
	
	private int width, height;
	private int texture;
	private int antX, antY;
	private float ortoSize, SpriteSize;
	private final int STANDARD_SPRITE_SIZE = 32;
	private int sumAntall;
	private int SpriteCordY, SpriteCordX = 0;
	
	private float[] vertices;
	
	private byte[] indices = new byte[]
			{
					0,1,2,
					2,3,0
					
			};
	
	private float[] tcs = new float[]
			{
					
					1,1,
					1,0,
					0,0,
					0,1
					
			};
	
	public SpriteTexture(String path, int antX, int antY, float ortoSize,int SpriteCordX, int SpriteCordY, float SpriteSize)
	{
		this.antX = antX * STANDARD_SPRITE_SIZE;
		this.antY = antY * STANDARD_SPRITE_SIZE;
		this.sumAntall = (antX * antY);
		this.ortoSize = ortoSize;
		this.SpriteSize = SpriteSize;
		this.SpriteCordX = SpriteCordX;
		this.SpriteCordY = SpriteCordY;
		
		texture = load(path);
	}
	
	private int load(String path)
	{
 				int[] pixels = null;
		
		vertices = new float[]
				{
						(SpriteSize * (antX/STANDARD_SPRITE_SIZE) / ortoSize) , -(SpriteSize/ ortoSize), 0.0f,
						((SpriteSize) * (antX/STANDARD_SPRITE_SIZE) / ortoSize), (SpriteSize * (antY/STANDARD_SPRITE_SIZE) / ortoSize), 0.0f,
						-((SpriteSize) / ortoSize), (SpriteSize * (antY/STANDARD_SPRITE_SIZE) / ortoSize), 0.0f,
						-((SpriteSize) / ortoSize), - (SpriteSize  / ortoSize), 0.0f,
				};
		
		try {
			BufferedImage image = ImageIO.read(new FileInputStream(path));
			width = image.getWidth();
			height = image.getHeight(); 
			
			//width = sizeX;
			//height = sizeY;
			
			
			pixels = new int[(antX) * (antY)];
			
			int y = 0;
			
			for(int i = 0; i < antX/STANDARD_SPRITE_SIZE * antY/STANDARD_SPRITE_SIZE + 1; i++)
			{
				
				if(antX/STANDARD_SPRITE_SIZE * antY/STANDARD_SPRITE_SIZE + 1 == 2)
					i++;
				
				image.getRGB(
						
						SpriteCordX * STANDARD_SPRITE_SIZE,
						
						SpriteCordY * STANDARD_SPRITE_SIZE,
						
						STANDARD_SPRITE_SIZE, STANDARD_SPRITE_SIZE, pixels,
						
						Math.abs(STANDARD_SPRITE_SIZE * i + ((antX * antY)/(antY / STANDARD_SPRITE_SIZE) - antX) * y - STANDARD_SPRITE_SIZE)
						
						,STANDARD_SPRITE_SIZE * (sumAntall / (antY/STANDARD_SPRITE_SIZE))
						
						);
				
				
				if( i % (antX/STANDARD_SPRITE_SIZE) == 0 && (i != 0))
					y++;
				
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int[] data = new int[(antX) * (antY)];
		for(int i = 0; i < (antX) * (antY); i++)
		{
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);
			
			data[i] = a << 24 | b << 16 | g << 8 | r;
		}
		
		int tex = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, tex);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, antX, antY, 0, GL_RGBA, GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data));
		glBindTexture(GL_TEXTURE_2D, 0);
		return tex;
		
	}
	
	public float[] getVertices()
	{
		return vertices;
	}
	
	public byte[] getIndices()
	{
		return indices;
	}
	
	public float[] getTcs()
	{
		return tcs;
	}
	
	public int getWidht()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void bind()
	{
		glBindTexture(GL_TEXTURE_2D, texture);
	}
	
	public void unbind()
	{
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public int getID()
	{
		return texture;
	}
	
}