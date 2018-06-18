package com.ubs.task;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.ubs.task.domain.TaskDefinition;
import com.ubs.task.domain.TaskDefinitionMirror;
import com.ubs.task.domain.TaskDefinitionMirrorRepository;
import com.ubs.task.domain.TaskDefinitionRepository;

import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class DefinitionMigratorTest {

	@InjectMocks
	private DefinitionMigrator migrator;

	@Mock
	private TaskDefinitionRepository repository;

	@Mock
	private TaskDefinitionMirrorRepository mirrorRepository;

	public DefinitionMigratorTest() {
		migrator = new DefinitionMigrator();
	}

	@Test
	public void testMigrateToMirrorWhenChangeDoesNotExist() {
		List<TaskDefinition> prevDefinitions = createTaskDefinitions();
		List<TaskDefinition> currentDefinitions = createTaskDefinitions();
		migrator.migrateToMirror(prevDefinitions, currentDefinitions);
		verify(mirrorRepository, never()).saveAll(Mockito.anyList());
	}

	@Test
	public void testMigrateToMirrorWhenChangeExists() {
		List<TaskDefinition> prevDefinitions = createTaskDefinitions();
		List<TaskDefinition> currentDefinitions = createTaskDefinitions();
        currentDefinitions.get(1).setName("name2");
        currentDefinitions.get(1).setDescription("description22");
        currentDefinitions.get(2).setName("name3");
        currentDefinitions.get(2).setDescription("description33");
		List<TaskDefinitionMirror> differenceAsTaskMirror = Lists.newArrayList(
				new TaskDefinitionMirror(2l, "name2", "description22"),
				new TaskDefinitionMirror(3l, "name3", "description33"));
		@SuppressWarnings("unchecked")
		ArgumentCaptor<List<TaskDefinitionMirror>> captor = ArgumentCaptor.forClass(List.class);
		migrator.migrateToMirror(prevDefinitions, currentDefinitions);
		verify(mirrorRepository, times(1)).saveAll(captor.capture());
		assertEquals(differenceAsTaskMirror, captor.getValue());
	}
	
	@Test
	public void testMigrateFromMirrorWhenChangeDoesNotExist() {
		List<TaskDefinitionMirror> prevDefinitions = createTaskDefinitionMirrors();
		List<TaskDefinitionMirror> currentDefinitions = createTaskDefinitionMirrors();
		migrator.migrateFromMirror(prevDefinitions, currentDefinitions);
		verify(repository, never()).saveAll(Mockito.anyList());
	}

	@Test
	public void testMigrateFromMirrorWhenChangeExists() {
		List<TaskDefinitionMirror> prevDefinitions = createTaskDefinitionMirrors();
		List<TaskDefinitionMirror> currentDefinitions = createTaskDefinitionMirrors();
        currentDefinitions.get(2).setName("name3");
        currentDefinitions.get(2).setDescription("description33");
		List<TaskDefinition> differenceAsTaskDefinition = Lists.newArrayList(
				new TaskDefinition(3l, "name3", "description33"));
		@SuppressWarnings("unchecked")
		ArgumentCaptor<List<TaskDefinition>> captor = ArgumentCaptor.forClass(List.class);
		migrator.migrateFromMirror(prevDefinitions, currentDefinitions);
		verify(repository, times(1)).saveAll(captor.capture());
		assertEquals(differenceAsTaskDefinition, captor.getValue());
	}

	private List<TaskDefinition> createTaskDefinitions() {
		return Lists.newArrayList(new TaskDefinition(1l, "name1", "description1"),
				new TaskDefinition(2l, "name2", "description2"), new TaskDefinition(3l, "name3", "description3"));
	}
	
	private List<TaskDefinitionMirror> createTaskDefinitionMirrors() {
		return Lists.newArrayList(new TaskDefinitionMirror(1l, "name1", "description1"),
				new TaskDefinitionMirror(2l, "name2", "description2"), new TaskDefinitionMirror(3l, "name3", "description3"));
	}


}
