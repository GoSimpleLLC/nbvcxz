package me.gosimple.nbvcxz.matching;

import me.gosimple.nbvcxz.matching.match.LeakMatch;
import me.gosimple.nbvcxz.matching.match.Match;
import me.gosimple.nbvcxz.resources.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Look for the password by querying the Leak API
 *
 * @author Adam Brusselback
 */
public final class LeakMatcher implements PasswordMatcher
{
    public List<Match> match(final Configuration configuration, final String password)
    {
        ArrayList<Match> result = new ArrayList<>();

        if (!configuration.getLeakApiEnabled())
            return result;

        String hashedPassword = hashPassword(password);
        String prefix = hashedPassword.substring(0, 5);
        String suffix = hashedPassword.substring(5).toUpperCase();
        String url = configuration.getLeakApiEndpoint() + prefix;

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "HIBP-Java-API");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                HashMap<String, Integer> response = new HashMap<>();
                while ((inputLine = in.readLine()) != null) {
                    String[] line = inputLine.split(":");
                    response.put(line[0], Integer.valueOf(line[1]));
                }
                in.close();

                if (response.containsKey(suffix)) {
                    LeakMatch match = new LeakMatch(password, configuration, response.get(suffix), 0, password.length());
                    result.add(match);
                    return result;
                } else {
                    return result;
                }
            } else {
                System.out.println("Error with request, response code: " + responseCode);
                return result;
            }
        } catch (IOException e) {
            System.out.println("Unable to run LeakMatcher");
            e.printStackTrace();
        }
        return null;
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
