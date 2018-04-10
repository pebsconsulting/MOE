/*
 * Copyright (c) 2011 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.devtools.moe.client.codebase.expressions;

import junit.framework.TestCase;

public class TermTest extends TestCase {

  public void testToString() throws Exception {
    Term t;

    t = Term.create("internal");
    assertEquals("internal", t.toString());

    t = Term.create("internal").withOption("foo", "bar");
    assertEquals("internal(foo=bar)", t.toString());

    t = Term.create("internal").withOption("foo", "bar").withOption("baz", "quux");
    // We sort arguments.
    assertEquals("internal(baz=quux,foo=bar)", t.toString());

    t = Term.create("inte rnal").withOption("foo", "bar").withOption("baz", "qu ux");
    assertEquals("\"inte rnal\"(baz=\"qu ux\",foo=bar)", t.toString());
  }
}
