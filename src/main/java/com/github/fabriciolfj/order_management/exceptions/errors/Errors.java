package com.github.fabriciolfj.order_management.exceptions.errors;

import java.util.ResourceBundle;

public enum Errors {

    SERVER_ERROR,
    INTERVAL_INVALID;

    public String toMessage() {
        var bundle = ResourceBundle.getBundle("exception/message");
        return bundle.getString(this.name() + ".message");
    }
}
