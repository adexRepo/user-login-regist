package adexRepo.userloginregist.email;

public interface EmailSender {
    void send(String toEmail,String subject, String body);
}
