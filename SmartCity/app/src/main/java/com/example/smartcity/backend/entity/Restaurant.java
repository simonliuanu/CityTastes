package com.example.smartcity.backend.entity;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

/**
 * Represents a Restaurant entity with relevant details.
 * This class implements Serializable to allow the restaurant data to be serialized,
 * and Comparable to allow sorting based on restaurant names.
 *
 */
public class Restaurant implements Serializable, Comparable<Restaurant> {
    private int id;
    private String name;
    private double rating;
    private String address;
    private String photo_url;
    private double latitude;
    private double longitude;
    private List<String> types;
    private Integer price_level;
    private String estimated_price;
    private int user_ratings_total;

    public Restaurant() {
    }

    /**
     * used to set filter restaurants types
     */
    public enum RestaurantType {
        CAFE, LODGING, FOOD, BAR
    }

    /**
     * Constructs a Restaurant object with specified details.
     *
     * @param id                the unique identifier for the restaurant
     * @param name              the name of the restaurant
     * @param rating            the rating of the restaurant (out of 5)
     * @param address           the address of the restaurant
     * @param photo_url         the URL of the restaurant's photo
     * @param latitude          the latitude of the restaurant's location
     * @param longitude         the longitude of the restaurant's location
     * @param types             the list of types associated with the restaurant
     * @param priceLevel        the price level of the restaurant
     * @param estimatedPrice    the estimated price range for meals at the restaurant
     * @param userRatingsTotal  the total number of user ratings for the restaurant
     */
    public Restaurant(int id, String name, double rating, String address, String photo_url,
                      double latitude, double longitude, List<String> types,
                      int priceLevel, String estimatedPrice, int userRatingsTotal) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.address = address;
        this.photo_url = photo_url;
        this.latitude = latitude;
        this.longitude = longitude;
        this.types = (types != null) ? types : new ArrayList<>();
        this.price_level = priceLevel;
        this.estimated_price = estimatedPrice;
        this.user_ratings_total = userRatingsTotal;
    }



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<String> getTypes() {
        return types;
    }

    public int getPrice_level() {
        return price_level;
    }

    public void setPrice_level(int price_level) {
        this.price_level = price_level;
    }

    public String getEstimated_price() {
        return estimated_price;
    }

    /**
     * Returns an integer representation of the estimated price level.
     *
     * @return the price level as an integer based on the estimated price string
     *
     */
    public int getPrice() {
        switch (estimated_price) {
            case "$10-$25":
                return 1;
            case "$25-$50":
                return 2;
            case "$50-$100":
                return 3;
            case "$100+":
                return 4;
            case "Unknown":
            default:
                return 5;
        }
    }

    public void setEstimated_price(String estimated_price) {
        this.estimated_price = estimated_price;
    }

    public int getUser_ratings_total() {
        return user_ratings_total;
    }

    public void setUser_ratings_total(int user_ratings_total) {
        this.user_ratings_total = user_ratings_total;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                "name='" + name + '\'' +
                ", rating=" + rating +
                ", address='" + address + '\'' +
                ", photoUrl='" + photo_url + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", types=" + (types != null ? String.join(", ", types) : "[]") +
                ", priceLevel='" + price_level + '\'' +
                ", estimatedPrice='" + estimated_price + '\'' +
                ", userRatingsTotal=" + user_ratings_total +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Restaurant)) return false;
        Restaurant that = (Restaurant) o;
        return Double.compare(that.rating, rating) == 0 &&
                Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0 &&
                user_ratings_total == that.user_ratings_total &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                Objects.equals(photo_url, that.photo_url) &&
                Objects.equals(types, that.types) &&
                price_level == that.price_level &&
                Objects.equals(estimated_price, that.estimated_price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, rating, address, photo_url, latitude, longitude, types, price_level, estimated_price, user_ratings_total);
    }

    /**
     * Compares this restaurant with the specified restaurant for order.
     *
     * @param other the restaurant to be compared
     * @return a negative integer, zero, or a positive integer as this restaurant
     *         is less than, equal to, or greater than the specified restaurant
     *
     */
    @Override
    public int compareTo(Restaurant other) {
        return this.name.compareTo(other.name);
    }

    /**
     * This function used to display the type
     * as ordinal data have some redundant types, we need filter them
     * @return restaurant type after filter
     */
    public String getDisplayedType() {
        StringBuilder typeBuilder = new StringBuilder();
        for (RestaurantType value : RestaurantType.values()) {
            if (types.contains(value.toString().toLowerCase())) {
                typeBuilder.append(" ").append(value.toString().toLowerCase()).append(",");
            }
        }

        return typeBuilder.toString().substring(0,typeBuilder.length() - 1);
    }
}
