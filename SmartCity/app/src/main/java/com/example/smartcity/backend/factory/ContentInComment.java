package com.example.smartcity.backend.factory;
/**
 *  ContentInComment class is used to generate the content of comment
 *  it is the usage of the factory method
 */
public class ContentInComment implements CommentItem {
    @Override
    public String getItem() {
        return randomComments[(int) (Math.random() * randomComments.length)];
    }

    String[] randomComments = {
            "Great food! The flavors were absolutely delightful, and I couldn't have asked for a better dining experience.",
            "Amazing service! The staff was very attentive indeed, making sure we didn't lack anything during the meal.",
            "Would definitely come again! Perfect setting to go out with any number of friends, from going out or a romantic dinner.",
            "A bit too expensive, if I had to say. Food was good, though-the price for the amount of food presented was too high, I thought.",
            "I love it! Unique dishes and creative presentation made this restaurant particular in the area.",
            "The food was mediocre, to say the best. I had hoped for more from the review, but it did not live up to my expectation.",
            "The wait was too long, and that really soured my experience in this restaurant.",
            "The portions were small given the price paid for each, thus making me unsatisfied and disappointed.",
            "The dessert was amazing. It was the highlight of the meal and something that stood out to me after the meal.",
            "The ambiance was nice, but the noise level made it very difficult to hold a conversation with my dining companions.",
            "I had high hopes for this place, but the flavors were bland and I was unsatisfied with my meal.",
            "Service was slow and unresponsive; it took them ages to take our orders and serve the food.",
            "I loved that they used fresh ingredients for the menu items. This made everything so vibrant and appetizing.",
            "Unfortunately, the restaurant was understaffed, which led to such disarray within the dining experience and long wait times to be serviced.",
            "Whereas the appetizers were great, the main course did not reach the same standard, and to that extent, it was disappointing."
    };
}
