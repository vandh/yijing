#!/bin/sh
cat $1 | sed -e "s/javax.servlet.jsp.el/org.boc.rule/g" > $1.bak
mv -f $1.bak $1
