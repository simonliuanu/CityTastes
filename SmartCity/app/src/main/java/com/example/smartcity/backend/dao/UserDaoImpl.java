package com.example.smartcity.backend.dao;


import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.smartcity.backend.entity.User;
import com.example.smartcity.util.LoginCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * Implementation of the UserDao interface to interact with Firebase database.
 */
public class UserDaoImpl implements UserDao {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef = db.collection("users");

    /**
     * This method used to check if the login user exist
     *
     * Since Firebase Firestore queries are asynchronous, we cannot directly
     * get the return value of the callback function externally, and need to
     * wait for the callback to complete before processing the returned result.
     *
     * @param user the info of login user with name and pwd
     * @param callback to process the survey results
     */
    @Override
    public void checkUser(User user, LoginCallback callback) {
        Query query = usersRef.whereEqualTo("name", user.getName()).whereEqualTo("pwd", user.getPwd());

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    System.out.println("Task successful");
                    if (!task.getResult().isEmpty()) {
                        System.out.println("Get login user's information");
                        callback.onCallback(true);
                        System.out.println("Update the user state");
                    } else {
                        System.out.println("Login user not exist");
                        callback.onCallback(false);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    callback.onCallback(false);
                }
            }
        });
    }

    /**
     * Add initial user information to Firebase
     */
    @Override
    public void initializeUser() {
        User testUser1 = new User("demo@citytastes.com", "demo123");
        User testUser2 = new User("test@citytastes.com", "test123");

        db.collection("users").document("demo").set(testUser1);
        db.collection("users").document("test").set(testUser2);
    }
}

