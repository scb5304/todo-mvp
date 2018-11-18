package com.brown.steven.todoapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.brown.steven.todoapp.R;
import com.brown.steven.todoapp.model.Todo;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class TodoActivity extends AppCompatActivity implements TodoContract.View {

    @BindView(R.id.todo_show_textview)
    TextView mTodoTextView;

    @Inject
    TodoContract.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        injectPresenter();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            mPresenter.onViewRecreated(this);
        }
    }

    private void injectPresenter() {
        if (getLastCustomNonConfigurationInstance() != null) {
            mPresenter = (TodoContract.Presenter) getLastCustomNonConfigurationInstance();
        } else {
            AndroidInjection.inject(this);
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mPresenter;
    }

    @OnClick(R.id.todo_get_button)
    void getTodoClicked() {
        mPresenter.onGetTodoClicked();
    }

    @OnClick(R.id.todo_reset_button)
    void postTodoClicked() {
        mPresenter.onResetTodoClicked();
    }

    @Override
    public void showTodo(Todo todo) {
        mTodoTextView.setText(todo.toString());
    }

    @Override
    public void showErrorToast(String todoId) {
        Toast.makeText(this, getString(R.string.get_todo_error, todoId), Toast.LENGTH_LONG).show();
    }

    @Override
    public void resetTodoView() {
        mTodoTextView.setText(null);
    }
}
