package com.github.tvanderb.geoip;

import com.github.tvanderb.geoip.api.Location;

import java.io.IOException;

public class Test {
    public static void main(String... args) throws IOException {
        System.out.println(Location.getMyLocation());
    }
}
