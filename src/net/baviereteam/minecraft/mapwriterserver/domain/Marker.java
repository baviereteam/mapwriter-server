package net.baviereteam.minecraft.mapwriterserver.domain;

import javax.persistence.*;

@Entity
public class Marker {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
    @Column(nullable = true, name = "markerGroup")
	private String group;

    @Column(nullable = false)
	private String name;
    
    @Column(nullable = false)
	private int x;
	
    @Column(nullable = false)
    private int y;

    @Column(nullable = false)
	private int z;

    @Column(nullable = false)
	private int dimension;

    // The colour we will use to represent this marker in MapWriter
    @Column(nullable = false)
	private int color;

    // The picture we will use to represent this marker in Minecraft Overviewer
    @Column(nullable = true)
	private String picture;
	
    // The server this marker is in
    @ManyToOne
    @JoinColumn
    private Server server;
    
	public long getId() {
		return id;
	}
	
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
	
	public int getDimension() {
		return dimension;
	}
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
	
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	protected Marker() {
	}
}
