package es.cheste.UD1.practica;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

public class EJEMPLO3_PropertiesLanguages {

    private static final Logger LOGGER = LogManager.getRootLogger();

    public static void main(String[] args) throws Exception {
        String[] i18n = {"es", "en", "es_ca", "ru"};
        for (String languaje : i18n){
            Locale locale = new Locale(languaje);
            loadLabel(locale);
        }
    }

    private static void loadLabel(Locale locale) {
        ResourceBundle labels = ResourceBundle.getBundle("languages.labels", locale);
        System.out.println(labels.getString("cancel"));
        System.out.println(labels.getString("accept"));
        System.out.println(labels.getString("reset"));

        ResourceBundle messages = ResourceBundle.getBundle("languages.prueba", locale);
        System.out.println(messages.getString("prueba"));
    }
}
