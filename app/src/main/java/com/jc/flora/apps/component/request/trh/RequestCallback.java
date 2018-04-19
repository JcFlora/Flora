package com.jc.flora.apps.component.request.trh;

public interface RequestCallback<Result> {
    void onSuccess(Result content);

    void onFail(String errorMessage);
}
