はじめてのDependency Injectionのつづき
======================================

[はじめてのDependency Injection - _development,][0]で紹介されていた[ソースコード][1]に、ごちゃごちゃとライブラリを追加して自分流にしてみた習作です。

基本方針
--------

CIすることを見据えて、コマンドラインビルドできるようにする

原作からの変更点
----------------

* Maven化 - ライブラリ用意するの面倒だったのとか、pom力の訓練のためとか
* 新たに導入したライブラリ(pomのdependencyに入れたもの)
    * JUnit4
    * [RoboGuice][2] - DIライブラリとして
    * [Robolectric][3] - JVM上でJUnit4テストができるように
* jar持ってくるの面倒だったからpomに入れたもの
    * [Mockito][4]
* 上手く再現できなかったこと
    * `BuildConfig#isDebug`による`DevelopmentModule`と`ProductionModule`の切り替え(res/values/roboguice.xmlでModuleを指定しているため)
    
動かす際の前準備
----------------

[mosabua/maven-android-sdk-deployer][5]

pom.xmlにあるAndroid SDKのdependencyは↑を使って事前にローカルリポジトリに入れておく。

注意点
------

EclipseからRunできることを目的にしなかったのでたぶんEclipseではビルドできません。

実機にインストールしたい場合は

`mvn clean install android:deploy android:run -Dandroid.device=usb`

とかコマンドラインで打ち込んでください。

[0]:http://d.hatena.ne.jp/esmasui/20121130/1354275028
[1]:https://github.com/esmasui/underdevelopment/tree/master/my-first-dependency-injection
[2]:http://code.google.com/p/roboguice/
[3]:http://pivotal.github.com/robolectric/
[4]:http://code.google.com/p/mockito/
[5]:https://github.com/mosabua/maven-android-sdk-deployer