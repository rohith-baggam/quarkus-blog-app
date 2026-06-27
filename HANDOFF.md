# Quarkus Learning Handoff

## Who I Am
- 4 years Django/Python/PostgreSQL/Redis/Celery backend experience
- Tech Lead and Solution Architect
- Built a complete generic framework on top of DRF (CoreGenericUtils, CoreGenericPostAPIView, handlers, standardized responses)
- Strong database knowledge, basic Spring Boot knowledge
- Writes conventional, highly readable enterprise-grade code that a team can follow

## Your Role
You are my TEACHER, not my code writer.
- Ask me a question / let me guess BEFORE giving the answer
- Explain concepts first, then guide me to write code myself
- Always map Quarkus concepts to Django equivalents
- Give hints when I'm stuck, not complete solutions
- One concept at a time
- Never write complete files for me
- Show small code snippets only to demonstrate a concept

## Project Goal
Building a Blog Application in Quarkus to learn:
- Project setup and fundamentals
- SQLite + MySQL database integration
- Panache ORM (Hibernate)
- Flyway migrations
- MVC architecture (Resource → Service → Repository)
- JWT Authentication
- Role-based authorization
- File uploads
- Pagination, search, filtering
- WebSockets
- Docker + GraalVM native build

## Tech Stack
- Quarkus 3.36.3
- Java 21
- SQLite (dev), MySQL (prod)
- Panache ORM
- Flyway migrations
- SmallRye JWT
- WebSockets
- REST + Jackson

## Project Location
~/Desktop/stuff/java-learning/quarkus-learning/learn-by-doing/project/blog-app

## Current Folder Structure
```
src/main/java/com/
  blog/model/
    BlogPost.java
    BlogPostImage.java
  common/
    exception/
      ConflictException.java
      GlobalExceptionMapper.java
      ResourceNotFoundException.java
      UnauthorizedException.java
    model/
      BaseEntity.java
    response/
      ApiResponse.java
    security/
      CurrentUser.java        # @RequestScoped — holds logged-in User (request.user)
  learning/
    HealthResource.java
    HealthReturnResponse.java
  user/
    dto/
      request/
        UserLoginRequest.java
        UserRegistrationRequest.java
      UserLoginResponse.java
      UserResponse.java
    model/
      User.java
    repository/
      UserRepository.java
    resource/
      UserAuthResource.java
    service/
      UserService.java
  utils/
    JwtUtil.java

src/main/resources/
  application.properties
  privateKey.pem              # RS256 signing key (PKCS#8)
  publicKey.pem               # RS256 verify key
  db/migration/
    V1__create_initial_tables.sql
```

## Dependencies in pom.xml
- quarkus-rest
- quarkus-rest-jackson
- quarkus-hibernate-orm-panache
- quarkus-jdbc-mysql
- quarkus-flyway
- quarkus-smallrye-jwt
- quarkus-websockets
- quarkus-smallrye-openapi
- quarkus-hibernate-validator (for @NotBlank, @Email, @Valid)
- quarkus-elytron-security-common (for BcryptUtil password hashing)
- quarkus-jdbc-sqlite (quarkiverse)
- spotless-maven-plugin (code formatting, auto-remove unused imports)

## Progress Completed
✅ Step 1  - Project created with Quarkus CLI
✅ Step 2  - HealthResource working on /api/health
✅ Step 3  - Swagger UI working at /swagger-ui
✅ Step 4  - DTO pattern learned (HealthReturnResponse)
✅ Step 5  - Models: BaseEntity (@MappedSuperclass), User, BlogPost, BlogPostImage
✅ Step 6  - V1 Flyway migration SQL written (TIMESTAMP, snake_case columns)
✅ Step 7  - Server starts, SQLite DB migrated
✅ Step 8  - MVC pattern: Resource, Service, Repository for User
✅ Step 9  - User registration API working end-to-end
✅ Step 10 - User login API + JWT token (RS256, 7 days expiry)
✅ Step 11 - JwtUtil in com.utils (static utility)
✅ Step 12 - ApiResponse<T> wrapper with success() and error() factory methods
✅ Step 13 - GlobalExceptionMapper with ConflictException, UnauthorizedException, ResourceNotFoundException
✅ Step 14 - Spotless formatter + pre-commit hook (.git/hooks/pre-commit)

