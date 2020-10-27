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
import java.util.regex.Pattern;

public class AddressInfo {

    private static final String GEO_LOCATE_URL = "https://tools.keycdn.com/geo?host=";
    private static final Pattern IP_REGEX = Pattern.compile("([0-9-]+\\.[0-9-]+\\.[0-9-]+\\.[0-9-]+)");

    /**
     * Gets info surrounding your IP address.
     *
     * @return The {@link AddressInfo AddressInfo} for your address/hostname.
     * @throws IOException If an I/O error occurs.
     */
    public static AddressInfo getMyAddress() throws IOException {
        URL ipUrl = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(ipUrl.openStream()));

        return getInfo(in.readLine());
    }

    /**
     * Gets info surrounding a hostname.
     *
     * @param hostname The hostname.
     * @return The {@link AddressInfo AddressInfo} for the address/hostname.
     * @throws IOException If an I/O error occurs.
     */
    public static AddressInfo getInfo(String hostname) throws IOException {
        if (!IP_REGEX.matcher(hostname).find()) {
            InetAddress addr = InetAddress.getByName(hostname);
            hostname = addr.getHostAddress();
        }

        String geoLocateUrl = GEO_LOCATE_URL + hostname;

        Document document = Jsoup
                .connect(geoLocateUrl)
                .get();

        Element table = document.getElementsByClass("row mb-0").get(1);
        Elements resultElements = table.getElementsByClass("col-8");

        Element hostnameElement = resultElements.get(1);
        String hostnameResult = hostnameElement.text();

        Element providerElement = resultElements.get(2);
        String provider = providerElement.text();

        Element asnElement = resultElements.get(3);
        String asn = asnElement.text();

        return new AddressInfo(provider, hostnameResult, asn, hostname);
    }

    private final String provider;
    private final String hostname;
    private final String asn;
    private final Location location;

    private AddressInfo(String provider, String hostname, String ASN, String address) throws IOException {
        this.provider = provider;
        this.hostname = hostname;
        this.asn = ASN;
        this.location = Location.getLocation(address);
    }

    /**
     * @return The address's ISP.
     */
    public String getProvider() {
        return this.provider;
    }

    /**
     * @return The address's hostname.
     */
    public String getHostname() {
        return this.hostname;
    }

    /**
     * @return The address's Autonomous System Number.
     */
    public String getAsn() {
        return this.asn;
    }

    /**
     * @return The class representing the address's location.
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * @return A string containing the info collected. (e.g "Location: (37.751 / -97.822), ISP: GOOGLE, ASN: 15169, Hostname: yyz10s03-in-f14.1e100.net"_
     */
    @Override
    public String toString() {
        return "Location: " + this.location + ", ISP: " + this.provider + ", ASN: " + this.asn + ", Hostname: " + this.hostname;
    }
}
