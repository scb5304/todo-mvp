package com.brown.steven.todoapp.ui;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract public class TodoModule {
    @ContributesAndroidInjector
    abstract TodoActivity contributeTodoActivity();

    @Binds
    abstract TodoContract.Presenter contributePresenter(TodoPresenter presenter);

    @Binds
    abstract TodoContract.View contributeActivity(TodoActivity todoActivity);
}
