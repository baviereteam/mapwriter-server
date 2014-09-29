package net.baviereteam.minecraft.mapwriterserver.controller;

import java.util.logging.Logger;

import net.baviereteam.minecraft.mapwriterserver.domain.Server;
import net.baviereteam.minecraft.mapwriterserver.json.OperationResult;
import net.baviereteam.minecraft.mapwriterserver.repository.ServerRepository;
import net.baviereteam.minecraft.mapwriterserver.service.MasterKeyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@EnableAutoConfiguration
@RequestMapping("/server")
public class ServerController {

	@Autowired
	private ServerRepository serverRepository;

	/* ADMIN METHODS */
	@RequestMapping(value="/list", produces="application/json")
	@ResponseBody
    public OperationResult list(@RequestParam String userKey) {
		OperationResult result = new OperationResult();
		
		// Only the Administrator can list the Servers; we need to check the Master Key.
		if(! MasterKeyService.getInstance().isValid(userKey)) {
			result.setErrorMessage("Authentication error.");
		}
		
		else {
			result.setResult(true);
			result.setResultingObject(serverRepository.findAll());
		}
		
        return result;
    }
	
	@RequestMapping(value="/create", produces="application/json")
	@ResponseBody
    public OperationResult create(@RequestParam String name, @RequestParam String userKey) {
		OperationResult result = new OperationResult();
		
		// Only the Administrator can create a Server; we need to check the Master Key.
		if(! MasterKeyService.getInstance().isValid(userKey)) {
			result.setErrorMessage("Authentication error.");
		}
		
		else {
		
			// Create the server and generate the key
			Server server = new Server();
			server.setName(name);
			server.generateKey();
			
			// Save, or answer with the errors
			try {
				serverRepository.save(server);
				result.setResultingObject(server);
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
		
		return result;
    }

	@RequestMapping(value="/rename/{id}", produces="application/json")
	@ResponseBody
	public OperationResult rename(@PathVariable int id, @RequestParam String name, @RequestParam String userKey) {
		OperationResult result = new OperationResult();
		
		// Only the Administrator can rename a Server; we need to check the Master Key.
		if(! MasterKeyService.getInstance().isValid(userKey)) {
			result.setErrorMessage("Authentication error.");
		}
		
		else {
			// Get the server (if it exists)
			Server server = serverRepository.findOne(id);
			if(server == null) {
				result.setErrorMessage("Server not found.");
			}
			
			else {
				// Rename it...
				server.setName(name);
				
				// Save, or answer with the errors
				try {
					serverRepository.save(server);
					result.setResultingObject(server);
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

	@RequestMapping(value="/changekey/{id}", produces="application/json")
	@ResponseBody
	public OperationResult changeKey(@PathVariable int id, @RequestParam String userKey) {
		OperationResult result = new OperationResult();
		
		// Only the Administrator can change the key for a Server; we need to check the Master Key.
		if(! MasterKeyService.getInstance().isValid(userKey)) {
			result.setErrorMessage("Authentication error.");
		}
		
		else {
			// Get the server (if it exists)
			Server server = serverRepository.findOne(id);
			if(server == null) {
				result.setErrorMessage("Server not found.");
			}
			
			else {
				// Change the key...
				server.generateKey();
				
				// Save, or answer with the errors
				try {
					serverRepository.save(server);
					result.setResultingObject(server);
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

	@RequestMapping(value="/delete/{id}", produces="application/json")
	@ResponseBody
	public OperationResult delete(@PathVariable int id, @RequestParam String userKey) {
		OperationResult result = new OperationResult();
		
		// Only the Administrator can delete a Server; we need to check the Master Key.
		if(! MasterKeyService.getInstance().isValid(userKey)) {
			result.setErrorMessage("Authentication error.");
		}
		
		else {
			// Don't mind if it exists or not,we're deleting it !
			// Delete, or answer with the errors
			try {
				serverRepository.delete(id);
				result.setResult(true);
			}
			
			catch(Exception e) {
				result.setErrorMessage(e.getMessage());
			}
		}
		
		return result;
	}
}