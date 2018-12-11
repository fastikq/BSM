package sec.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sec.model.AccessingContainers;

@Repository
public interface AccessingContainersRepository extends JpaRepository<AccessingContainers, Long> {

}
