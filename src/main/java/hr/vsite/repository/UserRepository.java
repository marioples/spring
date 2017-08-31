package hr.vsite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hr.vsite.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {

	User findByUsername(String username);
		
	@Query("select u.username from User u where u.username = :username")
	String findUsernameByUsername(@Param("username") String username);

	@Override
	List<User> findAll();

	@Override
	@Query("select u.username from User u")
	List<String> fetchUsers();

}
