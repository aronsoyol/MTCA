# Mongolian Text Component for Android

这是一个用c++实现的在Android上的蒙古文显示软件。

现在的蒙古文UNICODE编码被设计成了需要复杂渲染（选型）引擎的支持才能正确显示的编码。
可是遗憾的是几乎所有的软件都不支持蒙古文的特殊的选型规则。
而且蒙古文是竖排的，这一点也是很多软件，或者控件不支持的。
所以想要解决蒙古文的显示问题，一定要从底层的图形显示，以及文本渲染等等开始着手开发。

有些人用java开发了一些能够显示和编辑蒙文的APP但是java本身不支持蒙古文OpenType字体，所以不是完美的的解决方案。先要完美的支持蒙古文OpenType字体只能用C++实现。

这里提供的C++代码展示了如何使用Andorid NDK，以及在JNI中如何调用FreeType和HarfBuzz来进行渲染和显示蒙古文的方法。

希望这个开源工程能给其他的蒙古文软件开发者一点启示。

***

- Windows版：

码云: [https://gitee.com/aronsoyol/MTC](https://gitee.com/aronsoyol/MTC)

github:[https://www.github.com/aronsoyol/mtc](https://www.github.com/aronsoyol/mtc)
