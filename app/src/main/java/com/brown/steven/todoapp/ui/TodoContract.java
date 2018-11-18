package com.brown.steven.todoapp.ui;

import com.brown.steven.todoapp.model.Todo;

class TodoContract {
    interface View {
        void showTodo(Todo todo);
        void showErrorToast(String todoId);
        void resetTodoView();
    }

    interface Presenter {
        void onViewRecreated(View view);
        void onGetTodoClicked();
        void onResetTodoClicked();
    }
}
