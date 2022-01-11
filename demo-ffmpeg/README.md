## 1、基础服务安装

### 1.1、ffmpeg 安装

默认情况下用yum install ffmpeg 安装的版本比较老，一些旧的版本ffmpeg的一些命令不支持
一些旧版本的 FFmpeg 不支持选项 -c copy，可以使用选项 -vcodec copy -acodec copy 替代。

如果想要安装比较新的3.4.8版本的可以进行如下操作:

1.升级系统

sudo yum install epel-release -y
sudo yum update -y
sudo rpm --import http://li.nux.ro/download/nux/RPM-GPG-KEY-nux.ro
sudo rpm -Uvh http://li.nux.ro/download/nux/dextop/el7/x86_64/nux-dextop-release-0-5.el7.nux.noarch.rpm

2.卸载旧版本
yum remove ffmpeg
由于CentOS没有官方FFmpeg rpm软件包。但是，我们可以使用第三方YUM源（Nux Dextop）完成此工作。比如 rpmfusion.org的yum源： 此处选择rpmfusion.org的yum源方式安装

CentOS 7 安装   rpmfusion源：

yum localinstall --nogpgcheck https://download1.rpmfusion.org/free/el/rpmfusion-free-release-7.noarch.rpm

3.安装FFmpeg 和 FFmpeg开发包

sudo yum install ffmpeg ffmpeg-devel -y

4.测试是否安装成功

ffmpeg -version

### 1.2 常用命令

生成循环流

nohup ffmpeg -threads 2 -re -fflags +genpts -stream_loop -1 -i 70年.MP4 -s 640x360 -ac 2 -f flv -vcodec libx264 -profile:v baseline -b:v 600k -maxrate 600k -bufsize 600k -r 24 -ar 44100 -g 48 -c:a aac -b:a 64k "rtmp://10.12.109.176/live/70mp4" > 70mp4.log 2>&1  &
