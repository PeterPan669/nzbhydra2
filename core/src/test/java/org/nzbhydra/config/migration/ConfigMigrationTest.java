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
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.nzbhydra.config.MainConfig;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ConfigMigrationTest {

    @Mock
    private ConfigMigrationStep configMigrationStepMock;

    @InjectMocks
    private ConfigMigration testee = new ConfigMigration();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldMigrate() {
        HashMap<String, Object> input = new HashMap<>(ImmutableMap.of("main", new HashMap<>(ImmutableMap.of("configVersion", 1))));
        HashMap<String, Object> afterMigration = new HashMap<>(ImmutableMap.of("main", new HashMap<>(ImmutableMap.of("configVersion", 2))));

        when(configMigrationStepMock.forVersion()).thenReturn(1);
        when(configMigrationStepMock.migrate(any())).thenReturn(afterMigration);
        testee.steps = Arrays.asList(configMigrationStepMock);
        testee.expectedConfigVersion = 2;

        testee.migrate(input);

        verify(configMigrationStepMock).migrate(input);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionIfWrongConfigVersionAfterMigration() {
        HashMap<String, Object> input = new HashMap<>(ImmutableMap.of("main", new HashMap<>(ImmutableMap.of("configVersion", 1))));

        when(configMigrationStepMock.forVersion()).thenReturn(1);
        when(configMigrationStepMock.migrate(any())).thenReturn(input);
        testee.steps = Arrays.asList(configMigrationStepMock);
        testee.expectedConfigVersion = 2;

        testee.migrate(input);
    }

    @Test
    public void shouldFindMigrationStepsForAllPossibleConfigVersions() {
        Integer currentConfigVersion = new MainConfig().getConfigVersion();
        List<ConfigMigrationStep> steps = ConfigMigration.getMigrationSteps();
        for (int i = 1; i < currentConfigVersion; i++) {
            int finalI = i;
            assertThat(steps.stream().anyMatch(x -> x.forVersion() == finalI)).isTrue();
        }

    }
}