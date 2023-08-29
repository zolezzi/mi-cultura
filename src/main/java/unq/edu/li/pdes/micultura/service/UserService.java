package unq.edu.li.pdes.micultura.service;

import java.util.List;

import unq.edu.li.pdes.micultura.dto.JwtResponseDTO;
import unq.edu.li.pdes.micultura.dto.UserDTO;
import unq.edu.li.pdes.micultura.vo.UserLoginVO;
import unq.edu.li.pdes.micultura.vo.UserVO;

public interface UserService {

	JwtResponseDTO login(UserLoginVO user);
	
	UserDTO create(UserVO userVO);
	
	UserDTO findById(Long userId);
	
	void deleteById(Long userId, Long id) throws Exception;
	
	UserDTO update(UserDTO account, Long userId);
	
	List<UserDTO> findAll();
}
