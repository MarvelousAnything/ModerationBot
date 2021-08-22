package com.yoursole1;

import com.yoursole1.service.AdminService;
import com.yoursole1.service.BannedWordService;
import com.yoursole1.service.JDAService;
import com.yoursole1.service.ModeratorService;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;

@Slf4j
public class Main {
    public static final ModeratorService moderatorService = ModeratorService.getInstance();
    public static final AdminService adminService = AdminService.getInstance();
    public static final BannedWordService bannedWordService = BannedWordService.getInstance();
    public static JDA jda;

    public static void main(String[] args) throws InterruptedException {
        jda = JDAService.getInstance().getJda();
        jda.awaitReady();
    }
}
