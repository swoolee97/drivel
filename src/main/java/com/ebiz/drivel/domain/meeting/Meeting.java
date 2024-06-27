package com.ebiz.drivel.domain.meeting;

import com.ebiz.drivel.domain.course.entity.Course;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.util.Date;

@Entity
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "date")
    private Date date;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "meeting_point")
    private String meetingPoint;

    @OneToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @Column(name = "gender")
    private String gender;

    @Column(name = "start_age")
    private Integer startAge;

    @Column(name = "end_age")
    private Integer endAge;

}
