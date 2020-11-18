
#include <iostream>

class KitchenAppliance {
 protected:
  bool isElectric;

 public:
  KitchenAppliance() : isElectric(false) {}
  KitchenAppliance(bool isElectric) : isElectric(isElectric) {}

  virtual bool isElectrical();
};