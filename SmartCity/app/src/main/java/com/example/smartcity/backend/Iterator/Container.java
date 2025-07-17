package com.example.smartcity.backend.Iterator;

import java.util.Iterator;

/**
 * Represent a collection
 */
public interface Container {

    /**
     * Returns an iterator.
     *
     * @return an Iterator object
     */
    Iterator getIterator();
}
