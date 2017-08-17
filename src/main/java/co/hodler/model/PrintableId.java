package co.hodler.model;

public class PrintableId {
  private String keccakHash;

  public PrintableId(String keccakHash) {
    this.keccakHash = keccakHash;
  }

  public byte[] asByteArray() {
    return hexStringToByteArray(keccakHash.replaceFirst("0x", ""));
  }

  private byte[] hexStringToByteArray(String s) {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
        + Character.digit(s.charAt(i+1), 16));
    }
    return data;
  }
}
