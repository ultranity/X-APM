package github.tornaco.xposedmoduletest.xposed.submodules;

import android.util.Log;

import java.util.Set;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import github.tornaco.xposedmoduletest.xposed.util.XLog;

/**
 * Created by guohao4 on 2017/10/31.
 * Email: Tornaco@163.com
 */

// Hook retrieve settings.
class AMSSubModule4 extends AndroidSubModuleModule {

    @Override
    public void handleLoadingPackage(String pkg, XC_LoadPackage.LoadPackageParam lpparam) {
        hookAMSRetrieveSettings(lpparam);
    }

    private void hookAMSRetrieveSettings(XC_LoadPackage.LoadPackageParam lpparam) {
        XLog.logV("hookAMSRetrieveSettings...");
        try {
            Class ams = XposedHelpers.findClass("com.android.server.am.ActivityManagerService",
                    lpparam.classLoader);
            Set unHooks = XposedBridge.hookAllMethods(ams, "retrieveSettings", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    getBridge().retrieveSettings();
                }
            });
            XLog.logV("hookAMSRetrieveSettings OK:" + unHooks);
            setStatus(unhooksToStatus(unHooks));
        } catch (Exception e) {
            XLog.logV("Fail hookAMSRetrieveSettings");
            setStatus(SubModuleStatus.ERROR);
            setErrorMessage(Log.getStackTraceString(e));
        }
    }
}