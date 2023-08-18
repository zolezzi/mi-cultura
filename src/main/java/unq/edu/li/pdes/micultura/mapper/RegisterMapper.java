package unq.edu.li.pdes.micultura.mapper;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import unq.edu.li.pdes.micultura.dto.UserDTO;
import unq.edu.li.pdes.micultura.model.User;
import unq.edu.li.pdes.micultura.vo.UserLoginVO;
import unq.edu.li.pdes.micultura.vo.UserVO;

@Component
public class RegisterMapper {

	@Autowired
	private MapperFactory mapperFactory;
	
	@Autowired
	private Mapper mapper;
	
	@PostConstruct
	public void initialize() {

		mapperFactory.classMap(UserVO.class, User.class).customize(new CustomMapper<UserVO, User>() {
			@Override
			public void mapBtoA(User b, UserVO a, MappingContext context) {
				a.setEmail(b.getEmail());
				a.setPassword(b.getPassword());
			}
			@Override
			public void mapAtoB(UserVO a, User b, MappingContext context) {
				b.setEmail(a.getEmail());
				b.setPassword(a.getPassword());
			}
		}).byDefault().register();
		
		mapperFactory.classMap(UserDTO.class, User.class).customize(new CustomMapper<UserDTO, User>() {
			@Override
			public void mapBtoA(User b, UserDTO a, MappingContext context) {
				a.setId(b.getId());
				a.setEmail(b.getEmail());
				a.setPassword(b.getPassword());
			}
			@Override
			public void mapAtoB(UserDTO a, User b, MappingContext context) {
				b.setId(a.getId());
				b.setEmail(a.getEmail());
				b.setPassword(a.getPassword());
			}
		}).byDefault().register();
		
		mapperFactory.classMap(UserLoginVO.class, User.class).customize(new CustomMapper<UserLoginVO, User>() {
			@Override
			public void mapBtoA(User b, UserLoginVO a, MappingContext context) {
				a.setEmail(b.getEmail());
				a.setPassword(b.getPassword());
			}
			@Override
			public void mapAtoB(UserLoginVO a, User b, MappingContext context) {
				b.setEmail(a.getEmail());
				b.setPassword(a.getPassword());
			}
		}).byDefault().register();
		
	}
}
