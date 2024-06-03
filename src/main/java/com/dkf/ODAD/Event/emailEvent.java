package com.dkf.ODAD.Event;

import org.springframework.context.ApplicationEvent;

public class emailEvent extends ApplicationEvent {

    private final String email;

    public emailEvent(String email){
        super(email);
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

}
