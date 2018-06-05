package example.micronaut;

import java.util.List;

interface Email {
    String getRecipient();
    List<String> getCc();
    List<String> getBcc();
    String getSubject();
    String getHtmlBody();
    String getTextBody();
    String getReplyTo();
}