package com.brown.steven.todoapp.app;

import com.brown.steven.todoapp.ui.TodoModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        TodoModule.class
})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        AppComponent build();
    }

    void inject(TodoApplication application);
}
