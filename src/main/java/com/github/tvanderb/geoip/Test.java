package com.github.tvanderb.geoip;

import com.github.tvanderb.geoip.api.AddressInfo;
import com.github.tvanderb.geoip.api.Location;

import java.io.IOException;

public class Test {
    public static void main(String... args) throws IOException {
        System.out.println("Location: " + Location.getMyLocation());
        System.out.println("Info: " + AddressInfo.getMyAddress());

        System.out.println("\n--------------------------\n");

        System.out.println("Location: " + Location.getLocation("google.com"));
        System.out.println("Info: " + AddressInfo.getInfo("google.com"));
    }
}
