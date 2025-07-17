package com.example.smartcity.backend.dataStructure;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser is a class that corrects misspelled tokens in a list of tokens.
 * It uses the Levenshtein distance algorithm to find the closest valid token.
 * It is used to correct user input for search queries.
 *
 */
public class Parser {

    private List<String> validTokens;

    /**
     * Constructor for Parser class.
     *
     * @param validTokens A list of valid tokens
     */
    public Parser(List<String> validTokens) {
        this.validTokens = validTokens;
    }

    /**
     * Parses a list of tokens and corrects any misspelled tokens.
     *
     * @param tokens The list of tokens to parse
     * @return A list of corrected tokens
     */
    public List<String> parse(List<String> tokens) {
        List<String> parsedTokens = new ArrayList<>();
        for (String token : tokens) {
            String correctedToken = findClosestValidToken(token);
            parsedTokens.add(correctedToken);
        }
        return parsedTokens;
    }

    /**
     * Finds the closest valid token to a given token.
     * It calculates the Levenshtein distance between the given token and each valid token.
     * It returns the valid token with the smallest distance.
     *
     * @param token The token to correct
     * @return The closest valid token
     */
    private String findClosestValidToken(String token) {
        String closestToken = token;
        int minDistance = Integer.MAX_VALUE;
        String lowerCaseToken = token.toLowerCase();

        for (String validToken : validTokens) {
            int distance = levenshteinDistance(lowerCaseToken, validToken.toLowerCase());
            if (distance < minDistance) {
                minDistance = distance;
                closestToken = validToken;
            }
        }

        return closestToken;
    }

    /**
     * Calculates the Levenshtein distance between two strings.
     * It uses dynamic programming to calculate the minimum number of single-character edits
     * (insertions, deletions, or substitutions) required to change one string into another.
     *
     * @param a The first string
     * @param b The second string
     * @return The Levenshtein distance between the two strings
     */
    private int levenshteinDistance(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1),
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
                }
            }
        }

        return dp[a.length()][b.length()];
    }
}