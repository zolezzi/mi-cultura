package unq.edu.li.pdes.micultura.service;

import java.util.List;

import unq.edu.li.pdes.micultura.dto.AccountDTO;
import unq.edu.li.pdes.micultura.model.Account;
import unq.edu.li.pdes.micultura.vo.AccountVO;

public interface AccountService {

	AccountDTO findById(Long accountId);
	
	void deleteById(Long userId, Long id) throws Exception;
	
	AccountDTO save(AccountVO accountVO);
	
	AccountDTO update(AccountDTO account, Long userId, Long accountId);
	
	List<AccountDTO> findAll();
	
	Account createAccountByUser(AccountVO accountVO);
}
