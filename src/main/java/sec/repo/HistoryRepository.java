package sec.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sec.model.History;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    @Override
    Optional<History> findById(Long eventId);

    @Query(nativeQuery = true, value = "SELECT * FROM history WHERE container_id = :id_container AND " +
            "container_id IN (SELECT container_id FROM access_to_containers WHERE user_id = :id_user) ")
    List<History> getHistoryContainer(@Param("id_user") Long userId, @Param("id_container") Long containerId);

    @Query(nativeQuery = true, value = "SELECT * FROM history WHERE container_id = :id_container AND " +
            "container_id IN (SELECT container_id FROM access_to_containers WHERE user_id = :id_user) " +
            "AND event_date_time BETWEEN :startDate AND :endDate")
    List<History> getHistoryOfDayContainer(@Param("id_user") Long userId, @Param("id_container") Long containerId, @Param("startDate") String start, @Param("endDate") String end);
}
