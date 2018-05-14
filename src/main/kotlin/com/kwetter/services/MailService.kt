package com.kwetter.services

import com.kwetter.models.User
import java.util.Date
import javax.annotation.Resource
import javax.ejb.Stateless
import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart


@Stateless
class MailService {

    @Resource(name = "mail/kwetter")
    private lateinit var session: Session

    fun mailRegister(user: User) {
        this.mail("Thank you for registering ${user.username}", "Thank you for registering", "shaikunll@gmail.com")
    }

    fun mail(text: String, subject: String, recipient: String) {
        val message: Message = MimeMessage(session)

        message.subject = subject
        message.setFrom(InternetAddress.parse("shaikunll@gmail.com", false)[0])

        // Adjust to, cc, bcc: comma-separated Strings of email addresses
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, false))

        // Create the message body part
        val messageBodyPart = MimeBodyPart()

        // Insert the message's body
        messageBodyPart.setText(text)

        // create a multipart for different parts of the message
        val multipart = MimeMultipart()

        // add message's body to multipart
        multipart.addBodyPart(messageBodyPart)

        // This is not mandatory, however, it is a good
        // practice to indicate the software which
        // constructed the message.
        message.setHeader("X-Mailer", "Java Mail API")

        // set multipart as content of the message
        message.setContent(multipart)

        // Adjust the date of sending the message
        message.sentDate = Date()

        // Use the 'send' static method of the Transport
        // class to send the message
        Transport.send(message)
    }
}