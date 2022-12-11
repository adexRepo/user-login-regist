package adexRepo.userloginregist.registration;

import org.springframework.stereotype.Service;

import adexRepo.userloginregist.appuser.AppUser;
import adexRepo.userloginregist.appuser.AppUserRole;
import adexRepo.userloginregist.appuser.AppUserSevice;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrationService {

    private EmailValidator emailValidator;
    private AppUserSevice appUserService;

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
                false,true
            )
         );
    }
}
