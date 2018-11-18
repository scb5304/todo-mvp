package com.brown.steven.todoapp.ui;

import com.brown.steven.todoapp.api.TodoEndpoint;
import com.brown.steven.todoapp.model.Todo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@SuppressWarnings("unchecked")
public class TodoPresenterTest {

    @Mock
    private TodoEndpoint mEndpoint;

    @Mock
    private TodoContract.View mView;

    @Captor
    private ArgumentCaptor<Callback> mCallbackCaptor;

    private TodoContract.Presenter mPresenter;

    @Before
    public void setup() {
        initMocks(this);
        when(mEndpoint.getTodoById(anyString())).thenReturn(mock(Call.class));
        mPresenter = new TodoPresenter(mEndpoint, mView);
    }

    @Test
    public void clickingGetTodo_callsEndpoint() {
        mPresenter.onGetTodoClicked();
        verify(mEndpoint).getTodoById(anyString());
    }

    @Test
    public void receivingTodo_displaysInUi() {
        mPresenter.onGetTodoClicked();
        verify(mEndpoint.getTodoById(anyString())).enqueue(mCallbackCaptor.capture());

        Response mockResponse = Response.success(new Todo());
        mCallbackCaptor.getValue().onResponse(mock(Call.class), mockResponse);
        verify(mView).showTodo(any(Todo.class));
    }

    @Test
    public void todoError_showsErrorToast() {
        mPresenter.onGetTodoClicked();
        verify(mEndpoint.getTodoById(anyString())).enqueue(mCallbackCaptor.capture());

        mCallbackCaptor.getValue().onFailure(mock(Call.class), mock(Throwable.class));
        verify(mView).resetTodoView();
        verify(mView).showErrorToast(anyString());
    }

    @Test
    public void resetClicked_resetsView() {
        mPresenter.onResetTodoClicked();
        verify(mView).resetTodoView();
    }

    @Test
    public void rotatingDevice_displaysPreviousTodo() {
        mPresenter.onGetTodoClicked();
        verify(mEndpoint.getTodoById(anyString())).enqueue(mCallbackCaptor.capture());

        Todo todo = new Todo();
        Response mockResponse = Response.success(todo);
        mCallbackCaptor.getValue().onResponse(mock(Call.class), mockResponse);

        TodoContract.View recreatedView = mock(TodoContract.View.class);
        mPresenter.onViewRecreated(recreatedView);
        verify(recreatedView).showTodo(todo);
    }
}
