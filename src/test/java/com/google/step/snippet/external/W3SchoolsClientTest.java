package com.google.step.snippet.external;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.google.step.snippet.data.Card;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class W3SchoolsClientTest {

  private final W3SchoolsClient client = new W3SchoolsClient("CSE_ID");

  @Test
  public void htmlCodeCard() {
    Card actual = client.search("https://www.w3schools.com/tags/tag_img.asp", "html img");
    Card expected =
        new Card(
            "HTML &lt;img&gt; Tag",
            "&lt;img src=&quot;img_girl.jpg&quot; alt=&quot;Girl in a jacket&quot;"
                + " width=&quot;500&quot; height=&quot;600&quot;&gt;",
            "https://www.w3schools.com/tags/tag_img.asp",
            "How to insert an image:",
            "W3Schools",
            "https://w3schools.com/favicon.ico");
    assertEquals(expected, actual);
  }

  @Test
  public void jsonCodeCard() {
    Card actual = client.search("https://www.w3schools.com/js/js_json_intro.asp", "json");
    Card expected =
        new Card(
            "JSON - Introduction",
            "var myObj = {name: \"John\", age: 31, city: \"New York\"}; var myJSON ="
                + " JSON.stringify(myObj); window.location = \"demo_json.php?x=\" + myJSON;",
            "https://www.w3schools.com/js/js_json_intro.asp",
            "JSON: JavaScript Object Notation.",
            "W3Schools",
            "https://w3schools.com/favicon.ico");
    assertEquals(expected, actual);
  }

  @Test
  public void cssEscaping() {
    Card actual = client.search("https://www.w3schools.com/html/html_css.asp", "css style");
    Card expected =
        new Card(
            "HTML Styles - CSS",
            "&lt;h1 style=&quot;color:blue;&quot;&gt;A Blue Heading&lt;/h1&gt; &lt;p"
                + " style=&quot;color:red;&quot;&gt;A red paragraph.&lt;/p&gt;",
            "https://www.w3schools.com/html/html_css.asp",
            "CSS stands for Cascading Style Sheets.",
            "W3Schools",
            "https://w3schools.com/favicon.ico");
    assertEquals(expected, actual);
  }

  @Test
  public void divEscaping() {
    Card actual = client.search("https://www.w3schools.com/css/css3_flexbox.asp", "css flexbox");
    Card expected =
        new Card(
            "CSS Flexbox",
            "&lt;div class=&quot;flex-container&quot;&gt; &lt;div&gt;1&lt;/div&gt;"
                + " &lt;div&gt;2&lt;/div&gt; &lt;div&gt;3&lt;/div&gt; &lt;/div&gt;",
            "https://www.w3schools.com/css/css3_flexbox.asp",
            "A flex container with three flex items:",
            "W3Schools",
            "https://w3schools.com/favicon.ico");
    assertEquals(expected, actual);
  }

  @Test
  public void partiallyFilledCard() {
    Card actual = client.search("https://www.w3schools.com/html/html_quiz.asp", "html quiz");
    assertNull(actual);
  }

  @Test
  public void nonexistentLink() {
    Card actual = client.search("https://www.w3schools.com/css/html.asp", null);
    assertNull(actual);
  }

  @Test
  public void blankPageLink() {
    Card actual = client.search("https://www.w3schools.com/", "w3schools");
    assertNull(actual);
  }
}
