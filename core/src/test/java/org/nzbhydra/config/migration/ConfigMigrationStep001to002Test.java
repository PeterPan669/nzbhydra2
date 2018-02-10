/*
 *  (C) Copyright 2017 TheOtherP (theotherp@gmx.de)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.nzbhydra.config.migration;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigMigrationStep001to002Test {

    private ConfigMigrationStep001to002 testee = new ConfigMigrationStep001to002();

    @Test
    public void shouldMigrate() throws IOException {
        HashMap<String, Object> input = new HashMap<>();
        ArrayList<Object> indexers = new ArrayList<>();
        indexers.add(new HashMap<String, Object>(ImmutableMap.of("enabled", true)));
        indexers.add(new HashMap<String, Object>(ImmutableMap.of("enabled", false)));
        input.put("indexers", indexers);
        Map<String, Object> output = testee.migrate(input);

        indexers = (ArrayList<Object>) output.get("indexers");
        assertThat(indexers.size()).isEqualTo(2);
        HashMap<String, Object> indexer1 = (HashMap<String, Object>) indexers.get(0);
        assertThat(indexer1.get("state")).isEqualTo("ENABLED");
        HashMap<String, Object> indexer2 = (HashMap<String, Object>) indexers.get(1);
        assertThat(indexer2.get("state")).isEqualTo("DISABLED_USER");
    }

    @Test
    public void shouldMigrateFrom1() {
        assertThat(testee.forVersion()).isEqualTo(1);
    }
}