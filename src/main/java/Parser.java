/**
 * Created by Valsorya94 on 25.04.2017.
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}");

    public static void main(String[] args) throws Exception {
        Document page = getPage();
        Element tableWth = page.select("table[class=wt]").first();
        Elements elements = tableWth.select("tr");
        for (Element name : elements) {
            if (name.hasClass("wth")) {
                printDate(name);
            } else if (!name.getElementsByAttributeValue("valign", "top").isEmpty()) {
                printLineTD(name);
            }
        }
    }

    private static Document getPage() throws IOException {
        String url = "http://www.pogoda.spb.ru";
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    private static String getDateFromString(String stringDate) throws Exception {
        Matcher matcher = pattern.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        } else
            throw new Exception("Can't extract date from string!");
    }

    private static void printLineTD(Element name) {
        for (Element td : name.select("td")) {
            System.out.print(td.text() + "   ");
        }
        System.out.println("");

    }

    private static void printDate(Element name) throws Exception {
        String dateString = name.select("th[id=dt]").text();
        String date = getDateFromString(dateString);
        System.out.println();
        System.out.println(date + "      Явления    Температура     Давление     Влажность    Ветер");
    }
}
