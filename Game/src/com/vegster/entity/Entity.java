package com.vegster.entity;

import com.vegster.math.*;

public abstract class Entity {
	
	
	
	public void enable(Matrix4f pr_matrix, float ortograpicSize){
		
	}
	
	public void update(){}
	
	public void render(){}
	
	public boolean isSolid()
	{
		return false;	
	}
}
