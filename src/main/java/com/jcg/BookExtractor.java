package com.jcg;

import dev.langchain4j.service.UserMessage;

interface BookExtractor {

    @UserMessage("""
            Извлеките название, автора, год издания и жанр книги, описанные ниже.
            Верните только JSON, без какой-либо разметки markdown.
            Вот документ, описывающий книгу:
            ---
            {{it}}
            """)
    Book extract(String text);

}
