package com.university.mediaverse.repository;

import java.util.Optional;

import com.university.mediaverse.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;


public interface ImageRepository extends JpaRepository<Image, Long> {
	Optional<Image> findByName(String name);

	Optional<Image> findById(int id);

	@Transactional
	void deleteByName(String name);
}
