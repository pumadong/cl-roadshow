#!/bin/bash
# Programi:
# Using ping command to check the network's pc state.
# History:
# 2015-12-17	Puma	First release
PATH=/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin:/usr/local/sbin:~/bin
export PATH
network="192.168.1"
for sitenu in $(seq 1 10)
do
  # 取得ping的回传值是正确的还是失败的
  ping -c 1 ${network}.${sitenu} &> /dev/null && result=0 || result=1
  # 开始显示结果
  if [ "$result" == 0 ]; then
    echo "Server ${network}.${sitenu} is UP."
  else
    echo "Server ${network}.${sitenu} is DOWN."
  fi
done

