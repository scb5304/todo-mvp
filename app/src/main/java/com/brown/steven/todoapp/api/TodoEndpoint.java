package com.brown.steven.todoapp.api;

import com.brown.steven.todoapp.model.Todo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TodoEndpoint {
    @GET("todos/{id}")
    Call<Todo> getTodoById(@Path("id") String id);
}
