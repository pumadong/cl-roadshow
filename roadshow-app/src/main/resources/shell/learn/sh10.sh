#!/bin/bash
# Program:
# 	Using netstat and grep to detect WWW,SSH,FTP and Mail services.
# History:
# 2015-11-19	Puma	First release
PATH=/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin:/usr/local/sbin:~/bin
export PATH

# 1. 先做一些告知的操作
echo "Now, I will detect your Linux server's services!"
echo -a "The www, ftp, ssh, and mail will be detect! \n"

# 2. 开始进行一些测试的工作，并且也输出一些信息
testing=$(netstat -anop | grep ":80")
if [ "$testing" != "" ]; then
    echo "WWW is running in you system."
fi
 
testing=$(netstat -anop | grep ":22")
if [ "$testing" != "" ]; then
    echo "SSH is running in you system."
fi 

testing=$(netstat -anop | grep ":21")
if [ "$testing" != "" ]; then
    echo "FTP is running in you system."
fi
 
testing=$(netstat -anop | grep ":25")
if [ "$testing" != "" ]; then
    echo "Mail is running in you system."
fi 
