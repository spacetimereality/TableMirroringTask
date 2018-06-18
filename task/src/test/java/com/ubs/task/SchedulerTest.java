package com.ubs.task;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;


import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class SchedulerTest {

	@InjectMocks
	private final Scheduler scheduler;

	@Mock
	private Records records;

	@Mock
	DefinitionMigrator migrator;

	public SchedulerTest() {
		scheduler = new Scheduler();
	}

	@Test
	public void testExecuteWithoutInitialRecord() {
		when(records.isLoaded()).thenReturn(false);
		scheduler.execute();
		verify(records, times(1)).loadPreviousRecords();
		verify(records, never()).loadCurrentRecords();
		verify(records, never()).updatePreviousRecords(Mockito.anyList(), Mockito.anyList());
		verify(migrator, never()).migrateFromMirror(Mockito.anyList(), Mockito.anyList());
		verify(migrator, never()).migrateToMirror(Mockito.anyList(), Mockito.anyList());
	}

	@Test
	public void testExecuteWithInitialRecord() {
		when(records.isLoaded()).thenReturn(true);
		scheduler.execute();
		verify(records, never()).loadPreviousRecords();
		verify(records, times(1)).loadCurrentRecords();
		verify(records, times(1)).updatePreviousRecords(Mockito.anyList(), Mockito.anyList());
		verify(migrator, times(1)).migrateFromMirror(Mockito.anyList(), Mockito.anyList());
		verify(migrator, times(1)).migrateToMirror(Mockito.anyList(), Mockito.anyList());
	}

}
