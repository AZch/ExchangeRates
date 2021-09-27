package com.wcreators.utils.date;

import java.util.Date;

public interface DateUtils<T> {
    T now();

    String dateToString(T date);

    int getMinutes(T date);
}
