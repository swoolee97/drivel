package com.ebiz.drivel.domain.course.entity;

import com.ebiz.drivel.domain.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "courseLike")
public class CourseLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "courseId", referencedColumnName = "id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "memberId", referencedColumnName = "id")
    private Member member;

}
