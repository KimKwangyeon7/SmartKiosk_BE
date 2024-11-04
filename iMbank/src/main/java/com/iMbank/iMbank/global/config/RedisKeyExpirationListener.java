package com.iMbank.iMbank.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisKeyExpirationListener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();

        log.info("======================= Received expired key event for key: {}", expiredKey);
    }
}
