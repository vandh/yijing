vandh
simon.884856
vandh@163.com

1. jdk1.6,环境变量		java_home=D:\java_project\sdk\jdk1.6;  path=%java_home%\bin;
2. maven3，环境变量   maven_home=D:\java_project\sdk\maven3; path=%maven_home%\bin;


3. git
	Git是一个极其快速、高效、分布式版本控制系统，完美的协同开发软件。
	相比CVS/SVN，Git 的优势：
		－ 支持离线开发，离线Repository
		－ 强大的分支功能，适合多个独立开发者协作
		－ 速度块

	GitHub是与他人协同工作的最好方法。叉送管理你所有的公开和私有的git仓库。
	GitHub是一个托管Git （开源或闭源）项目的网站，闭源收费，最低7$/月起，免费的300G空间。

4. 使用GitHub步骤：
	1)、申请GitHub帐户 vandh ，创建名为yijing的新Repository 
	2)、安装Git客户端（Linux）,windows : Git-1[1].7.9-preview20120201.exe
		#yum install git git-gui
	3)、 生成密钥对，这样项目可以push到 GitHub上
		#ssh-keygen -t rsa -C "vandh@163.com"
	4)、将.ssh/id_rsa.pub拷贝到GitHub网站
	5) Global setup:
		git config --global user.name "vandh"
		git config --global user.email vandh@163.com
	6) 创建本地项目
		进入D:\java_project\myself\jyijing
		右击jyijing目录，选择git bash here
		git init
		git add --all
		git commit -m 'first commit'
		git remote add simon git@github.com:vandh/yijing.git
		git push -u simon master
      Existing Git Repo?
  cd existing_git_repo
  git remote add origin git@github.com:vandh/yijing.git
  git push -u origin master
      Importing a Subversion Repo?
  Check out the guide for step by step instructions.

5. 