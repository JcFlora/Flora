package com.jc.flora.apps.component.router.delegate;

/**
 * Created by Shijincheng on 2019/1/15.
 */

public class MultiRouterDelegate {

    public static ModuleRouterDelegate getModuleRouter(){
        return ModuleRouterDelegate.getInstance();
    }

    public static ActivityRouterDelegate getActivityRouter(){
        return ActivityRouterDelegate.getInstance();
    }

    public static FragmentRouterDelegate getFragmentRouter(){
        return FragmentRouterDelegate.getInstance();
    }

    public static UrlRouter3Delegate getUrlRouter(){
        return UrlRouter3Delegate.getInstance();
    }


}
