package net.baviereteam.minecraft.mapwriterserver.repository;

import net.baviereteam.minecraft.mapwriterserver.domain.Marker;

import org.springframework.data.repository.CrudRepository;

public interface MarkerRepository extends CrudRepository<Marker, Long> {

}