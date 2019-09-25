package com.carina.app.repository;

import com.carina.app.model.FavouritesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouritesRepository extends JpaRepository<FavouritesModel, Long> {

    List<FavouritesModel> findByUserId(Long id);

}