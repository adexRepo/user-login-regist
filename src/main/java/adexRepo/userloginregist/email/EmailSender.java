package adexRepo.userloginregist.email;

public interface EmailSender {
    void send(String to, String email);
}
