package net.baviereteam.minecraft.mapwriterserver.controller;

import java.util.logging.Logger;

import net.baviereteam.minecraft.mapwriterserver.domain.Marker;
import net.baviereteam.minecraft.mapwriterserver.domain.Server;
import net.baviereteam.minecraft.mapwriterserver.json.OperationResult;
import net.baviereteam.minecraft.mapwriterserver.repository.MarkerRepository;
import net.baviereteam.minecraft.mapwriterserver.repository.ServerRepository;
import net.baviereteam.minecraft.mapwriterserver.service.MasterKeyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
@RequestMapping("/markers")
public class MarkerController {

	// Markers are classified by servers and servers are the entry point so we still use this one
	@Autowired
	private ServerRepository serverRepository;
	
	@Autowired
	private MarkerRepository markerRepository;
	
	@RequestMapping(value="/{serverId}/list", produces="application/json")
	@ResponseBody
    public OperationResult list(@PathVariable int serverId, @RequestParam String userKey) {
		OperationResult result = new OperationResult();
		
		// We need to check the server key and thus need the server first.
		Server server = serverRepository.findOne(serverId);
		if(server == null) {
			result.setErrorMessage("Server not found.");
		}
		
		else {
			// Are we allowed to access the data ? (have the Server key or the Master key)
			if(MasterKeyService.getInstance().isValid(userKey) || server.isKeyValid(userKey)) {
				result.setResult(true);
				result.setResultingObject(server.getMarkers());
			}
			
			else {
				result.setErrorMessage("Authentication error.");
			}
		}
		
        return result;
    }

	@RequestMapping(value = "/get/{markerId}", produces = "application/json")
	@ResponseBody
	public OperationResult list(@PathVariable long markerId, @RequestParam String userKey) {
		OperationResult result = new OperationResult();

		// We need the marker to find the server (used to check the key)
		Marker marker = markerRepository.findOne(markerId);
		if (marker == null) {
			result.setErrorMessage("Marker not found.");
		}

		else {
			// Are we allowed to access the data ? (have the Server key or the Master key)
			if (MasterKeyService.getInstance().isValid(userKey) || marker.getServer().isKeyValid(userKey)) {
				result.setResult(true);
				result.setResultingObject(marker);
			}

			else {
				result.setErrorMessage("Authentication error.");
			}
		}

		return result;
	}

	@RequestMapping(value="/{serverId}/add", produces="application/json")
	@ResponseBody
	public OperationResult add(
			@PathVariable int serverId, 
			@RequestParam int color,
			@RequestParam int dimension,
			@RequestParam String group,
			@RequestParam String name,
			@RequestParam int x,
			@RequestParam int y,
			@RequestParam int z,
			@RequestParam(required = false) String picture,
			@RequestParam String userKey) {
		
		OperationResult result = new OperationResult();
		
		// We need to check the server key and thus need the server first.
		Server server = serverRepository.findOne(serverId);
		if(server == null) {
			result.setErrorMessage("Server not found.");
		}
		
		else {
			
			// Are we allowed to access the data ? (have the Server key or the Master key)
			if(!MasterKeyService.getInstance().isValid(userKey) && !server.isKeyValid(userKey)) {
				result.setErrorMessage("Authentication error.");
			}
			
			else {
				
				// Create and add the marker
				Marker marker = new Marker();
				marker.setColor(color);
				marker.setDimension(dimension);
				marker.setGroup(group);
				marker.setName(name);
				marker.setX(x);
				marker.setY(y);
				marker.setZ(z);
				
				if(picture != null) {
					marker.setPicture(picture);
				}
				
				marker.setServer(server);
				
				// Save, or answer with the errors
				try {
					markerRepository.save(marker);
					result.setResultingObject(marker);
					result.setResult(true);
				}
		
				catch(DataIntegrityViolationException e) {
					result.setErrorMessage(e.getRootCause().getMessage());
				}
				
				catch(Exception e) {
					Logger.getLogger(this.getClass().getName())
						.severe("Object to save was: " + server.toString());
					result.setErrorMessage(e.getMessage());
				}
			}
		}
		
        return result;
    }

	@RequestMapping(value = "/edit/{markerId}", produces = "application/json")
	@ResponseBody
	public OperationResult edit(@PathVariable long markerId,
			@RequestParam int color, @RequestParam int dimension,
			@RequestParam String group, @RequestParam String name,
			@RequestParam int x, @RequestParam int y, @RequestParam int z,
			@RequestParam(required = false) String picture,
			@RequestParam String userKey) {

		OperationResult result = new OperationResult();

		// We need to check the server key and thus need the marker and server
		// first.
		Marker marker = markerRepository.findOne(markerId);
		if (marker == null) {
			result.setErrorMessage("Marker not found.");
		}

		else {

			// Are we allowed to access the data ? (have the Server key or the Master key)
			if (!MasterKeyService.getInstance().isValid(userKey)
					&& !marker.getServer().isKeyValid(userKey)) {
				result.setErrorMessage("Authentication error.");
			}

			else {
				marker.setColor(color);
				marker.setDimension(dimension);
				marker.setGroup(group);
				marker.setName(name);
				marker.setX(x);
				marker.setY(y);
				marker.setZ(z);

				if (picture != null) {
					marker.setPicture(picture);
				}

				// Save, or answer with the errors
				try {
					markerRepository.save(marker);
					result.setResultingObject(marker);
					result.setResult(true);
				}

				catch (DataIntegrityViolationException e) {
					result.setErrorMessage(e.getRootCause().getMessage());
				}

				catch (Exception e) {
					Logger.getLogger(this.getClass().getName()).severe(
							"Object to save was: " + marker.toString());
					result.setErrorMessage(e.getMessage());
				}

			} // authentication ok
		} // marker == null

		return result;
	}

	@RequestMapping(value = "/delete/{markerId}", produces = "application/json")
	@ResponseBody
	public OperationResult delete(@PathVariable long markerId, @RequestParam String userKey) {
		OperationResult result = new OperationResult();

		// We need to check the server key and thus need the marker and server first.
		Marker marker = markerRepository.findOne(markerId);
		if (marker == null) {
			result.setErrorMessage("Marker not found.");
		}

		else {

			// Are we allowed to access the data ? (have the Server key or the Master key)
			if (!MasterKeyService.getInstance().isValid(userKey) && !marker.getServer().isKeyValid(userKey)) {
				result.setErrorMessage("Authentication error.");
			}

			else {
				
				// Don't mind if it exists or not,we're deleting it !
				// Delete, or answer with the errors
				try {
					markerRepository.delete(marker);
					result.setResult(true);
				}
				
				catch(Exception e) {
					result.setErrorMessage(e.getMessage());
				}

			} // authentication ok
		} // marker == null

		return result;
	}

}
