# ImageViewpager
图片预览项目

#预览

![imageviewpager](pic/ImageViewpager.jpg)

##使用
	ArrayList<String> list=new ArrayList<String>();
	list.add("http://sumile.cn/wp-content/themes/yusiyuPC/img/pic/18.jpg");
	list.add("http://sumile.cn/logo_bird_small.png");
	list.add("http://news.sohu.com/upload/itoolbar/index/toolbar_logo.png");
	ImagePagerActivity.startActivity(MainActivity.this,1,list);

##介绍
-	startActivity需要三个参数
	-	第一个参数为上下文
	-	第三个参数是要展示的图片列表
	-	第二个参数是要展示图片的position

