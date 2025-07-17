package com.example.smartcity.backend.dataStructure;

import com.example.smartcity.backend.entity.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * AvlTree is a generic AVL tree implementation.
 * It supports insertion, searching, and traversal operations.
 * It is used to store and manage data in a balanced binary search tree.
 * It supports generic data types that implement the Comparable interface by default.
 *
 * @param <T> the type of data to be stored in the AVL tree
 */
public class AvlTree<T extends Comparable<T>> {
    public static class Node<T> {
        private T data;
        private Node<T> left;
        private Node<T> right;
        private int height;

        public Node(T data) {
            this.data = data;
            this.height = 1;
        }

        public T getData() {
            return data;
        }

        public Node<T> getLeft() {
            return left;
        }

        public Node<T> getRight() {
            return right;
        }

        public int getHeight() {
            return height;
        }

        public String toString() {
            return data.toString();
        }
    }

    private Node<T> root;

    // Insert data into the AVL tree
    public void insert(T data) {
        root = insert(root, data);
    }

    private Node<T> insert(Node<T> node, T data) {
        if (node == null) {
            return new Node<>(data);
        }
        int cmp = data.compareTo(node.data);
        if (cmp < 0) {
            node.left = insert(node.left, data);
        } else if (cmp > 0) {
            node.right = insert(node.right, data);
        } else {
            return node;
        }
        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node);
    }

    // Search for an exact match by name
    public T searchExact(String name) {
        return searchExact(root, name);
    }

    public T searchExact(Node<T> node, String name) {
        if (node == null) {
            return null;
        }
        Restaurant restaurant = (Restaurant) node.data;
        int cmp = name.compareTo(restaurant.getName());
        if (cmp < 0) {
            return searchExact(node.left, name);
        } else if (cmp > 0) {
            return searchExact(node.right, name);
        } else {
            return node.data;
        }
    }

    // Search for nodes whose names contain the given prefix
    public List<T> searchByContains(String prefix) {
        List<T> list = new ArrayList<>();
        searchByContains(root, prefix, list);
        return list;
    }

    public void searchByContains(Node<T> node, String prefix, List<T> list) {
        if (node == null) {
            return;
        }
        Restaurant restaurant = (Restaurant) node.data;
        if (restaurant.getName().contains(prefix)) {
            list.add(node.data);
        }
        searchByContains(node.left, prefix, list);
        searchByContains(node.right, prefix, list);
    }

    // Convert the AVL tree to a list in order
    public List<T> toList() {
        List<T> list = new ArrayList<>();
        inOrderTraversal(root, list);
        return list;
    }

    private void inOrderTraversal(Node<T> node, List<T> list) {
        if (node != null) {
            inOrderTraversal(node.left, list);
            list.add(node.data);
            inOrderTraversal(node.right, list);
        }
    }

    // Balance the AVL tree
    private Node<T> balance(Node<T> node) {
        if (node == null) return null;

        int balance = height(node.left) - height(node.right);
        if (balance > 1) {
            if (height(node.left.left) < height(node.left.right)) {
                node.left = rotateLeft(node.left);
            }
            node = rotateRight(node);
        } else if (balance < -1) {
            if (height(node.right.right) < height(node.right.left)) {
                node.right = rotateRight(node.right);
            }
            node = rotateLeft(node);
        }
        return node;
    }

    // Rotate the subtree lef
    private Node<T> rotateLeft(Node<T> node) {
        Node<T> newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;
        node.height = 1 + Math.max(height(node.left), height(node.right));
        newRoot.height = 1 + Math.max(height(newRoot.left), height(newRoot.right));
        return newRoot;
    }

    // Rotate the subtree right
    private Node<T> rotateRight(Node<T> node) {
        Node<T> newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;
        node.height = 1 + Math.max(height(node.left), height(node.right));
        newRoot.height = 1 + Math.max(height(newRoot.left), height(newRoot.right));
        return newRoot;
    }

    private int height(Node<T> node) {
        return node == null ? 0 : node.height;
    }

    // Calculate the total number of nodes
    public int countNodes() {
        return countNodes(root);
    }

    private int countNodes(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    public Node<T> getRoot() {
        return root;
    }
}
