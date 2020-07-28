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
