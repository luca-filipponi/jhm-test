/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package org.openjdk.jmh.samples

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State

import java.util.concurrent.TimeUnit

@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
class JMHScalaSample_17_SyncIterations {

  /*
   * This is the another thing that is enabled in JMH by default.
   *
   * Suppose we have this simple benchmark.
   */

  private var src: Double = _

  @Benchmark
  def test: Double = {
    var s = src
    var i = 0
    while (i < 1000) {
      s = Math.sin(s)
      i += 1
    }
    s
  }

  /*
   * It turns out if you run the benchmark with multiple threads,
   * the way you start and stop the worker threads seriously affects
   * performance.
   *
   * The natural way would be to park all the threads on some sort
   * of barrier, and the let them go "at once". However, that does
   * not work: there are no guarantees the worker threads will start
   * at the same time, meaning other worker threads are working
   * in better conditions, skewing the result.
   *
   * The better solution would be to introduce bogus iterations,
   * ramp up the threads executing the iterations, and then atomically
   * shift the system to measuring stuff. The same thing can be done
   * during the rampdown. This sounds complicated, but JMH already
   * handles that for you.
   */

}
