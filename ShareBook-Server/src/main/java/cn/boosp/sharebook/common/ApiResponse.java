package cn.boosp.sharebook.common;

import java.io.Serializable;

public class ApiResponse implements Serializable {
    Status status;
    Object data;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ApiError getError() {
        return error;
    }

    public void setError(ApiError error) {
        this.error = error;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    ApiError error;
    Long totalItems;
    Integer totalPages;

    public ApiResponse(Status status, Object data) {
        this.status = status;
        this.data = data;
    }

    public ApiResponse(Status status, Object data, Long totalItems, Integer totalPages) {
        this.status = status;
        this.data = data;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

    public ApiResponse(Status status, Object data, ApiError error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public enum Status implements Serializable {
        ok(0),
        error(1);

        Integer value;

        Status(Integer value) {
            this.value = value;
        }
    }

    public static class ApiError implements Serializable {
        Integer errorCode;
        String errorMessage;

        public Integer getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(Integer errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public ApiError(Integer errorCode, String errorMessage) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }
    }
}
