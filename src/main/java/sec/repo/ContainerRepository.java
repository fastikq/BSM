package sec.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sec.model.Container;

import java.util.List;

@Repository
public interface ContainerRepository extends JpaRepository<Container, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM containers WHERE containers.id = :id")
    List<Container> getContainer(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM containers WHERE id IN " +
            "(SELECT container_id FROM access_to_containers WHERE user_id = :id)")
    List<Container> getUserContainers(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE containers SET info = :newInfo, date_registration = :newDate WHERE id = :id")
    void updateContainer(@Param("id") Long id, @Param("newInfo") String newInfo, @Param("newDate") String  newDate);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM containers WHERE id = :id")
    void deleteContainer(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM containers")
    List<Container> getContainers();

}
