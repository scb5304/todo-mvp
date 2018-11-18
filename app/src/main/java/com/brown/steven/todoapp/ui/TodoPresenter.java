package com.brown.steven.todoapp.ui;

import android.support.annotation.NonNull;

import com.brown.steven.todoapp.api.TodoEndpoint;
import com.brown.steven.todoapp.model.Todo;
import com.brown.steven.todoapp.util.Utility;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class TodoPresenter implements TodoContract.Presenter {

    private TodoEndpoint mEndpoint;
    private TodoContract.View mView;

    private Todo mTodo;

    @Inject
    TodoPresenter(TodoEndpoint todoEndpoint, TodoContract.View view) {
        mEndpoint = todoEndpoint;
        mView = view;
    }

    public void onViewRecreated(TodoContract.View view) {
        mView = view;
        if (mTodo != null) {
            mView.showTodo(mTodo);
        }
    }

    @Override
    public void onGetTodoClicked() {
        String todoId = generateRandomTodoId();
        mEndpoint.getTodoById(todoId).enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(@NonNull Call<Todo> call, @NonNull Response<Todo> response) {
                onGetTodoSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Todo> call, @NonNull Throwable t) {
                onGetTodoFailure(todoId, t);
            }
        });
    }

    private void onGetTodoSuccess(Todo todo) {
        mTodo = todo;
        mView.showTodo(mTodo);
    }

    private void onGetTodoFailure(String todoId, Throwable t) {
        Timber.e(t, "Failed to retrieve Todo %s!", todoId);
        mView.showErrorToast(todoId);
        mView.resetTodoView();
        mTodo = null;
    }

    @Override
    public void onResetTodoClicked() {
        mView.resetTodoView();
    }

    private String generateRandomTodoId() {
        return String.valueOf(Utility.randomNumberBetween(0, 100));
    }
}
