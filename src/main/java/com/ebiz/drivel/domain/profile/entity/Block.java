package com.ebiz.drivel.domain.profile.entity;

import com.ebiz.drivel.domain.member.entity.Member;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blocked_user_id", nullable = false)
    private Member blockedUser;

    public Block() {
    }

    public Block(Member user, Member blockedUser) {
        this.user = user;
        this.blockedUser = blockedUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getUser() {
        return user;
    }

    public void setUser(Member user) {
        this.user = user;
    }

    public Member getBlockedUser() {
        return blockedUser;
    }

    public void setBlockedUser(Member blockedUser) {
        this.blockedUser = blockedUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return Objects.equals(user, block.user) &&
                Objects.equals(blockedUser, block.blockedUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, blockedUser);
    }

    @Override
    public String toString() {
        return "Block{" +
                "id=" + id +
                ", user=" + user +
                ", blockedUser=" + blockedUser +
                '}';
    }
}