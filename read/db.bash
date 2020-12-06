###
 # @Author: Firefly
 # @Date: 2020-12-05 15:28:58
 # @Descripttion: 
 # @LastEditTime: 2020-12-05 15:33:37
### 

#!/bin/bash
db_set () {
	echo "$1,$2" >> database
}

db_get () {
	grep "^$1," database | sed -e "s/^$1,//" | tail -n 1
}