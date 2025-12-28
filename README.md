# Someverse

## 커밋 컨벤션

### 커밋 메시지 형식

```
<type>: <description>
```

레이어 관련 작업인 경우:
```
<type>: <layer> - <description>
```

### Type

| Type | 설명 |
|------|------|
| `feat` | 새로운 기능 추가 |
| `refactor` | 코드 리팩토링 (기능 변경 없음) |
| `add` | 리소스, 컴포넌트, 에셋 추가 |
| `remove` | 파일, 코드 삭제 |
| `fix` | 버그 수정 |
| `docs` | 문서 작성 및 수정 |
| `chore` | 빌드 설정, 패키지 관리 등 유지보수 |

### Layer (선택)

레이어 관련 작업 시 명시:

| Layer | 설명 |
|-------|------|
| `domain` | Domain 레이어 (UseCase, Entity, Repository Interface) |
| `data` | Data 레이어 (Repository Impl, DataSource, DTO, Mapper) |
| `presentation` | Presentation 레이어 (UI, ViewModel, Navigation) |

### 예시

```
feat: domain - feed usecase
feat: data - chat repository impl
feat: presentation - matching screen
refactor: 텍스트 Strings 사용
add: color - neutral20, neutral80, gradation
remove: firebender
docs: PR Template
chore: app logo
```
