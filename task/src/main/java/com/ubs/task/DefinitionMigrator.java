package com.ubs.task;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ubs.task.domain.TaskDefinition;
import com.ubs.task.domain.TaskDefinitionMirror;
import com.ubs.task.domain.TaskDefinitionMirrorRepository;
import com.ubs.task.domain.TaskDefinitionRepository;

@Component
public class DefinitionMigrator {

	@Autowired
	private TaskDefinitionRepository taskDefinitionRepository;

	@Autowired
	private TaskDefinitionMirrorRepository taskDefinitionMirrorRepository;

	public void migrateToMirror(List<TaskDefinition> prevDefinitions, List<TaskDefinition> currentDefinitions) {
		List<TaskDefinition> difference = currentDefinitions.stream().filter(p -> !prevDefinitions.contains(p))
				.collect(Collectors.toList());
		if (difference.size() > 0) {
			List<TaskDefinitionMirror> mirror = difference.stream().map(TaskDefinition.DEFINITION_MIRROR_MAPPER)
					.collect(Collectors.toList());
			taskDefinitionMirrorRepository.saveAll(mirror);
		}
	}

	public void migrateFromMirror(List<TaskDefinitionMirror> prevDefinitions,
			List<TaskDefinitionMirror> currentDefinitions) {
		List<TaskDefinitionMirror> difference = currentDefinitions.stream().filter(p -> !prevDefinitions.contains(p))
				.collect(Collectors.toList());
		if (difference.size() > 0) {
			taskDefinitionRepository.saveAll(
					difference.stream().map(TaskDefinitionMirror.DEFINITION_MAPPER).collect(Collectors.toList()));
		}
	}

}
