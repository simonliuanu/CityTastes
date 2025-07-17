package com.example.smartcity.frontend.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartcity.R;
import com.example.smartcity.backend.Iterator.RestaurantRepository;
import com.example.smartcity.frontend.activity.CommentActivity;
import com.example.smartcity.frontend.adapter.ItemListAdapter;
import com.example.smartcity.backend.dao.ItemDao;
import com.example.smartcity.backend.dao.ItemDaoImpl;
import com.example.smartcity.backend.entity.Restaurant;
import com.example.smartcity.util.DataCallback;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents the "Item" page in the application.
 *
 * The load more data function inspired and adapted from Tencent Cloud by '全栈程序员栈长'
 * Reference:
 *  Tencent cloud: https://cloud.tencent.com/developer/article/2042342
 *  Original link: https://javaforall.cn
 */
public class ItemFragment extends Fragment implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener {

    View itemView;
    private List<Restaurant> resList = new ArrayList<>();
    private ItemListAdapter itemListAdapter;
    private View moreDataView;
    private ProgressBar morePg;
    private Button moreBtn;
    private RestaurantRepository restaurantRepository;
    private ItemDao itemDao;
    private Iterator iterator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        itemView = inflater.inflate(R.layout.fragment_item, container, false);

        ListView listView = itemView.findViewById(R.id.item_list);

        //set listener for every item in the list
        listView.setOnItemClickListener(this);

        // Implement the load more data page
        moreDataView = getLayoutInflater().inflate(R.layout.moredata, null);

        moreBtn = moreDataView.findViewById(R.id.more_data_btn);
        morePg = moreDataView.findViewById(R.id.more_data_progress);

        itemDao = new ItemDaoImpl();

        // TODO : here is a deprecated class
        Handler handler = new Handler();

        // Initialize the page
        initiateData();

        itemListAdapter = new ItemListAdapter(getContext(), resList);
        listView.addFooterView(moreDataView);
        listView.setAdapter(itemListAdapter);
        listView.setOnScrollListener(this);


        restaurantRepository = new RestaurantRepository();
        iterator = restaurantRepository.getIterator();

        // once user click the more button, it will load more restaurant
        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                morePg.setVisibility(View.VISIBLE);
                moreBtn.setVisibility(View.GONE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (iterator.hasNext()) {
                            loadMoreData();
                            morePg.setVisibility(View.GONE);
                            moreBtn.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(getContext(), "No more data", Toast.LENGTH_SHORT).show();
                            morePg.setVisibility(View.GONE);
                            moreBtn.setVisibility(View.GONE);
                        }
                        itemListAdapter.notifyDataSetChanged();
                    }
                }, 2000);
            }
        });

        return itemView;
    }

    /**
     * Initialize the list of restaurants by fetching the initial data set from firestore by ItemDao.
     */
    private void initiateData() {
        itemDao.initialItemList(new DataCallback<List<Restaurant>>() {
            @Override
            public void onSuccess(List<Restaurant> result) {
                resList.clear();
                resList.addAll(result);
                itemListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String error) {
                Log.e("itemList", "Error loading data: " + error);
            }
        });
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
    }

    /**
     * Load more restaurant data by adding the next set of restaurants to the existing list.
     */
    public void loadMoreData() {
        resList.addAll((List<Restaurant>) iterator.next());
    }

    /**
     * @param adapterView
     * @param view
     * @param i
     * @param l
     * go to comment page
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this.getContext(), CommentActivity.class);
        Restaurant selectedRestaurant = resList.get(i);
        intent.putExtra("restaurant", selectedRestaurant);
        startActivity(intent);
    }
}

