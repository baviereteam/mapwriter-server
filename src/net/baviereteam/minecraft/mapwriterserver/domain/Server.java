package net.baviereteam.minecraft.mapwriterserver.domain;

import java.util.List;

import javax.persistence.*;

@Entity
public class Server {
	@Id
    @GeneratedValue
	private int id;
	
	// Name of the server (a slug provided to the webservice to access some server)
	@Column(nullable = false)
	private String name;
	
	// A generated access key to give the users to avoid everyone messing with our points
	@Column(nullable = false)
	private String key;
	
	private List<Marker> markers;
	
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public List<Marker> getMarkers() {
		return markers;
	}
	public void setMarkers(List<Marker> markers) {
		this.markers = markers;
	}
	
}