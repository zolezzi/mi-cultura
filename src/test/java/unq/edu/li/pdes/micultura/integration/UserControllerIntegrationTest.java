package unq.edu.li.pdes.micultura.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import unq.edu.li.pdes.micultura.service.impl.UserServiceImpl;
import unq.edu.li.pdes.micultura.utils.TokenUtils;
import unq.edu.li.pdes.micultura.vo.AccountVO;
import unq.edu.li.pdes.micultura.vo.UserLoginVO;
import unq.edu.li.pdes.micultura.vo.UserVO;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private UserServiceImpl userService;
    
    @MockBean
    private TokenUtils tokenUtils;

    @Test
    public void testLoginEndpoint() throws Exception {
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setEmail("charlie@gmail.com");
        userLoginVO.setPassword("Password1234!");

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLoginVO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateEndpoint() throws Exception {
        UserVO userVO = new UserVO();
        userVO.setEmail("user@example.com");
        userVO.setPassword("Password1234!");
        userVO.setPassword("Password1234!");
        AccountVO accountVO = new AccountVO();
        accountVO.setAddress("Calle falsa 123");
        accountVO.setDni("32432423423");
        accountVO.setFirstname("Charlie");
        accountVO.setLastname("Test");
        accountVO.setPhoneNumber("21332123123");
        accountVO.setRole("TOURIST");
        mockMvc.perform(post("/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userVO)))
                .andExpect(status().isOk());
    }
}