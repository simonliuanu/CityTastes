package com.example.smartcity.backend.dataStructure;

import java.util.ArrayList;
import java.util.List;

/**
 * Tokenizer is a class that tokenizes a query string into a list of tokens.
 * It splits the query string by whitespace and returns a list of tokens.
 * It is used to process user input for search queries.
 *
 */
public class Tokenizer {
    public List<String> tokenize(String query) {
        String[] tokens = query.split("\\s+");
        List<String> tokenList = new ArrayList<>();
        for (String token : tokens) {
            tokenList.add(token);
        }
        return tokenList;
    }
}
