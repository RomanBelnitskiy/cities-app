package com.example.citiesApp.loadNames;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class UkrainianCitiesNamesLoader implements CitiesNamesLoader {
    private static final String WIKI_PAGE_URL = "https://en.wikipedia.org/wiki/List_of_cities_in_Ukraine";
    @SuppressWarnings("SpellCheckingInspection")
    private static final String QUERY = "a.extiw";

    private final Set<String> cityNames;

    public UkrainianCitiesNamesLoader() {
        cityNames = new HashSet<>();
        try {
            Document doc = org.jsoup.Jsoup.connect(WIKI_PAGE_URL).get();
            Elements references = doc.select(QUERY);
            for (Element ref : references) {
                cityNames.add(ref.text());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<String> getCitiesNames() {
        return cityNames;
    }
}
