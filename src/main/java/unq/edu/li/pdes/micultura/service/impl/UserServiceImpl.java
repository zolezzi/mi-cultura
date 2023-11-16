package unq.edu.li.pdes.micultura.service.impl;

import java.util.List;
import java.util.Vector;

import javax.transaction.Transactional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import unq.edu.li.pdes.micultura.dto.JwtResponseDTO;
import unq.edu.li.pdes.micultura.dto.UserDTO;
import unq.edu.li.pdes.micultura.exception.MiCulturaException;
import unq.edu.li.pdes.micultura.exception.UserCreateException;
import unq.edu.li.pdes.micultura.exception.UserNotFoundException;
import unq.edu.li.pdes.micultura.mapper.Mapper;
import unq.edu.li.pdes.micultura.model.User;
import unq.edu.li.pdes.micultura.repository.AccountRepository;
import unq.edu.li.pdes.micultura.repository.UserRepository;
import unq.edu.li.pdes.micultura.service.UserService;
import unq.edu.li.pdes.micultura.utils.EncodeAndDecodeCrypt;
import unq.edu.li.pdes.micultura.utils.ErrorUtils;
import unq.edu.li.pdes.micultura.utils.TokenUtils;
import unq.edu.li.pdes.micultura.validator.UserValidator;
import unq.edu.li.pdes.micultura.vo.UserLoginVO;
import unq.edu.li.pdes.micultura.vo.UserVO;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	private final UserRepository repository;
	private final AccountRepository accountRepository;
	private final AuthenticationManager authenticationManager;
	private final AccountServiceImpl accountService;
	private final Mapper mapper;
    private final TokenUtils tokenUtils;
    private final EncodeAndDecodeCrypt passwordEncoder;
    private Vector<String> errors = new Vector<>();
	
    @Override
    public JwtResponseDTO login(UserLoginVO user) {
    	String passEncrypt = user.getPassword();
    	user.setPassword(passwordEncoder.encode(passEncrypt));
    	var userDetails = (User) repository.findOneByEmail(user.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException(String.format("No found user:%s", user.getEmail())));
    	var token = tokenUtils.createToken(userDetails.getUsername());
    	authenticate(user.getEmail(), user.getPassword());
    	
		return new JwtResponseDTO(userDetails.getUsername(), token, userDetails.getAccount().getFirstname(),
				userDetails.getAccount().getLastname(), userDetails.getAccount().getAccountRole().name(), 
				userDetails.getId(), userDetails.getAccount().getId());
	}
	
	@Transactional
	@Override
	public UserDTO create(UserVO userVO) {
		UserValidator.validateSamePassword(userVO.getPassword(), userVO.getRepeatPassword(), errors);
		User newUser = mapper.map(userVO, User.class);
        var serDB = repository.findOneByEmail(newUser.getEmail());
        errors = serDB.isPresent() ? ErrorUtils.addError(errors, String.format("There is already a registered user with this email:%s ", newUser.getEmail())) : errors;
        if(!UserValidator.validate(newUser, errors) || !errors.isEmpty()) {
			throw new UserCreateException(String.format("Error register user resolved errors: %s ", errors.toString()));
		}
        var accountNew = accountService.createAccountByUser(userVO.getAccount());
        newUser.setAccount(accountNew);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return mapper.map(repository.save(newUser), UserDTO.class);
	}

	@Override
	public UserDTO findById(Long userId) {
		var userDB = getUserById(userId);
		return mapper.map(userDB, UserDTO.class);
	}

	@Override
	public void deleteById(Long userId, Long adminId) throws Exception {
		var userAdmin = repository.findById(adminId).orElseThrow(() -> new MiCulturaException(String.format("No found user:%s", adminId)));
		if(!userAdmin.isAdmin()) {
			throw new MiCulturaException(String.format("Error Unauthorized permission user: %s ", adminId));
		}
		var userDelete = repository.findById(userId).orElseThrow(() -> new MiCulturaException(String.format("No found user:%s", userId)));
		repository.delete(userDelete);
		accountRepository.delete(userDelete.getAccount());
	}

	@Override
	public UserDTO update(UserDTO user, Long userId) {
		var userDB = getUserById(userId);
		userDB = mapper.map(userDB, User.class);
		return mapper.map(repository.save(userDB), UserDTO.class);
	}

	@Override
	public List<UserDTO> findAll() {
		return mapper.mapList(repository.findAll(), UserDTO.class);
	}
	
	private User getUserById(Long userId) {
		var userIdIdOpt = repository.findById(userId);
		if(userIdIdOpt.isEmpty()) {
			throw new UserNotFoundException(String.format("User not found with id:%s ", userId));
		}
		return userIdIdOpt.get();
	}
	
    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
