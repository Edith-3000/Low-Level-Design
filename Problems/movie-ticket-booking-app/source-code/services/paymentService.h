#ifndef PAYMENTSERVICE_H
#define PAYMENTSERVICE_H

#include<mutex>
#include<map>
#include "booking.h"
#include "badRequestException.h"
#include "inMemorySeatLockProvider.h"

using namespace std;

class PaymentService {
    static PaymentService* paymentServiceInstance;
    static mutex mtx;
    const int allowedRetries;
    map<Booking*, int> bookingFailures;

    PaymentService(int allowedRetries);
    PaymentService(const PaymentService&);

public:
    static PaymentService* getPaymentServiceInstance(int allowedRetries);
    void processPaymentFailed(Booking* booking, string user);
};

PaymentService* PaymentService::paymentServiceInstance = nullptr;

mutex PaymentService::mtx;

PaymentService* PaymentService::getPaymentServiceInstance(int allowedRetries = 2) {
    // Double Check Locking
    if(paymentServiceInstance == nullptr) {
        mtx.lock();
        if(paymentServiceInstance == nullptr) {
            paymentServiceInstance = new PaymentService(allowedRetries);
        }
        mtx.unlock();
    }

    return paymentServiceInstance;
}

void PaymentService::processPaymentFailed(Booking* booking, string user) {
    if(booking->getUser() != user) {
        throw BadRequestException();
    }

    if(bookingFailures.find(booking) == bookingFailures.end()) {
        bookingFailures[booking] = 0;
    }

    int currentFailuresCount = bookingFailures[booking];
    int newFailuresCount = currentFailuresCount + 1;

    bookingFailures[booking] = newFailuresCount;

    if(newFailuresCount > allowedRetries) {
        SeatLockProvider* seatLockProvider = InMemorySeatLockProvider::getInMemorySeatLockProviderInstance(10);
        seatLockProvider->unlockSeats(booking->getShow(), booking->getBookedSeats(), user);
    }
}

#endif