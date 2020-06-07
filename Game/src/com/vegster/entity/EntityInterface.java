package com.vegster.entity;

import com.vegster.math.*;

public interface EntityInterface {
	
	void enable(Matrix4f pr_matrix, float ortograpicSize);
	
	void update();
	
	void render();
	
	public boolean isSolid();

	void render(float x, float y);
}
