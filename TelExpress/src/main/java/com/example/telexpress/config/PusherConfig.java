package com.example.telexpress.config;

import com.pusher.rest.Pusher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class PusherConfig {
    @Bean
    public Pusher pusher(){
        Pusher pusher = new Pusher("1911295", "c2b569641488ac763e5c", "14b2d3b2842cd6079316");
        pusher.setCluster("us2");
        pusher.setEncrypted(true);

        return pusher;
    }
}
