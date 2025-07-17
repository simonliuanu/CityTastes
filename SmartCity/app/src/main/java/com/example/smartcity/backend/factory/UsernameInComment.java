package com.example.smartcity.backend.factory;
/**
 *  UsernameInComment class is used to generate the username of comment
 *  it is the usage of the factory method
 */
public class UsernameInComment implements CommentItem{
    @Override
    public String getItem() {
        return randomUserName[(int) (Math.random() * randomUserName.length)];
    }
    String[] randomUserName = {"Mike Smith", "Simon", "Lily Brown", "Emma", "Noah Wilson", "Lucas Lee",
            "Sophia", "Ava Anderson", "Oliver Thomas", "Ethan", "Mia White",
            "Isabella Harris", "Liam", "Amelia Thompson", "Jackson Garcia", "Harper",
            "Aiden Robinson", "Charlotte Clark", "Caleb", "Grace Walker"};
}
