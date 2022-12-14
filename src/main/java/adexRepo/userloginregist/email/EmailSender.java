package adexRepo.userloginregist.email;

public interface EmailSender {
    void sendSimple(String toEmail,String subject, String body);
    void sendMimeMsg(String to,String email);
}
