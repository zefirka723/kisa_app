package mosecom.service;


import mosecom.dao.auth.DbUserRepository;
import mosecom.dto.auth.DbUserProjection;
import mosecom.model.auth.Role;
import mosecom.model.auth.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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
    public User save(DbUserProjection dto) throws IllegalStateException, IOException {
        User user = dbUserRepository.findUserByUsername(dto.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        dbUserRepository.save(user);
        return user;
    }

    public User findUserByName(String username) {
        return dbUserRepository.findUserByUsername(username);
    }

    public User getUser(int id) {
        return dbUserRepository.getOne(id);
    }


}
