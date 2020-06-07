package com.vegster.entity.test;

import com.vegster.entity.*;
import com.vegster.graphics.*;
import com.vegster.math.*;

public class Barrel implements EntityInterface{

	private VertexArray vertexarray; 
	private SpriteTexture spritetexture;
	private Matrix4f pr_matrix;
	private float ortograpicSize;
	private float spirteSize = 5.0f;
	
	private float posX = 5.0f;
	private float posY = 3.0f;
	
	
	public Barrel(Matrix4f pr_matrix, float ortograpicSize)
	{
		this.pr_matrix = pr_matrix;
		this.ortograpicSize = ortograpicSize;
		enable(this.pr_matrix, this.ortograpicSize);
	}
	
	@Override
	public void enable(Matrix4f pr_matrix, float ortograpicSize) {
		Shader.BARREL.enable();
		Shader.BARREL.setUnifromMat4f("pr_matrix", pr_matrix);
		Shader.BARREL.setUniform1f("tex", 1);
		Shader.BARREL.disable();
		
		spritetexture = new SpriteTexture("texture/Inside_B.png", 1, 1, ortograpicSize, 15, 10, spirteSize);
		vertexarray = new VertexArray(spritetexture.getVertices(), spritetexture.getIndices(), spritetexture.getTcs());
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float x, float y) {
		
		spritetexture.bind();
		Shader.BARREL.enable();
			Shader.BARREL.setUnifromMat4f("vm_matrix", Matrix4f.translte(new Vector3f(posX + x, posY + y,0.0f)));	
			vertexarray.render();
		Shader.BARREL.disable();
		spritetexture.unbind();
	}

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}
	
	//0 --> UP
	//1 --> DOWN
	//2 --> LEFT
	//3 --> RIGHT
	//-1 --> No collision
	public boolean[] collision(float yScroll, float xScroll)
	{
		
		boolean[]  collision = new boolean[4];
		
		
		if( (xScroll <= - ((posX -  spirteSize / ortograpicSize) - 0.1f))
			&& (xScroll >= - ((posX -  spirteSize / ortograpicSize) + 1.1f)) &&
			(yScroll >= - posY - spirteSize / ortograpicSize - 0.0f) &&
			(yScroll <= - posY - spirteSize / ortograpicSize + 0.7f))
			collision[0] = true;
		
		if( (xScroll <= - ((posX -  spirteSize / ortograpicSize) - 0.1f))
			&& (xScroll >= - ((posX -  spirteSize / ortograpicSize) + 1.1f))&&
			(yScroll >= - posY - spirteSize / ortograpicSize - 0.3f) &&
			(yScroll <= - posY - spirteSize / ortograpicSize + 0.5f))
			collision[1] = true;
		
		
		if( (xScroll <=  -  (posX - spirteSize / ortograpicSize - 0.2f) && 
			(xScroll >= - ((posX -  spirteSize / ortograpicSize) + 1.2f))) && 
			(yScroll > -((posY + 0.25f) + spirteSize / ortograpicSize) && 
			(yScroll < -((posY - 2 * spirteSize/ortograpicSize + 0.90f))))) 
			collision[2] = true; 		
		
		if(	(xScroll <= - ((posX - spirteSize / ortograpicSize) - 0.25f) && 
			(xScroll >= - ((posX -  spirteSize / ortograpicSize) + 0.2f)) && 
			(yScroll > -((posY + 0.25f) + spirteSize / ortograpicSize) && 
			(yScroll < -((posY - 2 * spirteSize/ortograpicSize + 0.95f))))))
			collision[3] = true;
		
		return collision;
	}
	
	public float getPosX()
	{
		return posX;
	}
	
	public float getPosY()
	{
		return posY;
	}
	
	public float getWidth()
	{
		return 5.0f;
	}
	
	public float getHeight()
	{
		return 5.0f;
	}

}
