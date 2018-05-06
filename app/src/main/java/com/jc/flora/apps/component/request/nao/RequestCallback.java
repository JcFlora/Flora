package com.jc.flora.apps.component.request.nao;

public interface RequestCallback<Result> {
    void onSuccess(Result content);

    void onFail(String errorMessage);
}
