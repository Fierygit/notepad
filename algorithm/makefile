#makefile
#  $@	--目标文件
#  $^	--所有的依赖文件
#  $< 	--第一个依赖文件。
 
CC := g++
CFLAGS := -g
TARGET := test
SRCS := $(wildcard *.cpp)
OBJS := $(patsubst %cpp,%o,$(SRCS))
all:$(TARGET)
%.o:%.cpp
	$(CC) $(CFLAGS) -c $<
$(TARGET):$(OBJS)
	$(CC) $(CFLAGS) -o $@ $^
	
clean:
	rm -rf $(TARGET) *.o