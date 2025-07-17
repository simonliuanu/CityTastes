package com.example.smartcity.backend.Iterator;

import com.example.smartcity.backend.dataStructure.AvlTree;
import com.example.smartcity.backend.dataStructure.AvlTreeManager;
import com.example.smartcity.backend.entity.Restaurant;

import java.util.Iterator;
import java.util.List;

/**
 * This class implements the Container interface
 * and provides an iterator for retrieving pages of Restaurant objects.
 *
 */
public class RestaurantRepository implements Container {

    /**
     * Return an instance of RestaurantIterator to iterate through restaurant pages.
     *
     * @return a RestaurantIterator object
     */
    @Override
    public RestaurantIterator getIterator() {
        return new RestaurantIterator();
    }

    private int curPage = 1;

    AvlTree<Restaurant> instance = AvlTreeManager.getInstance();

    /**
     * Inner class that implements the Iterator interface to iterate through
     * restaurant data in a paginated manner.
     */
    private class RestaurantIterator implements Iterator<List<Restaurant>> {


        private static final int PER_PAGE_LIMITS = 12;
        private static final int TOTAL_DATA = 3503;

        /**
         * Checks if there is more data in next page.
         *
         * @return true if there are more pages to fetch, false otherwise
         */
        @Override
        public boolean hasNext() {
            return curPage * PER_PAGE_LIMITS <= TOTAL_DATA;
        }

        /**
         * Retrieves the next page of restaurants from the AVL tree.
         * <p>
         * If there is more data, it returns the next 12 restaurants and
         * increments the current page counter. Otherwise, it returns null.
         *
         * @return a list of Restaurants for the next page or null if no more data
         */
        @Override
        public List<Restaurant> next() {
            List<Restaurant> nextPageRes;
            if (this.hasNext()) {
                nextPageRes = instance.toList().subList(curPage * PER_PAGE_LIMITS, ++curPage * PER_PAGE_LIMITS);
            } else {
                nextPageRes = null;
            }
            return nextPageRes;
        }
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }
}
