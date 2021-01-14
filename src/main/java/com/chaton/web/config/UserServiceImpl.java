package com.chaton.web.config;

import com.chaton.model.user.Gender;
import com.chaton.repository.ApplicationUserRoleRepository;
import com.chaton.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
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

    @Autowired
    JavaMailSender mailSender;

    @Override
    public ResponseEntity<ApplicationUser> createUser(String email, String password,
                                                      Gender gender,
                                                      String role, HttpServletRequest request) throws Exception {

        try {
            if(userRepository.existsByUsername(email)){
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

                String verificationCode = RandomString.make(64);

                user.setUsername(email);
                user.setPassword(passwordEncoder.passwordEncoder().encode(password));
                user.setGender(gender);
                user.setRoles(userRole);
                user.setVerificationCode(verificationCode);
                user.setIsEnabled(false);


                logger.info(email + " just sign up");
                sendVerificationEmail(user,getSiteURL(request));

                userRepository.save(user);

                logger.info("the user was save successfully");
                return  ResponseEntity.ok(user);
            }
        }
        catch (Exception ex){
            ex.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    // Get the domain Url from the request
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    // Send email to the current user
    private void sendVerificationEmail(ApplicationUser user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getUsername();
        String fromAddress = "ovuefec@gmail.com";
        String senderName = "Chaton platform";
        String subject = "VERIFY YOUR EMAIL";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your email to complete your registration process:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">Verify your email</a></h3>"
                + "Thank you,<br>"
                + "The Chaton platform Team";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getUsername());
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
        logger.info("email was sent");
    }

    @Override
    public boolean verify(String verificationCode, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        Optional<ApplicationUser> user = userRepository.findByVerificationCode(verificationCode);

        if (user.isEmpty() || user.get().isEnabled()) {
            return false;
        } else {
            user.get().setVerificationCode(null);
            user.get().setIsEnabled(true);
            sendConfirmationEmail(user.get(), getSiteURL(request));
            userRepository.save(user.get());

            return true;
        }

    }

    @Override
    public String resetPassword(String email, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        Optional<ApplicationUser> user = userRepository.findByUsername(email);
        if (user.isEmpty()){
            return "User with "+ email + "does not exist";
        }
        else {
            String resetPasswordToken = RandomString.make(64);
            user.get().setResetPasswordToken(resetPasswordToken);
            userRepository.save(user.get());

            sendResetPasswordEmail(user.get(),getSiteURL(request));

            return "A reset password email have been sent to "+ email;
        }

    }

    // Send reset password email to the current user
    private void sendResetPasswordEmail(ApplicationUser user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getUsername();
        String fromAddress = "ovuefec@gmail.com";
        String senderName = "Chaton platform";
        String subject = "Reset your password";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to reset your password:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">Reset your password</a></h3>"
                + "Thank you,<br>"
                + "The Chaton platform Team";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getUsername());
        String verifyURL = siteURL + "/confirmPassword?token=" + user.getResetPasswordToken();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
        logger.info("email was sent");
    }




    @Override
    public String confirmResetPassword(String resetPasswordToken, String newPassword,String confirmPassword, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {

        Optional<ApplicationUser> user = userRepository.findByResetPasswordToken(resetPasswordToken);

        if (user.isEmpty()) {
            return "User does not exist";
        } else {
            if (!newPassword.equals(confirmPassword)){
                return "Password did not match";
            }
            else {
                user.get().setResetPasswordToken(null);
                user.get().setPassword(newPassword);
                sendConfirmResetPasswordEmail(user.get(), getSiteURL(request));
                userRepository.save(user.get());

                return "Password reset successfully!!!";
            }

        }
    }

    // Send confirm reset password email to the current user
    private void sendConfirmResetPasswordEmail(ApplicationUser user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getUsername();
        String fromAddress = "ovuefec@gmail.com";
        String senderName = "Chaton platform";
        String subject = "Welcome to Chaton";
        String content = "Dear [[name]]," +
                "<br><br>"
                + "Your password have been reset successfully!!!, kindly login to explore<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">Login</a></h3> <br>"
                + "Thank you," +
                "<br><br>"
                + "The Chaton platform Team";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getUsername());
        String verifyURL = siteURL + "/login" ;

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
        logger.info("email was sent");
    }

    // Send confirmation email to the current user
    private void sendConfirmationEmail(ApplicationUser user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getUsername();
        String fromAddress = "ovuefec@gmail.com";
        String senderName = "Chaton platform";
        String subject = "Welcome to Chaton";
        String content = "Dear [[name]]," +
                "<br><br>"
                + "Your account have been verified, kindly login to explore<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">Login</a></h3> <br>"
                + "Thank you," +
                "<br><br>"
                + "The Chaton platform Team";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getUsername());
        String verifyURL = siteURL + "/login" ;

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
        logger.info("email was sent");
    }

    @Override
    public ResponseEntity<ApplicationUser> updateUser(String email, String password, String firstName,
                                                      String lastName, String profileName, String phoneNumber,
                                                      String profileImage,
                                                      String userBillBoard, String role) throws Exception {
        try {
            Optional<ApplicationUser> currentUser = userRepository.findByUsername(email);
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
                if (!profileName.isEmpty()){
                    currentUser.get().setProfileName(profileName);
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
        Optional<ApplicationUser> user = userRepository.findByUsername(username);
        if (!user.get().isEnabled()){
            return  ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }
        else {
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
    }

    @Override
    public Optional<ApplicationUser> findUserByUsername(String email) throws Exception {
        return Optional.of( userRepository.findByUsername(email)).orElseThrow();
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = null;

        Optional<ApplicationUser> activeUser = userRepository.findByUsername(email);
        if(activeUser.isEmpty()){
            throw new UsernameNotFoundException(String.format("User with username {} not found", email));
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
