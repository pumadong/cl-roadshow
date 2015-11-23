#!/bin/bash
# Program:
# 	Health Check
# History:
# 2015-11-17	DongYongjin	First release

PATH=/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin:/usr/local/sbin:~/bin
export PATH

# 要检查的服务器列表
SERVERS=(dx-travel-noah-online01 dx-travel-noah-online02 dx-travel-noah-online03 dx-travel-noah-online04)
# 要检查的端口列表
PORTS=(8411 9411)
# 要检查的URL
URLS=(http://hostname:8411/login/test)
# 错误日志文件位置
ERRORLOGS=/opt/logs/mobile/noah-server/error.log

COMMAND=$1

if [ ! -z $2 ]; then
  SERVERS=($2)
fi

function __check() {
  k=1
  for k in $(seq 1 ${#SERVERS[@]})
  do
    server=${SERVERS[k-1]}
    echo -e "\n\033[44;31;1m正在检查机器 : $server\033[0m\n"
    ssh -q -tt ${server} exit
    if [ ! $? -eq 0 ]; then
      echo -e "$server 连接失败"
      continue
    fi
    # 检查端口
    echo -e "\033[44;32;1m检查端口\033[0m"
    l=1
    for l in $(seq 1 ${#PORTS[@]})
    do
      # cmd="ssh -tt ${SERVERS[k-1]} netstat -anop | grep LIST | grep 8411 | wc -l"
      # 这种方式不能执行 info could be read for "-p": geteuid()=1423182852 but you should be root.)
      # result=`($cmd)`
    
      result=`ssh -q -tt ${SERVERS[k-1]} netstat -anop | grep LIST | grep ${PORTS[l-1]} | wc -l`
      echo -e "${server} ${PORTS[l-1]} 统计结果 : $result"
    done
    
    # 检查HTTP应用
    echo -e "\033[44;32;1m检查HTTP应用\033[0m"
    l=1
    for l in $(seq 1 ${#URLS[@]})
    do
      url=${URLS/hostname/${SERVERS[k-1]}}
      statuscode=`curl -I -m 10 -o /dev/null -s -w %{http_code} $url`
      echo -e "${SERVERS[k-1])} curl ${url} return $statuscode"
    done
    
    # 检查错误日志
    echo -e "\033[44;32;1m检查错误日志的最后一万行\033[0m"
    ssh -q -tt $server tail -10000 $ERRORLOGS | grep ERROR
    
    # 检查磁盘空间
    echo -e "\033[44;32;1m检查磁盘空间\033[0m"
    ssh -q -tt $server df -h
  done
}

function __help(){
    echo "usage: bash healthcheck commond [host];the command will be check|help"
    exit
}

if [[ $# < 1 ]]; then
    __help
fi

case $COMMAND in
  'help')
    __help
    ;;
  'check')
    __check
    ;;
  '*')
    echo "未知命令"
    exit
    ;;
esac

echo ''
