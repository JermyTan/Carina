package com.carina.app.repository;

import com.carina.app.model.CarparkAvailabilityMondayModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarparkAvailabilityMondayRepository extends JpaRepository<CarparkAvailabilityMondayModel, Integer> {

    List<CarparkAvailabilityMondayModel> findByCarparkId(String carparkId);

}
