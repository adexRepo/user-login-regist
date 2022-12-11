package adexRepo.userloginregist.registration;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import adexRepo.userloginregist.appuser.AppUser;
import adexRepo.userloginregist.appuser.AppUserRole;
import adexRepo.userloginregist.appuser.AppUserSevice;
import adexRepo.userloginregist.registration.token.ConfirmationToken;
import adexRepo.userloginregist.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrationService {

    private EmailValidator emailValidator;
    private AppUserSevice appUserService;
    private ConfirmationTokenService confirmationTokenService;

    public String register(RegistrationRequest request){

        boolean isValidEmail = emailValidator.test(request.getEmail());

        if(!isValidEmail){
            throw new IllegalStateException("email not valid!");
        }

        return appUserService.signUpUser(
            new AppUser(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                AppUserRole.USER,
                false,false
            )
        );
    }

    @Transactional
    public String confirmToken(String token){
        ConfirmationToken confirmationToken = 
        confirmationTokenService.getToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));

        if(confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("email alredy confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if(expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());
        
        return "confirmed token success";
    }

}
