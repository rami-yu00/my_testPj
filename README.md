# my_testPj

Tomcat 9(javax) + JSP + jQuery 기반의 커스텀 태그 데모 프로젝트입니다. `kr.co.mytestpj` 패키지의 cbui 태그와 `static/widget.js` 스크립트를 이용해 data-field/data-option/data-io 규칙을 테스트할 수 있습니다.

## 실행 방법
1. **패키징**
   ```bash
   mvn clean package
   ```
2. **배포**
   - 생성된 `target/my-testpj.war`를 Tomcat 9의 `webapps` 폴더에 배포합니다.
   - Tomcat 9은 `javax.*` API 기준입니다(jakarta 금지). JDK 11을 사용해 주세요.
3. **접속 URL**
   - 기본 컨텍스트로 배포했다면: `http://localhost:8080/my-testpj/`
   - 다른 컨텍스트라면: `http://localhost:8080/<context>/`

## 제공 태그
- `<cbui:input />`
- `<cbui:combo />`
- `<cbui:checkbox />`
- `<cbui:radio />`
- `<cbui:realgrid />` (HTML table 대체)
- `<cbui:button />`
- `<cbui:textarea />`
- `<cbui:calendar />`

모든 태그는 `data-field`, `data-option`, `data-io`를 공통 지원하며 combo/checkbox/radio/calendar는 hidden input을 포함하고, combo/radio는 `data-codebase`로 코드베이스를 지정합니다.

## data-io 규칙 (widget.js, jQuery 기반)
- `I`: collect 포함, apply 제외
- `O`: apply 포함, collect 제외
- `IO`: 둘 다
- API
  - `cbui.collect({ io: ["I","IO"] })` → payload object
  - `cbui.apply(dataObj, { io: ["O","IO"] })`

## 데모(test.jsp)
- 위치: `/WEB-INF/jsp/test.jsp` (루트 `/` 접근 시 forward)
- 검색 영역: input/combo/calendar/checkbox/radio (data-io=I/IO)
- 목록 영역: `<cbui:realgrid>` 테이블에 조회 결과 렌더 + 행 클릭 시 상세 apply
- 상세 영역: input/combo/calendar/textarea (data-io=O/IO)
- 버튼: cbui:button으로 조회/초기화/저장
- 코드베이스 mock: `PAY_TYPE`, `YN` (widget.js 내 정의)

## 기타
- JS 동작은 **반드시 jQuery 기반**으로 작성되었습니다.
- 필요 시 `conf/server.xml`의 커넥터에 `URIEncoding="UTF-8"` 설정을 권장합니다.
