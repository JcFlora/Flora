package com.jc.flora.apps.component.router.delegate;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Shijincheng on 2019/1/15.
 */

public class ModuleRouterDelegate {

    private static ModuleRouterDelegate sInstance = new ModuleRouterDelegate();

    private LauncherRouter mLauncherRouter;
    private NotFoundRouter mNotFoundRouter;
    private WithDataRouter mWithDataRouter;
    private ReceiveDataRouter mReceiveDataRouter;
    private H5Router mH5Router;

    public static ModuleRouterDelegate getInstance(){
        return sInstance;
    }

    private ModuleRouterDelegate(){}

    public void setLauncherRouter(LauncherRouter launcherRouter) {
        mLauncherRouter = launcherRouter;
    }

    public void setNotFoundRouter(NotFoundRouter notFoundRouter) {
        mNotFoundRouter = notFoundRouter;
    }

    public void setWithDataRouter(WithDataRouter withDataRouter) {
        mWithDataRouter = withDataRouter;
    }

    public void setReceiveDataRouter(ReceiveDataRouter receiveDataRouter) {
        mReceiveDataRouter = receiveDataRouter;
    }

    public void setH5Router(H5Router h5Router) {
        mH5Router = h5Router;
    }

    public void gotoLauncher(Context context) {
        if(mLauncherRouter != null){
            mLauncherRouter.gotoLauncher(context);
        }
    }

    public void gotoNotFound(Context context) {
        if(mNotFoundRouter != null){
            mNotFoundRouter.gotoNotFound(context);
        }
    }

    public void gotoNotFound2(Context context) {
        if(mNotFoundRouter != null){
            mNotFoundRouter.gotoNotFound2(context);
        }
    }

    public void goWithData(Context context, String from) {
        if(mWithDataRouter != null){
            mWithDataRouter.goWithData(context, from);
        }
    }

    public void goAndReceiveData(AppCompatActivity activity, int requestCode) {
        if(mReceiveDataRouter != null){
            mReceiveDataRouter.goAndReceiveData(activity, requestCode);
        }
    }

    public void gotoH5(Context context, String url) {
        if(mH5Router != null){
            mH5Router.gotoH5(context, url);
        }
    }

    public interface LauncherRouter{
        void gotoLauncher(Context context);
    }

    public interface NotFoundRouter{
        void gotoNotFound(Context context);
        void gotoNotFound2(Context context);
    }

    public interface WithDataRouter{
        void goWithData(Context context, String from);
    }

    public interface ReceiveDataRouter{
        void goAndReceiveData(AppCompatActivity activity, int requestCode);
    }

    public interface H5Router{
        void gotoH5(Context context, String url);
    }

}
