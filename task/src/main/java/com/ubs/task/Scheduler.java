package com.ubs.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

	@Autowired
	private Records records;

	@Autowired
	DefinitionMigrator migrator;

	@Scheduled(fixedRate = 10000)
	public void execute() {

		if (!records.isLoaded()) {
			records.loadPreviousRecords();
		} else {
			records.loadCurrentRecords();
			migrator.migrateToMirror(records.getPreviousRecords(), records.getCurrentRecords());
			migrator.migrateFromMirror(records.getPreviousMirrorRecords(), records.getCurrentMirrorRecords());
			records.updatePreviousRecords(records.getCurrentRecords(), records.getCurrentMirrorRecords());
		}

	}

}
