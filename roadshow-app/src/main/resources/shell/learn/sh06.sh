#!/bin/bash
# Program:
# 	This program shows the user's choice
# History:
# 2015-11-16	Puma	First release
PATH=/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin:/usr/local/sbin:~/bin
export PATH

read -p "Please input (Y/N) : " yn
[ "$yn" == "Y" -o "$yn" == "y" ] && echo "OK, continues" && exit 0
[ "$yn" == "N" -o "$yn" == "n" ] && echo "Oh, interrupt!" && exit 0
echo " I don't know  what your choise is" && exit 0
