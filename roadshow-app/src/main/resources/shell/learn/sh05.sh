#!/bin/bash
# Program:
# 	User input a filename, Program will check the flowing:
#	1.) exists?	2.) file/directory?	3.) file permissions
# History:
# 2015-11-16	Puma	First release
PATH=/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin:/usr/local/sbin:~/bin
export PATH

# 1. 让用户输入文件名，并且判断用户是否真的有输入字符串
echo -e "Please input a filename, I will check the filename's type and \
permission. \n\n"
read -p "Input a filename: " filename
test -z $filename && echo "You MUST input a filename." && exit 0

# 2. 判断文件是否存在，若不存在则显示信息并结束脚本
test ! -e $filename && echo "The filename '$filename' DO NOT exists" && exit 0

# 3. 开始判断文件类型与属性
test -f $filename && filetype="regulare file"
test -d $filename && filetype="directory"
test -r $filename && perm="readable"
test -w $filename && perm="$perm writable"
test -x $filename && perm="$perm executable"

# 4. 开始输出信息
echo "The filename : $filename is a $filetype"
echo "And the permissions are : $perm" 
