#include <cstring>
#include <iostream>

template <class T, int size = 4>  // size is the expression parameter
class StaticArray {
 private:
  // The expression parameter controls the size of the array
  T m_array[size]{};

 public:
  T* getArray() { return m_array; }

  T& operator[](int index) { return m_array[index]; }
};

template <typename T, int size>
void print(StaticArray<T, size>& array) {
  for (int count{0}; count < size; ++count) std::cout << array[count] << ' ';
}

// Override print() for fully specialized StaticArray<char, 14>
template <>
void print(StaticArray<char, 14>& array) {
  for (int count{0}; count < 14; ++count) std::cout << array[count];
}

template <int size>  // size is still a templated expression parameter
void print(
    StaticArray<char, size>& array)  // we're explicitly defining type char here
{
  for (int count{0}; count < size; ++count) std::cout << array[count];
  std::cout << " what ?" << std::endl;
}

int main() {
  // declare a char array
  StaticArray<char, 14> char14{};

  StaticArray<char, 15> char15{};
  StaticArray<char> char4{};
  std::strcpy(char14.getArray(), "Hello, world!");
  std::strcpy(char15.getArray(), "Hello, world!");
  std::strcpy(char4.getArray(), "Hel");
  // Print the array
  print(char14);
  print(char15);
  print(char4);

  return 0;
}