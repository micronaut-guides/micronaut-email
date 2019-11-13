package example.micronaut

import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import spock.lang.Shared
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest // <1>
@Property(name = 'spec.name', value = 'mailcontroller') // <2>
class MailControllerSpec extends Specification {

    @Shared
    @Inject
    ApplicationContext applicationContext // <3>

    @Shared
    @Inject
    @Client("/")
    RxHttpClient client // <4>

    def "/mail/send interacts once email service"() {
        given:
        EmailCmd cmd = new EmailCmd(subject: 'Test',
                recipient: 'delamos@grails.example',
                textBody: 'Hola hola')
        HttpRequest request = HttpRequest.POST('/mail/send', cmd) // <5>

        when:
        Collection<Class> emailServices = applicationContext.getBeansOfType(EmailService)

        then:
        !emailServices.any { it == SendGridEmailService.class}
        !emailServices.any { it == AwsSesMailService.class}

        when:
        EmailService emailService = applicationContext.getBean(EmailService)

        then:
        emailService instanceof MockEmailService

        when:
        HttpResponse rsp = client.toBlocking().exchange(request)

        then:
        rsp.status.code == 200
        ((MockEmailService)emailService).emails.size() == old(((MockEmailService)emailService).emails.size()) + 1 // <6>
    }
}
