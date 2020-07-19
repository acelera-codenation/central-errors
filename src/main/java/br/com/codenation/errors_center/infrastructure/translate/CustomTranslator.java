package br.com.codenation.errors_center.infrastructure.translate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CustomTranslator {

    private static MessageSource messageSource;

    @Autowired
    CustomTranslator(MessageSource messageSource) {
        CustomTranslator.messageSource = messageSource;
    }

    public static String toLocale(String msg) {
        return messageSource.getMessage(msg, null, LocaleContextHolder.getLocale());
    }

    public static String toLocale(String msg, String... args) {
        return messageSource.getMessage(msg, args, LocaleContextHolder.getLocale());
    }
}
