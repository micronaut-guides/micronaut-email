package example.micronaut;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Property;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest // <1>
@Property(name = "spec.name", value = "mailcontroller") // <2>
class MailControllerTest {

    @Inject
    ApplicationContext applicationContext; // <3>

    @Inject
    @Client("/")
    RxHttpClient client; // <4>

    @Test
    public void mailsendInteractsOnceEmailService() {
        EmailCmd cmd = new EmailCmd();
        cmd.setSubject("Test");
        cmd.setRecipient("delamos@grails.example");
        cmd.setTextBody("Hola hola");
        HttpRequest<EmailCmd> request = HttpRequest.POST("/mail/send", cmd); // <5>
        Collection<EmailService> emailServices = applicationContext.getBeansOfType(EmailService.class);
        assertEquals(1, emailServices.size());
        EmailService emailService = applicationContext.getBean(EmailService.class);
        assertTrue(emailService instanceof MockEmailService);
        int oldEmailsSize = ((MockEmailService) emailService).emails.size();
        HttpResponse<?> rsp = client.toBlocking().exchange(request);
        assertEquals(HttpStatus.OK, rsp.getStatus());
        assertEquals(oldEmailsSize + 1 , ((MockEmailService) emailService).emails.size()); // <6>
    }
}
