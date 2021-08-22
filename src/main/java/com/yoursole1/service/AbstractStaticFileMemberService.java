package com.yoursole1.service;

import com.yoursole1.repository.StaticFileRepository;
import net.dv8tion.jda.api.entities.Member;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractStaticFileMemberService extends AbstractStaticFileService<Member, Integer> {

    public static final Function<Member, String> memberToTag = member -> member.getUser().getAsTag();

    AbstractStaticFileMemberService(StaticFileRepository repository) {
        super(repository);
    }

    @Override
    public void add(Member member) {
        getRepository().save(memberToTag.apply(member));
    }

    @Override
    public void add(List<Member> members) {
        getRepository()
                .save(members
                        .stream()
                        .map(memberToTag)
                        .collect(Collectors.toList()
                        )
                );
    }

    @Override
    public void remove(Member member) {
        getRepository().delete(memberToTag.apply(member));
    }

    @Override
    public Member find(Integer id) {
        return JDAService.tagToMember(getRepository().get(id));
    }

    @Override
    public List<Member> findAll() {
        return getRepository()
                .getAll()
                .stream()
                .map(JDAService::tagToMember)
                .collect(Collectors.toList());
    }
}
