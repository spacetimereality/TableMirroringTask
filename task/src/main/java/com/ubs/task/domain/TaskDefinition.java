package com.ubs.task.domain;

import java.util.function.Function;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "task_definition")
public class TaskDefinition {

	public static final Function<TaskDefinition, TaskDefinitionMirror> DEFINITION_MIRROR_MAPPER = element -> {
		return new TaskDefinitionMirror(element.getId(), element.getName(), element.getDescription());
	};

	public TaskDefinition() {
	}

	public TaskDefinition(Long id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	@Id
	protected Long id;

	@NotBlank
	protected String name;

	@NotBlank
	protected String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object obj) {
		TaskDefinition taskDefinition = (TaskDefinition) obj;
		return this.id.equals(taskDefinition.getId()) && this.name.equals(taskDefinition.getName())
				&& this.description.equals(taskDefinition.getDescription());
	}

	@Override
	public String toString() {
		return new StringBuilder().append("id : ").append(id).append(" name :").append(name).append(" description :")
				.append(description).toString();
	}

}
