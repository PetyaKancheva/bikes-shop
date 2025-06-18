package bg.softuni.bikes_shop.service.impl;

import bg.softuni.bikes_shop.configuration.properties.MailConfigProperties;
import bg.softuni.bikes_shop.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class EmailServiceImpl implements EmailService {
    private final static String REGISTRATION_EMAIL_SUBJECT_LINE =
            "Thank you for registering at Bikes-Shop! Please confirm your e-Mail.";
    private final static String UPDATE_EMAIL_SUBJECT_LINE =
            "Your profile has been updated!";
    private final JavaMailSender javaMailSender;
    private final MailConfigProperties mailConfigProperties;
    private final TemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender javaMailSender, MailConfigProperties mailConfigProperties, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.mailConfigProperties = mailConfigProperties;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendRegistrationEmail(String userEmail, String userFirstName, String activationCode) {
        String emailBody = generateRegistrationEmailBody(userFirstName, activationCode);
        compileAndSendEmail(userEmail, REGISTRATION_EMAIL_SUBJECT_LINE, emailBody);

    }

    @Override
    public void sendProfileUpdateEmail(String userEmail, String userFirstName, String timeOfUpdate) {
        String emailBody = generateUpdateEmailBody(userFirstName, timeOfUpdate);
        compileAndSendEmail(userEmail, UPDATE_EMAIL_SUBJECT_LINE, emailBody);

    }

    private String generateUpdateEmailBody(String userFirstName, String timeOfUpdate) {
        Context context = new Context();

        context.setVariable("user_first_name", userFirstName);
        context.setVariable("timeOfUpdate", timeOfUpdate);


        return templateEngine.process("/email/profile-update-email.html", context);

    }

    private String generateRegistrationEmailBody(String userFirstName, String activationCode) {
        Context context = new Context();
        context.setVariable("user_first_name", userFirstName);

        String uri = UriComponentsBuilder.newInstance()
                .scheme("http").host("localhost").
                port(8080).path("/user/activate/").
                queryParam("activation_code",activationCode)
                .toUriString();

        context.setVariable("uri", uri);
        return templateEngine.process("/email/authentication-email.html", context);

    }

    private void compileAndSendEmail(String userEmail, String emailSubject, String emailBody) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setFrom(mailConfigProperties.getEmail());
            mimeMessageHelper.setReplyTo(mailConfigProperties.getEmail());
            mimeMessageHelper.setSubject(emailSubject);
            mimeMessageHelper.setText(emailBody, true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            throw new RuntimeException(e);

        }
    }
}

