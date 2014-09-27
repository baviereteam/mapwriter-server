package net.baviereteam.minecraft.mapwriterserver.controller;

import net.baviereteam.minecraft.mapwriterserver.repository.ServerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration
@RequestMapping("/markers")
public class MarkerController {

	// Markers are classified by servers and servers are the entry point so we still use this one
	@Autowired
	private ServerRepository serverRepository;
	
}
