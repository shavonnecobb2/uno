package com.improving;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContextExtensionsKt;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringContext.class);

        Game game = context.getBean(Game.class);
        game.play();

    }
}
