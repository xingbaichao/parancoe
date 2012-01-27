/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Yaml.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.parancoe.yaml;

import java.util.Iterator;

/**
 * YamlStream is nothing but an Iterator that is also an Iterable. It allows for
 * both of the below types of stream access:
 * 
 * <pre>
 * for (Object obj : Yaml.loadStream(yamlText)) {
 *     //do something with obj 
 * }
 * </pre>
 * 
 * or
 * 
 * <pre>
 * Iterator iterator = Yaml.loadStream(yamlText);
 * while (iterator.hasNext()) {
 *     Object obj = iterator.next();
 *     //do something with obj
 * }
 * </pre>
 * 
 * @param <T>
 *            the type which the iterator yields
 */
public interface YamlStream<T> extends Iterable<T>, Iterator<T> {
}
