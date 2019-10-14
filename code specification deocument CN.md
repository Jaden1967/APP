## 基本格式

*   缩进层级

    使用四个空格，设置tab键 为四个空格

*   语句结尾

    要么独占一行，要么以分号结尾。

```
// 合格代码
var team = "omwteam";

function sayTeam () {

    return {
        name: "freddy",
        data: []
    }
}

// 不合格代码

var team = "omwteam"

function sayTeam () {

    return 
    {
        name: "freddy",
        data: []
    }
}

// 这段代码会被编译器解析成如下这段代码
// 原意结果是返回一个对象，实际上却返回 undefined;

var team = "omwteam";

function sayTeam () {

    return ;
    {
        name: "freddy",
        data: []
    }
}

```

*   行的长度

    单行长度不应超过80行，超过80行应强制换行。

*   适当的地方换行

    当一行超过80个时，需要手动换行，换行部分应使用两个缩进；

```
// 正确做法
callback(this,document,'test',[],'xxx','dsds',windows,
        'ddsds');

// 不正确 换行部分只有一个缩进
callback(this,document,'test',[],'xxx','dsds',windows,
    'ddsds');

// 不正确 换行部分带上 ","运算符前面
callback(this,document,'test',[],'xxx','dsds',windows
    ,'ddsds');       

```

*   在合适的地方加上空行，以增加代码的可读性

    *   在方法之间。

    *   在方法中的局部变量和第一条语句

    *   在多行或者单行注释之前

    *   在方法内的逻辑片段之间插入空行，提高可读性

```
// 合理的写法
var list = [];

if (list && list.length) {

    for (i = 0, l = list.length; i < l; i++) {
        item = list[i];
        type = object[item]

        if (Object.hasOwnProperty(item)) {

            if (type && type === 'object') {
                return true;
            } else {
                return false;
            }
        }
    }
}

// 不合理的
var list = [];

if (list && list.length) {
    for (i = 0, l = list.length; i < l; i++) {
        item = list[i];
        type = object[item]
        if ( Object.hasOwnProperty(item) ) {
            if (type && type === 'object') {
                return true;
            } else {
                return false;
            }
        }
    }
}

```

*   变量与函数命名

    一般采用驼峰法，语义化准则以增强代码的可读性

```
var anotherVarible;
var thisIsMyName;

// 好的写法
var count = '';
var myName = '';

// 不好的写法 变量写起来像函数
var getCount = '';
var isFound = '';

// 好的写法

function getName () {
    return myName;
}

// 不好的写法： 函数看起来像变量

function theName () {
    return count;
}

```

-----------------------

Update

Method names start with a lower case letter and use upper case letters to separate words.

Examples: getScore(), isLunchTime(). 

Some use this notation for both methods and attributes. In the code, you can usually distinguish methods and attributes because method names are followed by parentheses. This is commonly called “CamelCase”.

Attribute names start with a lower case letter and use underscores to separate words.

Examples: start_time, current_task.

Constants use upper case letters with underscores between words.

Examples: MAXIMUM_TEMPERATURE, MAIN_WINDOW_WIDTH.

Global names are prefixed with the project name.

Example: MMTstandardDeviation. This may avoid name clashes when the code is combined/reused elsewhere which may have the same global variable names.

Function/method’s local variables are written entirely in lower case without underscore.

Examples: index, nextitem.

# 包名称规范

Java的包名都有小写单词组成，类名首字母大写；包的路径符合所开发的 系统模块的 定义，比如生产对生产，物资对物资，基础类对基础类。以便看了包名就明白是哪个模块，从而直接到对应包里找相应的实现。

 

    由于Java面向对象的特性，每名Java开发人员都可以编写属于自己的Java Package，为了保障每个Java Package命名的唯一性，在最新的Java编程规范中，要求开发人员在自己定义的包名前加上唯一的前缀。由于互联网上的域名称是不会重复的，所以多数开发人员采用自己公司在互联网上的域名称作为自己程序包的唯一前缀。例如： com.sun.swt.……。

 

    从而，我们知道，一般公司命名为“com.公司名.项目名.模块名....”。
    那，我们个人的项目又怎么命名呢？

    经过我对“个人的”单词搜索，有“individual、personal、private、one-man”，进一步对以上4个单词词意的分析，并在保证了唯一性，使用每个单词的前4个字母作为前缀，正好和“com”也做了区分。如下：

    indi ：

         个体项目，指个人发起，但非自己独自完成的项目，可公开或私有项目，copyright主要属于发起者。

         包名为“indi.发起者名.项目名.模块名.……”。

    pers ：

         个人项目，指个人发起，独自完成，可分享的项目，copyright主要属于个人。

         包名为“pers.个人名.项目名.模块名.……”。

    priv ：

         私有项目，指个人发起，独自完成，非公开的私人使用的项目，copyright属于个人。

         包名为“priv.个人名.项目名.模块名.……”。

    onem ：

         与“indi”相同，推荐使用“indi”。

 

    另外，我为了区分团队项目和前面所说项目的区分，还有了一下扩展：

    team ：

         团队项目，指由团队发起，并由该团队开发的项目，copyright属于该团队所有。

         包名为“team.团队名.项目名.模块名.……”。

    com ：

 公司项目，copyright由项目发起的公司所有。

         包名为“com.公司名.项目名.模块名.……”。


