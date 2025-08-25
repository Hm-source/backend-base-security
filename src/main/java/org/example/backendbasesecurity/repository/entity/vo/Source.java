package org.example.backendbasesecurity.repository.entity.vo;

// 특정 사용자가 어떤 방식으로 시스템에 들어왔는지 기록하는 역할 - 사용자의 로그인 경로에 따라 다른 인증 로직을 적용
public enum Source {
    HOMEPAGE,
    KAKAO,
    NAVER;
}

