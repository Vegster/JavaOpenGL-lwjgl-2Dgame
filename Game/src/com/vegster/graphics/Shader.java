package com.vegster.graphics;

import static org.lwjgl.opengl.GL20.*;

import com.vegster.math.*;
import com.vegster.util.*;

public class Shader {
	
	public static final int VERTEX_ATTRIB = 0;
	public static final int TEXTURE_ATTRIB = 1;
	
	private int ID;
	
	//SHADERS
	public static Shader BASIC;
	public static Shader TEST;
	public static Shader BACKGROUND;
	public static Shader MORGAN;
	public static Shader BARREL;
	
	private Shader(String vertex, String fragment)
	{
		ID = ShaderUtils.load(vertex, fragment);
	}
	
	public static void loadAll()
	{
		BASIC = new Shader("shaders/Shaders.vert", "shaders/Shaders.frag");
		BACKGROUND = new Shader("shaders/Background.vert", "shaders/Background.frag");
		TEST = new Shader("shaders/test.vert", "shaders/test.frag");
		MORGAN = new Shader("shaders/test.vert", "shaders/test.frag");
		BARREL = new Shader("shaders/test.vert", "shaders/test.frag");
	}
	
	public int getUniform(String name)
	{
		return glGetUniformLocation(ID, name);
	}
	
	public void setUniform1i(String name, int value)
	{
		glUniform1i(getUniform(name), value);
	}
	
	public void setUniform1f(String name, int value)
	{
		glUniform1f(getUniform(name), value);
	}
	
	public void setUniform2f(String name, int x, int y)
	{
		glUniform2f(getUniform(name), x,y);
	}
	
	public void setUniform3f(String name, Vector3f vector)
	{
		glUniform3f(getUniform(name), vector.x, vector.y, vector.z);
	}
	
	public void setUnifromMat4f(String name, Matrix4f matrix)
	{
		glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
	}
	
	public void enable()
	{
		glUseProgram(ID);
	}
	
	public void disable()
	{
		glUseProgram(0);
	}
}
