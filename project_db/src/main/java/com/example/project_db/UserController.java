package com.example.project_db;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // ❗️ 이 클래스가 REST API를 위한 컨트롤러임을 나타냅니다.
public class UserController {

    // final로 선언하고 생성자를 통해 주입받는 방식을 권장합니다. (DI)
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ❗️ http://localhost:8080/api/user 주소로 GET 요청이 오면 이 메소드가 실행됩니다.
    @GetMapping("/api/user")
    public List<User> getAllUsers() {
        // 1. Repository에게 모든 User 데이터를 가져오라고 시킵니다.
        List<User> userList = userRepository.findAll();

        // 2. 가져온 데이터 리스트를 그대로 리턴합니다.
        // ❗️ @RestController 덕분에 이 List<User> 객체는 자동으로 JSON 배열로 변환되어 응답됩니다.
        return userList;
    }
}