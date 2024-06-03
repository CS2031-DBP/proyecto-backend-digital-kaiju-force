package com.dkf.ODAD.Event;

import com.dkf.ODAD.Email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class emailListener {

    @Autowired
    private EmailService emailService;

    @EventListener
    @Async
    public void handleEmailEvent(emailEvent event){
        emailService.sendSimpleMessage(event.getEmail(), "Muchas gracias por registrarse en ODAD.", "Ahora puede usar nuestro servicio y recibir atención médica a domicilio.");
    }

}
