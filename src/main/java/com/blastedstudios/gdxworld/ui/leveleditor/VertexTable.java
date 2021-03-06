package com.blastedstudios.gdxworld.ui.leveleditor;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class VertexTable extends Table {
	private final TextField coordXLabel, coordYLabel;

	public VertexTable(final Vector2 vertex, final Skin skin){
		this(vertex, skin, null, 150);
	}
	
	public VertexTable(final Vector2 vertex, final Skin skin, 
			final VertexRemoveListener listener){
		this(vertex, skin, listener, 150);
	}
	/**
	 * @param vertex current vertex to set text field data
	 * @param listener used only in a list to delete vertex. if null, does not
	 * add delete button
	 */
	public VertexTable(final Vector2 vertex, final Skin skin, 
			final VertexRemoveListener listener, int width){
		final Button deleteButton = new TextButton("Delete", skin);
		coordXLabel = new TextField(vertex.x+"", skin);
		coordYLabel = new TextField(vertex.y+"", skin);
		deleteButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				listener.remove(vertex);
			}
		});
		add(coordXLabel).width(width);
		add(coordYLabel).width(width);
		if(listener != null)
			add(deleteButton);
	}
	
	public boolean isCursorActive(){
		return coordXLabel.getCursorPosition() != 0 || coordYLabel.getCursorPosition() != 0;
	}
	
	public Vector2 getVertex(){
		return new Vector2(convert(coordXLabel), convert(coordYLabel));
	}
	
	public void setVertex(float x, float y){
		coordXLabel.setText(x+"");
		coordYLabel.setText(y+"");
		coordXLabel.setCursorPosition(0);
		coordYLabel.setCursorPosition(0);
	}
	
	public interface VertexRemoveListener{
		public void remove(Vector2 vertex);
	}
	
	/**
	 * Center the camera on the current coordinates
	 */
	public void center(Camera camera){
		camera.position.set(convert(coordXLabel), convert(coordYLabel), camera.position.z);
	}
	
	private float convert(TextField field){
		if(field.getText().equals(""))
			return 0f;
		return Float.parseFloat(field.getText());
	}
	
	public void setDisabled(boolean disabled){
		coordXLabel.setDisabled(disabled);
		coordYLabel.setDisabled(disabled);
	}
}
