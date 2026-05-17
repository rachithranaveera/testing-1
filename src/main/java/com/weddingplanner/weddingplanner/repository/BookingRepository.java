package com.weddingplanner.weddingplanner.repository;

import com.weddingplanner.weddingplanner.model.Booking;
import com.weddingplanner.weddingplanner.model.Couple;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCouple(Couple couple);
    List<Booking> findByStatus(String status);
}
