package com.example.citiesApp.loadNames;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UkrainianCitiesEnglishNamesLoader implements CitiesNamesLoader{
    private final static String WIKI_PAGE_URL = "https://en.wikipedia.org/wiki/List_of_cities_in_Ukraine";
    @SuppressWarnings("SpellCheckingInspection")
    private final static String QUERY = "table.wikitable td:eq(0)";
    private final static String REGEX = "^(.*?)\\[(a|b|c|d)\\]$";

    private final Set<String> englishCityNames;

    public UkrainianCitiesEnglishNamesLoader() {
        englishCityNames = new HashSet<>();
        try {
            Document doc = Jsoup.connect(WIKI_PAGE_URL).get();
            Elements references = doc.select(QUERY);
            Pattern pattern = Pattern.compile(REGEX);
            for (Element element : references) {
                String cityName = element.text();
                Matcher matcher = pattern.matcher(cityName);
                if (matcher.matches()) {
                    englishCityNames.add(matcher.group(1));
                } else {
                    englishCityNames.add(cityName);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<String> getCitiesNames() {
        return englishCityNames;
    }
}
