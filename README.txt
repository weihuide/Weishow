//    Glide 的一些常用方法
//    .with() 图片加载的环境：1,Context对象。2,Activity对象。3,FragmentActivity对象。4,Fragment对象
//    .load() 加载资源：1,drawable资源。2,本地File文件。3,uri。4,网络图片url。5,byte数组（可以直接加载GIF图片）
//    .placeholder() 图片占位符
//    .error() 图片加载失败时显示
//    .crossFade() 显示图片时执行淡入淡出的动画默认300ms
//    .dontAnimate() 不执行显示图片时的动画
//    .override() 设置图片的大小
//    .centerCrop() 和 fitCenter() 图片的显示方式
//    .animate() view动画 2个重构方法
//    .transform() bitmap转换
//    .bitmapTransform() bitmap转换。比如旋转，放大缩小，高斯模糊等（当用了转换后你就不能使用.centerCrop()或.fitCenter()了。）
//    .priority(Priority.HIGH) 当前线程的优先级
//    .signature(new StringSignature(“ssss”))
//    .thumbnail(0.1f) 缩略图，3个重构方法：优先显示原始图片的百分比(10%)
//    .listener() 异常监听
//    .into() 图片加载完成后进行的处理：1,ImageView对象。2,宽高值。3,Target对象


//    SwipeToLoadLayout常用属性：
//    app:refresh_enabled：设置是否可以下拉刷新
//    app:load_more_enabled：设置是否可以上拉加载更多
//    app:swipe_style：设置下拉刷新与上拉加载的样式，其值为classic，above，blew或scale
//    app:refresh_trigger_offset：触发下拉刷新的偏移量，默认值是下拉刷新头部的高度
//    app:load_more_trigger_offset：触发上拉加载更多的偏移量，默认值是上拉加载更多的高度
//    app:refresh_final_drag_offset：下拉刷新最大可以拖动的偏移量
//    app:load_more_final_drag_offset：上拉加载更多最大可以拖动的偏移量
//    app:release_to_refreshing_scrolling_duration：释放下拉刷新持续滚动的时间
//    app:release_to_loading_more_scrolling_duration：释放上拉加载更多持续滚动的时间
//    app:refresh_complete_delay_duration：下拉刷新完成延迟的持续时间
//    app:load_more_complete_delay_duration：上拉加载更多完成延迟的持续时间
//    app:refresh_complete_to_default_scrolling_duration：默认完成下拉刷新持续滚动时间
//    app:load_more_complete_to_default_scrolling_duration： 默认完成上拉加载更多持续滚动时间
//    app:default_to_refreshing_scrolling_duration：默认下拉刷新滚动时间
//    app:default_to_loading_more_scrolling_duration：默认上拉加载更多滚动时间