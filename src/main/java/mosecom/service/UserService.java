package mosecom.service;


import mosecom.dao.auth.DbUserRepository;
import mosecom.dto.auth.DbUserProjection;
import mosecom.model.auth.Role;
import mosecom.model.auth.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    DbUserRepository dbUserRepository;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        return User.builder()
//                .username("user")
//                .password(new BCryptPasswordEncoder().encode("123"))
//                .accountNonExpired(true)
//                .accountNonLocked(true)
//                .credentialsNonExpired(true)
//                .enabled(true)
//                .authorities(Arrays.asList(Role.USER))
//                .build();

//        System.out.println(new BCryptPasswordEncoder().encode("test"));
//
//        User myUser = dbUserRepository.findUserByUsername("user");
//        myUser.setPassword(new BCryptPasswordEncoder().encode("test"));
//        dbUserRepository.save(myUser);


        User user = dbUserRepository.findUserByUsername(s);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setAuthorities(Arrays.asList(Role.UNIDENTIFIED));
        return user;
    }

    @Transactional
    public User save(DbUserProjection dto, String oldpass, String newpass) throws IllegalStateException, IOException {
        User user = dbUserRepository.findUserById(dto.getId());
        if (newpass != null) {
            user.setPassword(new BCryptPasswordEncoder().encode(newpass));
        }
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        dbUserRepository.save(user);
        return user;
    }

    public User dropPassword(DbUserProjection dto) {
        User user = dbUserRepository.findUserById(dto.getId());
        user.setPassword(new BCryptPasswordEncoder().encode("Q1w2e3r4"));
        dbUserRepository.save(user);
        return user;
    }

    public User findUserByName(String username) {
        return dbUserRepository.findUserByUsername(username);
    }

    public User getUser(int id) {
        return dbUserRepository.getOne(id);
    }

    public List<DbUserProjection> getUsersList() {
        return dbUserRepository.findUsersList();
    }

    public int getCurrentUserId() throws NullPointerException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return dbUserRepository.findUserByUsername(authentication.getName()).getId();
    }
}


