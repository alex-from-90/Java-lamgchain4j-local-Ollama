package com.jcg;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

@SpringBootApplication
public class StructuredResponseAppApplication {

    @Value("${ollama.model.name}")
    private String modelName;

    @Value("${ollama.host.url}")
    private String baseUrl;

    @Value("${spring.datasource.timeout}")
    private Duration timeout;

    public static void main(String[] args) {
        SpringApplication.run(StructuredResponseAppApplication.class, args);
    }

    @Bean("bookExtractionApplicationRunner")
    ApplicationRunner applicationRunner() {
        return args -> {
            ChatLanguageModel model = OllamaChatModel.builder()
                    .baseUrl(baseUrl)
                    .modelName(modelName)
                    .timeout(timeout)
                    .logRequests(true)
                    .logResponses(true)
                    .build();

            BookExtractor bookExtractor = AiServices.create(BookExtractor.class, model);

            String inputText = """
                    "Эпоха разума" - это работа английского и американского политического деятеля Томаса Пейна,
                    в которой он отстаивает философскую позицию деизма. Впервые опубликованная в 1794 году, она критикует\s
                    институционализированную религию и ставит под сомнение легитимность Библии. Книга засекречена\s
                    в жанре философии и оказал влияние на развитие секуляризма.
                    """;

            Book book = bookExtractor.extract(inputText);

            System.out.println(book);
        };
    }

}
