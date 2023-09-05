package com.example.citiesapp.loadNames;

import com.example.citiesapp.loadNames.CitiesNamesLoader;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class UkrainianCitiesNamesLoader implements CitiesNamesLoader {
    private static final String WIKI_PAGE_URL = "https://en.wikipedia.org/wiki/List_of_cities_in_Ukraine";

    private Set<String> cityNames;

    public UkrainianCitiesNamesLoader() {
        cityNames = new HashSet<>();
        try {
            Document doc = org.jsoup.Jsoup.connect(WIKI_PAGE_URL).get();
            Elements references = doc.select("a.extiw");
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
