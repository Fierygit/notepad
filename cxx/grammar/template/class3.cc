template<typename Tag, typename Tag::type M>
struct Rob { 
  friend typename Tag::type get(Tag) {
    return M;
  }
};

struct A {
private:
    int a;
    friend void f();
};

// tag used to access A::a
struct A_f { 
  typedef int A::*type;
  friend type get(A_f);
};

// Explicit instantiation - OK, no access checks
template struct Rob<A_f, &A::a>;

// Try to use the type in some way - get an error.
struct Rob<A_f, &A::a> r;            // error
typedef struct Rob<A_f, &A::a> R;    // error
void g(struct Rob<A_f, &A::a>);      // error

// However, it's Ok inside a friend function.
void f() {
    Rob<A_f, &A::a> r;               // OK
    typedef Rob<A_f, &A::a> R;       // OK
}

