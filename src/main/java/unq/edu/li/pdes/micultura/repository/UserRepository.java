package unq.edu.li.pdes.micultura.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import unq.edu.li.pdes.micultura.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findOneByEmail(String email);
}
