package com.example.newsapp.presenter;

public interface MainViewInterface {

    void showToast(String s);
    void displayError(String s);
    void showProgress();
    void hideProgress();

}
