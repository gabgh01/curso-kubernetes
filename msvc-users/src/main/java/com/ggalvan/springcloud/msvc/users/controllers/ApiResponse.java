package com.ggalvan.springcloud.msvc.users.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
//@AllArgsConstructor
public class ApiResponse {
    private String message;
    private Object data;

    public ApiResponse(String message, Object data) {
        this.data = data;
        this.message = message;
    }



    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
