# my_testPj

Tomcat 9(javax) 기반 JSP + 커스텀 태그 샘플 프로젝트입니다. `kr.co.mytestpj` 패키지로 작성된 Widget/Combo 태그와 `widget.js` 헬퍼를 이용해 data-field/data-option/data-io/data-codebase 규칙을 테스트할 수 있는 실무형 화면을 제공합니다.

## 실행 방법
1. **패키징**
   ```bash
   mvn clean package
   ```
2. **배포**
   - 생성된 `target/my-testpj.war`를 Tomcat 9의 `webapps` 폴더에 배포합니다.
   - 필요 시 컨텍스트 이름을 변경하려면 `my-testpj.war` 파일명을 원하는 컨텍스트명으로 수정합니다.
3. **접속 URL**
   - 기본 컨텍스트로 배포했다면: `http://localhost:8080/my-testpj/`
   - 다른 컨텍스트라면: `http://localhost:8080/<context>/`

## Tomcat 9 팁
- JAVA_HOME이 JDK 11을 가리키는지 확인하세요.
- `conf/server.xml`에서 커넥터 인코딩을 `URIEncoding="UTF-8"`로 설정하면 한글 파라미터 처리가 안정적입니다.
- 필요 시 `conf/context.xml`의 `<WatchedResource>WEB-INF/web.xml</WatchedResource>`가 기본 포함되어 있으므로 배포 후 `web.xml` 변경을 감지합니다.

## 샘플 화면
- `/` 접근 시 `WEB-INF/jsp/test.jsp`로 forward되어 검색/목록/상세/저장 흐름을 확인할 수 있습니다.
- 조회: `widget.collect({io:['I','IO']})`로 조건을 모아 mock 데이터를 테이블에 렌더링합니다.
- 행 클릭: `widget.apply(row,{io:['O','IO']})`로 상세 영역이 채워집니다.
- 저장: `widget.collect({io:['I','IO']})` 결과 payload를 콘솔로 출력합니다.
