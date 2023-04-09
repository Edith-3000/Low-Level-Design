#include "user.hpp"
#include "order.hpp"
#include "orderManager.hpp"

int main() {
    User* user = new User("Tony Stark");

    Stock* stock = new Stock("GOOGL", 108.42, EXCHANGE::NSE);

    Order* order = new Order(TXN_TYPE::BUY, ORDER_TYPE::LIMIT, EXCHANGE::NSE, 50, 105, stock);

    OrderManager* orderManager = new OrderManager();
    orderManager->placeOrder("Tony Stark", order);

    return 0;
}