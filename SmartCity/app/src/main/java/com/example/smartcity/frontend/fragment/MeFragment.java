package com.example.smartcity.frontend.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartcity.R;
import com.example.smartcity.frontend.activity.CommentActivity;
import com.example.smartcity.frontend.adapter.ItemListAdapter;
import com.example.smartcity.backend.entity.User;
import com.example.smartcity.backend.observer.LikeRestaurant;
import com.example.smartcity.backend.entity.Restaurant;
import com.example.smartcity.backend.observer.LikeRestaurantObserver;

import java.util.ArrayList;

/**
 * Represents the "Me" page in the application.
 *
 */
public class MeFragment extends Fragment implements LikeRestaurantObserver {
    View meView;
    private ItemListAdapter itemListAdapter;
    private Spinner filter;
    private ArrayList<Restaurant> filterRes;
    private LikeRestaurant likeRes;
    private String curType;
    private final String TYPE_ALL = "all";
    private TextView meUserName;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        meView = inflater.inflate(R.layout.fragment_me, container, false);

        ListView likeList = meView.findViewById(R.id.me_like_list);
        filter = meView.findViewById(R.id.me_spinner);
        meUserName = meView.findViewById(R.id.me_user_name);

        // update the display message of login user
        User loginUser = User.getInstance();
        meUserName.setText(loginUser.getLoginUserName());

        likeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant selectedRestaurant = itemListAdapter.getItem(position);

                Intent intent = new Intent(getContext(), CommentActivity.class);
                intent.putExtra("restaurant", selectedRestaurant);
                startActivity(intent);
            }
        });

        likeRes = LikeRestaurant.getInstance();
        // as the default type is "all", so the filterRes is likeRes
        filterRes = new ArrayList<>(likeRes);

        itemListAdapter = new ItemListAdapter(getContext(), filterRes);
        likeList.setAdapter(itemListAdapter);

        likeRes.attach(this);

        // set the spinner types
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new String[]{"All", "Bar", "Cafe", "Food", "Lodging"});
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filter.setAdapter(spinnerAdapter);


        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // update the filter res list by type
                String selectedGroup = (String) parentView.getItemAtPosition(position);
                curType = selectedGroup.toLowerCase();
                filterRestaurantsByType(curType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                filterRestaurantsByType(TYPE_ALL);
            }
        });

        Button logoutBtn = meView.findViewById(R.id.me_logout_btn);

        // back to the login page
        logoutBtn.setOnClickListener(v -> {
            Toast.makeText(getContext(), "You have logged out", Toast.LENGTH_SHORT).show();
            // as the follow only store in memory, it will be cleaned when user logout
            likeRes.clear();
            requireActivity().finish();
        });

        return meView;
    }

    /**
     * Filter the list of filter restaurants based on the selected type from the spinner.
     *
     * @param type The type of restaurant to filter by (e.g., "all", "bar", "cafe", "food", "lodging").
     */
    private void filterRestaurantsByType(String type) {
        filterRes.clear();
        if (type.equals(TYPE_ALL)) {
            filterRes.addAll(likeRes);
        } else {
            for (Restaurant restaurant : likeRes) {
                if (restaurant.getTypes().contains(type)) {
                    filterRes.add(restaurant);
                }
            }
        }
        itemListAdapter.notifyDataSetChanged();
    }

    /**
     * When the state of likeRes changed (e.g. user unlike a restaurant)
     * update the filter restaurant to show the page after removing the
     * corresponding restaurant
     *
     */
    @Override
    public void update() {
        filterRestaurantsByType(curType);
        itemListAdapter.notifyDataSetChanged();
    }
}
