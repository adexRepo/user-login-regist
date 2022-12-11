package adexRepo.userloginregist.appuser;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import adexRepo.userloginregist.registration.token.ConfirmationToken;
import adexRepo.userloginregist.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserSevice implements UserDetailsService {

    private final AppUserRepository appRepository;
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found!";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(AppUser appUser){

        // ANCHOR Checking User Already taken or not
        boolean userExist = appRepository.findByEmail(appUser.getEmail()).isPresent();
        if(userExist){
            throw new IllegalStateException("email already taken");
        }

        String encodePass = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodePass);
        appRepository.save(appUser);

        // ANCHOR: Send confirmation token
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
            token,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            appUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

}
