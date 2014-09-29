package net.baviereteam.minecraft.mapwriterserver.repository;

import java.util.List;

import net.baviereteam.minecraft.mapwriterserver.domain.Server;

import org.springframework.data.repository.*;

public interface ServerRepository extends CrudRepository<Server, Integer> {

    List<Server> findAll();

    Server findByName(String name);
    
}