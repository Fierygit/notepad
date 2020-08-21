template <class T>
class DynamicArray {
 private:
  T* m_array;
  int m_length;

 public:
  DynamicArray(int length) : m_array(new T[length]), m_length(length) {}

  ~DynamicArray() { delete[] m_array; }

  // Copy constructor
  DynamicArray(const DynamicArray& arr) = delete;

  // Copy assignment
  DynamicArray& operator=(const DynamicArray& arr) = delete;

  // Move constructor
  DynamicArray(DynamicArray&& arr)
      : m_length(arr.m_length), m_array(arr.m_array) {
    arr.m_length = 0;
    arr.m_array = nullptr;
  }

  // Move assignment
  DynamicArray& operator=(DynamicArray&& arr) {
    if (&arr == this) return *this;

    delete[] m_array;

    m_length = arr.m_length;
    m_array = arr.m_array;
    arr.m_length = 0;
    arr.m_array = nullptr;

    return *this;
  }

  int getLength() const { return m_length; }
  T& operator[](int index) { return m_array[index]; }
  const T& operator[](int index) const { return m_array[index]; }
};

#include <chrono>  // for std::chrono functions
#include <iostream>

class Timer {
 private:
  // Type aliases to make accessing nested type easier
  using clock_t = std::chrono::high_resolution_clock;
  using second_t = std::chrono::duration<double, std::ratio<1> >;

  std::chrono::time_point<clock_t> m_beg;

 public:
  Timer() : m_beg(clock_t::now()) {}

  void reset() { m_beg = clock_t::now(); }

  double elapsed() const {
    return std::chrono::duration_cast<second_t>(clock_t::now() - m_beg).count();
  }
};

// Return a copy of arr with all of the values doubled
DynamicArray<int> cloneArrayAndDouble(const DynamicArray<int>& arr) {
  DynamicArray<int> dbl(arr.getLength());
  for (int i = 0; i < arr.getLength(); ++i) dbl[i] = arr[i] * 2;

  return dbl;
}

int main() {
  Timer t;

  DynamicArray<int> arr(1000000);

  for (int i = 0; i < arr.getLength(); i++) arr[i] = i;

  arr = cloneArrayAndDouble(arr);

  std::cout << t.elapsed();
}