package net.baviereteam.minecraft.mapwriterserver.domain;

import java.util.List;
import java.util.UUID;

import javax.persistence.*;

@Entity
public class Server {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	// Name of the server (a slug provided to the webservice to access some server)
	@Column(nullable = false, unique = true)
	private String name;
	
	// A generated access key to give the users to avoid everyone messing with our points
	@Column(nullable = false)
	private String key;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="server")
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
	
	public void generateKey() {
		this.key = UUID.randomUUID().toString();
	}
	
	@Override
	public String toString() {
		return "Server (Name: " + this.name + ", Key: " + this.key + ")";
	}
	
}