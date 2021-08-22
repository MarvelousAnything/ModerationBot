package com.yoursole1;

import com.yoursole1.service.JDAService;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;

@Slf4j
public class Main {
    public static void main(String[] args) {
        JDA jda = JDAService.getInstance().getJda();
        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            log.error("Interrupted", e);
            Thread.currentThread().interrupt();
        }
    }
}
