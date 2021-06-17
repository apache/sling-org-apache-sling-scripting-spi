/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 ~ Licensed to the Apache Software Foundation (ASF) under one
 ~ or more contributor license agreements.  See the NOTICE file
 ~ distributed with this work for additional information
 ~ regarding copyright ownership.  The ASF licenses this file
 ~ to you under the Apache License, Version 2.0 (the
 ~ "License"); you may not use this file except in compliance
 ~ with the License.  You may obtain a copy of the License at
 ~
 ~   http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~ Unless required by applicable law or agreed to in writing,
 ~ software distributed under the License is distributed on an
 ~ "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 ~ KIND, either express or implied.  See the License for the
 ~ specific language governing permissions and limitations
 ~ under the License.
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
package org.apache.sling.api.resource.type;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.osgi.framework.Version;

public class ResourceTypeTest {

    private void assertResourceType(String resourceTypeString,
            String expectedType, String expectedResourceLabel,
            Version expectedVersion) {
        ResourceType t1 = ResourceType.parseResourceType(resourceTypeString);
        assertEquals(expectedType, t1.getType());
        assertEquals(expectedResourceLabel, t1.getResourceLabel());
        assertEquals(expectedVersion, t1.getVersion());
    }

    @Test
    public void testSlashNoVersion() {
        assertResourceType("a/b/c", "a/b/c", "c", null);
    }

    @Test
    public void testSlashVersion() {
        assertResourceType("a/b/c/1.0.0", "a/b/c", "c", new Version("1.0.0"));
    }

    @Test
    public void testOneSegmentNoVersion() {
        assertResourceType("a", "a", "a", null);
    }

    @Test
    public void testOneSegmentVersion() {
        assertResourceType("a/1.2.3", "a", "a", new Version("1.2.3"));
    }

    @Test
    public void testDotNoVersion() {
        assertResourceType("a.b.c", "a.b.c", "c", null);
    }

    @Test
    public void testDotVersion() {
        assertResourceType("a.b.c/42.0.0", "a.b.c", "c", new Version("42.0.0"));
    }

    /**
     * Test when the version segment is not parsable as a version
     */
    @Test
    public void testDotInvalidVersion() {
        assertResourceType("a.b.c/1.2.#@$abc", "a.b.c/1.2.#@$abc", "1.2.#@$abc", null);
    }

    /**
     * Test when the version segment is not parsable as a version
     */
    @Test
    public void testSlashInvalidVersion() {
        assertResourceType("a/b/c/1.0.#@$abc", "a/b/c/1.0.#@$abc", "1.0.#@$abc", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyString() {
        ResourceType.parseResourceType(StringUtils.EMPTY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNull() {
        ResourceType.parseResourceType(null); // NOSONAR
    }

}
