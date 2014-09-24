package net.baviereteam.minecraft.mapwriterserver.repository;

import net.baviereteam.minecraft.mapwriterserver.domain.Server;

import org.springframework.data.domain.*;
import org.springframework.data.repository.*;

public interface ServerRepository extends Repository<Server, Integer> {

    Page<Server> findAll(Pageable pageable);

    Server findByName(String name);
}