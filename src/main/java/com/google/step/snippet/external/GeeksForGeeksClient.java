package com.google.step.snippet.external;

import com.google.step.snippet.data.Card;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

public class GeeksForGeeksClient extends Client {
  private static final String TITLE_TAG = "h1";
  private static final String DESC_TAG = "p";
  private static final String SNIPPET_CLASS = "code-block";
  private static final String CODE_CLASS = "code-container";
  private static final String SOURCE_NAME = "GeeksForGeeks";
  private static final String ICON_LINK = "https://geeksforgeeks.org/favicon.ico";

  private final String cseId;
  private final List<String> escapeFilters = Arrays.asList("html", "svg", "icons", "css");

  public GeeksForGeeksClient(String cseId) {
    this.cseId = cseId;
  }

  @Override
  public String getCseId() {
    return cseId;
  }

  /**
   * Creates and returns a {@code Card} for the given GeeksForGeeks URL.
   *
   * @param geeksLink the URL of the GeeksForGeeks web page to create the card for
   * @return the created card, or {@code null} if a card could not be created
   */
  @Override
  public Card search(String geeksLink, String query) {
    Document doc = null;
    try {
      doc = Jsoup.connect(geeksLink).get();
    } catch (IOException e) {
      return null;
    }
    Elements titles = doc.getElementsByTag(TITLE_TAG);
    if (titles.isEmpty() || titles.first().text().isEmpty()) {
      return null;
    }
    Elements descriptions = doc.getElementsByTag(DESC_TAG);
    if (descriptions.isEmpty() || descriptions.first().text().isEmpty()) {
      return null;
    }
    Elements snippets = doc.getElementsByClass(SNIPPET_CLASS);
    if (snippets.isEmpty() || snippets.first().getElementsByClass(CODE_CLASS).text().isEmpty()) {
      return null;
    }
    String title = Jsoup.clean(titles.first().text(), Whitelist.relaxed());
    String description = Jsoup.clean(descriptions.first().text(), Whitelist.relaxed());
    String code = snippets.first().getElementsByClass(CODE_CLASS).text();
    if (containsEscape(query.toLowerCase())
        || containsEscape(geeksLink)
        || containsEscape(title.toLowerCase())
        || containsEscape(description.toLowerCase())) {
      title = StringEscapeUtils.escapeHtml4(title);
      description = StringEscapeUtils.escapeHtml4(description);
      code = StringEscapeUtils.escapeHtml4(code);
    } else {
      code = Jsoup.clean(code, Whitelist.relaxed());
    }
    long votes = getVotes(geeksLink);
    return new Card(title, code, geeksLink, description, votes, SOURCE_NAME, ICON_LINK);
  }

  private boolean containsEscape(String possibleHtml) {
    for (String filterWord : escapeFilters) {
      if (possibleHtml.contains(filterWord)) {
        return true;
      }
    }
    return false;
  }
}
