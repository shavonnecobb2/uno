package com.improving;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContextExtensionsKt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringContext.class);
        Map<String, Integer> winCount = new HashMap<>();

        Logger logger = context.getBean(Logger.class);
        Game game = context.getBean(Game.class);
        logger.enabled = false;

        winCount.put("Shavonne", 0);
        winCount.put("Dummy 1", 0);
        winCount.put("Dummy 2", 0);

        for (int i = 0; i < 1000; i++) {
            game.play();
            winCount.put(game.getWinningPlayer().getName(), winCount.get(game.getWinningPlayer().getName()) + 1);
        }

        for (var player : game.getPlayerInfo()) {
            System.out.println("" + player.getName() + " " + winCount.get(player.getName()));
        }


    }
}
