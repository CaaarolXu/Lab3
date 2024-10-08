package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    private JSONArray jsonArray;
    private ArrayList<String> coun = new ArrayList<>();
    private ArrayList<String> lang = new ArrayList<>();

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {
            String jsonString = Files.readString(Paths.get(getClass().getClassLoader()
                                                .getResource(filename).toURI()));
            this.jsonArray = new JSONArray(jsonString);
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        ArrayList<String> languages = new ArrayList<>();
        for (int i = 0; i < this.jsonArray.length(); i++) {
            JSONObject o = this.jsonArray.getJSONObject(i);
            if (o.getString("alpha3").equals(country)) {
                for (String key : o.keySet()) {
                    languages.add(key);
                }
                languages.remove("id");
                languages.remove("alpha2");
                languages.remove("alpha3");
            }
        }
        return languages;
    }

    @Override
    public List<String> getCountries() {
        ArrayList<String> countries = new ArrayList<>();
        for (int i = 0; i < this.jsonArray.length(); i++) {
            JSONObject o = this.jsonArray.getJSONObject(i);
            countries.add(o.getString("alpha3"));
        }
        return countries;
    }

    @Override
    public String translate(String country, String language) {
        for (int i = 0; i < this.jsonArray.length(); i++) {
            JSONObject o = this.jsonArray.getJSONObject(i);
            if (o.getString("alpha3").equals(country)) {
                for (String key : o.keySet()) {
                    if (key.equals(language)){
                        return o.getString(key);
                    }
                }
            }
        }
        return null;
    }
}
