#!/bin/bash
# Program:
# 
# History:
# 2015-12-19	Puma	First release
PATH=/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin:/usr/local/sbin:~/bin
export PATH

# 1.看看目录是否存在
read -p "Please input a directory: " dir
if [ "$dir" == "" -o ! -d "$dir" ]; then
  echo "The $dir is NOT exists in your system."
  exit 1
fi

# 2.开始测试文件
filelist=$(ls $dir)
for filename in $filelist
do
  perm=""
  test -r "$dir/$filename" && perm="$perm readable"
  test -w "$dir/$filename" && perm="$perm writable"
  test -x "$dir/$filename" && perm="$perm executable"
  echo "The file $dir/$filename's permission is $perm "
done
