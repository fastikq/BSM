package sec.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sec.model.Container;

import java.util.List;

@Repository
public interface ContainerRepository extends JpaRepository<Container, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM containers WHERE containers.id = :id")
    List<Container> getContainer(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM containers WHERE id IN " +
            "(SELECT container_id FROM access_to_containers WHERE user_id = :id)")
    List<Container> getUserContainers(@Param("id") Long id);
    }
