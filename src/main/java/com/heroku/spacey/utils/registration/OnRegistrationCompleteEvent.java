package com.heroku.spacey.utils.registration;

import com.heroku.spacey.entity.User;
import com.heroku.spacey.utils.EmailComposer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private EmailComposer emailComposer;
    private User user;

    public OnRegistrationCompleteEvent(User user, EmailComposer emailComposer) {
        super(user);

        this.user = user;
        this.emailComposer = emailComposer;
    }
}
