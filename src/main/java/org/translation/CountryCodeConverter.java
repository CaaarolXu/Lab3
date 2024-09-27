package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
// TODO CheckStyle: Wrong lexicographical order for 'java.util.HashMap' import (remove this comment once resolved)
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {

    private List<String> countries;
    private List<String> alpha3Codes;
    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources folder.
     */
    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));
            this.countries = new ArrayList<>();
            this.alpha3Codes = new ArrayList<>();
            lines.remove(0);
            for (String line : lines) {
                String[] parts = line.split("\t");
                this.countries.add(parts[0]);
                this.alpha3Codes.add(parts[2]);
            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        int i = this.alpha3Codes.indexOf(code.toUpperCase());
        return this.countries.get(i);
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        int i = this.countries.indexOf(country);
        return this.alpha3Codes.get(i);
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        return this.countries.size();
    }
}
