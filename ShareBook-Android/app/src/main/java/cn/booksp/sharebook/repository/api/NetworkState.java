package cn.booksp.sharebook.repository.api;

public enum NetworkState {
    RUNNING("运行中"),
    SUCCESS("网络请求成功"),
    FAILED("网络请求失败");
    String message;
    NetworkState(String message){
        this.message = message;
    }
}