## ✅ Prior bugs resolved
- Login auth exceptions now use `UnauthorizedException` (not Conflict).
- `UserResponse` got a `UserResponse(User)` constructor → `new UserResponse(user)` is valid (chose constructor over `.from()` factory).
- ⚠️ Minor known gap: that constructor does NOT set `isActive`, so it always serializes `false`. Not yet fixed.

## Step 15 — Real RS256 keys + JWT config (DONE)
- Generated `src/main/resources/privateKey.pem` (PKCS#8) + `publicKey.pem` via openssl. (Previously relied on dev-mode auto-generated keys — ephemeral, dev-only.)
- Added to `application.properties`:
  ```
  smallrye.jwt.sign.key.location=privateKey.pem
  mp.jwt.verify.publickey.location=publicKey.pem
  mp.jwt.verify.issuer=blog-app
  ```
- Login confirmed: still returns 200 + RS256 token, now signed by our private key.
- Key learning: `.sign()` / `@Authenticated` read keys from config automatically (convention-over-config); `.properties` files have NO inline comments and NO quoted values.

## Step 16 — Authorization (IN PROGRESS)
Two-layer model:
- **Layer 1** `@Authenticated` — token valid (signature/expiry/issuer). Free via SmallRye, no DB. (DRF `IsAuthenticated`.)
- **Layer 2** custom DB check — user exists + `is_active`. Runs AFTER layer 1 (sub only trustworthy once token validated).

Done: `com.common.security.CurrentUser` — `@RequestScoped` CDI bean (`private User user` + getter/setter). The `request.user` holder.

## Step 17 — AuthenticationFilter (DONE)
`com.common.security.AuthenticationFilter` (`@Provider implements ContainerRequestFilter`) = Django middleware. Injects `JsonWebToken` / `UserRepository` / `CurrentUser`. Logic: if `jwt.getSubject()` null → return (public endpoint); else `UUID.fromString(sub)` → `userRepository.find("id", userId).firstResultOptional()` (NOT `findById` — repo is `PanacheRepository<User>` which hardcodes id=Long, mismatches UUID) → check `isActive` → `currentUser.setUser(user)`. Rejections throw `UnauthorizedException`.

## Step 18 — `GET /api/users/me` (@Authenticated) returns current user (DONE)

## Step 19 — Blog Post CREATE API (DONE, confirmed working)
`/api/blog-generic-api/create-post`. 5 bugs fixed: (1) don't `new` a CDI bean — call own method directly; (2) link image→post FK; (3) remove `@Null` (optional = no annotation); (4) null/empty guard on image list; (5) `@Valid` on resource param. Also: `BlogPostImageRepository` needed `@ApplicationScoped`.

## Step 20 — Blog Post LIST API, limit/offset paginated (DONE)
`GET /api/blog-generic-api` → `getBlogPaginatedList(BlogListParams)`. Pagination via Panache `findAll().range(offset, offset+limit-1).list()` (range indices zero-based + INCLUSIVE → the `-1`). Query params bundled via `@BeanParam BlogListParams` (fields `@QueryParam`+`@DefaultValue` from jakarta.ws.rs; limit=10, offset=0). NOTE: Quarkus has NO DRF FilterSet/SearchFilter — assemble from primitives (range, Sort, dynamic find).

## Step 21 — N+1 fix (DONE, pending boot fix)
`com.blog.utils.BlogPostListUtils.getBlogPostImageMap(blogList)`: ONE `find("post.id in ?1", postIds).list()` then groups into `Map<UUID,List<BlogPostImage>>`. Service pulls images via `imagesByPost.getOrDefault(post.id, new ArrayList<>())`. 2 queries total. Django `prefetch_related` pattern (NOT join — join breaks pagination on to-many).

## ⚠️ RESUME HERE
1. **Add `@ApplicationScoped` to `BlogPostListUtils`** — server was failing to boot ("no bean defining annotation"). Was being fixed at end of session. Confirm boot + 2-query SQL log.
2. Build remaining `BlogListParams` filters ONE BY ONE: search by title (`like`), sort by title (`Sort`), filter by authorId, filter by createdAt — all optional → dynamic query building.
3. Pagination metadata in response (total count / page info — DRF `count`/`results` shape).
4. Then `@RolesAllowed("user")` (groups claim already in token); then blog CRUD read/update/delete + ownership checks.

## Recurring lesson (hit 3×): anything you `@Inject` must be a CDI bean (class needs `@ApplicationScoped`); never `new` a bean. Panache queries use Java FIELD names, never db column names.

## Key Concepts Learned This Session
- MVC: Resource → Service → Repository — who does what
- DTO pattern: Request (input) + Response (output), never expose raw Entity
- ApiResponse<T>: standardized wrapper, static factory methods (success/error)
- GlobalExceptionMapper: @Provider, one place for all exceptions
- Custom exceptions: ConflictException, UnauthorizedException, ResourceNotFoundException
- @Transactional: required for writes, NOT required for reads
- Optional<T>: never null, use isEmpty()/orElseThrow()
- JWT: RS256 (asymmetric), generated with SmallRye Jwt.issuer().subject().sign()
- Static utility vs CDI bean: static = no @Inject possible, CDI = injectable

## Understanding Tracker
| Area | Level | Notes |
|---|---|---|
| Project setup & Quarkus CLI | ✅ Solid | |
| application.properties config | ✅ Solid | |
| Flyway migrations | ✅ Solid | Can't modify after run; delete DB in dev to reset |
| @Entity / @MappedSuperclass | ✅ Solid | |
| @JoinColumn / FK relationships | ✅ Solid | |
| SQL snake_case vs Java camelCase | ✅ Solid | @Column(name=) bridges the gap |
| DATETIME vs TIMESTAMP | ✅ Solid | Hibernate maps LocalDateTime → TIMESTAMP |
| MVC layer separation | ✅ Solid | Built register + login end-to-end |
| PanacheRepository | ✅ Solid | findByEmail, orElseThrow, persist |
| Jakarta Validation (@NotBlank etc) | ✅ Solid | Replaces is_valid() |
| @Transactional | ✅ Solid | WHY writes need it, reads don't |
| DTO pattern (Request/Response) | ✅ Solid | Wrote independently |
| Optional<T> | ✅ Solid | orElseThrow pattern |
| Static factory method (.from()) | ✅ Solid | vs constructor — Django @classmethod |
| ApiResponse<T> generics | ⚠️ Developing | Struggled with <T> on static methods |
| JWT generation (SmallRye) | ✅ Solid | RS256 vs HS256 tradeoffs understood |
| Custom exceptions + ExceptionMapper | ✅ Solid | @Provider pattern, exception routing |
| CDI / @Inject / @ApplicationScoped | ⚠️ Developing | Seen it, not fully comfortable yet |
| Static utility vs CDI bean | ⚠️ Developing | Understands difference |
| Spotless + pre-commit | ✅ Solid | Added to pom.xml, hook configured |
| RS256 keys + JWT verify config | ✅ Solid | privateKey/publicKey.pem + sign/verify/issuer props |
| CurrentUser @RequestScoped bean | ✅ Solid | request.user holder, getter/setter |
| @Authenticated / @RolesAllowed | ⚠️ In progress | concept learned, filter next |
| ContainerRequestFilter (middleware) | ❌ Next step | load User + is_active → setUser |
| Blog post CRUD | ❌ Not started | |
| Pagination / filtering | ❌ Not started | |
| File uploads | ❌ Not started | |
| WebSockets | ❌ Not started | |
| Docker + GraalVM native | ❌ Not started | |

## Key Django → Quarkus Mappings
| Django | Quarkus |
|---|---|
| models.py | Entity class with @Entity |
| models.Model | extends PanacheEntityBase |
| abstract = True in Meta | @MappedSuperclass |
| auto_now_add | @PrePersist |
| auto_now | @PreUpdate |
| ForeignKey | @ManyToOne + @JoinColumn |
| migrations/ | db/migration/ (Flyway SQL files) |
| makemigrations | Write SQL manually |
| migrate | flyway.migrate-at-start=true |
| settings.py | application.properties |
| requirements.txt | pom.xml |
| urls.py | @Path on Resource class |
| views.py / APIView | Resource class |
| serializers.py fields | DTO with @NotBlank/@Email etc |
| serializer.is_valid() | @Valid on Resource method param |
| to_representation() | UserResponse.from(user) |
| @classmethod | static factory method |
| Model.objects.filter() | PanacheRepository.find() |
| model.save() | repository.persist(model) |
| @transaction.atomic | @Transactional |
| raise ValidationError | throw new ConflictException |
| raise AuthenticationFailed | throw new UnauthorizedException |
| raise Http404 | throw new ResourceNotFoundException |
| EXCEPTION_HANDLER in settings | @Provider ExceptionMapper |
| success_response() utility | ApiResponse.success() |
| black + isort | Spotless Maven plugin |
| pre-commit hooks | .git/hooks/pre-commit |
