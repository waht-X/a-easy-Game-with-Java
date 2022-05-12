# a-easy-Game-with-Java
使用Java制作一款简单的小游戏

* game2Image用于存放游戏所需要的图片资源
* ImageUtils 主要是用来加载图片资源
* Localtion 主要是用来存储需要绘制的类的左上角坐标
* Size 主要是用于存放需要绘制的类的大小
* TypeEnum 主要是标记主体类的状态
* Block类是所有需要绘制的类的父类，定义了部分基础参数
* Obstacles和Player都是继承Block类，分别是表示障碍物和玩家
* KeyListener用于监听空格键，控制人物的跳跃
* CreateGame是游戏类的主体部分，主要逻辑都将在这个类中。
* Start将使用CreateGame类的getGame方法创建游戏

---

关于每个类的内部逻辑在代码内都有注释，逻辑相对较为简单

---
**需要注意的是当将文件打包为Jar包时需要将图片资源文件复制到jar包同级目录内**

---

Use Java to make a simple game



* game2Image is used to store the image resources needed for the game

* ImageUtils is mainly used to load image resources

Localtion is mainly used to store the upper-left coordinates of the class to be drawn

* Size is mainly used to hold the Size of the class to be drawn

* TypeEnum mainly marks the state of the body class

* The Block class is the parent of all classes that need to be drawn and defines some of the basic parameters

* Both Obstacles and Player are derived from Block classes, which refer to Obstacles and players, respectively

* KeyListener is used to listen for the space bar and control the jump of the character

* CreateGame is the body of the game class, where the main logic will be.

* Start will create the game using the getGame method of the CreateGame class



---



The internal logic for each class is commented in the code and the logic is relatively simple



---

** Note that when packaging a file as a Jar package, copy the image resource file to the same directory as the Jar package **



---

