package com.vegster.font;

import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

import com.vegster.util.*;

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

public class Text {
	
	private String txt;
	private float size;
	private int Text;
	private float ortoSize;
	
	private float[] vertices;
	
	private byte[] indices = new byte[]
			{
					0,1,2,
					2,3,0
			};
	
	private float[] tcs = new float[]
			{
					0, 1,
					0, 0,
					1, 0,
					1, 1
			};
	
	
	private final int STANDARD_FONT_SIZE = 16;
	private int sizeX, sizeY = STANDARD_FONT_SIZE;
	
	public Text(String txt, float ortoSize, float size)
	{
		
		this.txt = txt;
		this.size = size;
		this.sizeX = STANDARD_FONT_SIZE * txt.length();
		this.ortoSize = ortoSize;
		Text = load("texture/fonts.png");
		
	}
	
	private String wordIndex = 
		
				"ABCDEFGHIJKLMNOPQRSTUVWXYZÆØÅ"+
				"abcdefghijklmnopqrstuvwxyzæøå"+
				"1234567890.,_<>+-*/!?=(): ";
				
		
	

	private int load(String path)
	{
		int[] pixels = null;
		vertices = new float[]
				{
						-(size * 0.1f * (0.3f * txt.length()) / ortoSize) , -(size * 0.03f / ortoSize), 0.0f,
						-(size * 0.1f * (0.3f * txt.length()) / ortoSize), (size * 0.03f / ortoSize), 0.0f,
						(size * 0.1f  * (0.3f * txt.length())/ ortoSize), (size * 0.03f / ortoSize), 0.0f,
						(size * 0.1f * (0.3f * txt.length())/ ortoSize), -(size * 0.03f / ortoSize), 0.0f,
				};
		
		
		try{
			BufferedImage image = ImageIO.read(new FileInputStream(path));
			
			
			pixels = new int[sizeY * sizeX];
			
			
			for(int i = 0; i < txt.length(); i++)
			{
				int y = 0;
				char currntChar = txt.charAt(i);
				int index = wordIndex.indexOf(currntChar);
				
				while(index > 28)
				{
					y++;
					index = index - 29;
				}
				image.getRGB(STANDARD_FONT_SIZE * index, STANDARD_FONT_SIZE * y, STANDARD_FONT_SIZE, STANDARD_FONT_SIZE, pixels, STANDARD_FONT_SIZE * i , sizeX); // <---
			}
			
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
		int[] data = new int[sizeY * sizeX];
		for(int i = 0; i < sizeY * sizeX; i++)
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
	
	public void bind()
	{
		glBindTexture(GL_TEXTURE_2D, Text);
	}
	
	public void unbind()
	{
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public int getID()
	{
		return Text;
	}
	
	

}
