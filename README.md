# PermissionHelper
A helper for checking and requesting permissions for app that targeting Android M.

##screenshot
![image](arts/screenshot.jpg)

##Usage

###Gradle
```
dependencies {
   	compile 'com.github.liuguangqiang.permissionhelper:permissionhelper:0.0.1'
}
```

###Maven
```
<dependency>
  	<groupId>com.github.liuguangqiang.permissionhelper</groupId>
  	<artifactId>permissionhelper</artifactId>
  	<version>0.0.1</version>
  	<type>aar</type>
</dependency>
```

##Annotations

```
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PermissionGranted {

    String permission();

}
```

```
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PermissionDenied {

    String permission();

}
```

##Sample

```
private void requestCameraPermission() {
    PermissionHelper.getInstance().requestPermission(this, Manifest.permission.CAMERA);
}
```

```
@PermissionGranted(permission = Manifest.permission.CAMERA)
public void cameraGranted() {
    Log.i("PermissionHelper", "cameraGranted");
}
```

```
@PermissionDenied(permission = Manifest.permission.CAMERA)
public void cameraDenied() {
    Log.i("PermissionHelper", "cameraDenied");
}
```

##License

    Copyright 2015 Eric Liu

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
