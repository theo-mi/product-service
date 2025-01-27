# 무신사 - 최저가 코디 조회 서비스

## 1. 개요

8개 카테고리에 대한 상품을 기반으로 최저가 코디를 조회하고, 최저가의 상품도 조회합니다. 또한 브랜드와 상품의 CRUD를 제공하는 서비스입니다.

### 1.1. 사용 기술

- java 21
- Spring Boot 3.4.1
- Spring Data JPA
- H2 Database
- Spring Cache
- JUnit5
- FixtureMonkey
- Swagger
- Lombok
- Gradle

### 1.2. 코드 빌드 및 실행 방법

```bash
./gradlew clean build
./gradlew bootRun
```

#### 1.2.1. Swagger

`http://localhost:8080/swagger-ui/index.html`
- Swagger를 통해 API 명세 확인 및 호출이 가능합니다.

#### 1.2.2. H2 Database

`http://localhost:8080/h2-console`
- H2 Database를 통해 데이터 확인이 가능합니다.
  - jdbc url: `jdbc:h2:~/test`
  - username: `sa`
  - password: 미입력

## 2. 구현 범위

과제 요구사항에 따른 4가지 기능을 중심으로 구현하였으며, 각 기능은 다음과 같습니다.
또한 Unit Test 및 Integration Test를 작성하였습니다.

1. **카테고리 별 최저가격 브랜드/상품 및 총액 조회**
   - `GET /outfits/cheapest`
   - e.g) 스니커즈(A브랜드, 9,000원), 양말(I브랜드, 1,700원) 등과 그 합계. 
2. **단일 브랜드로 모든 카테고리 상품을 구매 시 최저가격 브랜드와 총액 조회** 
   - `GET /outfits/cheapest/brand`
   - e.g) D 브랜드 기준, 상의(10,100원), 아우터(5,100원), ... 총액 36,100원 
3. **특정 카테고리 최저가/최고가 브랜드 조회**
   - `GET /prices/min-max?categoryName=상의`
   - e.g) 상의 - 최저: C(10,000원), 최고: I(11,400원)
4. **브랜드 및 상품 등록 / 수정 / 삭제** 
   - 브랜드: `POST /brands`, `PUT /brands/{id}`, `DELETE /brands/{id}`
   - 상품: `POST /products`, `PUT /products/{id}`, `DELETE /products/{id}`
5. **Unit Test 및 Integration Test 작성**
    - 테스트코드 환경의 경우 캐시 비활성화 처리

## 3. 주안점

- **데이터 삭제시 softDelete 처리**
    - SQLRestriction, SQLDelete 어노테이션을 활용하여 삭제시 실제 데이터 삭제가 아닌 softDelete 처리 및 조회
- **적절한 정책이 필요한 부분은 임의로 처리**
  1. 코디 요청시 최저가 브랜드가 여러개인 경우
  2. 코디 요청시 카테고리의 최저가 상품이 여러개인 경우
- **로컬 캐싱 기능 추가**
    - (단일 instance, 요구사항과 같이 1개의 코디 조합만 응답하는 상태라고 가정)
    - (캐싱 적용에 의의를 두어 간단하게 스프링 내장 캐시기능을 사용하였기 때문에 TTL 기능이 없어 write through 방식으로 캐시 초기화)
  - **코디 조회 로직**
    - 상품, 브랜드의 CUD 발생시, 코디 관련 로컬캐시를 초기화.
  - **카테고리 조회 로직**
    - 카테고리는 변경이 없으므로, 캐시 초기화 로직은 없음.
- **예외/검증 로직 처리**
  - 요청한 데이터가 없을 경우 (코디에 필요한 카테고리 또는 브랜드 상품이 없을 경우 포함)
    - 에러 메시지 포맷을 통합하여 매직 리터럴 최소화
  - 유효성 검사 실패시
  - 정의되지 않은 Exception 발생시 에러 마스킹 처리
- **Swagger내 API 명세 설명 및 호출시 파라미터 예제 상세화**

## 4. 프로젝트 구조
```
.
├── README.md
├── build.gradle
└── src
    └── main
        ├── java
        │   └── com.mms
        │       ├── ProductApplication.java          # Spring Boot 메인 클래스 (main 메서드 포함)
        │       ├── controller                       # API 진입점(Controller) 패키지
        │       │   ├── BrandController.java         # 브랜드 등록/수정/삭제
        │       │   ├── OutfitController.java        # 최저가 코디, 최저가 브랜드 코디 조회
        │       │   ├── PriceController.java         # 카테고리별 최저가/최고가 상품 조회
        │       │   └── ProductController.java       # 상품 등록/수정/삭제
        │       ├── enums
        │       │   └── exception
        │       │       ├── ErrorFormat.java         # 예외 메시지 포맷을 구성하기 위한 인터페이스
        │       │       └── NotFoundErrorFormat.java # 데이터 없을 경우 에러 메시지 포맷
        │       ├── exception
        │       │   ├── ExceptionHandlers.java       # 전역 예외 처리
        │       │   └── NotFoundException.java       # 데이터 없음 예외 (RuntimeException)
        │       ├── model
        │       │   ├── Outfit.java                  # 코디(Outfit) 도메인 객체
        │       │   ├── dto
        │       │   │   └── CheapestBrandDto.java    # 브랜드 ID와 총가격을 담는 DTO
        │       │   ├── entity
        │       │   │   ├── BaseEntity.java          # 공통 등록/수정 시간 필드 (Auditing)
        │       │   │   ├── Brand.java               # 브랜드 엔티티
        │       │   │   ├── Category.java            # 카테고리 엔티티
        │       │   │   └── Product.java             # 상품 엔티티
        │       │   ├── request
        │       │   │   ├── BrandRequest.java        # 브랜드 등록/수정 시 Request Body
        │       │   │   └── ProductRequest.java      # 상품 등록/수정 시 Request Body
        │       │   └── response
        │       │       ├── brand
        │       │       │   └── BrandPriceResponse.java          # 상품과 브랜드 정보를 담은 응답
        │       │       ├── category
        │       │       │   └── CategoryPriceResponse.java       # 카테고리+상품 가격 응답
        │       │       ├── error
        │       │       │   ├── DefaultErrorDetailResponse.java  # 유효성 검사 등 필드별 에러 세부정보
        │       │       │   └── DefaultErrorResponse.java        # 공통 에러 포맷
        │       │       ├── outfit
        │       │       │   ├── CheapestBrandOutfitResponse.java # 최저가 브랜드 코디 전체 응답
        │       │       │   ├── CheapestBrandResponse.java       # (코디 브랜드, 카테고리별 가격, 총합)
        │       │       │   └── OutfitResponse.java              # (코디 상품 목록 + 총가격) 응답
        │       │       └── product
        │       │           ├── MinMaxProductResponse.java       # 카테고리별 최소/최대 가격 응답
        │       │           └── ProductResponse.java             # 단일 상품 응답
        │       ├── repository 
        │       │   ├── BrandRepository.java
        │       │   ├── CategoryRepository.java
        │       │   └── ProductRepository.java
        │       └── service 
        │           ├── BrandService.java       # 브랜드 추가/수정/삭제 + 최저가 브랜드 찾기
        │           ├── CategoryService.java    # 카테고리 조회 (by name/id)
        │           ├── OutfitService.java      # 최저가 코디 조회 로직
        │           └── ProductService.java     # 상품 CRUD 및 카테고리별 최저/최고가 조회
        └── resources
            ├── application.yml                 # Spring Boot 설정
            └── import.sql                      # 초기 데이터 insert (Brand, Category, Product)
```

