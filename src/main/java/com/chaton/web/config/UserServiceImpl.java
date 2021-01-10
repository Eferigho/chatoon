package com.chaton.web.config;

import com.chaton.model.user.Gender;
import com.chaton.repository.ApplicationUserRoleRepository;
import com.chaton.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

@Service("userService")
public class UserServiceImpl  implements UserService, UserDetailsService {
    Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Autowired
    UserRepository userRepository;

    @Autowired
    ApplicationUserRoleRepository roleRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordConfig passwordEncoder;

    @Autowired TokenProvider tokenProvider;



    @Override
    public ResponseEntity<ApplicationUser> createUser(String username, String password,
                                                      String email, Gender gender, String role) throws Exception {

        try {
            if(userRepository.existsByUsername(username)){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            }else{
                ApplicationUser user = new ApplicationUser();
                ApplicationRole roles = new ApplicationRole();
                Set<ApplicationRole> userRole = new HashSet<>();

                    roles.setRole(Role.NORMAL_USER.name());
                    userRole.add(roles);

                 if (role.equals(Role.BUSINESS_USER.name())){
                    roles.setRole(Role.BUSINESS_USER.name());
                    userRole.add(roles);
                }
                else if(role.equals(Role.ADMIN.name())){
                    roles.setRole(Role.ADMIN.name());
                    userRole.add(roles);
                }
                roleRepository.save(roles);

                user.setUsername(username);
                user.setPassword(passwordEncoder.passwordEncoder().encode(password));
                user.setEmail(email);
                user.setGender(gender);
                user.setRoles(userRole);

                userRepository.save(user);
                logger.info(username + " just sign up");
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }
        catch (Exception ex){
            ex.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<ApplicationUser> updateUser(String username, String password, String firstName,
                                                      String lastName, String email, String phoneNumber,
                                                      String profileImage,
                                                      String userBillBoard, String role) throws Exception {
        try {
            Optional<ApplicationUser> currentUser = userRepository.findByUsername(username);
            if (currentUser.isPresent()){
                logger.info(currentUser.get().getUsername()+" is found");
                if (!password.isEmpty()){
                    currentUser.get().setPassword(password);
                }
                if (!firstName.isEmpty()){
                    currentUser.get().setFirstName(firstName);
                }
                if (!lastName.isEmpty()){
                    currentUser.get().setLastName(lastName);
                }
                if (!email.isEmpty()){
                    currentUser.get().setEmail(email);
                }
                if (!phoneNumber.isEmpty()){
                    currentUser.get().setPhoneNumber(phoneNumber);
                }
                if (!profileImage.isEmpty()){
                    currentUser.get().setProfileImage(profileImage);
                }
                if (!userBillBoard.isEmpty()){
                    currentUser.get().setUserBillboard(userBillBoard);
                }

                if (!role.isEmpty() && role.equals(Role.BUSINESS_USER.name())){
                    ApplicationRole roles = new ApplicationRole();
                    Set<ApplicationRole> userRole = new HashSet<>();
                    roles.setRole(Role.BUSINESS_USER.name());
                    userRole.add(roles);
                    currentUser.get().setRoles(userRole);
                }

                userRepository.save(currentUser.orElseThrow());
                return  new ResponseEntity<>(HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception ex){
            ex.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> generateToken(String username, String password) {
        final Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                username,
                                password
                        )
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final  String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
    }

    @Override
    public Optional<ApplicationUser> findUserByUsername(String username) throws Exception {
        return Optional.of( userRepository.findByUsername(username)).orElseThrow();
    }



    @Override
    public List<ApplicationUser> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;

        Optional<ApplicationUser> activeUser = userRepository.findByUsername(username);
        if(activeUser.isEmpty()){
            throw new UsernameNotFoundException(String.format("User with username {} not found", username));
        }
        else {
            user = new User(activeUser.get().getUsername(),activeUser.get().getPassword(),
                    getAuthority(activeUser.get()));
        }

        return user;
    }

    private Set<SimpleGrantedAuthority> getAuthority(ApplicationUser user){
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
        });
        return authorities;
    }

}
