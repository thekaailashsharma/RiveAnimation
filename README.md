
<h1 align="center">  😎 Rive with Compose 🫶🏻  </h1> <br>
<p align="center"> 
  <a href="https://firebasestorage.googleapis.com/v0/b/samruddhi-d0846.appspot.com/o/rive.gif?alt=media&token=1eafa04a-dcab-49e2-828f-f462b3caa52a">
    <img src="https://firebasestorage.googleapis.com/v0/b/samruddhi-d0846.appspot.com/o/rive.gif?alt=media&token=1eafa04a-dcab-49e2-828f-f462b3caa52a" border="0">
  </a
</p>

<p align="center">
 Creating Stunning Visuals
</p>

## 📽️ Demo Video
[Demo Video](https://www.linkedin.com/feed/update/urn:li:activity:7081408152709062656/)

## 📦 Packages

### Android 
| Name | Description |
| --- | --- |
| [`@compose`](https://developer.android.com/jetpack/compose) | Jetpack Compose |
| [`@materialUI`](https://m3.material.io/) | Material 3 |
| [`@rive`](https://rive.app/) | Rive |
| [`@system-ui-controller`](https://google.github.io/accompanist/systemuicontroller/) | System UI Controller |


## Steps To use Rive
- Add following dependancies
  `app.rive:rive-android:5.0.1"`
  `androidx.startup:startup-runtime:1.1.1`
-  Add a provider in Manifest file as follows
  ` <provider
            android:name="androidx.startup.InitializationProvider"
            android:authorities="${applicationId}.androidx-startup"
            android:exported="false"
            tools:node="merge">
            <meta-data android:name="app.rive.runtime.kotlin.RiveInitializer"
                android:value="androidx.startup" />
        </provider>
   `
-  Add a `.riv` animation in your raw folder.
-  Go to `anim/rive/compose/RiveAnimationView.kt` And use the same composable with whatever changes you need.


## Authors

[@thekaailashsharma](https://linkedin.com/in/thekaailashsharma).
