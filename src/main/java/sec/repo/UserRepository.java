package sec.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sec.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	Optional<User> findByUsernameOrEmail(String username, String email);

	List<User> findByIdIn(List<Long> userIds);

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	@Query(nativeQuery = true, value = "SELECT * FROM users WHERE id IN (SELECT user_id FROM user_roles WHERE role_id = 1)")
	List<User> getUsers();

	@Query(nativeQuery = true, value = "SELECT * FROM users WHERE users.id = :id")
	User getUserDetails(@Param("id") Long id);

	@Query(nativeQuery = true, value = "SELECT * FROM users WHERE id IN (SELECT user_id FROM user_roles WHERE role_id = 3)")
	List<User> getGuestUsers();

    @Modifying
    @Transactional
	@Query(nativeQuery = true, value = "UPDATE user_roles SET role_id = 1 WHERE user_id = :idUser")
    void acceptRegistrationRequest(@Param("idUser") Long idUser);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE users SET name = :newName, username = :newUsername, email = :newEmail WHERE id = :idUser")
    void updateUser(@Param("idUser") Long id, @Param("newName") String newName ,@Param("newUsername") String newUsername, @Param("newEmail") String newEmail);


}
