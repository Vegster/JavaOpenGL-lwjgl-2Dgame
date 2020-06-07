package com.vegster.main;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.*;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import com.vegster.graphics.*;
import com.vegster.input.*;
import com.vegster.testrendering.*;

public class Main implements Runnable {

	public static long window;
	public static int widht = 1280; 
	public static int height = 740;
	
	public static int frames = 0;
	public static int updates = 0;
	
	private Thread thread;
	private boolean running = false;
	
	private static  Testrendering rendtest;
	 
	
	public void start()
	{
		running = true;
		thread = new Thread(this,"Game");
		thread.start();
		
	}
	
	
	public void run()
	{
		init();
		
		long lastTime = System.nanoTime();
		double delta = 0.0;
		double ns =  1000000000.0 / 60.0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		
		while(running)
		{
			
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if(delta >= 1.0)
			{
				update();
				updates++;
				delta--;
			}
			
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				Main.frames = frames;
				Main.updates = updates;
				updates = 0;
				frames = 0;
				
			}
			
			if(glfwWindowShouldClose(window))
				running = false;
		}
		
		
	}
	
	
	public void init()
	{
		
		System.out.println("Hello LWGL " + Version.getVersion());
		
		GLFWErrorCallback.createPrint(System.err).set();
		
		if(!glfwInit())
			throw new IllegalStateException("Unible to initialize GLFW");
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		
		window = glfwCreateWindow(widht, height, "Hello Word", NULL, NULL);
		
		try( MemoryStack stack = stackPush())
		{
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			
			
			
			glfwGetWindowSize(window, pWidth, pHeight);
			
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			
			glfwSetWindowPos(
					window,
					(vidmode.width() - pWidth.get(0)) / 2,
					(vidmode.height() - pHeight.get(0)) / 2
				);
		}
		glfwSetKeyCallback(window, new Input());
		
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);
		
		
		GL.createCapabilities();
		
		
		glClearColor(0.0f, 0.5f, 0.5f, 0.0f);
		
		int vao = glGenVertexArrays();
		glBindVertexArray(vao);
		
		
		glActiveTexture(GL_TEXTURE0);
		Shader.loadAll();
		
		
		//testCode
		rendtest = new Testrendering();
		rendtest.endableShaders();
		
		
	
	}
	
	public void update()
	{
		glfwPollEvents();
		rendtest.update();
		if(Input.isKeyDown(GLFW_KEY_ESCAPE))
			running = false;
	}
	
	private void render()
	{
	
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			rendtest.render();
			glfwSwapBuffers(window);
		
	}
	
	public static void SizeTest()
	{
		return;
	}
	
	public static void main(String[] args) 
	{
		new Main().start();
		
	}

}
