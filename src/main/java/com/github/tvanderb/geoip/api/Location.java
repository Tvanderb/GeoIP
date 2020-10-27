package com.github.tvanderb.geoip.api;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Location {

    private static final String GEO_LOCATE_URL = "https://tools.keycdn.com/geo?host=";
    private static final Pattern LAT_LONG_SPLIT = Pattern.compile("([^0-9-\\.-])+");
    private static final Pattern IP_REGEX = Pattern.compile("([0-9-]+\\.[0-9-]+\\.[0-9-]+\\.[0-9-]+)");
    private static final Pattern COORD_REGEX = Pattern.compile("^((-)?[0-9]+\\.[0-9]+) \\(lat\\) \\/ ((-)?[0-9]+\\.[0-9]+) \\(long\\)$");

    /**
     * Gets the location of your IP address.
     *
     * @return The {@link Location Location} of your IP address.
     * @throws IOException If an I/O error occurs.
     */
    public static Location getMyLocation() throws IOException {
        URL ipUrl = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(ipUrl.openStream()));

        return getLocation(in.readLine());
    }

    /**
     * Gets the specified IP's coordinates.
     *
     * @param hostname The IP address you wish to find the coordinates of.
     * @return The {@link Location Location} of the IP address.
     * @throws IOException If an I/O error occurs.
     */
    public static Location getLocation(String hostname) throws IOException {
        if (!IP_REGEX.matcher(hostname).find()) {
            InetAddress addr = InetAddress.getByName(hostname);
            hostname = addr.getHostAddress();
        }

        String geoLocateUrl = GEO_LOCATE_URL + hostname;

        Document document = Jsoup
                .connect(geoLocateUrl)
                .get();

        Element table = document.getElementsByClass("row mb-0").first();
        Elements resultElements = table.getElementsByClass("col-8");

        String coordinates = "00.000 (lat) / 00.000 (long)";

        for (Element element : resultElements) {
            String text = element.text();
            Matcher m = COORD_REGEX.matcher(text);
            if (m.find()) {
                coordinates = text;
            }
        }

        String[] coordsSplit = coordinates.replaceAll(LAT_LONG_SPLIT.pattern(), " ").split(" ");

        return new Location(coordsSplit[0], coordsSplit[1]);
    }

    private final String latitude;
    private final String longitude;

    private Location(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * @return The latitude coordinate of the location.
     */
    public String getLatitude() {
        return this.latitude;
    }

    /**
     * @return The longitude coordinate of the location.
     */
    public String getLongitude() {
        return this.longitude;
    }

    /**
     * @return The location as a string. "(e.g (37.751 / -97.822)")
     */
    @Override
    public String toString() {
        return "(" + this.latitude + " / " + this.longitude + ")";
    }
}
