package org.withcheesedev.async_job.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "job")
@Getter
@Setter
public class Job {
    @Id()
    @Column(name = "job_id")
    private int jobId;

    @Column(name = "status")
    private String status;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
}
