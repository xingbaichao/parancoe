package org.parancoe.utility.exception;

/**
 * This exception is the base class for all the web runtime exceptions.
 */
public class GeneralFailureException
    extends RuntimeException implements java.io.Serializable {

  private Throwable t;

  public GeneralFailureException(String s) {
    super(s);
  }

  public GeneralFailureException(String s, Throwable t) {
    super(s);
    this.t = t;
  }

  public String getThrowable() {
    return ("Received throwable with Message: " + t.getMessage());
  }
}
