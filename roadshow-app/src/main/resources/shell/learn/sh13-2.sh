#!/bin/bash
# Program:
# Repeat question until user input correct answer
# History:
# 2015-12-08	Puma	First release
PATH=/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin:/usr/local/sbin:~/bin
export PATH

until [ "$yn" == "yes" -o "$yn" == "YES" ]
do
  read -p "Pleas input yes/YES to stop this program: " yn
done
echo "OK! You input the correct answer."
