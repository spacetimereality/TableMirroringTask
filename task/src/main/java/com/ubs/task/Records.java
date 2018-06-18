package com.ubs.task;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ubs.task.domain.TaskDefinition;
import com.ubs.task.domain.TaskDefinitionMirror;
import com.ubs.task.domain.TaskDefinitionMirrorRepository;
import com.ubs.task.domain.TaskDefinitionRepository;

@Component
public class Records {

	@Autowired
	TaskDefinitionRepository taskDefinitionRepository;

	@Autowired
	TaskDefinitionMirrorRepository taskDefinitionMirrorRepository;

	private List<TaskDefinition> previousMainRecords;
	private List<TaskDefinitionMirror> previousMirrorRecords;

	private List<TaskDefinition> currentMainRecords;
	private List<TaskDefinitionMirror> currentMirrorRecords;

	private boolean isLoaded;

	public void loadPreviousRecords() {
		previousMainRecords = taskDefinitionRepository.findAll();
		previousMirrorRecords = taskDefinitionMirrorRepository.findAll();
		isLoaded = true;
	}

	public void loadCurrentRecords() {
		currentMainRecords = taskDefinitionRepository.findAll();
		currentMirrorRecords = taskDefinitionMirrorRepository.findAll();
	}

	public void updatePreviousRecords(List<TaskDefinition> mainRecords, List<TaskDefinitionMirror> mirrorRecords) {
		this.previousMainRecords = mainRecords;
		this.previousMirrorRecords = mirrorRecords;
		isLoaded = true;
	}

	public boolean isLoaded() {
		return isLoaded;
	}

	public List<TaskDefinition> getPreviousRecords() {
		return previousMainRecords == null ? Collections.emptyList() : previousMainRecords;
	}

	public List<TaskDefinitionMirror> getPreviousMirrorRecords() {
		return previousMirrorRecords == null ? Collections.emptyList() : previousMirrorRecords;
	}

	public List<TaskDefinition> getCurrentRecords() {
		return currentMainRecords == null ? Collections.emptyList() : currentMainRecords;
	}

	public List<TaskDefinitionMirror> getCurrentMirrorRecords() {
		return currentMirrorRecords == null ? Collections.emptyList() : currentMirrorRecords;
	}

}
