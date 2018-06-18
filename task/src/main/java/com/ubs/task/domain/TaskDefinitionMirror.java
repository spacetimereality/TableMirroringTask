package com.ubs.task.domain;

import java.util.function.Function;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "task_definition_mirror")
public class TaskDefinitionMirror {

	public static final Function<TaskDefinitionMirror, TaskDefinition> DEFINITION_MAPPER = element -> {
		return new TaskDefinition(element.getId(), element.getName(), element.getDescription());
	};

	public TaskDefinitionMirror() {
	}

	public TaskDefinitionMirror(Long id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
		TaskDefinitionMirror mirror = (TaskDefinitionMirror) obj;
		return this.id.equals(mirror.getId()) && this.name.equals(mirror.getName())
				&& this.description.equals(mirror.getDescription());
	}

	@Override
	public String toString() {
		return new StringBuilder().append("id : ").append(id).append(" name :").append(name).append(" description :")
				.append(description).toString();
	}

}
