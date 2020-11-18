#include "KitchenAppliance.hpp"

class Refrigerator : public KitchenAppliance {
  float cost;

 public:
  Refrigerator(int cost) : KitchenAppliance(true), cost(cost) {}
  bool isElectrical() override;
};
