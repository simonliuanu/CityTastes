package com.example.smartcity.frontend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.smartcity.R;
import com.example.smartcity.backend.observer.LikeRestaurant;
import com.example.smartcity.backend.entity.Restaurant;

import java.util.List;

/**
 * This adapter for displaying a list of Restaurants in listview within a fragment.
 *
 * the adapter catch the item_list layout and put
 * the attribute List<Restaurant> in the list layout
 * once it be called by the itemFragment
 * the listView in itemFragment will be filled by the item_list.xml
 *
 * The usage of ViewHolder adapter from CSDN blog by '泊停Bo'
 *
 * When sliding the list on the 'item' page, there is a situation where the position of the item is
 * repeated. That is, only the position value of number of the item that can be displayed on the
 * current page is loaded, and duplicate items will appear when sliding.
 *
 * To solve this bug, introduced the ViewHolder class for management
 *
 *  Reference:
 *      https://blog.csdn.net/qq_39402590/article/details/90473268
 */
public class ItemListAdapter extends ArrayAdapter<Restaurant> {

    private List<Restaurant> list;

    public ItemListAdapter(@NonNull Context context, List<Restaurant> list) {
        super(context, R.layout.item_list,list);
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // use viewHolder to solve the repeat list in list view;
        ViewHolder holder;

        LikeRestaurant likeRes = LikeRestaurant.getInstance();

        // if null, create a new view
        if(convertView == null) {
            LayoutInflater from = LayoutInflater.from(getContext());
            // appear a bug on attachToRoot, without false, will
            // show  addView(View, LayoutParams) is not supported in AdapterView
            convertView = from.inflate(R.layout.item_list, parent,false);
            // Initialize the ViewHolder and find the views
            holder = new ViewHolder();
            System.out.println("viewerHolder update, this is " + holder.holdName);
            // store the list attribute in view holder
            holder.holdImage = convertView.findViewById(R.id.item_image);
            holder.holdName = convertView.findViewById(R.id.item_restaurant_name);
            holder.holdAddress = convertView.findViewById(R.id.item_restaurant_address);
            holder.holdRate = convertView.findViewById(R.id.item_restaurant_rate);
            holder.holdPrice = convertView.findViewById(R.id.item_restaurant_price);
            holder.holdType = convertView.findViewById(R.id.item_restaurant_type);
            holder.likeBtn = convertView.findViewById(R.id.item_like_btn);

            // Store the holder with the view
            convertView.setTag(holder);
        } else {
            // Retrieve the holder
            holder = (ViewHolder) convertView.getTag();
        }

        Restaurant curRestaurant = list.get(position);

        Glide.with(convertView.getContext()).load(curRestaurant.getPhoto_url()).into(holder.holdImage);
        holder.holdName.setText(curRestaurant.getName());
        holder.holdAddress.setText(curRestaurant.getAddress());
        holder.holdRate.setText(String.valueOf(curRestaurant.getRating()));
        holder.holdType.setText(curRestaurant.getDisplayedType());
        holder.holdPrice.setText(curRestaurant.getEstimated_price());

        // to make sure all the restaurant is non like initialization
        if (likeRes.contains(curRestaurant)) {
            holder.likeBtn.setImageResource(R.mipmap.item_like_btn_on);
        } else {
            holder.likeBtn.setImageResource(R.mipmap.item_like_btn_off);
        }

        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Restaurant chosenRes = list.get(position);
                if(likeRes.contains(chosenRes)) {
                    likeRes.remove(list.get(position));
                    /* image from https://www.iconfont.cn/collections/detail?spm=a313x.search_index.0.da5a778a4.662f3a81xOEdMU&cid=7077 */
                    holder.likeBtn.setImageResource(R.mipmap.item_like_btn_off);
                    // update the item list in time when user unlike a restaurant
                    // this refresh used in me page
                    notifyDataSetChanged();
                } else {
                    likeRes.add(chosenRes);
                    /* image from https://www.iconfont.cn/collections/detail?spm=a313x.search_index.0.da5a778a4.662f3a81xOEdMU&cid=7077 */
                    holder.likeBtn.setImageResource(R.mipmap.item_like_btn_on);
                    notifyDataSetChanged();
                }
            }
        });
        return convertView;
    }

    /**
     * A ViewHolder class to hold references to the views within each list item.
     */
    static class ViewHolder {
        ImageView likeBtn;
        ImageView holdImage;
        TextView holdName;
        TextView holdAddress;
        TextView holdRate;
        TextView holdPrice;
        TextView holdType;
    }
}
