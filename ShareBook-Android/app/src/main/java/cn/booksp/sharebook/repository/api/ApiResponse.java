package cn.booksp.sharebook.repository.api;

import java.io.Serializable;

public class ApiResponse<T> implements Serializable {
    Status status;
    T data;
    ApiError error;
    Long totalItems;
    Integer totalPages;

    public ApiResponse() {
    }

    public ApiResponse(Status status, T data) {
        this.status = status;
        this.data = data;
    }

    public ApiResponse(Status status, T data, Long totalItems, Integer totalPages) {
        this.status = status;
        this.data = data;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

    public ApiResponse(Status status, T data, ApiError error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ApiError getError() {
        return error;
    }

    public void setError(ApiError error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "status=" + status +
                ", data=" + data +
                ", error=" + error +
                ", totalItems=" + totalItems +
                ", totalPages=" + totalPages +
                '}';
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

    public boolean hasMore() {
        return true;
    }

    public enum Status implements Serializable {
        ok(0),
        error(1);

        Integer value;

        Status(Integer value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Status{" +
                    "value=" + value +
                    '}';
        }
    }

    public static class ApiError implements Serializable {
        Integer errorCode;
        String errorMessage;

        public ApiError(Integer errorCode, String errorMessage) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }

        public ApiError() {
        }

        @Override
        public String toString() {
            return "ApiError{" +
                    "errorCode=" + errorCode +
                    ", errorMessage='" + errorMessage + '\'' +
                    '}';
        }

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
    }
}
