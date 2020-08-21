
static void putc(int fd, char c) { write(fd, &c, 1); }

// 进制转换输出， sgn 是符号， base 是进制， xx 是一个10进制数
static void printint(int fd, int xx, int base, int sgn) {
  static char digits[] = "0123456789ABCDEF";
  char buf[16];
  int i, neg;
  uint x;

  neg = 0;
  if (sgn && xx < 0) {
    neg = 1;
    x = -xx;
  } else {
    x = xx;
  }

  i = 0;
  do {
    buf[i++] = digits[x % base];
  } while ((x /= base) != 0);
  if (neg) buf[i++] = '-';

  while (--i >= 0) putc(fd, buf[i]);
}

typedef unsigned int uint;

// c 语言的可变参数的获取方法
// Print to the given fd. Only understands %d, %x, %p, %s.
void myprintf(int fd, const char *fmt, ...) {
  char *s;
  int c, i, state;
  uint *ap;

  state = 0;
  // 参数压栈的时候是按顺序的， 只需要fmt 加上1即可取得变参数, 使用 uint 来操作字节
  ap = (uint *)(void *)&fmt + 1;
  for (i = 0; fmt[i]; i++) {
    c = fmt[i] & 0xff;
    if (state == 0) {
      if (c == '%') {
        state = '%';
      } else {
        putc(fd, c);
      }
    } else if (state == '%') {
      if (c == 'd') {
        printint(fd, *ap, 10, 1);
        ap++;
      } else if (c == 'x' || c == 'p') {
        printint(fd, *ap, 16, 0);
        ap++;
      } else if (c == 's') {
        s = (char *)*ap;
        ap++;
        if (s == 0) s = "(null)";
        while (*s != 0) {
          putc(fd, *s);
          s++;
        }
      } else if (c == 'c') {
        putc(fd, *ap);
        ap++;
      } else if (c == '%') {
        putc(fd, c);
      } else {
        // Unknown % sequence.  Print it to draw attention.
        putc(fd, '%');
        putc(fd, c);
      }
      state = 0;
    }
  }
}