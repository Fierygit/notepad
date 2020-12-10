###
 # @Author: Firefly
 # @Date: 2020-12-05 15:28:58
 # @Descripttion: 
 # @LastEditTime: 2020-12-09 15:59:40
### 

#!/bin/bash
db_set () {
	echo "$1,$2" >> database
}

db_get () {
	grep "^$1," database | sed -e "s/^$1,//" | tail -n 1
}

cat wc.txt | 
tr -s ' ' '\n' | 
uniq -c | sort -r | awk '{print $2" "$1}'