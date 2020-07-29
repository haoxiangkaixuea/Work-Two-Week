Fragment
一般手机和平板的屏幕差距过大时，我们会使用碎片来调节控件，使得控件在平板上显示的更加合理，


1.建立两个碎片活动，继承于Fragment，在OncreateView方法中用LayoutInflaer的Inflaer来获取获取到碎片布局，

最后在主布局中用fragment来结合两个碎片布局，就是一个简单的适用于平板上的碎片布局。


2、动态添加碎片
新建一个碎片活动，activity_main中添加一个FragmentLayout布局，然后在MainActivity中做动态添加处理，


给左侧的碎片中的按钮绑定一个点击事件，用replaceFragment方法点击这个按钮机会添加新建的这个碎片，


在onclick方法中告诉点击左侧按钮，把右侧right换成anotherright，


创建待添加的碎片实例,在replaceFragment中


获取用FragmentManager，先通过getSupportFragmentManager调用直接获取碎片


然后创建FragmentTransaction实例，通过调用beginTransaction()开启一个事务，


通过replace来获取待替换的碎片id和实例,


最后用commit提交事务。

在活动中使用碎片可以通过FragmentManager的findfragmentId来获取碎片实例，

RightFragment right=(RightFragment)getSupportFragmentManager().findFragmentById(R.id.right_layout);

在碎片中使用活动可以用getActivity方法获取活动实例:

MainActyvity main=(MainActivity) getActivity();

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



添加碎片：onAttach(),onCreate(),onCreateView(),onActivityCreate(),onStart(),onResume()，碎片已经激活

当用户点击回退（碎片被添加到返回栈）或者碎片被替换或移除：onPause(),onStop(),onDestroyView(),onDestroy,onDetach().


Fragment与Activity之间的通信

在新闻程序中，有A标题和B内容两个碎片，当用户选择了A中的标题1，这时碎片就要告诉活动，活动再告诉B，然后B显示出相应的内容1。这样可以增加碎片的可重用性。

Fragment------>Activity

在Fragment内部定义一个接口，再让和Fragment相关的Activity实现这个接口，然后在碎片中的onAttach方法中判断是否实现了接口

Activity----->Fragment

在Activity中创建Bundle包，并调用Fragment中的setArguments(Bundle bundle)方法。




service

服务的生命中周期只有onCreate(),onStart(),onDestroy().两种启动方式对于服务的生命周期的影响是不一样的

1、通过startService
Service会经历onCreate->onStart,stopService的时候直接onDestroy，如果是调用者(TestServiceHolder)自己直接退出而没有调用stopService的话，Service会一直在后台运行。下次TestServiceHolder再起来可以stopService。

2、通过bindService
Service只会运行onCreate，这个时候TestServiceHolder和TestService绑定在一起，TestServiceHolder退出了，Srevice就会调用onUnbind->onDestroyed所谓绑定在一起就共存亡了。

bindService用于绑定一个服务。这时会调用服务中的onBind方法，这样当bindService(intent,conn,flags)后，就会绑定一个服务。这样做可以获得这个服务对象本身，而用startService(intent)的方法只能启动服务。

方法：在服务里面创建一个继承自Binder的类(Binder实现IBinder接口)，最后活动该类的实例，在onBind里面返回实例对象。

在活动里面创建ServiceConnection类，冲重写类里面的onServiceDisconnected和onServiceConnected方法，

3、服务与活动之间的通信

需要用到服务的onBind方法，调用方可以获取到onBind方法里返回的IBinder对象的实例，这样就可以与服务进行自由的通信

4、前台服务于普通服务最大的区别就是前台服务在运行的时候就会有一个运行图标在系统的状态栏显示，下拉状态栏可以看到更加详细的信息，类似于通知，

方法：构建类似于通知的对象，调用startForeground让Myservice变成一个前台服务，在系统状态栏显示出来

5、IntentService的使用

 为避免忘记创建子线程，或者忘记调用selfstop()方法，创建一个简单的异步的，会自动停止的服务，Android专门提供了一个IntentService类来解决这个问题

方法：创建一个MyIntentService类继承自IntentService,重写里面的MyIntentService和onHandleIntent方法，


