#ifndef PAYMENTCONTROLLER_H
#define PAYMENTCONTROLLER_H

#include<string>
#include "paymentService.h"
#include "bookingService.h"
using namespace std;

class PaymentController {
public:
    PaymentController();
    void paymentFailed(const string bookingId, const string user);
    void paymentSuccess(const string bookingId, const string user);
};

PaymentController::PaymentController() {}

void PaymentController::paymentFailed(const string bookingId, const string user) {
    PaymentService* paymentServiceInstance = PaymentService::getPaymentServiceInstance();
    BookingService* bookingServiceInstance = BookingService::getBookingServiceInstance();

    Booking* booking = bookingServiceInstance->getBooking(bookingId);
    paymentServiceInstance->processPaymentFailed(booking, user);
}

void PaymentController::paymentSuccess(const string bookingId, const string user) {
    BookingService* bookingServiceInstance = BookingService::getBookingServiceInstance();

    try {
        bookingServiceInstance->confirmBooking(bookingServiceInstance->getBooking(bookingId), user);
    } catch(BadRequestException e) {
        cout << e.what() << endl;
    }
}

#endif