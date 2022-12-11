package adexRepo.userloginregist.appuser;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserSevice implements UserDetailsService {

    private final AppUserRepository appRepository;
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found!";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(AppUser appUser){
        boolean userExist = appRepository.findByEmail(appUser.getEmail()).isPresent();

        if(userExist){
            throw new IllegalStateException("email already taken");
        }

        String encodePass = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodePass);

        appRepository.save(appUser);

        // ANCHOR: Send confirmation token

        return "password success encoded!";
    }

}
