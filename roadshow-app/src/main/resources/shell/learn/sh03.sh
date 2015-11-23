#!/bin/bash
# Program:
# 	Program creates three files, which named by user's input and date command
# History:
# 2015-11-16	Puma	First release
PATH=/bin:/sbin:/usr/bin:/usr/sbin/:/usr/local/bin:/usr/local/sbin:~/bin
export PATH

# 1.让用户输入文件名，并取得 fileuser 这个变量
echo -e "I will use 'touch' command to create 3 files."
read -p "Please input your filename: " fileuser

# 2.为了避免用户随意按 [Enter] ,利用变量功能分析文件名是否有设置
filename=${fileuser:-"filename"}

# 3.开始利用 date 命令来取得所需要的文件名了
date1=$(date --date='2 days ago' +%Y%m%d)
date2=$(date --date='1 days ago' +%Y%m%d)
date3=$(date +%Y%m%d)
file1=${filename}${date1}
file2=${filename}${date2}
file3=${filename}${date3}

# 4.创建文件名
touch "$file1"
touch $file2
touch "$file3"
