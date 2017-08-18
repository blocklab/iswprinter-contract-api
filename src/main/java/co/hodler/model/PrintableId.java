package co.hodler.model;

public class PrintableId {
  private String content;

  public PrintableId(String content) {
    this.content = content;
  }

  public String asString() {
    return this.content;
  }
}
