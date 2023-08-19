package unq.edu.li.pdes.micultura.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import unq.edu.li.pdes.micultura.model.Account;
import unq.edu.li.pdes.micultura.model.AccountRole;

public interface AccountRepository extends JpaRepository<Account, Long>{

	Optional<Account> findOneByDni(String dni);

	@Query("select a from Account a where a.accountRole in (:roles)")
	List<Account> findAllByAccountRoles(@Param("roles") List<AccountRole> roles);
}
