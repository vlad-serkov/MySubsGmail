package com.example.gmail;

import com.google.api.client.util.StringUtils;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;

public class GmailService {
    public static void getMailBody(String searchString, Gmail service, String user) throws IOException {

        // Access Gmail inbox

        Gmail.Users.Messages.List request = service.users().messages().list(user).setQ(searchString);

        ListMessagesResponse messagesResponse = request.execute();
        request.setPageToken(messagesResponse.getNextPageToken());

        // Get ID of the email you are looking for
        String messageId = messagesResponse.getMessages().get(0).getId();

        Message message = service.users().messages().get(user, messageId).execute();

        // Print email body

        String emailBody = StringUtils
                .newStringUtf8(Base64.decodeBase64(message.getPayload().getParts().get(0).getBody().getData()));

        System.out.println("Email body : " + emailBody);

    }
}
