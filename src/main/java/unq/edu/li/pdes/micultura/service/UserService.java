package unq.edu.li.pdes.micultura.service;

import unq.edu.li.pdes.micultura.dto.JwtResponseDTO;
import unq.edu.li.pdes.micultura.dto.UserDTO;
import unq.edu.li.pdes.micultura.vo.UserLoginVO;
import unq.edu.li.pdes.micultura.vo.UserVO;

public interface UserService {

	JwtResponseDTO login(UserLoginVO user);
	
	UserDTO create(UserVO userVO);
}
