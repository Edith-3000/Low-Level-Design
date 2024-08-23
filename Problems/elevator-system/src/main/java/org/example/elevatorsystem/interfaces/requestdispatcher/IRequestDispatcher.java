package org.example.elevatorsystem.interfaces.requestdispatcher;

import lombok.NonNull;
import org.example.elevatorsystem.models.Request;

public interface IRequestDispatcher {
    void dispatchRequest(@NonNull final Request request);
}
