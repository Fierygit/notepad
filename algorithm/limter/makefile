
#makefile
#  $@	--目标文件
#  $^	--所有的依赖文件
#  $< 	--第一个依赖文件。

util = ../../util.o
thread = -l pthread

target: util rate_limter slid_limter token_limter
	echo "start to run"
	echo $^ is made
util:
	g++ -c ../../util.cc -o ../../util.o

rate_limter : rate_limter.cc
	echo "make $@" 
	g++ $@.cc  $(util) $(thread) -o $@.exe

slid_limter : slid_limter.cc
	echo "make $@" 
	g++ $<  $(util) $(thread) -o $@.exe

token_limter : token_limter.cc
	echo "make $@" 
	g++ $@.cc  $(util) $(thread) -o $@.exe



clean:
	rm -r *.exe 
	rm a.out
	rm *.o