package com.example.jwtoken.listener;

import com.example.jwtoken.entity.Email;
import com.example.jwtoken.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Async
@Slf4j
@Component
@RequiredArgsConstructor
public class SignupListener {

    private final EmailRepository emailRepository;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onSignupEvent(SignupEvent signupEvent) {
        log.info("send email to : {}, content: {}", signupEvent.getEmail(), signupEvent.getContent());
        emailRepository.save(Email.of(signupEvent.getEmail(), signupEvent.getContent()));
    }

}
