package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 비즈니스 로직 ~ service
@Service
@Transactional(readOnly = true)
//@AllArgsConstructor
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 생성자 injection ~ 생성자가 하나인 경우엔 @Autowired 노필요 스프링이 알아서 인젝션 해줌
    // Lombok의 @AllArgsConstructor 해주면 이 코드 그대로 적어줌
    // Lombok의 @RequiredArgsConstructor 해주면 final을 가지고 있는 애들만 가지고 생성자 만들어줌
//    @Autowired
//    public MemberService(MemberRepository memberRepository){
//        this.memberRepository = memberRepository;
//    }

    /*
     * 회원가입
     */
    @Transactional(readOnly = false)    // 위에 기본이 readOnly=True 이므로 , join은 insert 해야하므로 readOnly=False로 세팅
    public Long join(Member member){
        validateDuplicateMember(member);    // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }


    private void validateDuplicateMember(Member member){
        // Exception ~>
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }


    // 회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    
    // 회원 단건 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }


    // 회원 수정 ~ 이름수정
    @Transactional
    public void update(Long id, String name){
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }


}
