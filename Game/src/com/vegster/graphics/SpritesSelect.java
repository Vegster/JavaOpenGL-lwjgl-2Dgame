package com.vegster.graphics;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

import com.vegster.util.*;

public class SpritesSelect {

	
	private int width, height;
	private int texture;
	private int sizeX, sizeY;
	
	public SpritesSelect(String txt, int SingleSpriteSizeX, int SingleSpriteSizeY, int ant)
	{
		this.sizeX = SingleSpriteSizeX * ant;
		this.sizeY = SingleSpriteSizeY;
		texture = load("texture/fonts.png");
	}
	
	private int load(String path)
	{
		
		int[] pixels = null;
		
		try {
			BufferedImage image = ImageIO.read(new FileInputStream(path));
			width = image.getWidth();
			height = image.getHeight(); 
			
			System.out.println("width: " + sizeX + " height: "+ sizeY);
			pixels = new int[(sizeX) * sizeY];
			
			
			
			image.getRGB(0, 0, sizeX, sizeY, pixels, 0, sizeX);
			image.getRGB(0, 0, 32, 32, pixels, 64, sizeX);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int[] data = new int[sizeX * sizeY];
		for(int i = 0; i < sizeX * sizeY; i++)
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
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, sizeX, sizeY, 0, GL_RGBA, GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data));
		glBindTexture(GL_TEXTURE_2D, 0);
		return tex;
		
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
