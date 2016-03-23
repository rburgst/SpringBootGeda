package com.example;

import com.inspiresoftware.lib.dto.geda.assembler.Assembler;
import com.inspiresoftware.lib.dto.geda.assembler.DTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}


@Repository
interface UserRepository extends CrudRepository<UserEntity, Long> {}

@Controller
@Transactional(readOnly = true)
class UserController {
    private final Assembler userAssembler;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
        userAssembler = DTOAssembler.newAssembler(UserDTO.class, UserEntity.class, UserEntity.class.getClassLoader());
    }

    @RequestMapping(path = "/user", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public List<UserDTO> getAllUsers() {
        Iterable<UserEntity> users = userRepository.findAll();
        if (!users.iterator().hasNext()) {
            // dont have any users yet, create one just for demo
            UserEntity user = new UserEntity();
            user.setName("Hans Maier");
            userRepository.save(user);

            users = userRepository.findAll();
        }
        List<UserEntity> userList = StreamSupport.stream(users.spliterator(), false).collect(Collectors.toList());
        List<UserDTO> result = new ArrayList<>();
        userAssembler.assembleDtos(result, userList, null, null);
        return result;
    }
}
