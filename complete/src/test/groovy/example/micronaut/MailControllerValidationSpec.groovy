package example.micronaut

import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.annotation.MicronautTest
import spock.lang.Shared
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest // <1>
@Property(name = 'spec.name', value = 'mailcontroller') // <2>
class MailControllerValidationSpec extends Specification {

    @Shared
    @Inject
    ApplicationContext applicationContext

    @Shared
    @Inject
    @Client("/")
    RxHttpClient client

    def "/mail/send cannot be invoked without subject"() {
        given:
        EmailCmd cmd = new EmailCmd(
                recipient: 'delamos@micronaut.example',
                textBody: 'Hola hola')
        HttpRequest request = HttpRequest.POST('/mail/send', cmd) // <3>

        when:
        client.toBlocking().exchange(request)

        then:
        HttpClientResponseException e = thrown(HttpClientResponseException)
        e.status.code == 400
    }

    def "/mail/send cannot be invoked without recipient"() {
        given:
        EmailCmd cmd = new EmailCmd(
                subject: 'Hola',
                textBody: 'Hola hola')
        HttpRequest request = HttpRequest.POST('/mail/send', cmd) // <3>

        when:
        client.toBlocking().exchange(request)

        then:
        HttpClientResponseException e = thrown(HttpClientResponseException)
        e.status.code == 400
    }

    def "/mail/send cannot be invoked without either textBody or htmlBody"() {
        given:
        EmailCmd cmd = new EmailCmd(
                subject: 'Hola',
                recipient: 'delamos@micronaut.example',)
        HttpRequest request = HttpRequest.POST('/mail/send', cmd) // <3>

        when:
        client.toBlocking().exchange(request)

        then:
        HttpClientResponseException e = thrown(HttpClientResponseException)
        e.status.code == 400
    }

    def "/mail/send can be invoked without textBody and not htmlBody"() {
        given:
        EmailCmd cmd = new EmailCmd(
                subject: 'Hola',
                recipient: 'delamos@micronaut.example',
                textBody: 'Hello')
        HttpRequest request = HttpRequest.POST('/mail/send', cmd) // <3>

        when:
        HttpResponse rsp = client.toBlocking().exchange(request)

        then:
        rsp.status().code == 200
    }

    def "/mail/send can be invoked without htmlBody and not textBody"() {
        given:
        EmailCmd cmd = new EmailCmd(
                subject: 'Hola',
                recipient: 'delamos@micronaut.example',
                htmlBody: '<h1>Hello</h1>')
        HttpRequest request = HttpRequest.POST('/mail/send', cmd) // <3>

        when:
        HttpResponse rsp = client.toBlocking().exchange(request)

        then:
        rsp.status().code == 200
    }
}