/*
 *   Copyright 2015 Eric Liu
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.liuguangqiang.permissionhelper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.liuguangqiang.permissionhelper.annotations.PermissionDenied;
import com.liuguangqiang.permissionhelper.annotations.PermissionGranted;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * A helper for checking and requesting permissions for app that targeting Android M (API >= 23)
 * <p>
 * Created by Eric on 15/12/28.
 */
public class PermissionHelper {

    private static PermissionHelper instance = new PermissionHelper();

    public static PermissionHelper getInstance() {
        return instance;
    }

    /**
     * Return if the context has the permission.
     *
     * @param context
     * @param permission
     * @return
     */
    public boolean hasPermission(Context context, String permission) {
        return (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * Request the permission.
     *
     * @param context
     * @param permission
     */
    public void requestPermission(Activity context, String permission) {
        if (hasPermission(context, permission)) {
            return;
        }

        ActivityCompat.requestPermissions(context, new String[]{permission}, 0);
    }

    public void onRequestPermissionsResult(Object obj, String[] permissions, int[] grantResults) {
        if (isGranted(grantResults)) {
            invokeGrantedMethod(obj, permissions[0]);
        } else {
            invokeDeniedMethod(obj, permissions[0]);
        }
    }

    private boolean isGranted(int[] grantResults) {
        return grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Invoke a method with annotation PermissionGranted.
     *
     * @param obj
     * @param permission
     */
    private void invokeGrantedMethod(Object obj, String permission) {
        Class clazz = obj.getClass();
        PermissionGranted permissionGranted;

        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(PermissionGranted.class)) {
                permissionGranted = method.getAnnotation(PermissionGranted.class);
                if (permissionGranted.permission().equals(permission)) {
                    if (method.getModifiers() != Modifier.PUBLIC) {
                        throw new IllegalArgumentException(String.format("Annotation method %s must be public.", method));
                    }

                    if (method.getParameterTypes().length > 0) {
                        throw new RuntimeException(String.format("Cannot execute non-void method %s.", method));
                    }

                    try {
                        method.invoke(obj);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Invoke a method with annotation PermissionDenied.
     *
     * @param obj
     * @param permission
     */
    private void invokeDeniedMethod(Object obj, String permission) {
        Class clazz = obj.getClass();
        PermissionDenied permissionDenied;

        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(PermissionDenied.class)) {
                permissionDenied = method.getAnnotation(PermissionDenied.class);
                if (permissionDenied.permission().equals(permission)) {
                    if (method.getModifiers() != Modifier.PUBLIC) {
                        throw new IllegalArgumentException(String.format("Annotation method %s must be public.", method));
                    }

                    if (method.getParameterTypes().length > 0) {
                        throw new RuntimeException(String.format("Cannot execute non-void method %s.", method));
                    }

                    try {
                        method.invoke(obj);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
