#!/bin/bash
# Program:
# 	User inputs 2 integer numbers; program will cross these two numbers.
# History:
# 2015-11-16	Puma	First release
PATH=/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin:/usr/local/sbin:~/bin
export PATH
echo -e "Your SHOULD input 2 numbers, I will cross them! \n"
read -p "first number: " firstnu
read -p "second number: " secondnu
total=$(($firstnu*$secondnu))
echo -e "\nThe result of $firstnu * $secondnu is ==> $total" 
