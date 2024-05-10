package com.example.chatback.controllers;


import com.example.chatback.configuration.UserAuthProvider;
import com.example.chatback.entity.Chat;
import com.example.chatback.entity.CredentialsDto;
import com.example.chatback.entity.UserDto;
import com.example.chatback.entity.Users;
import com.example.chatback.repository.UserRepository;
import com.example.chatback.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService; // Предположим, что у вас есть репозиторий для сущности User
    private final UserAuthProvider userAuthenticationProvider;
    @GetMapping("/create/{id}")
    public List<Chat> get( @PathVariable Long id) {
        return  userService.get(id);
    }
    @GetMapping("/get")
    public Users gets( String name) {
        System.out.println(name);
        return  userService.getByName(name);
    }
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
        System.out.println("login");
        Users user = userService.login(credentialsDto);
//        System.out.println(user);
//        ЭТА СУКА УКРАЛА У МЕНЯ НЕСКОЛЬКО ЧАСОВ ЖИЗНИ БЛЯТЬ
        UserDto userDto=UserDto.builder()
                .id(user.getId())
                .login(user.getEmail())
                .name(user.getUsername())
                .build();
        userDto.setToken(userAuthenticationProvider.createToken(userDto));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser ( @RequestBody Users users) {
        System.out.println("Это передали в контрлллер"+users.toString());
        List<Users> list  = userService.getAllUsers();
        if (list.size()!=0){
            Users user = userService.registration(users);
            for (Users item:list) {
                item.getFriendsList().add(users);
                userService.save(item);
                users.getFriendsList().add(item);
                userService.save(users);
            }
            UserDto userDto = new UserDto(user.getId(),user.getEmail(),user.getUsername(),"d");
            userDto.setToken(userAuthenticationProvider.createToken(userDto));
            return ResponseEntity.created(URI.create("/users/" + userDto.getId())).body(userDto);
        }
        System.out.println("ПППППП");
        Users user = userService.registration(users);
//        GJLEVFNM
        UserDto userDto = new UserDto(user.getId(),user.getEmail(),user.getUsername(),"d");
        userDto.setToken(userAuthenticationProvider.createToken(userDto));
        return ResponseEntity.created(URI.create("/users/" + userDto.getId())).body(userDto);
    }
    @GetMapping("/allUsers")
    public List<String> friends (){
        List<String> userList = userService.getAllUsers().stream().map(item->item.getName()).toList();
        return userList;
    }
//    @GetMapping("/allUsers")
//    public List<Users> friends (){
//        List<Users> userList = userService.getAllUsers();
//        return userList;
//    }
}