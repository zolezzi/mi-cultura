package unq.edu.li.pdes.micultura.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import unq.edu.li.pdes.micultura.dto.AccountDTO;
import unq.edu.li.pdes.micultura.exception.AccountNotFoundException;
import unq.edu.li.pdes.micultura.exception.MiCulturaException;
import unq.edu.li.pdes.micultura.mapper.Mapper;
import unq.edu.li.pdes.micultura.model.Account;
import unq.edu.li.pdes.micultura.model.AccountRole;
import unq.edu.li.pdes.micultura.repository.AccountRepository;
import unq.edu.li.pdes.micultura.repository.UserRepository;
import unq.edu.li.pdes.micultura.service.AccountService;
import unq.edu.li.pdes.micultura.vo.AccountVO;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

	private final AccountRepository repository;
	private final UserRepository userRepository;
	private final Mapper mapper;
	
	@Override
	public AccountDTO findById(Long accountId) {
		var accountDB = getAccountById(accountId);
		return mapper.map(accountDB, AccountDTO.class);
	}

	@Override
	public void deleteById(Long userId, Long id) throws Exception {
		var userAdmin = userRepository.findById(userId).orElseThrow(() -> new MiCulturaException(String.format("No found user:%s", userId)));
		if(!userAdmin.isAdmin()) {
			throw new MiCulturaException(String.format("Error Unauthorized permission user: %s ", userId));
		}
		var accountBD = getAccountById(id);
		var userBD = userRepository.findOneByAccount(accountBD).orElseThrow(() -> new MiCulturaException(String.format("No found user:%s", accountBD.getId())));
		userRepository.delete(userBD);
		repository.delete(accountBD);
	}

	@Override
	public AccountDTO save(AccountVO accountVO) {
		var accountDB = repository.findOneByDni(accountVO.getDni());
		if(accountDB.isPresent()) {
			throw new MiCulturaException(String.format("error there is already an account registered with this DNI: %s ", accountVO.getDni()));
		}
		var accountNew = mapper.map(accountVO, Account.class);
		accountNew = repository.save(accountNew);
		return mapper.map(accountNew, AccountDTO.class);
	}

	@Override
	public AccountDTO update(AccountDTO account, Long userId, Long accountId) {
		var accountDB = getAccountById(accountId);
		var userBD = userRepository.findById(userId)
				.orElseThrow(() -> new MiCulturaException(String.format("No found user:%s", userId)));
		if(!userBD.getAccount().equals(accountDB)) {
			throw new MiCulturaException(String.format("No found account with id :%s, for user with id:%s ", accountId, userId));
		}
		accountDB = mapper.map(account, Account.class);
		return mapper.map(repository.save(accountDB), AccountDTO.class);
	}

	@Override
	public List<AccountDTO> findAll() {
		return mapper.mapList(repository.findAllByAccountRoles(Arrays.asList(AccountRole.TOURIST, AccountRole.VISITOR)), AccountDTO.class);
	}
	
	@Override
	public Account createAccountByUser(AccountVO accountVO) {
		var accountDB = repository.findOneByDni(accountVO.getDni());
		if(accountDB.isPresent()) {
			throw new MiCulturaException(String.format("error there is already an account registered with this DNI: %s ", accountVO.getDni()));
		}
		var accountNew = mapper.map(accountVO, Account.class);
		return repository.save(accountNew);
	}
	
	private Account getAccountById(Long accountId) {
		var accountIdIdOpt = repository.findById(accountId);
		if(accountIdIdOpt.isEmpty()) {
			throw new AccountNotFoundException(String.format("Account not found with id:%s ", accountId));
		}
		return accountIdIdOpt.get();
	}
}
