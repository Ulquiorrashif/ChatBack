package com.example.chatback.services;


import com.example.chatback.configuration.AppException;
import com.example.chatback.entity.Chat;
import com.example.chatback.entity.CredentialsDto;
import com.example.chatback.entity.UserDto;
import com.example.chatback.entity.Users;
import com.example.chatback.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository usersRep;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public Users registration(Users user){

        if (usersRep.findByEmail(user.getEmail())==null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            System.out.println(user.getEmail()+" "+ user.getPassword());
            Users regUser = usersRep.save(user);
            return regUser;
        }
        throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
    }
    public Users login(CredentialsDto credentialsDto) {
        System.out.println("credentialsDto"+credentialsDto);
//        Users user = null;
        Users user= usersRep.findByEmail(credentialsDto.login());
        System.out.println("Пользователь при login");
        if (user!=null){
//            System.out.println("Сравниваем пароли до преобоазования"+credentialsDto.password()+"после"+credentialsDto.password());
            if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), user.getPassword())) {
                return user;
            }else
                throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
        }
        throw new AppException("Неизвестный пользователь", HttpStatus.NOT_FOUND);
    }
    public UserDto findByLogin(String login) {
        Users user = usersRep.findByEmail(login);
        if (user!= null){
            return UserDto.builder()
                    .id(user.getId())
                    .login(user.getEmail())
                    .name(user.getUsername())
                    .build();
        }
        throw  new AppException("Unknown user", HttpStatus.NOT_FOUND);

    }

    public void save(Users user){

        usersRep.save(user);
    }
    public List<Users> getAllUsers(){
        return usersRep.findAll();
    }

    public List<Chat> get(Long id) {
        return usersRep.findById(id).get().getChatList();
    }

    public Users getByName(String name) {
        return  usersRep.findByEmail(name);
    }

}