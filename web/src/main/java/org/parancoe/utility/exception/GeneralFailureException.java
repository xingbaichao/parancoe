// Copyright 2006 The Parancoe Team
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
// http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
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
