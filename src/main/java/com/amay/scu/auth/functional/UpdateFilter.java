package com.amay.scu.auth.functional;


import com.amay.scu.report.controller.FilterItem;

import java.util.List;

public interface UpdateFilter {
    void execute(List<FilterItem> filterItems);
}
