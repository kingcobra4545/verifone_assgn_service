package com.prajwal.analyticsservice;

import com.prajwal.analyticsservice.AnalyticsData;

interface IAnalyticsService {
    //Expected Deliverables
    //1. AIDL file defining getCurrentStats() method.
    AnalyticsData getCurrentStats();
}