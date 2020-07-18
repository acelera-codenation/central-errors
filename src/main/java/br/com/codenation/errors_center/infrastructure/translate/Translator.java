package br.com.codenation.errors_center.infrastructure.translate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Translator {

    private static MessageSource messageSource;

    @Autowired
    Translator(MessageSource messageSource) {
        Translator.messageSource = messageSource;
    }

    public static String toLocale(String msg) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msg, null, locale);
    }

    public static String toLocale(String msg, String... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msg, args, locale);
    }
}
