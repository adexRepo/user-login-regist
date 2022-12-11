package adexRepo.userloginregist.registration.token;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        Optional<ConfirmationToken> confirmationToken =
        confirmationTokenRepository.findByToken(token);

        return confirmationToken;
    }

    public void setConfirmedAt(String token){
        Optional<ConfirmationToken> confirmationToken =
        confirmationTokenRepository.findByToken(token);   
        
        confirmationToken.get().setConfirmedAt(LocalDateTime.now());
        confirmationTokenRepository.save(confirmationToken.get());
    }
}
