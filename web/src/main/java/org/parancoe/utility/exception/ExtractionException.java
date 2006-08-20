package org.parancoe.utility.exception;

/**
 * <p>ExtractionException</p>
 * <p>ExtractionException estende RuntimeException</p>
 * @author Andrea Stefani AKA Hollywood
 * @version 1.0
 */
public class ExtractionException extends RuntimeException {

  public ExtractionException() {
  }

  private Throwable t;

  public ExtractionException(String s) {
      super(s);
  }

  public ExtractionException(String s, Throwable t) {
      super(s);
      this.t = t;
  }

  public String getThrowable() {
      return ("Received throwable with Message: "+ t.getMessage());
  }

}
