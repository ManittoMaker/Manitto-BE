# Manitto-BE

## ✨ Husky & Commitlint

Git 훅을 이용해 커밋 메세지 형식을 강제하여 일관성을 유지합니다.

### 설치

```
npm install
```

### 커밋 메세지 규칙

- `Type(필수)`: Commit의 종류. commit을 할 때, type에 상응하는 이모지가 자동으로 붙습니다.
- `Scope(선택)`: Commit의 범위. 기능, 함수, 페이지, API 등 자유롭게 선택할 수 있습니다.
- `Subject(필수)`: Commit의 제목. 되도록 간결하게 작성하고, 명사형 어미로 끝나도록 합니다.
- `Body(선택)`: Commit의 내용. 어떤 이유로, 어떻게 변경했는지 작성합니다.
- `Footer(선택)`: Commit의 추가 정보. 이슈 트래킹이나 참고 사항을 기록합니다.

### 헤더 예시

```
<type>(optional scope): <subject>

✨ Feat(login/SignUp): 회원가입 기능 추가
🐛 Fix(login): 로그인 기능 수정
⭐️ Style: 코드 포맷 변경
♻️ Refactor(SignUp): 회원 가입 로직 개선
📁 File: 이미지 파일 추가
✅ Test: 테스트 코드 추가
📝 Docs: README.md 업데이트
🔥 Remove: 사용하지 않는 파일 제거
💚 Ci: 자동 배포 스크립트 변드
🔖 Release: 릴리즈 버전 1.0.3
🔧 Chore: 설정파일 수정
```

### 메세지 구조

```
<type>(optional scope): <subject>

[optional body]

[optional footer(s)]
```