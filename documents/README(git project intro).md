# 一、基本概念

## 1、 仓库（Repository ）

- 线上版本仓库（这里称为：源仓库）

  - 项目启动时，由项目管理者搭建起最原始的仓库，称 origin。
  - 源仓库的有个作用 :
    - 1.汇总参与该项目的各个开发者的代码
    - 2.存放趋于稳定和可发布的代码
  - 源仓库应该是受保护的，开发者不应该直接对其进行开发工作。只有项目管理者能对其进行较高权限的操作。

- 开发者仓库 （独自、本地仓库）

  - 任何开发者都不会对源仓库进行直接的操作，源仓库建立以后，每个开发者需要做的事情就是把源仓库的“复制”一份，作为自己日常开发的仓库。这个复制是Gitlab上面的`Fork`。
  - 每个开发者所`Fork`的仓库是完全独立的，互不干扰，甚至与源仓库都无关。每个开发者仓库相当于一个源仓库实体的影像，开发者在这个影像中进行编码，提交到自己的仓库中，这样就可以轻易地实现团队成员之间的并行开发工作。而开发工作完成以后，开发者可以向源仓库发送 `Pull Request` ，本地仓库先合并源仓库，解决冲突，再发起`Merge Request`请求管理员把自己的代码合并到源仓库中的`develop`分支，这样就实现了分布式开发工作和集中式的管理。

## 2、分支（Branch）

- master branch ： 主分支

  - master：主分支从项目一开始便存在，它用于存放经过测试，已经完全稳定代码；在项目开发以后的任何时刻当中，master存放的代码应该是可作为产品供用户使用的代码。所以，应该随时保持master仓库代码的清洁和稳定，确保入库之前是通过`完全测试`和`code reivew`的。master分支是所有分支中最不活跃的，大概每个月或每两个月更新一次，每一次master更新的时候都应该用git打上`tag`，来说明产品有新版本发布。

- develop branch : 开发分支

  - develop：开发分支，一开始从master分支中分离出来，用于开发者存放基本稳定代码。每个开发者的仓库相当于源仓库的一个镜像，每个开发者自己的仓库上也有master和develop。开发者把功能做好以后，是存放到自己的develop中，当测试完以后，可以向管理者发起一个`Pull Request`，请求把`自己仓库的develop分支`合并到`源仓库的develop`中。所有开发者开发好的功能会在源仓库的develop分支中进行汇总，当develop中的代码经过不断的测试，已经逐渐趋于稳定了，接近产品目标了。这时候，就可以把develop分支合并。到master分支中，发布一个新版本。

    > 注:任何人不应该向master直接进行无意义的合并、提交操作。正常情况下，master只应该接受develop的合并，也就是说，master所有代码更新应该源于合并develop的代码。

- feature branch : 功能分支

  - feature：功能性分支，是用于开发项目的功能的分支，是开发者主要战斗阵地。开发者在本地仓库从develop分支分出功能分支，在该分支上进行功能的开发，开发完成以后再合并到develop分支上，这时候功能性分支已经完成任务，可以删除。功能性分支的命名一般为feature-"为需要开发的功能的名称"。

# 二、举例说明

**Step 1 ： 创建项目，搭建源仓库**
团队开发，属于 Group级项目，Namespace使用Group name，Visibility 默认Internal。

**Step 2：开发者Fork源仓库 **
源仓库建立以后，每个开发就可以去克隆一份源仓库到自己的Gitlab账号中，然后作为自己开发所用的仓库。

**Step 3：把自己的项目仓库clone到本地 **

```ruby
$ git clone git@git@123.159.2.190:junting.liu/BizAdmin.git
```

**Step 4：本地自己构建功能分支进行开发 **
假设我们要开发一个Vue 表单组件的功能:

```ruby
# 查看当前分支
$ git branch
# 切换`develop`分支
$ git checkout develop
# 切换到一个功能性分支
$ git checkout -b feature-formComponent
# 开发完毕，先进行当前状态监测
$ git status
# 将开发好的功能添加到Git暂存区，并没有提交，这个时候文件是处于工作区
$ git add .
# 这个时候你可以使用 git status 监测下这个时候的状态
# 将暂存区的文件提交下
$ git commit -m "修饰当前你进行了什么操作"
# 再次使用git status 监测下git的状态，会提示 当前work clean 工作区很干净
# 切回develo分支，合并功能分支
$ git checkout develop
$ git merge feature-formComponent
# 合并后，监测状态、冲突
$ git diff
# 删除功能性分支
$ git branch -d feature-formComponent
# 拉取自己的远程中的develop分支,本地解决冲突
$ git pull origin junting   <remote> <branch>
#推送到自己远程的仓库develop分支
$ git push origin develop
# 功能开发完毕，就要向源仓库推送自的develop分支
# 先还是要拉取下源仓库develp分支，本地合并解决冲突
# 这几步也是跟上两步一样的
```

### 三、协同合作中最常用的Git命令

- 本地使用Git，都需要先配置下自己仓库的邮箱和用户名，基本只使用一次
  - git config --global [user.email](https://link.jianshu.com/?t=http://user.email) "[you@example.com](https://link.jianshu.com/?t=mailto:you@example.com)"
  - git config --global user.name "Your Name"
- 常用命令
  - git clone <url> #克隆远程版本库
  - git init #初始化本地仓库
  - git status #查看下仓库的当前的状态
  - git add <file> / . #跟踪指定文件或所有改动过的文件
  - git commit -m "commit message" # 提交所有更新过的文件
  - git log #查看提交历史日志
  - git relog #查看所有操作记录日志，回退穿越很有用
  - git reset --hard HEAD # 回退
  - git pull <remote> <branch> # 拉取代码并快速合并
  - git push <remote> <branch> # 推送代码及快速合并
  - git fetch <remote> / --all # 获取指定远程仓库或所有远程仓库代码
  - git merge <branch> # 合并指定分支到当前分支

## Git常用命令速查表

![Git常用命令速查表](https://upload-images.jianshu.io/upload_images/735083-174c2a92b31bfe7d.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/946/format/webp)

