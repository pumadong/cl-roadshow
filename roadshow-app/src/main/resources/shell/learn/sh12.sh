#!/bin/bash
# Program:
# 	This script only accepts the flowing parameter: one, two, three
# History:
# 2015-11-26	Puma	First release
PATH=/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin:/usr/local/sbin:~/bin
export PATH

echo "This program will print your selection !"
# read -p "Input your choice: " choice
# case $choice in
case $1 in
  "one")
    echo "Your choice is ONE"
    ;;
  "two")
    echo "Your choice is TWO"
    ;;
  "three")
    echo "Your choice is THREE"
    ;;
  *)
    echo "Usage $0 {one|tow|three}"
    ;;
esac
