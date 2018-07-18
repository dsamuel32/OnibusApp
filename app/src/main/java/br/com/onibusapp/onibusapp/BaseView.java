package br.com.onibusapp.onibusapp;

/**
 * Created by diego on 16/07/2018.
 */

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);

}
