###  第二周

#### Fragment的使用

 碎片的生命周期

和活动的生命周期差不多，有四个状态

运行：当活动运行时，与他相关的碎片也会处于运行状态。

暂停：当活动进入暂停状态（另一个活动到了栈顶），与他相关的碎片也会处于暂停状态。

停止：进入停止状态时，活动对用户来说是不可见的，当活动进入停止状态（），与他相关的碎片也会处于停止状态。或者会通过调用FragmentTransation的remove和replace方法来将碎片从活动中移除，如果在事务提交之前就用OnBackStarck方法，碎片也会进入停止状态。

销毁：当活动被销毁时，与他相关的碎片也会被销毁，或者会通过调用FragmentTransation的remove和replace方法来将碎片从活动中移除，如果在事务提交之前就用OnBackStarck方法，碎片也会进入销毁状态。

额外的回调方法

1.toAttach()

当活动与碎片建立关联的时候进行调用

2.onCreateView()

为碎片创建视图（加载布局）时调用

3.onActivityCreated()

确保与碎片相关的试图被移除的时候进行调用

4.onDestroyView()

当与碎片关联的视图被移除的时候进行调用。

5.onDetach()

当碎片与活动解除关联的时候调用

![img](https://img-blog.csdn.net/20160309102642394)


添加碎片：onAttach(),onCreate(),onCreateView(),onActivityCreate(),onStart(),onResume()，碎片已经激活

当用户点击回退（碎片被添加到返回栈）或者碎片被替换或移除：onPause(),onStop(),onDestroyView(),onDestroy,onDetach().

![image-20200728150934758](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200728150934758.png)

动态添加碎片：

1.创建待添加的碎片实例

![image-20200730092540980](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200730092540980.png)

![image-20200730092512398](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200730092512398.png)



在activity_main中添加一个碎片布局替换原来的碎片，只留下一个未被替换的碎片

![image-20200730093140096](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200730093140096.png)

```
给左侧碎片添加一个点击实例，点击左侧的按钮就会把右侧碎片替换为新的碎片
```

![image-20200730092851370](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200730092851370.png)



![image-20200730092920156](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200730092920156.png)

2.获取FramentManager，在活动中可以直接通过调用getSupportFragmentManager()方法得到

3.开启一个事务，通过调用beginTransaction()方法开启

4.向容器内添加或替换碎片，一般使用replace()方法实现，需要传入容器的id和待添加的碎片实例

5.提交事务，调用commit()方法完成

![image-20200730092936676](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200730092936676.png)


Fragment与Activity之间的通信

在新闻程序中，有A标题和B内容两个碎片，当用户选择了A中的标题1，这时碎片就要告诉活动，活动再告诉B，然后B显示出相应的内容1。这样可以增加碎片的可重用性。

Fragment------>Activity

在Fragment内部定义一个接口，再让和Fragment相关的Activity实现这个接口，然后在碎片中的onAttach方法中判断是否实现了接口

在碎片中调用活动：为了方便碎片和活动之间进行通信，FragmentManager提供了一个类似于findViewById()的方法，
MainActivity activity = (MainActivity)getActivity();

Activity----->Fragment

在Activity中创建Bundle包，并调用Fragment中的setArguments(Bundle bundle)方法。

//在活动中调用碎片的方法：
RightFragment rightFragment = (RightFragment)getFragmentManager().
                findFragmentById(R.id.right_fragment);

Fragment---->Fragment

碎片通信其他碎片：（首先获取与当前碎片关联的活动，再通过这个活动获取另一个碎片的实例
 LeftFragment leftFragment = (LeftFragment)activity.getFragmentManager().
                findFragmentById(R.id.left_fragment);





service的启动方式

在用户打开其他的程序时，该程序依然会运行，简单来说就是可以一边听音乐一边看小说。

服务的生命中周期只有onCreate(),onStart(),onDestroy().两种启动方式对于服务的生命周期的影响是不一样的

1、通过startService
Service会经历onCreate->onStart,stopService的时候直接onDestroy，如果是调用者(TestServiceHolder)自己直接退出而没有调用stopService的话，Service会一直在后台运行。下次TestServiceHolder再起来可以stopService。

![image-20200730094022317](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200730094022317.png)

![image-20200729104932129](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200729104932129.png)

![image-20200729104913811](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200729104913811.png)

2、通过bindService
Service只会运行onCreate，这个时候TestServiceHolder和TestService绑定在一起，TestServiceHolder退出了，Srevice就会调用onUnbind->onDestroyed所谓绑定在一起就共存亡了。

bindService用于绑定一个服务。这时会调用服务中的onBind方法，这样当bindService(intent,conn,flags)后，就会绑定一个服务。这样做可以获得这个服务对象本身，而用startService(intent)的方法只能启动服务。

方法：在服务里面创建一个继承自Binder的类(Binder实现IBinder接口)，最后活动该类的实例，在onBind里面返回实例对象。

在活动里面创建ServiceConnection类，冲重写类里面的onServiceDisconnected和onServiceConnected方法，

3、服务与活动之间的通信

需要用到服务的onBind方法，调用方可以获取到onBind方法里返回的IBinder对象的实例，这样就可以与服务进行自由的通信

首先创建一个DownLoadBinder类继承于Binder,在里面构造两个方法，

![image-20200730094233693](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200730094233693.png)

获取DownLoadBinder类的实例，在onBind方法里面返回这个实例，

![image-20200730094447264](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200730094447264.png)

![image-20200730094505241](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200730094505241.png)

```
创建ServiceConnection匿名类（匿名内部类只能使用一次，它通常用来简化代码编写，但使用匿名内部类还有个前提条件：必须继承一个父类或实现一个接口）
重写onServiceDisconnected（解绑服务时调用），onServiceConnected（绑定服务时调用）方法，、
向下转型的得到DownLoadBinder实例，然后调用DownLoadBinder中的两个方法，
```

![image-20200730095618802](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200730095618802.png)

![image-20200730095737265](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200730095737265.png)

![image-20200730095713594](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200730095713594.png)

4、前台服务于普通服务最大的区别就是前台服务在运行的时候就会有一个运行图标在系统的状态栏显示，下拉状态栏可以看到更加详细的信息，类似于通知，

方法：构建类似于通知的对象，调用startForeground让Myservice变成一个前台服务，在系统状态栏显示出来

![image-20200729145633230](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200729145633230.png)

IntentService的使用

 为避免忘记创建子线程，或者忘记调用selfstop()方法，创建一个简单的异步的，会自动停止的服务，Android专门提供了一个IntentService类来解决这个问题

方法：创建一个MyIntentService类继承自IntentService,重写里面的MyIntentService和onHandleIntent方法，

![image-20200730095855979](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200730095855979.png)

![image-20200729154956098](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200729154956098.png)



Broadcast

1、广播的类型主要分为两种，有序广播和标准广播

有序广播：是一种同步执行的广播，发送一条广播，优先级高的广播接收器会先接收到这条消息，当这个广播接收器处理完逻辑之后才会继续传给下一个广播，广播的接收是有先后顺序的，而且先接收广播的接收器可以截断这条消息，这样后面的接收器就不能收到这条消息了。

方法：创建一个AnotherBroadcastReceiver类继承自BroadcastReceiver，然后直接在类里面写一条Toast语句，

![image-20200730091725278](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200730091725278.png)

在AndroidManifest.xml中注册，使用priority给广播接收器设定优先级，定义一条action，

![image-20200730091744258](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200730091744258.png)

最后使用Intent获取action,调用sendOrderedBroadcast发送广播。

![image-20200730091917038](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200730091917038.png)

用abortBroadcast()可以截断广播，这样优先级低的接收器就不会收到这条广播。

![image-20200730092022338](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200730092022338.png)

标准广播：是一种异步执行的广播，发送一条广播消息，所有的广播接收器会同时接收到这条消息，没有先后顺序，这种方式的广播的效率会比较高，但是也就说明它不能被截断。

方法：创建一个类继承自BroadcastReceiver，然后直接在类里面写一条Toast语句，

![image-20200730092048393](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200730092048393.png)

在AndroidManifest.xml中注册，定义一条action，

![image-20200730092105818](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200730092105818.png)

最后使用Intent获取action,调用sendBroadcast发送广播。

![image-20200730092122227](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200730092122227.png)

2、

动态注册：在代码中注册，动态注册的广播接收器在最后一定要在onDestroy中取消注册

方法：创建一个NetworkChangeRecevier类，继承自Broadcast-Receive,并且重写父类的onReceive方法。主要是给用户发送提示消息。发送一条Toast提示网络的变化。

![image-20200730091304633](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200730091304633.png)

先获取IntentFilter实例，用addAction添加相应action，当网路发生变化是，系统会发出下面的广播，我们接收器要监听什么广播，就添加什么action，创建NetworkChangeRecevier实例，调用registerReceiver注册，把前面两个实例对象都传进去。最后NetworkChangeRecevier会接收到一条值为android.net.conn.CONNECTIVITY_CHANGE的广播，实现了监听网路变化的功能

![image-20200729171210299](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200729171210299.png)

动态注册的广播接收器最后一定要在onDestroy中调用unregisterReceiver()取消注册。

![image-20200730090927947](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200730090927947.png)

静态注册：在AndroidManifest.xml中注册，

![image-20200730091047288](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200730091047288.png)

只需要创建一个BootCompletReceiver类继承自BroadcastRecevie，然后写一条Toast消息。

实现通知的点击效果，使用PendingIntent来启动一个通知活动
Intent intentnotice = new Intent(this, NotificationActivity.class);
PendingIntent pi = PendingIntent.getActivity(this, 0, intentnotice, 0);


 Notification（系统消息通知）
 在消息通知的时候，我们经常用到两个控件Notification和Toast。特别是重要的和需要长时间显示的信息，用Notification最合适不过了。他可以在顶部显示一个图标以标示有了新的通知，当我们拉下通知栏的时候，可以看到详细的通知内容。
 
 实现通知的点击效果，使用PendingIntent来启动一个通知活动
 
Intent intentnotice = new Intent(this, NotificationActivity.class);

PendingIntent pi = PendingIntent.getActivity(this, 0, intentnotice, 0);

创建通知首先要创建一个NotificationManager来对通知进行管理，通过getSystemService获取到，

getSystemService里面需要穿一个字符串，一般传Context.NOTIFICATION_SERVICE

接下来要用Bulider来构造Notification对象，这里我们使用NotificationCompat类来构造创建Notification对象确保我们的程序字啊所有android系统版本都能运行

使用提供的api来完成这个通知，一般有通知的标题setContentTitle，通知的内容setContentText,还可以通过style设置大文本，图片，发出通知的时间setWhen，通知的小标题setSmallIcon，

通知点开后的大标题setLargeIcon，启动通知活动setContentIntent，设置取消通知etAutoCancel，设置通知的重要程度，setPriority

最后让通知显示出来，manager.notify(1, notification);


