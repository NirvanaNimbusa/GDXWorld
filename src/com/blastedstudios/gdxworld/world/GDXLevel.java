package com.blastedstudios.gdxworld.world;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.blastedstudios.gdxworld.world.joint.GDXJoint;

public class GDXLevel implements Serializable{
	private static final long serialVersionUID = 1L;
	private final List<GDXPolygon> polygons = new ArrayList<GDXPolygon>();
	private String name = "";
	private Vector2 coordinates = new Vector2();
	/**
	 * Contains list of level names this level depends on before being playable
	 */
	private List<String> prerequisites = new ArrayList<String>();
	private List<GDXNPC> npcs = new ArrayList<GDXNPC>();
	private List<GDXPath> paths = new ArrayList<GDXPath>();
	private List<GDXJoint> joints = new ArrayList<GDXJoint>();

	public List<GDXPolygon> getPolygons() {
		return polygons;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector2 getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Vector2 coords) {
		this.coordinates = coords;
	}

	public List<String> getPrerequisites() {
		return prerequisites;
	}

	public void setPrerequisites(List<String> prerequisites) {
		this.prerequisites = prerequisites;
	}

	public void setPrerequisitesString(String prerequisites) {
		this.prerequisites = new ArrayList<String>();
		for(String prereq : prerequisites.split(","))
			if(!prereq.equals(""))
				this.prerequisites.add(prereq);
	}

	public String getPrerequisitesString() {
		if(prerequisites.isEmpty())
			return "";
		String prereqs = "";
		for(String prereq : prerequisites)
			prereqs += prereq + ",";
		return prereqs.substring(0,prereqs.length()-1);
	}

	public List<GDXNPC> getNpcs() {
		return npcs;
	}

	public void setNpcs(List<GDXNPC> npcs) {
		this.npcs = npcs;
	}

	public List<GDXPath> getPaths() {
		return paths;
	}

	public void setPaths(List<GDXPath> paths) {
		this.paths = paths;
	}

	public List<GDXJoint> getJoints() {
		return joints;
	}

	public void setJoints(List<GDXJoint> joints) {
		this.joints = joints;
	}

	public GDXPolygon getClosestPolygon(float x, float y) {
		GDXPolygon closest = null;
		float closestDistance = Float.MAX_VALUE;
		for(GDXPolygon level : polygons){
			for(Vector2 vertex : level.getVertices()){
				float distance = vertex.dst2(x, y);
				if(closest == null || closestDistance > distance){
					closest = level;
					closestDistance = distance;
				}
			}
		}
		return closest;
	}

	public GDXNPC getClosestNPC(float x, float y) {
		GDXNPC closest = null;
		float closestDistance = Float.MAX_VALUE;
		for(GDXNPC npc : getNpcs()){
			float distance = npc.getCoordinates().dst2(x, y);
			if(closest == null || closestDistance > distance){
				closest = npc;
				closestDistance = distance;
			}
		}
		return closest;
	}

	public GDXPath getClosestPath(float x, float y) {
		GDXPath closest = null;
		float closestDistance = Float.MAX_VALUE;
		for(GDXPath path : getPaths()){
			for(Vector2 node : path.getNodes()){
				float distance = node.dst2(x, y);
				if(closest == null || closestDistance > distance){
					closest = path;
					closestDistance = distance;
				}
			}
		}
		return closest;
	}

	public GDXJoint getClosestJoint(float x, float y, World world) {
		GDXJoint closest = null;
		float closestDistance = Float.MAX_VALUE;
		for(GDXJoint path : getJoints()){
			float distance = path.getDistance(x, y, world);
			if(closest == null || closestDistance > distance){
				closest = path;
				closestDistance = distance;
			}
		}
		return closest;
	}

	@Override public String toString(){
		return "[GDXLevel name:" + name + " coords:" + coordinates + "]";
	}
}