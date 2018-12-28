package sec.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sec.model.AccessingContainers;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AccessingContainersRepository extends JpaRepository<AccessingContainers, Long> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE access_to_containers SET user_id = :userId, container_id = :containerId WHERE id = :id")
    void updateAccessingContainers(@Param("id") Long id, @Param("userId") Long userId, @Param("containerId") Long containerId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM access_to_containers WHERE id = :id")
    void deleteAccessingContainers(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM access_to_containers")
    List<AccessingContainers> getAccessing();


}
