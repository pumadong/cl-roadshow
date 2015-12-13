#!/bin/bash
# Program:
# Using for...loop to print 3 animals 
# History:
# 2015-12-10	Puma	First release
PATH=/bin:/sbin:/usr/local/bin:/usr/local/sbin:/usr/bin:/usr/sbin:~/bin
export PATH

for animal in dog cat elephant
do
  echo "There are ${animal}s..."
done

